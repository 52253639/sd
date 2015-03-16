package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.ISHERevBillEntry;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IChequeDetailEntry;
import com.kingdee.eas.fdc.sellhouse.IChequeRevListEntry;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class ChequeDetailEntryControllerBean extends AbstractChequeDetailEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	ChequeDetailEntryInfo info = (ChequeDetailEntryInfo)model;
    	info.setStatus(ChequeStatusEnum.WrittenOff);
    	
    	IObjectPK pk = super._submit(ctx, model);
    	//更新被选择了的明细的发票号，开票金额
    	
    	ChequeRevListEntryCollection revColl = ChequeRevListEntryFactory.getLocalInstance(ctx)
    				.getChequeRevListEntryCollection("select *,chequeDetail.* where chequeDetail.id = '"+pk.toString()+"' and isSelect = 1 ");
    	ChequeTypeEnum cheQueType = info.getCheQueType();
    	if(ChequeTypeEnum.invoice.equals(cheQueType)){    		
    		updateRevBillEntry(ctx,revColl,true);
    	} else {//更新收款单上的收据编码
    		for(int i = 0 ; i <revColl.size() ;i++){
    			ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
    			if(chequeRevInfo.getRevBillEntry()==null) continue;
    			SelectorItemCollection UPSel = new SelectorItemCollection();
    			UPSel.add("receiptNumber");
    			SHERevBillEntryInfo  revBillInfo = SHERevBillEntryFactory.getLocalInstance(ctx)
    						.getSHERevBillEntryInfo("select receiptNumber where id = '"+chequeRevInfo.getRevBillEntry().getId()+"' ");
    			if(revBillInfo.getReceiptNumber()!=null) { 
    				revBillInfo.setReceiptNumber(revBillInfo.getReceiptNumber() + ";" + chequeRevInfo.getChequeDetail().getNumber());
    			}else{
    				revBillInfo.setReceiptNumber(chequeRevInfo.getChequeDetail().getNumber());
    			}
    			SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revBillInfo, UPSel);
    		}
    	}
    	return pk;
    }
    
    /**
     * 回收，清除收款明细上的票据编码 
     * @throws EASBizException 
     * 
     */
	protected void _reBack(Context ctx, String id) throws BOSException, EASBizException {
			doReback(ctx,id);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param id 为票据明细ID ChequeDetailEntryInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void doReback(Context ctx, String id) throws BOSException, EASBizException{
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("revListEntry.revBillEntry.*");
		selector.add("revListEntry.*");
		selector.add("cheQue.*");
		IChequeDetailEntry  entryFac = ChequeDetailEntryFactory.getLocalInstance(ctx);
		ChequeDetailEntryInfo info= entryFac.getChequeDetailEntryInfo(new ObjectUuidPK(id), selector);
		ChequeRevListEntryCollection revColl = info.getRevListEntry();
		if(ChequeTypeEnum.invoice.equals(info.getCheque().getChequeType())){
    		//先清除掉明细上的票据号 和开票金额，然后写上
			for(int i = 0 ; i <revColl.size() ;i++){
				ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
				SHERevBillEntryInfo  revBillInfo = chequeRevInfo.getRevBillEntry();
				SelectorItemCollection UPSel = new SelectorItemCollection();
				UPSel.add("invoiceNumber");
				UPSel.add("hasMakeInvoiceAmount");
				revBillInfo.setInvoiceNumber(null);
				revBillInfo.setHasMakeInvoiceAmount(null);
				SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revBillInfo, UPSel);
				
			}
			updateRevBillEntry(ctx,revColl,false);
    	} else {//回收收款单上的收据编码
    		for(int i = 0 ; i <revColl.size() ;i++){
    			ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
    			SelectorItemCollection UPSel = new SelectorItemCollection();
    			UPSel.add("receiptNumber");
    			SHERevBillEntryInfo  revBillInfo = chequeRevInfo.getRevBillEntry();
    			revBillInfo.setReceiptNumber(null);
    			SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(revBillInfo, UPSel);
    		}
    	}
		//更新票据状态
		info.setStatus(ChequeStatusEnum.Verified);
		info.setChequeCustomer(null);
		info.setAmount(null);
		info.setRoom(null);
		info.setCapitalization(null);
		info.setWrittenOffer(null);
		info.setWrittenOffTime(null);
		info.setDes(null);		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("status");
		sel.add("chequeCustomer");
		sel.add("amount");
		sel.add("room.*");
		sel.add("capitalization");
		sel.add("writtenOffer");
		sel.add("writtenOffTime");
		sel.add("des");
		
		ChequeRevListEntryFactory.getLocalInstance(ctx).delete("where chequeDetail.id='"+info.getId()+"' ");
		ChequeCustomerEntryFactory.getLocalInstance(ctx).delete("where cheque.id='"+info.getId()+"' ");
		entryFac.updatePartial(info, sel);
	}
	
//	private void batchreback(Context ctx, Set chequeSet) throws EASBizException, BOSException {
//		for(Iterator it = chequeSet.iterator();it.hasNext();){
//			String id = (String)it.next();
//			doReback(ctx, id);
//		}
//	}
	/**
	 * 更新每条明细的发票号 和已开票金额
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void updateRevBillEntry(Context ctx,ChequeRevListEntryCollection revColl,boolean isIncludeSelf) throws BOSException, EASBizException{
		String number = null;
		for(int i = 0 ; i <revColl.size() ;i++){
			ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
			if(!chequeRevInfo.isIsSelect()){
				continue;
			}
			//查数据库 看是否有相同的收款明细开过票了
			StringBuffer sb = new StringBuffer();
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("chequeDetail.number");
			sel.add("chequeAmount");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",chequeRevInfo.getId(),CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isSelect",new Boolean(true)));
			filter.getFilterItems().add(new FilterItemInfo("revBillEntry.id",chequeRevInfo.getRevBillEntry().getId()));
			filter.getFilterItems().add(new FilterItemInfo("chequeDetail.status",new Integer(ChequeStatusEnum.WRITTENOFF_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("chequeDetail.cheque.chequeType",ChequeTypeEnum.INVOICE_VALUE));
			//chequeDetail
			view.setFilter(filter);
			view.setSelector(sel);
			ChequeRevListEntryCollection targetColl = ChequeRevListEntryFactory.getLocalInstance(ctx).getChequeRevListEntryCollection(view);
			BigDecimal invoiceAmount = new BigDecimal("0");
			for(int j = 0 ; j <targetColl.size();j++){
				sb.append(";");
				sb.append(targetColl.get(j).getChequeDetail().getNumber());
				invoiceAmount = invoiceAmount.add(targetColl.get(j).getChequeAmount()!=null?targetColl.get(j).getChequeAmount():FDCHelper.ZERO);
			}
			
			if(isIncludeSelf) {
				sb.append(";"+chequeRevInfo.getChequeDetail().getNumber());
				invoiceAmount = invoiceAmount.add(chequeRevInfo.getChequeAmount());
			}
			number = sb.toString().replaceFirst(";", "");
			
			//更新收款单上的发票号 和 已开票金额
			SelectorItemCollection UPSel = new SelectorItemCollection();
			UPSel.add("invoiceNumber");
			UPSel.add("hasMakeInvoiceAmount");
			SHERevBillEntryInfo  revBillInfo = chequeRevInfo.getRevBillEntry();
			revBillInfo.setInvoiceNumber(number);
			revBillInfo.setHasMakeInvoiceAmount(invoiceAmount);
			SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(chequeRevInfo.getRevBillEntry(), UPSel);
		}
	}

	/**
	 * 取消开票
	 */
	protected void _cancelInvoice(Context ctx, String id) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("revListEntry.revBillEntry.*");
		selector.add("revListEntry.*");
		selector.add("cheQue.*");
		selector.add("*");
		IChequeDetailEntry  entryFac = ChequeDetailEntryFactory.getLocalInstance(ctx);
		ChequeDetailEntryInfo info= entryFac.getChequeDetailEntryInfo(new ObjectUuidPK(id), selector);
		ChequeRevListEntryCollection revColl = info.getRevListEntry();
		if(ChequeTypeEnum.invoice.equals(info.getCheque().getChequeType())){
    		//先清除掉明细上的票据号 和开票金额，然后写上
			for(int i = 0 ; i <revColl.size() ;i++){
				ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
				SHERevBillEntryInfo  revBillInfo = chequeRevInfo.getRevBillEntry();
				SelectorItemCollection UPSel = new SelectorItemCollection();
				UPSel.add("invoiceNumber");
				UPSel.add("hasMakeInvoiceAmount");
				revBillInfo.setInvoiceNumber(null);
				revBillInfo.setHasMakeInvoiceAmount(null);
				SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(chequeRevInfo.getRevBillEntry(), UPSel);
				
			}
			updateRevBillEntry(ctx,revColl,false);
    	} else {//回收收款单上的收据编码
    		for(int i = 0 ; i <revColl.size() ;i++){
    			ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
    			SelectorItemCollection UPSel = new SelectorItemCollection();
    			UPSel.add("receiptNumber");
    			SHERevBillEntryInfo  revBillInfo = chequeRevInfo.getRevBillEntry();
    			revBillInfo.setReceiptNumber(null);
    			SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(chequeRevInfo.getRevBillEntry(), UPSel);
    		}
    	}
		//更新票据信息
		info.setStatus(ChequeStatusEnum.Picked);
		SelectorItemCollection sel = new SelectorItemCollection();
		info.setWrittenOffer(null);
		info.setWrittenOffTime(null);
		info.getCustomerEntry().clear();
		info.setRoom(null);
		info.setChequeCustomer(null);
		info.setDes(null);
		info.setAmount(null);
		info.setCapitalization(null);
		
		info.getRevListEntry().clear();
		info.getCustomerEntry().clear();
		entryFac.update(new ObjectUuidPK(info.getId()), info);
	}
	
	/**
	 * 作废
	 */
	protected void _invalid(Context ctx, String id) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("revListEntry.revBillEntry.*");
		selector.add("revListEntry.*");
		selector.add("cheQue.*");
		IChequeDetailEntry  entryFac = ChequeDetailEntryFactory.getLocalInstance(ctx);
		ChequeDetailEntryInfo info= entryFac.getChequeDetailEntryInfo(new ObjectUuidPK(id), selector);
		ChequeRevListEntryCollection revColl = info.getRevListEntry();
		if(ChequeTypeEnum.invoice.equals(info.getCheque().getChequeType())){
    		//先清除掉明细上的票据号 和开票金额，然后写上
			for(int i = 0 ; i <revColl.size() ;i++){
				ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
				SHERevBillEntryInfo  revBillInfo = chequeRevInfo.getRevBillEntry();
				SelectorItemCollection UPSel = new SelectorItemCollection();
				UPSel.add("invoiceNumber");
				UPSel.add("hasMakeInvoiceAmount");
				revBillInfo.setInvoiceNumber(null);
				revBillInfo.setHasMakeInvoiceAmount(null);
				SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(chequeRevInfo.getRevBillEntry(), UPSel);
				
			}
			updateRevBillEntry(ctx,revColl,false);
    	} else {//回收收款单上的收据编码
    		for(int i = 0 ; i <revColl.size() ;i++){
    			ChequeRevListEntryInfo chequeRevInfo = revColl.get(i);
    			SelectorItemCollection UPSel = new SelectorItemCollection();
    			UPSel.add("receiptNumber");
    			SHERevBillEntryInfo  revBillInfo = chequeRevInfo.getRevBillEntry();
    			revBillInfo.setReceiptNumber(null);
    			SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(chequeRevInfo.getRevBillEntry(), UPSel);
    		}
    	}
		//更新票据状态
		info.setStatus(ChequeStatusEnum.Cancelled);
		info.setWrittenOffer(null);
		info.setWrittenOffTime(null);
		info.setRoom(null);
		info.setChequeCustomer(null);
		info.setDes(null);
		info.setAmount(null);
		info.setCapitalization(null);		
		info.getRevListEntry().clear();
		info.getCustomerEntry().clear();		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("status");
		sel.add("writtenOffer");
		sel.add("writtenOffTime");
		sel.add("room");
		sel.add("chequeCustomer");
		sel.add("des");
		sel.add("amount");
		sel.add("capitalization");
		entryFac.updatePartial(info, sel);
		ChequeRevListEntryFactory.getLocalInstance(ctx).delete("where chequeDetail.id='"+info.getId()+"' ");
		ChequeCustomerEntryFactory.getLocalInstance(ctx).delete("where cheque.id='"+info.getId()+"' ");
		
	}

	/**
	 * 换票，只支持统一票据管理
	 */
	protected void _changeInvoice(Context ctx, String oldId, String newId ,String desc)
			throws BOSException, EASBizException {
		IChequeDetailEntry  entryFac = ChequeDetailEntryFactory.getLocalInstance(ctx);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("writtenOffer.*");
		selector.add("room.*");
		selector.add("customerEntry.*");
		selector.add("customerEntry.customer.*");
		selector.add("revListEntry.*");
		selector.add("revListEntry.revBillEntry.*");
		ChequeDetailEntryInfo oldInfo= entryFac.getChequeDetailEntryInfo(new ObjectUuidPK(oldId), selector);
		ChequeDetailEntryInfo newInfo= entryFac.getChequeDetailEntryInfo(new ObjectUuidPK(newId), selector);
		
		//先回收旧票据，然后开票
		if(oldId!=null){//回收票据
			selector = new SelectorItemCollection();
			selector.add("revListEntry.revBillEntry.*");
			selector.add("revListEntry.*");
			selector.add("cheQue.*");			
			ChequeDetailEntryInfo info= entryFac.getChequeDetailEntryInfo(new ObjectUuidPK(oldId), selector);

			info.setStatus(ChequeStatusEnum.Verified);
			info.setChequeCustomer(null);
			info.setAmount(null);
			info.setCapitalization(null);
			info.setRoom(null);
			info.setWrittenOffer(null);
			info.setWrittenOffTime(null);
			info.setDes("换票到"+newInfo.getNumber());
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("status");
			sel.add("chequeCustomer");
			sel.add("amount");
			sel.add("capitalization");
			sel.add("writtenOffer");
			sel.add("writtenOffTime");
			sel.add("des");
			sel.add("room.*");
			ChequeRevListEntryFactory.getLocalInstance(ctx).delete("where chequeDetail.id='"+info.getId()+"' ");
			ChequeCustomerEntryFactory.getLocalInstance(ctx).delete("where cheque.id='"+info.getId()+"' ");
			entryFac.updatePartial(info, sel);
		}
		
		
		//开票
		newInfo.setAmount(oldInfo.getAmount());
		newInfo.setCapitalization(oldInfo.getCapitalization());
		newInfo.setChequeCustomer(oldInfo.getChequeCustomer());
		newInfo.getRevListEntry().clear();
		newInfo.setRoom(oldInfo.getRoom());
		newInfo.setDes(desc);
		newInfo.setWrittenOffer(oldInfo.getWrittenOffer());
		newInfo.setWrittenOffTime(oldInfo.getWrittenOffTime());
		newInfo.setChequeCustomer(oldInfo.getChequeCustomer());		
		for (int i = 0; i < oldInfo.getRevListEntry().size(); i++) {
			ChequeRevListEntryInfo oldEntryInfo = oldInfo.getRevListEntry().get(i);
			oldEntryInfo.setId(null);
			oldEntryInfo.setChequeDetail(newInfo);
			newInfo.getRevListEntry().add(oldEntryInfo);
		}
		newInfo.getCustomerEntry().clear();
		for (int i = 0; i < oldInfo.getCustomerEntry().size(); i++) {
			ChequeCustomerEntryInfo oldEntryInfo = oldInfo.getCustomerEntry().get(i);
			oldEntryInfo.setId(null);
			oldEntryInfo.setCheque(newInfo);
			newInfo.getCustomerEntry().add(oldEntryInfo);
		}
		
		//是不是有发票限额的问题 先不处理
		_submit(ctx, newInfo);
	}

	protected void _changeInvoice(Context ctx, ArrayList oldIds, String newId,
			String desc) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("writtenOffer.*");
		selector.add("room.*");
		IChequeDetailEntry  entryFac = ChequeDetailEntryFactory.getLocalInstance(ctx);
		ChequeDetailEntryInfo newInfo= entryFac.getChequeDetailEntryInfo(new ObjectUuidPK(newId), selector);
		
		//先回收旧票据，然后开票		
		RoomInfo oldRoomInfo = null;
		ChequeCustomerEntryCollection oldCustEntryColl = null;
		String oldCustIdsStr = null;
		String oldCustNamseStr = "";
		String oldCheCustStr = "";
		BigDecimal oldAllAmount = new BigDecimal("0");
		ChequeRevListEntryCollection oldRevEntryColl = new ChequeRevListEntryCollection();
		if(oldIds!=null && oldIds.size()>0){//回收票据
			String oldIdStr = "";
			for (int i = 0; i < oldIds.size(); i++) {
				oldIdStr += ",'"+oldIds.get(i)+"'";
			}
			ChequeDetailEntryCollection entryColl = entryFac.getChequeDetailEntryCollection("select *,customerEntry.* " +
								",customerEntry.customer.name,revListEntry.*,revListEntry.revBillEntry.*	" +
								"where id in ("+oldIdStr.substring(1)+")");
			
			//客户必须一致，房间必须一致
			for (int i = 0; i < entryColl.size(); i++) {
				ChequeDetailEntryInfo info = entryColl.get(i);
				
				if(i==0)
					oldRoomInfo = info.getRoom();
				else if( (info.getRoom()==null && oldRoomInfo!=null) || (info.getRoom()!=null && oldRoomInfo ==null)   
						|| (info.getRoom()!=null && oldRoomInfo!=null && !info.getRoom().getId().equals(oldRoomInfo.getId()))	) {
					throw new EASBizException(new NumericExceptionSubItem("100","多张旧票据的房间不一致，请检查！"));
				}

				String newCustIdsStr = "";
				for (int j = 0; j < info.getCustomerEntry().size(); j++) {
					newCustIdsStr += ","+info.getCustomerEntry().get(j).getCustomer().getId().toString();
					oldCustNamseStr += ","+info.getCustomerEntry().get(j).getCustomer().getName();
				}				
				if(!newCustIdsStr.equals("")) newCustIdsStr = newCustIdsStr.substring(1);
				if(oldCustEntryColl==null) { 
					oldCustEntryColl = info.getCustomerEntry();
					oldCustIdsStr = newCustIdsStr;
				}else if(!oldCustIdsStr.equals(newCustIdsStr)){
					throw new EASBizException(new NumericExceptionSubItem("100","多张旧票据的客户不一致，请检查！"));
				}
				
				if(info.getChequeCustomer()!=null && oldCheCustStr.indexOf(info.getChequeCustomer())<0)
					oldCheCustStr += ","+ info.getChequeCustomer();
				
				oldAllAmount = oldAllAmount.add(info.getAmount());
				oldRevEntryColl.addCollection(info.getRevListEntry());
				
				info.setStatus(ChequeStatusEnum.Verified);
				info.setChequeCustomer(null);
				info.setAmount(null);
				info.setCapitalization(null);
				info.setRoom(null);
				info.setWrittenOffer(null);
				info.setWrittenOffTime(null);
				info.setDes("换票到"+newInfo.getNumber());
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("status");
				sel.add("chequeCustomer");
				sel.add("amount");
				sel.add("capitalization");
				sel.add("writtenOffer");
				sel.add("writtenOffTime");
				sel.add("des");
				sel.add("room.*");
				ChequeRevListEntryFactory.getLocalInstance(ctx).delete("where chequeDetail.id='"+info.getId()+"' ");
				ChequeCustomerEntryFactory.getLocalInstance(ctx).delete("where cheque.id='"+info.getId()+"' ");
				entryFac.updatePartial(info, sel);				
			}			
		}
		
		
		//开票
		newInfo.setAmount(oldAllAmount);
     	CompanyOrgUnitInfo tempCompany = ContextUtil.getCurrentFIUnit(ctx);
		CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(ctx,tempCompany.getId().toString(),null,false);
		company.getBaseCurrency().setIsoCode("RMB");
		String capStr = FDCHelper.transCap(company.getBaseCurrency(), oldAllAmount);			
		newInfo.setCapitalization(capStr);
		newInfo.setChequeCustomer(oldCustNamseStr.equals("")?"":oldCustNamseStr.substring(1));
		newInfo.getRevListEntry().clear();
		newInfo.setRoom(oldRoomInfo);
		newInfo.setDes(desc);
		newInfo.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
		newInfo.setWrittenOffTime(new Timestamp(System.currentTimeMillis()));
		newInfo.setChequeCustomer(oldCheCustStr.equals("")?"":oldCheCustStr.substring(1));		
		newInfo.getCustomerEntry().clear();
		for (int i = 0; i < oldCustEntryColl.size(); i++) {
			ChequeCustomerEntryInfo oldEntryInfo = oldCustEntryColl.get(i);
			oldEntryInfo.setId(null);
			oldEntryInfo.setCheque(newInfo);
			newInfo.getCustomerEntry().add(oldEntryInfo);
		}
		for (int i = 0; i < oldRevEntryColl.size(); i++) {
			ChequeRevListEntryInfo oldEntryInfo = oldRevEntryColl.get(i);
			oldEntryInfo.setId(null);
			oldEntryInfo.setChequeDetail(newInfo);
			newInfo.getRevListEntry().add(oldEntryInfo);
		}
		
		//是不是有发票限额的问题 先不处理
		_submit(ctx, newInfo);
	}
	
	/**
	 * 在收款单，以及收付管理界面使用清除开票功能
	 * @param isRevBillId 为true 传入的是收款单ID，为false 传入的是收款明细ID
	 */
	protected boolean _clearInvoice(Context ctx, String id, boolean isRevBillId) throws BOSException, EASBizException {
		if(isRevBillId){//传进来为收款单
			//取得所有的收款明细
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id",id));
			view.setFilter(filter);
			SHERevBillEntryCollection billColl = SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryCollection(view);
			for(int i = 0 ; i < billColl.size(); i++){
				clearInvoiceFromRevBillEntry(ctx,billColl.get(i).getId().toString());
			}
			
		}else {//传过来是收款明细
			clearInvoiceFromRevBillEntry(ctx,id);
		}
		return true;
	}

	private void clearInvoiceFromRevBillEntry(Context ctx, String id) throws EASBizException, BOSException {
		ISHERevBillEntry  SHERevBillFaced = SHERevBillEntryFactory.getLocalInstance(ctx);
		IChequeRevListEntry chequeRevListEntryFaced = ChequeRevListEntryFactory.getLocalInstance(ctx);
		IChequeDetailEntry   chequeDetailEntryfaced  = ChequeDetailEntryFactory.getLocalInstance(ctx);
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("invoiceNumber");
		sel.add("hasMakeInvoiceAmount");
		SHERevBillEntryInfo SHERevInfo = SHERevBillFaced.getSHERevBillEntryInfo(new ObjectUuidPK(id));
		//清除发票
		SHERevInfo.setInvoiceNumber(null);
		SHERevInfo.setHasMakeInvoiceAmount(null);
		SHERevBillFaced.updatePartial(SHERevInfo, sel);
		//是否需要改变票据状态,
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("revBillEntry.id",id));
		view.setFilter(filter);
		ChequeRevListEntryCollection coll= chequeRevListEntryFaced.getChequeRevListEntryCollection(view);
		for(int i = 0 ; i < coll.size() ;i++){
			ChequeRevListEntryInfo info = coll.get(i);
			ChequeDetailEntryInfo chequeInfo = info.getChequeDetail();
			String prarentId = info.getChequeDetail().getId().toString();
			chequeRevListEntryFaced.delete(new ObjectUuidPK(info.getId()));
			filter.getFilterItems().clear();
			filter.getFilterItems().add(new FilterItemInfo("chequeDetail.id",prarentId));
			if(!chequeRevListEntryFaced.exists(filter)){//票据头上没有明细了，则把此票据设置为已领用
				chequeInfo.setStatus(ChequeStatusEnum.Picked);
				//更新票据状态
				chequeInfo.setChequeCustomer(null);
				chequeInfo.setRoom(null);
				chequeInfo.setWrittenOffer(null);
				chequeInfo.setWrittenOffTime(null);
				chequeInfo.setChequeCustomer(null);
				chequeInfo.setDes(null);
				chequeInfo.setAmount(null);
				chequeInfo.setCapitalization(null);
				sel.clear();
				sel.add("status");
				sel.add("chequeCustomer");
				sel.add("room");
				sel.add("writtenOffer");
				sel.add("writtenOffTime");
				sel.add("des");
				sel.add("amount");
				sel.add("capitalization");
				chequeDetailEntryfaced.updatePartial(chequeInfo, sel);
				ChequeCustomerEntryFactory.getLocalInstance(ctx).delete("where cheque.id='"+chequeInfo.getId()+"' ");
			}
		}
	}

	/**
	 * 批量在收款单，以及收付管理界面使用清除开票功能
	 * @param isRevBillId 为true 传入的是收款单ID，为false 传入的是收款明细ID
	 */
	protected boolean _clearInvoice(Context ctx, Object[] idArray, boolean isRevBill) throws BOSException,
			EASBizException {
		if(isRevBill){
			for(int i = 0 ; i < idArray.length ; i++){
				String id = (String)idArray[i];
				_clearInvoice(ctx,id,isRevBill);
			}
		} else {
			for(int i = 0 ; i < idArray.length ; i++){
				String id = (String)idArray[i];
				_clearInvoice(ctx,id,isRevBill);
			}
		}
		return true;
	}

	protected void _dealForSheRevBillCheque(Context ctx, SHERevBillInfo newSheRevBillInfo) throws BOSException,	EASBizException {
		if(newSheRevBillInfo==null) return;
		//若之前开过票的，则不允许在通过此途径开票
		for (int i = 0; i < newSheRevBillInfo.getEntrys().size(); i++) {
			SHERevBillEntryInfo billEntryInfo = newSheRevBillInfo.getEntrys().get(i);
			if(billEntryInfo.getInvoiceNumber()!=null || (billEntryInfo.getHasMakeInvoiceAmount()!=null && billEntryInfo.getHasMakeInvoiceAmount().compareTo(new BigDecimal("0"))>0)
				 || billEntryInfo.getReceiptNumber()!=null	) {
				return;
			}
		}
		
		ChequeDetailEntryInfo detailInfo = null; 
		//新开发票
		if(newSheRevBillInfo.getInvoice()!=null || (newSheRevBillInfo.getInvoiceNumber()!=null && !newSheRevBillInfo.getInvoiceNumber().trim().equals(""))) {
			detailInfo = createChequeDetailEntryInfoByRevBill(ctx,newSheRevBillInfo,ChequeTypeEnum.invoice);
			this._submit(ctx, detailInfo);
		}
		
		//新开收据
		if(newSheRevBillInfo.getReceipt()!=null || (newSheRevBillInfo.getReceiptNumber()!=null && !newSheRevBillInfo.getReceiptNumber().trim().equals(""))) {
			detailInfo = createChequeDetailEntryInfoByRevBill(ctx,newSheRevBillInfo,ChequeTypeEnum.receipt);
			this._submit(ctx, detailInfo);
		}
		
	}

	
	/**根据收款单信息生成票据信息*/
	private ChequeDetailEntryInfo createChequeDetailEntryInfoByRevBill(Context ctx, SHERevBillInfo newSheRevBillInfo,ChequeTypeEnum cheQueType) throws BOSException,	EASBizException {
		String cheDetailIdStr = null;
		if(cheQueType.equals(ChequeTypeEnum.invoice))
			if(newSheRevBillInfo.getInvoice()!=null) cheDetailIdStr = newSheRevBillInfo.getInvoice().getId().toString();
		else if(cheQueType.equals(ChequeTypeEnum.receipt))
			if(newSheRevBillInfo.getReceipt()!=null) cheDetailIdStr = newSheRevBillInfo.getReceipt().getId().toString();
		
		ChequeDetailEntryInfo detailInfo = null;
		if(cheDetailIdStr!=null) {
			detailInfo = ChequeDetailEntryFactory.getLocalInstance(ctx).getChequeDetailEntryInfo(
					"select * where id = '"+cheDetailIdStr+"' and status = "+ChequeStatusEnum.PICKED_VALUE+" ");
		}else{
			detailInfo = new ChequeDetailEntryInfo(); 
		}
		
		detailInfo.setWrittenOffer(ContextUtil.getCurrentUserInfo(ctx));
		detailInfo.setWrittenOffTime(new Timestamp(System.currentTimeMillis()));
		detailInfo.setRoom(newSheRevBillInfo.getRoom());
		detailInfo.setDes("由收付款单("+newSheRevBillInfo.getNumber()+")直接开票生成");
		detailInfo.setCheQueType(cheQueType);
		if(cheDetailIdStr==null) {
			if(cheQueType.equals(ChequeTypeEnum.invoice))
				detailInfo.setNumber(newSheRevBillInfo.getInvoiceNumber());
			else if(cheQueType.equals(ChequeTypeEnum.receipt))
				detailInfo.setNumber(newSheRevBillInfo.getReceiptNumber());
		}
			
		BigDecimal allAmount = new BigDecimal("0");
		String cheQueCustomStr = "";
		
		detailInfo.getCustomerEntry().clear();
		for (int i = 0; i < newSheRevBillInfo.getCustomerEntry().size(); i++) {
			SHERevCustEntryInfo revCustEntryInfo = newSheRevBillInfo.getCustomerEntry().get(i);
			if(revCustEntryInfo.getSheCustomer()==null) continue;
			ChequeCustomerEntryInfo cheQueCustEntryInfo = new ChequeCustomerEntryInfo();
			cheQueCustEntryInfo.setCheque(detailInfo);
			cheQueCustEntryInfo.setCustomer(revCustEntryInfo.getSheCustomer());
			detailInfo.getCustomerEntry().add(cheQueCustEntryInfo);
			cheQueCustomStr += "," + revCustEntryInfo.getSheCustomer().getName();
		}
		if(!cheQueCustomStr.equals("")) cheQueCustomStr = cheQueCustomStr.substring(1);
		
		detailInfo.getRevListEntry().clear();
		for (int i = 0; i < newSheRevBillInfo.getEntrys().size(); i++) {
			SHERevBillEntryInfo revEntryInfo = newSheRevBillInfo.getEntrys().get(i);
			ChequeRevListEntryInfo cqEntryInfo = new ChequeRevListEntryInfo();
			cqEntryInfo.setRevBillEntry(revEntryInfo);
			cqEntryInfo.setChequeDetail(detailInfo);
			cqEntryInfo.setInvoiceNum(detailInfo.getNumber());
			cqEntryInfo.setChequeAmount(revEntryInfo.getRevAmount());
			cqEntryInfo.setIsSelect(true);
			detailInfo.getRevListEntry().add(cqEntryInfo);
			allAmount = allAmount.add(revEntryInfo.getRevAmount());
		}			
		
     	CompanyOrgUnitInfo tempCompany = ContextUtil.getCurrentFIUnit(ctx);
		CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(ctx,tempCompany.getId().toString(),null,false);
		company.getBaseCurrency().setIsoCode("RMB");
		String capStr = FDCHelper.transCap(company.getBaseCurrency(), allAmount);			
		detailInfo.setAmount(allAmount);
		detailInfo.setCapitalization(capStr);
		detailInfo.setChequeCustomer(cheQueCustomStr);
		return detailInfo;
	}
	
	
	
	
	
	
//	protected void _reBack(Context ctx, IObjectCollection revBillEntryColl) throws BOSException, EASBizException {
//		//CHECK 如果一条明细对应的票据上所开明细不是同一张收款单则提示在票据管理入口回收
//		SHERevBillEntryCollection revColl = (SHERevBillEntryCollection)revBillEntryColl;
//		Set chequeSet = new HashSet();
//		for (int i =0 ; i <revColl.size();i ++){
//			SHERevBillEntryInfo info = revColl.get(i);
//			chequeSet.add(checkReventry(ctx,info));
//		}
//		batchreback(ctx,chequeSet);
//	}

	

//	private Set checkReventry(Context ctx,SHERevBillEntryInfo info) throws BOSException, EASBizException {
//		//必须要有parentID
////		String pId= info.getParent().getId().toString();
//		//查看这条明细所开的票据
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("revBillEntry.id",info.getId()));
//		view.setFilter(filter);
//		ChequeRevListEntryCollection chequeRevlist = ChequeRevListEntryFactory.getLocalInstance(ctx).getChequeRevListEntryCollection(view);
//		Set chequeRevIDSet = new HashSet();
//		for(int i = 0; i<chequeRevlist.size();i++){
//			String chequeId = chequeRevlist.get(i).getChequeDetail().getId().toString();
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("revListEntry.revBillEntry.*");
//			selector.add("revListEntry.revBillEntry.parent.number");
//			//票据号
//			ChequeDetailEntryInfo cheque = ChequeDetailEntryFactory.getLocalInstance(ctx).getChequeDetailEntryInfo(new ObjectUuidPK(chequeId), selector);
//			ChequeRevListEntryCollection ChequeRevListColl = cheque.getRevListEntry();
//			//不会为空
//			String revBillId = ChequeRevListColl.get(0).getRevBillEntry().getParent().getId().toString();
//			for(int j = 1 ;j <ChequeRevListColl.size();i++){
//				String revId =ChequeRevListColl.get(i).getRevBillEntry().getParent().getId().toString();
//				if(!revId.equals(revBillId)){
//					throw new EASBizException(new NumericExceptionSubItem("100", "收款明细"+ChequeRevListColl.get(i).getRevBillEntry().getParent().getNumber()+"所开票据上有其他收款单明细，请在票据管理界面再次回收此票据！" ));
//				}
//			}
//			chequeRevIDSet.add(cheque.getId().toString());
//		}
//		return chequeRevIDSet;
//	}

	
}