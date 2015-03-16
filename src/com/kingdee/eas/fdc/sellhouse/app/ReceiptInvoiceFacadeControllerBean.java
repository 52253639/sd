package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillRecordInfo;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;
import com.kingdee.eas.fm.nt.NTNumberFormat;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ReceiptInvoiceFacadeControllerBean extends
		AbstractReceiptInvoiceFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.ReceiptInvoiceFacadeControllerBean");

	/**
	 * 根据收款单或发票ID取得操作记录
	 */
	protected IObjectCollection _getRecord(Context ctx, BOSUuid id)
			throws BOSException {
		FDCReceiveBillRecordCollection col = null;

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("operatingUser.id");
		sic.add("operatingUser.name");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head", id));
		view.setFilter(filter);
		SorterItemCollection sort = new SorterItemCollection();
		SorterItemInfo sii = new SorterItemInfo("operatingDate");
		sii.setSortType(SortType.ASCEND);
		sort.add(sii);
		view.setSorter(sort);

		col = FDCReceiveBillRecordFactory.getLocalInstance(ctx)
				.getFDCReceiveBillRecordCollection(view);

		return col;
	}

	/**
	 * 开换收据并更新操作记录
	 * fdcPK 已作废
	 * 修改记录：由于已经进行换票操作，需要对先前的票据的一些信息进行修改
	 * 
	 */
	protected void _updateReceipt(Context ctx, String pk, String fdcPK,
			String receiptID, String receiptNum, String oldReceiptNum,
			String description) throws BOSException {

		// 更新收款单
		if (pk == null) { 
			return;
		}

		FDCReceivingBillInfo updateFDCRevInfo = new FDCReceivingBillInfo();
		updateFDCRevInfo.setId(BOSUuid.read(pk));
		if(receiptID!=null && receiptID.trim().length()>0) {
			ChequeInfo receptInfo = new ChequeInfo();
			receptInfo.setId(BOSUuid.read(receiptID));
			updateFDCRevInfo.setReceipt(receptInfo);
		}
		updateFDCRevInfo.setReceiptNumber(receiptNum);
		if ((receiptNum != null && !receiptNum.equals("")) || receiptID != null) 
			updateFDCRevInfo.setReceiptState(ReceiptStatusEnum.HasMake);
		else
			updateFDCRevInfo.setReceiptState(ReceiptStatusEnum.UnMake);	
		SelectorItemCollection updateFDCRevSels = new SelectorItemCollection();
		updateFDCRevSels.add("receipt");
		updateFDCRevSels.add("receiptNumber");
		updateFDCRevSels.add("receiptState");
		try{
			FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(updateFDCRevInfo, updateFDCRevSels);
			updateFDCRevInfo = FDCReceivingBillFactory.getLocalInstance(ctx)
						.getFDCReceivingBillInfo("select amount,Customer.name,bizDate,room.name,revBillType,revBizType " +
								"where id = '"+updateFDCRevInfo.getId()+"'");
		}catch(Exception e){e.printStackTrace();}
		
			
		// 填开收据
		if (receiptID != null) {
			ChequeInfo cheque = new ChequeInfo();
			cheque.setId(BOSUuid.read(receiptID));
			// 状态
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			// 填开人
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			// 填开日期
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			// 付款人
			if(updateFDCRevInfo.getCustomer()!=null)
				cheque.setPayer(updateFDCRevInfo.getCustomer().getName());

			// 付款日期
			if(updateFDCRevInfo.getBizDate()!=null)
				cheque.setPayTime(new Timestamp(updateFDCRevInfo.getBizDate().getTime()));
			
			if(updateFDCRevInfo.getRoom()!=null)
				cheque.setResume(updateFDCRevInfo.getRoom().getName()+","
							+ updateFDCRevInfo.getRevBillType().getAlias()+","+updateFDCRevInfo.getRevBizType().getAlias());
			
			BigDecimal invoiceAmount = updateFDCRevInfo.getAmount();
			if(invoiceAmount == null)	invoiceAmount = new BigDecimal("0");			
			Format u = NTNumberFormat.getInstance("rmb");
			cheque.setCapitalization(u.format(invoiceAmount));    		
			cheque.setAmount(invoiceAmount);
			cheque.setDescription(description);

			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("status");
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			sels.add("capitalization");
			sels.add("amount");
			
			sels.add("payer");
			sels.add("payTime");
			sels.add("resume");
			sels.add("Description");
			try {
				ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}

		// 增加操作记录
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// 单据头 && 类型
		record.setHead(pk);
		record.setHeadType(0);
		// 操作类型 && 内容
		if (oldReceiptNum == null || oldReceiptNum.equals("")) {
			record.setRecordType(RecordTypeEnum.MakeOutReceipt);
			record.setContent("开收据" + receiptNum);
		} else {
			record.setRecordType(RecordTypeEnum.ChangeReceipt);
			record.setContent("收据" + oldReceiptNum + "换为收据" + receiptNum);
		}
		// 操作人
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// 操作日期
		record.setOperatingDate(new Date());
		// 备注
		record.setDescription(description);

		// 新增记录
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 回收收据并更新操作记录
	 * fdcPK 作废了 
	 */
	protected void _retackReceipt(Context ctx, String pk, String fdcPK,
			String receiptNum, String description,Map paramMap) throws BOSException {
		// 更新收款单
		if (pk == null) {
			return;
		}
		
		
		
		
		
		
		if (paramMap!=null && paramMap.get("retakeReceivingBillidSet")!=null){
			Set idSet = (Set)paramMap.get("retakeReceivingBillidSet");
			if(idSet.size()==1){//仅仅有一个的时候 可以去修改票据管理中的状态；不为一个的时候不需要修改，因为另外一个还在使用这张票据
				//回收非统一票据管理的票据
				if(paramMap.get("receivingBillInfo")!=null && paramMap.get("receivingBillInfo") instanceof FDCReceivingBillInfo){
					FDCReceivingBillInfo receivInfo = (FDCReceivingBillInfo)paramMap.get("receivingBillInfo");
					if(receivInfo.getReceiptNumber()!=null && receivInfo.getRoom().getId()!=null ){
						
						// 回收票据收据
						StringBuffer sql = new StringBuffer();
						sql.append(" update T_SHE_Cheque set FWrittenOfferID = null,FWrittenOffTime = null,FStatus = 2 ");
						sql.append(" where FID in (select FReceiptID from T_BDC_FDCReceivingBill ");
						sql.append(" where FID = '"+pk+"' and FReceiptID is not null ) ");
						DbUtil.execute(ctx, sql.toString());
						
						
						//回收票据管理
						StringBuffer sqll = new StringBuffer();
						sqll.append(" update T_SHE_Invoice set FDate = null,FUserID = null ");
						sqll.append(" where FChequeID in (select FReceiptID from T_BDC_FDCReceivingBill ");
						sqll.append(" where FID = '"+pk+"' and FReceiptID is not null ) ");
						DbUtil.execute(ctx, sqll.toString());
						
						StringBuffer sqlByFNumber = new StringBuffer();
						sqlByFNumber.append(" update T_SHE_Invoice set FDate = null ");
						sqlByFNumber.append(" where fnumber = '" +receivInfo.getReceiptNumber() +"' ");
						sqlByFNumber.append(" and froomid ='"+String.valueOf(receivInfo.getRoom().getId())+"' ");
						DbUtil.execute(ctx, sqlByFNumber.toString());
						
						
						
					}
				}
			}
		} 
		
		
		
		
		FDCReceivingBillInfo updateFDCRevInfo = new FDCReceivingBillInfo();
		updateFDCRevInfo.setId(BOSUuid.read(pk));
		updateFDCRevInfo.setReceipt(null);
		updateFDCRevInfo.setReceiptNumber(null);
		updateFDCRevInfo.setReceiptState(ReceiptStatusEnum.HasRecover);	
		SelectorItemCollection updateFDCRevSels = new SelectorItemCollection();
		updateFDCRevSels.add("receipt");
		updateFDCRevSels.add("receiptNumber");
		updateFDCRevSels.add("receiptState");
		try{
			FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(updateFDCRevInfo, updateFDCRevSels);
		}catch(Exception e){e.printStackTrace();}
		
		

		// 增加操作记录
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// 单据头 && 类型
		record.setHead(pk);
		record.setHeadType(0);
		// 操作类型 && 内容
		record.setRecordType(RecordTypeEnum.RetakeReceipt);
		record.setContent("回收收据" + receiptNum);
		// 操作人
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// 操作日期
		record.setOperatingDate(new Date());
		// 备注
		record.setDescription(description);

		// 新增记录
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新操作记录
	 */
	protected void _updateRecord(Context ctx, int billType, String pk,
			RecordTypeEnum recordType, String content, String description)
			throws BOSException {
		// 增加操作记录
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// 单据头 && 类型
		record.setHead(pk);
		record.setHeadType(billType);
		// 操作类型 && 内容
		record.setRecordType(recordType);
		record.setContent(content);
		// 操作人
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// 操作日期
		record.setOperatingDate(new Date());
		// 备注
		record.setDescription(description);

		// 新增记录
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新发票并更新操作记录
	 * 销售发票 -- 换票  功能
	 */
	protected void _updateInvoice(Context ctx, String pk, String oldChequeNum,
			String newChequePK, String newChequeNum, String description)
			throws BOSException {
		// 更新发票
		if (pk == null) {
			return;
		}
		InvoiceInfo invoice = null;
		try {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("room.*");
			sels.add("cheque.*");
			sels.add("customer.*");
			sels.add("user.*");
			invoice = InvoiceFactory.getLocalInstance(ctx).getInvoiceInfo(new ObjectUuidPK(pk),sels);
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer sql = new StringBuffer();
		Object[] params = new Object[] { newChequeNum, newChequePK, pk };
		sql.append(" update T_SHE_Invoice set ");
		sql.append(" FNumber = ? , FChequeID = ? ");
		sql.append(" where fid = ? ");
//		DbUtil.execute(ctx, sql.toString(), params);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx,sql.toString());
		builder.addParams(params);
		builder.executeUpdate(ctx);

		// 填开发票
		if (newChequePK != null) {
			//chequePk不为空的情况下，应该是属于同于票据管理，这要就可通过oldChequeNum查询之前的票据的信息
			ChequeInfo cheque = new ChequeInfo();
			ChequeCollection oldChequeColl = ChequeFactory.getLocalInstance(ctx).getChequeCollection("where number = '"+oldChequeNum+"'");
			ChequeInfo oldCheQueInfo=null;
			if(oldChequeColl.size()==1) {
				oldCheQueInfo = oldChequeColl.get(0); 
				cheque = (ChequeInfo)oldCheQueInfo.clone();
				
				//清除旧的票据的信息
				try{
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("resume");
					sels.add("status");
					sels.add("payer");
					sels.add("amount");
					sels.add("description");
					sels.add("capitalization");    		
					sels.add("writtenOffer");
					sels.add("writtenOffTime");
					/**
					 * 清除付款日期
					 * update by renliang
					 * at 2010-10-7
					 */
					sels.add("payTime");
					oldCheQueInfo.setCapitalization(null);
					oldCheQueInfo.setPayer(null);
					oldCheQueInfo.setResume("");
					oldCheQueInfo.setStatus(ChequeStatusEnum.Booked);
					oldCheQueInfo.setAmount(null);
					oldCheQueInfo.setDescription("");
					oldCheQueInfo.setWrittenOffer(null);
					oldCheQueInfo.setWrittenOffTime(null);
					/**
					 * 清除付款日期
					 * update by renliang
					 * at 2010-10-7
					 */
					oldCheQueInfo.setPayTime(null);
					ChequeFactory.getLocalInstance(ctx).updatePartial(oldCheQueInfo, sels);
					
				}catch(Exception e){e.printStackTrace();}
			}
			
			cheque.setId(BOSUuid.read(newChequePK));
			// 状态
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			// 填开人
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			// 填开日期
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			cheque.setAmount(invoice.getAmount());
			if(invoice.getCustomer() != null){
				cheque.setPayer(invoice.getCustomer().getName());
			}
			cheque.setPayTime(invoice.getCreateTime());
			cheque.setCU(invoice.getCU());
			cheque.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			cheque.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
			//摘要
//			if(oldCheQueInfo!=null){
//				cheque.setResume(oldCheQueInfo.getResume());
//			}
			Format u = NTNumberFormat.getInstance("rmb");
			cheque.setCapitalization(u.format(invoice.getAmount()));  
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("status");
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			sels.add("CU");
			sels.add("payer");
			sels.add("payTime");
			sels.add("amount");
			sels.add("capitalization");
			sels.add("resume");
			
			try {
				ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}

		// 增加操作记录
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// 单据头 && 类型
		record.setHead(pk);
		record.setHeadType(1);
		// 操作类型 && 内容
		if (oldChequeNum == null || oldChequeNum.equals("")) {
			record.setRecordType(RecordTypeEnum.MakeOutInvoice);
			record.setContent("开票据" + newChequeNum);
		} else {
			record.setRecordType(RecordTypeEnum.ChangeInvoice);
			record.setContent("票据" + oldChequeNum + "换为票据" + newChequeNum);
		}
		// 操作人
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// 操作日期
		record.setOperatingDate(new Date());
		// 备注
		record.setDescription(description);

		// 新增记录
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		//换收据后 收款单上需要替换掉
		try {
			FDCReceivingBillCollection revcoll = null;
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection coll = new SelectorItemCollection();
			FilterInfo filter = new FilterInfo();
//			coll.add("*");
//			coll.add("");
			view.setSelector(coll);
			view.setFilter(filter);
			
			if(ChequeTypeEnum.receipt.equals(invoice.getChequeType())&&invoice!=null){//只处理收据，发票不用处理
				//用旧的收据去找收款单
			 if (newChequePK!=null&&invoice.getCheque()!=null){//启用统一票据管理
					ChequeInfo cheque = ChequeFactory.getLocalInstance(ctx).getChequeInfo(new ObjectUuidPK(newChequePK));
					filter.getFilterItems().add(new FilterItemInfo("receipt.id",invoice.getCheque().getId()));
					revcoll = FDCReceivingBillFactory.getLocalInstance(ctx).getFDCReceivingBillCollection(view);
					SelectorItemCollection coll1 = new SelectorItemCollection();
					coll1.add("receipt.id");
					for(int i = 0;i<revcoll.size();i++){
						FDCReceivingBillInfo revinfo =revcoll.get(i);
						revinfo.setReceipt(cheque);
						FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(revinfo, coll1);
					}
			}else {//没有启用统一票据管理
						filter.getFilterItems().add(new FilterItemInfo("receiptNumber",invoice.getNumber()));
						revcoll = FDCReceivingBillFactory.getLocalInstance(ctx).getFDCReceivingBillCollection(view);
						SelectorItemCollection coll1 = new SelectorItemCollection();
						coll1.add("receiptNumber");
						for(int i = 0;i<revcoll.size();i++){
							FDCReceivingBillInfo revinfo =revcoll.get(i);
							revinfo.setReceiptNumber(newChequeNum);
							FDCReceivingBillFactory.getLocalInstance(ctx).updatePartial(revinfo, coll1);
						}
					}
					
					
				}
				
			
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 通过更名单取得票据信息
	 */
	protected List _getChequebyChgID(Context ctx, String chgID)
			throws BOSException {
		List lst = new ArrayList();
		Map data;

		// 取收据数据
		StringBuffer sql = new StringBuffer();
		sql.append(" select DISTINCT fr.Fid as id , '收据' as type , ");
		sql.append(" fr.FReceiptNumber as oldNumber , fr.FAmount as amount ");
		sql.append(" from T_SHE_PChCustomer as pcc ");
		sql.append(" left join T_SHE_Purchase as pc on pc.FID = pcc.FPurchaseID ");
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" where pcc.FID = '").append(chgID).append("' ");
		sql.append(" and fr.FReceiptState = '"+ReceiptStatusEnum.HASMAKE_VALUE + "'");
		
		// 取发票数据
		sql.append(" union all ");
		sql.append(" select DISTINCT iv.Fid as id , '发票' as type , ");
		sql.append(" iv.FNumber as oldNumber , iv.FAmount as amount ");
		sql.append(" from T_SHE_PChCustomer as pcc ");
		sql.append(" left join T_SHE_Purchase as pc on pc.FID = pcc.FPurchaseID ");
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
		sql.append(" where iv.Fid is not null ");
		sql.append(" and pcc.FID = '").append(chgID).append("'");

		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		try {
			String id;
			String type;
			String number;
			BigDecimal amount;
			while (rs.next()) {
				id = rs.getString("id");
				type = rs.getString("type");
				number = rs.getString("oldNumber");
				amount = rs.getBigDecimal("amount");

				data = new HashMap();
				data.put("id", id);
				data.put("type", type);
				data.put("number", number);
				data.put("amount", amount);
				lst.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * 更名单更新票据
	 */
	protected void _updateChequeForChg(Context ctx, List param)
			throws BOSException {
		if (param != null && param.size() > 0) {
			Map row;
			String chgPK;
			String id;
			String type;
			String oldNum;
			Object newNum;
			String description;
			for (int i = 0; i < param.size(); i++) {
				row = (Map) param.get(i);
				chgPK = (String) row.get("chgPK");
				id = (String) row.get("id");
				type = (String) row.get("type");
				oldNum = (String) row.get("oldNum");
				newNum = row.get("newNum");
				description = (String) row.get("description");
				if (type.equals("收据")) {
					if (newNum instanceof String) {
						_updateReceipt(ctx, id, null, null, (String) newNum, oldNum, description);
					} else if (newNum instanceof ChequeInfo) {
						ChequeInfo info = (ChequeInfo) newNum;
						_updateReceipt(ctx, id, null, info.getId().toString(),	info.getNumber(), oldNum, description);
						writtenOffCheque(ctx, info.getId().toString());
					}
				} else if (type.equals("发票")) {
					if (newNum instanceof String) {
						_updateInvoice(ctx, id, oldNum, null, (String) newNum,	description);
					} else if (newNum instanceof ChequeInfo) {
						ChequeInfo info = (ChequeInfo) newNum;
						_updateInvoice(ctx, id, oldNum,	info.getId().toString(), info.getNumber(),
								description);
						writtenOffCheque(ctx, info.getId().toString());
					}
					changeInvoiceCustomer(ctx, chgPK, id);
				}
			}
		}
	}

	/**
	 * 通过退房单或换房取得票据信息
	 */
	protected List _getChequeForRoom(Context ctx, String sourceID,
			String sourceType) throws BOSException {
		List lst = new ArrayList();
		Map data;

		// 取收据数据
		StringBuffer sql = new StringBuffer();
		sql.append(" select DISTINCT fr.Fid as id , '收据' as type , ");
		sql.append(" fr.FReceiptNumber as number , fr.FAmount as amount ");
		if (sourceType.equals("退房")) {
			sql.append(" from T_SHE_QuitRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FPurchaseID ");
		} else if (sourceType.equals("换房")) {
			sql.append(" from T_SHE_ChangeRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FOldPurchaseID ");
		}
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" where cr.FID = '").append(sourceID).append("' ");
		sql.append(" and fr.FReceiptState = '1'");
		sql.append(" and fr.Fid is not null ");
		// 取发票数据
		sql.append(" union all ");
		sql.append(" select DISTINCT iv.Fid as id , '发票' as type , ");
		sql.append(" iv.FNumber as number , iv.FAmount as amount ");
		if (sourceType.equals("退房")) {
			sql.append(" from T_SHE_QuitRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FPurchaseID ");
		} else if (sourceType.equals("换房")) {
			sql.append(" from T_SHE_ChangeRoom as cr ");
			sql.append(" left join T_SHE_Purchase as pc on pc.FID = cr.FOldPurchaseID ");
		}
		sql.append(" left join T_BDC_FDCReceivingBill as fr on fr.FPurchaseObjID = pc.FID ");
		sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
		sql.append(" where iv.Fid is not null ");
		sql.append(" and iv.FNumber is not null ");
		sql.append(" and cr.FID = '").append(sourceID).append("' ");

		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		try {
			String id;
			String type;
			String number;
			BigDecimal amount;
			while (rs.next()) {
				id = rs.getString("id");
				type = rs.getString("type");
				number = rs.getString("number");
				amount = rs.getBigDecimal("amount");

				// 由于发票没有状态控制，判断如果最新操作记录是回收发票类型，则掠过该记录
				if ("发票".equals(type)) {
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("id");
					sic.add("head");
					sic.add("recordType");
					view.setSelector(sic);
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("head", id));
					view.setFilter(filter);
					SorterItemInfo sort = new SorterItemInfo("operatingDate");
					sort.setSortType(SortType.DESCEND);
					view.getSorter().add(sort);
					FDCReceiveBillRecordCollection rec = FDCReceiveBillRecordFactory
							.getLocalInstance(ctx)
							.getFDCReceiveBillRecordCollection(view);
					if (rec != null && rec.size() > 0) {
						FDCReceiveBillRecordInfo info = rec.get(0);
						if (info != null
								&& info.getRecordType().getValue().equals(
										RecordTypeEnum.RETAKEINVOICE_VALUE)) {
							continue;
						}
					}
				}

				data = new HashMap();
				data.put("id", id);
				data.put("type", type);
				data.put("number", number);
				data.put("amount", amount);
				lst.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * 退房或换房时回收票据
	 */
	protected void _retakeChequeForRoom(Context ctx, List param)
			throws BOSException {
		if (param != null && param.size() > 0) {
			Map row;
			String sourceID;
			String id;
			String type;
			String number;
			String description;
			for (int i = 0; i < param.size(); i++) {
				row = (Map) param.get(i);
				sourceID = (String) row.get("sourceID");
				id = (String) row.get("id");
				type = (String) row.get("type");
				number = (String) row.get("number");
				description = (String) row.get("description");
				if (type.equals("收据")) {
					_retackReceipt(ctx, id, null, number, description,null);
				} else if (type.equals("发票")) {
					_retackInvoice(ctx, id, number, description);
				}
			}
		}
	}

	/**
	 * 回收发票 暂时先不对发票做操作，只增加操作记录。更新发票等下个版本的需求 需求变为回收发票不处理收款单的收据状态
	 */
	protected void _retackInvoice(Context ctx, String pk, String number,
			String description) throws BOSException {
		// 更新发票
		if (pk == null) {
			return;
		}
		StringBuffer sql = new StringBuffer();
		// Object[] params = new Object[] { pk };
		// sql.append(" update T_SHE_Invoice set ");
		// sql.append(" FNumber = null ");
		// sql.append(" where fid = ? ");
		// DbUtil.execute(ctx, sql.toString(), params);

		// 更新收款单，删除所有引用了此发票的地方
		sql = new StringBuffer();
		sql.append(" update T_BDC_FDCReceivingBill set FInvoiceID = null");
		// sql
		// .append(" ,FReceiptState = (CASE FReceiptNumber WHEN null THEN 0 ELSE 1 END) ");
		sql.append(" where FInvoiceID = '").append(pk).append("' ");
		DbUtil.execute(ctx, sql.toString());

		// 回收票据发票
		sql = new StringBuffer();
		sql
				.append(" update T_SHE_Cheque set FWrittenOfferID = null,FWrittenOffTime = null,FStatus = 2 ");
		sql.append(" where FID in (select FChequeID from T_SHE_Invoice ");
		sql.append(" where FID = '").append(pk).append(
				"' and FChequeID is not null ) ");
		DbUtil.execute(ctx, sql.toString());

		// 增加操作记录
		FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
		// 单据头 && 类型
		record.setHead(pk);
		record.setHeadType(1);
		// 操作类型 && 内容
		record.setRecordType(RecordTypeEnum.RetakeInvoice);
		record.setContent("回收发票" + number);
		// 操作人
		record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
		// 操作日期
		record.setOperatingDate(new Date());
		// 备注
		record.setDescription(description);

		// 新增记录
		try {
			FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除收款单发票 
	 */
	protected void _clearInvoice(Context ctx, String pk, boolean isClearAll)
			throws BOSException {
		if (pk == null) {
			return;
		}
		StringBuffer sql = new StringBuffer();
		if (isClearAll) {
			sql.append(" select iv.FNumber as number , fr.FID as pk ");
			sql.append(" from T_BDC_FDCReceivingBill as fr ");
			sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
			sql.append(" where fr.FInvoiceID = '").append(pk).append("' ");
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			try {
				String number;
				String id;
				while (rs.next()) {
					number = rs.getString("number");
					id = rs.getString("pk");
					_updateRecord(ctx, 0, id, RecordTypeEnum.ClearInvoice,"清除发票" + number, null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			sql.append(" select iv.FNumber as number , fr.FID as pk ");
			sql.append(" from T_BDC_FDCReceivingBill as fr ");
			sql.append(" left join T_SHE_Invoice as iv on iv.FID = fr.FInvoiceID ");
			sql.append(" where fr.FID = '").append(pk).append("' ");
			IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
			try {
				String number;
				String id;
				while (rs.next()) {
					number = rs.getString("number");
					id = rs.getString("pk");
					_updateRecord(ctx, 0, id, RecordTypeEnum.ClearInvoice,	"清除发票" + number, null);
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 根据房间ID取得最新认购单的主客户
	 */
	protected IObjectValue _getCustomerByRoom(Context ctx, String roomID)
			throws BOSException {
		if (roomID == null) {
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select Cust.fid id,Cust.FNumber number,Cust.FName_l2 name ");
		sql.append(" from t_bdc_FDCReceivingBill fdcBill ");
		sql.append(" inner join t_bdc_RevFDCCustomerEntry fdcCust on fdcbill.fid = fdcCust.fheadId ");
		sql.append(" inner join T_SHE_FDCCustomer as Cust on fdcCust.FFdcCustomerID = Cust.Fid ");
		sql.append(" where fdcbill.froomId = '"+roomID+"' ");
		sql.append(" and fdcbill.fbillStatus != '"+RevBillStatusEnum.SAVE_VALUE+"' and fdcbill.frevBillType = '"+RevBillTypeEnum.GATHERING_VALUE+"' ");
		sql.append(" order by fdcBill.fcreateTime desc,fdcCust.fseq ");		
		
		IRowSet rs = DbUtil.executeQuery(ctx, sql.toString());
		try {
			if(rs.next()) {
				String id = rs.getString("id");
				String number = rs.getString("number");
				String name = rs.getString("name");
				FDCCustomerInfo customer = new FDCCustomerInfo();
				customer.setId(BOSUuid.read(id));
				customer.setNumber(number);
				customer.setName(name);
				return customer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据更名单更新发票客户为新客户信息中的主客户
	 * 
	 * @param chgID
	 */
	private void changeInvoiceCustomer(Context ctx, String chgID, String invID) {
		if (chgID != null) {
			StringBuffer sql = new StringBuffer();
			sql.append(" select fc.fid as customerID ");
			sql.append(" from T_SHE_pchCustomer as pc ");
			sql
					.append(" left join T_SHE_PChCusomerEntry as pe on pe.FHeadID = pc.FID ");
			sql
					.append(" left join T_SHE_FDCCustomer as fc on fc.FID = pe.FCustomerID ");
			sql.append(" where pc.FID = '").append(chgID).append("' ");
			sql.append(" and pe.FSeq = 0 ");
			IRowSet rs;
			try {
				rs = DbUtil.executeQuery(ctx, sql.toString());
				while (rs.next()) {
					String customerID = rs.getString("customerID");
					sql = new StringBuffer();
					sql.append(" update T_SHE_Invoice set FCustomerID = '")
							.append(customerID).append("' where Fid = '")
							.append(invID).append("' ");
					DbUtil.execute(ctx, sql.toString());
				}
			} catch (BOSException e1) {
				e1.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 填开票据
	 * 
	 * @param ctx
	 * @param chequeID
	 */
	private void writtenOffCheque(Context ctx, String chequeID) {
		if (chequeID != null) {
			ChequeInfo cheque = new ChequeInfo();
			cheque.setId(BOSUuid.read(chequeID));
			// 状态
			cheque.setStatus(ChequeStatusEnum.WrittenOff);
			// 填开人
			cheque.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
			// 填开日期
			cheque.setWrittenOffTime(new Timestamp(new Date().getTime()));
			// 付款人

			// 付款日期

			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("status");
			sels.add("writtenOffer");
			sels.add("writtenOffTime");
			try {
				ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 描述：批量回收收据
	 * @author pu_zhang  2010-11-15
	 * @param paramMap<key,value>  key：retakeReceivingBillidSet, receivingBillInfo   value：Set，FDCReceivingBillInfo
	 */
	protected void _retackReceiptBatch(Context ctx, Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException {
		
		// 更新收款单
		if (paramMap==null) {
			return;
		}
		if(paramMap.get("retakeReceivingBillidSet")==null){
			return;
		}
		Set idSet = (Set)paramMap.get("retakeReceivingBillidSet");
		Object[]  idArray = idSet.toArray();
		String inStr = getInString(idArray);
		// 回收票据收据
		StringBuffer sql = new StringBuffer();
		sql.append(" update T_SHE_Cheque set FWrittenOfferID = null,FWrittenOffTime = null,FStatus = 2 ");
		sql.append(" where FID in (select FReceiptID from T_BDC_FDCReceivingBill ");
		sql.append(" where FReceiptID is not null ");
		sql.append(" and FID in ( ");
		if("".equals(inStr)){
			sql.append("'null'");
		}else{
			sql.append(inStr);
		}
		sql.append(" ) ");
		sql.append(" ) ");
		DbUtil.execute(ctx, sql.toString());
		
		//回收票据管理
		StringBuffer sqll = new StringBuffer();
		sqll.append(" update T_SHE_Invoice set FDate = null,FUserID = null ");
		sqll.append(" where FChequeID in (select FReceiptID from T_BDC_FDCReceivingBill ");
		sqll.append(" where FReceiptID is not null ");
		sqll.append(" and FID in ( ");
		if("".equals(inStr)){
			sqll.append("'null'");
		}else{
			sqll.append(inStr);
		}
		sqll.append(" ) ");
		sqll.append(" ) ");
		DbUtil.execute(ctx, sqll.toString());
		
		//回收非统一票据管理的票据
		if(paramMap.get("receivingBillInfo")!=null && paramMap.get("receivingBillInfo") instanceof FDCReceivingBillInfo){
			FDCReceivingBillInfo receivInfo = (FDCReceivingBillInfo)paramMap.get("receivingBillInfo");
			if(receivInfo.getReceiptNumber()!=null && receivInfo.getRoom().getId()!=null ){
				StringBuffer sqlByFNumber = new StringBuffer();
				sqlByFNumber.append(" update T_SHE_Invoice set FDate = null ");
				sqlByFNumber.append(" where fnumber = '" +receivInfo.getReceiptNumber() +"' ");
				sqlByFNumber.append(" and froomid ='"+String.valueOf(receivInfo.getRoom().getId())+"' ");
				DbUtil.execute(ctx, sqlByFNumber.toString());
			}
		}
		
		//回收票据管理
		StringBuffer sqlRevInfo = new StringBuffer();
		sqlRevInfo.append(" update t_bdc_FDCReceivingBill set freceiptid = null,freceiptNumber=null, freceiptState='2' ");
		sqlRevInfo.append(" where 1=1 ");
		sqlRevInfo.append(" and FID in ( ");
		if("".equals(inStr)){
			sqlRevInfo.append("'null'");
		}else{
			sqlRevInfo.append(inStr);
		}
		sqlRevInfo.append(" ) ");
		DbUtil.execute(ctx,sqlRevInfo.toString());
		
		try {
			// 增加操作记录
			FDCReceiveBillRecordInfo record = new FDCReceiveBillRecordInfo();
			// 单据头 && 类型
			record.setHeadType(0);
			// 操作类型 && 内容
			record.setRecordType(RecordTypeEnum.RetakeReceipt);
			record.setContent("回收收据" + receiptNum);
			// 操作人
			record.setOperatingUser(ContextUtil.getCurrentUserInfo(ctx));
			// 操作日期
			record.setOperatingDate(new Date());
			// 备注
			record.setDescription(description);
			// 新增记录
			for(int i = 0; i < idArray.length; i++){
				record.setHead(idArray[i].toString());
				FDCReceiveBillRecordFactory.getLocalInstance(ctx).addnew(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getInString(Object[] array) {
		StringBuffer sb = new StringBuffer("");
		
		for(int i = 0; i < array.length; i++){
			sb.append("'"+array[i]+"',");
		}
		String  reStr= sb.toString();
		if(!"".equals(reStr)){
			reStr = reStr.substring(0,reStr.lastIndexOf(","));
		}
		return reStr;
	}
}