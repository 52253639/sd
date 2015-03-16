package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jxl.write.DateFormats;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.propertymgmt.DepositBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositBillInfo;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillCollection;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillFactory;
import com.kingdee.eas.fdc.propertymgmt.DepositWithdrawBillInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillEntryFactory;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fm.nt.NTNumberFormat;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.scm.sm.pur.util.DateHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class FDCReceiveBillControllerBean extends AbstractFDCReceiveBillControllerBean
{
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.FDCReceiveBillControllerBean");

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException
	{
		
		return super._submit(ctx, model);
		
	}
	
	protected void _updatePrintCount(Context ctx, String billID, int printCount)
			throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		String strSql="";
		StringBuffer strBuffer=new StringBuffer();
		strBuffer.append("update T_SHE_FDCReceiveBill set FPrintCount="+printCount+" where fid in"+"\n");
		strBuffer.append("(select FFdcReceiveBillID from  T_CAS_ReceivingBill where fid='"+billID+"')");
		
		strSql=strBuffer.toString();
		try{
			DbUtil.execute(ctx, strSql);
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}
		
		
	}

	protected int _getPrintCount(Context ctx, String billID)
			throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		String strSql="";
		int printCount=0;
		StringBuffer strBuffer=new StringBuffer();
		
		strBuffer.append("select distinct isnull(t1.FPrintCount,0) FPrintCount from T_SHE_FDCReceiveBill t1"+"\n");
		strBuffer.append("inner join T_CAS_ReceivingBill t2 on"+"\n");  
		strBuffer.append("t2.FFdcReceiveBillID =t1.FID"+"\n");
		strBuffer.append("where t2.fid='"+billID+"'");
			
		
		strSql=strBuffer.toString();
		IRowSet rowSet=null;
		try{
			rowSet=DbUtil.executeQuery(ctx, strSql);
			while(rowSet.next()){
				printCount=rowSet.getInt("FPrintCount");
			}
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}
		return printCount;
	}
	/**
	 * @author jetdai
	 * 更新临时应收单
	 * */
	private void updateTempBillByID(Context ctx,String tempID,BigDecimal arAmount,BigDecimal payAmount) throws Exception{
		EntityViewInfo env=new EntityViewInfo();
		FilterInfo filterInfo=new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", tempID,CompareType.INCLUDE));
		filterInfo.setMaskString("#0");
		env.setFilter(filterInfo);
		
		PPMTemporaryInfo ppmInfo= PPMTemporaryFactory.getLocalInstance(ctx).getPPMTemporaryInfo(new ObjectUuidPK(tempID));
		//更新应收，已付。
		ppmInfo.setArAmout(arAmount);
		ppmInfo.setPayedAmount(payAmount);
		PPMTemporaryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(tempID), ppmInfo);
	}

	//jetdai 2009-12-11
	//在单据上点提交时，生成临时应收单
	protected void _addTemporaBill(Context ctx, ArrayList listBills,
			IObjectValue billInfo) throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		if(listBills.size()>0){
			//判断本张零星收款单是否已生成了临时应收的新单据，如果有，只需更新已付金额就行了
			
			
			ReceivingBillInfo billInfos = (ReceivingBillInfo) billInfo;
			FDCReceiveBillInfo fdcRecBillInfo= billInfos.getFdcReceiveBill();
			String billNumber="",tempID="";
			Map map=null;
			
			//把收款单 更新成 零星 收款
			
			//融合零星收款后，零星收款也为收款类型
			//fdcRecBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.);
			//FDCReceiveBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(fdcRecBillInfo.getId().toString()), fdcRecBillInfo);
			
			CustomerEntryCollection customerEntryColl=fdcRecBillInfo.getCustomerEntrys();//收款单中标准的客户
			for(int j=0;j<customerEntryColl.size();j++){
				CustomerEntryInfo customerInfo=customerEntryColl.get(j);
				CustomerInfo cusInfo=customerInfo.getCustomer();//某个标准 客户
				
				//根据某个标准客户 过滤出相应的房地产客户
				EntityViewInfo env=new EntityViewInfo();
				FilterInfo filterInfo=new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("sysCustomer.id", cusInfo.getId().toString(),CompareType.INCLUDE));
				filterInfo.setMaskString("#0");
				env.setFilter(filterInfo);
				FDCCustomerCollection fdcCustomerCollection= FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerCollection(env);
				for(int t=0;t<fdcCustomerCollection.size();t++){
					FDCCustomerInfo fdcCustomerInfo=fdcCustomerCollection.get(t);
					//每个客户生成一张相应的单据
					for(int i=0;i<listBills.size();i++){
						map=(HashMap)listBills.get(i);
						//判断 这个这个收款单对应 的款项类型在 临时应收单中存不存在
						try{
							tempID=GetTempoarBill(ctx, billInfos.getId().toString(), map.get("moneyTypeID").toString());
							if(!tempID.equalsIgnoreCase("")){
								//直接更新
								updateTempBillByID(ctx,tempID,(BigDecimal)map.get("appAmount"),(BigDecimal)map.get("gatheringAmount"));
								continue;
							}
						}catch(Exception ex){
							throw new BOSException(ex.getMessage());
						}
						//************************ end     
						
						
						PPMTemporaryInfo ppmTempInfo=new PPMTemporaryInfo();
						
						//0.UUID
						//融合零星收款
						
						//String payListId = BOSUuid.create(new PPMTemporaryInfo().getBOSType()).toString();
						String payListId = String.valueOf(map.get("payListId"));
						ppmTempInfo.setId(BOSUuid.read(payListId));
						//1.单据编号
						billNumber=getBillNumber(ppmTempInfo, ctx);
						//取服务器日期
						if(billNumber.equalsIgnoreCase("")){
							Date date=SysUtil.getAppServerTime(ctx);
							DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
							String dateStr = df.format(date);
							billNumber=dateStr;
						}
						
						ppmTempInfo.setNumber(billNumber);
						//2.物业项目
						//SellProjectInfo selInfo=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(rowSet.getString("SELLPROJECTID")));
//						ppmTempInfo.setSellproject(fdcRecBillInfo.getSellProject());
						//3.业务日期
						ppmTempInfo.setBizDate(fdcRecBillInfo.getCreateTime());
						//4.房间
						//RoomInfo roomInfo=RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(rowSet.getString("ROOMid")));
//						ppmTempInfo.setRoom(fdcRecBillInfo.getRoom());
						//5.客户
						//FDCCustomerInfo custInfo=FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(new ObjectUuidPK(rowSet.getString("FCustomerID")));
						ppmTempInfo.setCustomer(fdcCustomerInfo);
						//6.收费项目
						MoneyDefineInfo moneyDefineInfo=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(map.get("moneyTypeID").toString()));
//						ppmTempInfo.setChargeItem(moneyDefineInfo);
						//7.应收金额
						ppmTempInfo.setArAmout((BigDecimal)map.get("appAmount"));
						//8.已付金额
						ppmTempInfo.setPayedAmount((BigDecimal)map.get("gatheringAmount"));
						//9.应缴费日期
						ppmTempInfo.setArDate(billInfos.getBizDate());
						//10.制单人
						//UserInfo userInfo=UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(rowSet.getString("FCreatorid")));
						ppmTempInfo.setCreator(fdcRecBillInfo.getCreator());
						
						//11.单据状态
						ppmTempInfo.setState(FDCBillStateEnum.AUDITTED);// 
						
						ppmTempInfo.setDescription("本单据由收款单{"+billInfos.getNumber().toString()+"生成!}");
						//12.制单日期、审批人、审批日期,CU
						ppmTempInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
						//OrgUnitInfo orgInfo= ContextUtil.getCurrentOrgUnit(ctx);

						/*ppmTempInfo.setCreateTime(null);
						ppmTempInfo.setAuditor(null);
						ppmTempInfo.setAuditTime(null);
						ppmTempInfo.setCU(null);*/
						
						
						//生成临时应收单
						PPMTemporaryFactory.getLocalInstance(ctx).save(ppmTempInfo);
						
						//融合零星收款所以不用更新
						/**
						//把收款单 更新成 零星 收款
						fdcRecBillInfo.setBillTypeEnum(ReceiveBillTypeEnum.partial);
						FDCReceiveBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(fdcRecBillInfo.getId().toString()), fdcRecBillInfo);
						//******************  BEGIN ***************
						//这里，只能希望收款单的普通客户对应房地产客户时，只有一条。
						//这样就可以把应收金额反写到收款单分录上。如果有多个客户，就不适用于此逻辑。
						StringBuffer strBuffer =new StringBuffer();
						String sql="";
						strBuffer.append("update T_SHE_FDCReceiveBillEntry set FPayListID='"+payListId+"'"+"\n");
						strBuffer.append("where  FReceivingBillID='"+billInfos.getId().toString()+"'"+"\n");
						strBuffer.append("and FMoneyDefineID='"+moneyDefineInfo.getId().toString()+"'"+"\n");
						sql=strBuffer.toString();
						DbUtil.execute(ctx, sql);
						//******************  END  ************************
						*/
						
					}
				}
			}
			
		}
		
	}
	
	/**
	 * @author jetdai
	 * date:2009-12-13
	 * 描述：判断这次传过来的付款明细ID在临时应收单上存不存在
	 * */
	private String GetTempoarBill(Context ctx,String billID,String moneyTypeID)throws Exception{
		boolean bol=false;
		String tempBillID="";
		String sql="";
		StringBuffer strBuffer=new StringBuffer();
		//IRowSet rowSet= DbUtil.executeQuery(ctx,sql);
		//IRowSet rowSet=null;
		//FPayedAmount
		//update T_PPM_PPMTemporary set FPayedAmount= where fid= and FChargeItemID=
		strBuffer.append("select fid,FChargeItemID ,count(*) as fcnt from T_PPM_PPMTemporary"+"\n");
		strBuffer.append("where fid in"+"\n");
		strBuffer.append("("+"\n");
		strBuffer.append("select distinct FDCRECEIVEBILLENTRY.fpaylistid as fid"+"\n");
		strBuffer.append("FROM T_CAS_ReceivingBill AS RECEIVINGBILL"+"\n");
		strBuffer.append("INNER JOIN T_SHE_FDCReceiveBill AS FDCRECEIVEBILL"+"\n");
		strBuffer.append("ON RECEIVINGBILL.FFdcReceiveBillID = FDCRECEIVEBILL.FID"+"\n");
		strBuffer.append("LEFT OUTER JOIN T_SHE_FDCReceiveBillEntry AS FDCRECEIVEBILLENTRY"+"\n");
		strBuffer.append("ON RECEIVINGBILL.FID = FDCRECEIVEBILLENTRY.FReceivingBillID"+"\n");
		strBuffer.append("where RECEIVINGBILL.FID in ('"+billID+"')"+"\n");
		strBuffer.append(") and FChargeItemID='"+moneyTypeID+"'"+"\n");
		strBuffer.append("group by fid,FChargeItemID"+"\n");
		
		sql=strBuffer.toString();
		IRowSet rowSet= DbUtil.executeQuery(ctx,sql);
		try{
			while(rowSet.next()){
				tempBillID=rowSet.getString("fid");
			}
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}
		return tempBillID;
	}

	//jetdai 209-12-01
	//实现： 在序事簿和单据上点审批时生成 临时应收单
	protected void _addTemporaBill(Context ctx, ArrayList listIds)
			throws BOSException, EASBizException
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		
		
		
		//1.得到收款单的单据ID
		String strIDs="";
		for (int i=0;i<listIds.size();i++){
			//ReceivingBillInfo receiveBill = (ReceivingBillInfo) listFDCReceiveID.getObject(i);
			strIDs=strIDs +"'"+listIds.get(i).toString()+"',";
		}
		//2.根据SQL，再用收款单ID进行过滤。这里的SQL一条记录就要生成一张临时应收单
		String sql="";
		StringBuffer strBuffer=new StringBuffer();
		try{
			if(strIDs.length()>0){
				strIDs=strIDs.substring(0, strIDs.length()-1);
				//把所选 中的零星收款 所对应的临时应收单中的状态全部设为 审核
				strBuffer.append("update t_ppm_PPMTemporary set FState='4AUDITTED' where fid in"+"\n");
				strBuffer.append("("+"\n");
				strBuffer.append("select distinct FDCRECEIVEBILLENTRY.fpaylistid as fid"+"\n");
				strBuffer.append("FROM T_CAS_ReceivingBill AS RECEIVINGBILL"+"\n");
				strBuffer.append("INNER JOIN T_SHE_FDCReceiveBill AS FDCRECEIVEBILL"+"\n");
				strBuffer.append("ON RECEIVINGBILL.FFdcReceiveBillID = FDCRECEIVEBILL.FID"+"\n");
				strBuffer.append("LEFT OUTER JOIN T_SHE_FDCReceiveBillEntry AS FDCRECEIVEBILLENTRY"+"\n");
				strBuffer.append("ON RECEIVINGBILL.FID = FDCRECEIVEBILLENTRY.FReceivingBillID"+"\n");
				strBuffer.append("where RECEIVINGBILL.FID in ("+strIDs+")"+"\n");
				strBuffer.append("and FDCRECEIVEBILL.fbilltypeenum='partial'"+"\n");
				strBuffer.append(")"+"\n");
				
				sql=strBuffer.toString();
				DbUtil.execute(ctx, sql);

			}
		
		
		
		/*
		String strIDs="";
		for (int i=0;i<listIds.size();i++){
			//ReceivingBillInfo receiveBill = (ReceivingBillInfo) listFDCReceiveID.getObject(i);
			strIDs=strIDs +"'"+listIds.get(i).toString()+"',";
		}
		//2.根据SQL，再用收款单ID进行过滤。这里的SQL一条记录就要生成一张临时应收单
		String sql="";
		if(strIDs.length()>0){
			strIDs="("+strIDs.substring(0, strIDs.length()-1)+")";
			sql=getCreateTempPayBillSql(strIDs);
		}
		if(sql.length()==0)
			return;
		String billNumber="";
		IRowSet rowSet= DbUtil.executeQuery(ctx,sql);
		try{
			while(rowSet.next()){
				
				PPMTemporaryInfo ppmTempInfo=new PPMTemporaryInfo();
				
				//1.单据编号
				billNumber=getBillNumber(ppmTempInfo, ctx);
				//取服务器日期
				if(billNumber.equalsIgnoreCase("")){
					Date date=SysUtil.getAppServerTime(ctx);
					DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String dateStr = df.format(date);
					billNumber=dateStr;
					
				}
				
				ppmTempInfo.setNumber(billNumber);
				//2.物业项目
				SellProjectInfo selInfo=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(rowSet.getString("SELLPROJECTID")));
				ppmTempInfo.setSellproject(selInfo);
				//3.业务日期
				ppmTempInfo.setBizDate(rowSet.getDate("CREATETIME"));
				//4.房间
				RoomInfo roomInfo=RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(rowSet.getString("ROOMid")));
				ppmTempInfo.setRoom(roomInfo);
				//5.客户
				FDCCustomerInfo custInfo=FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerInfo(new ObjectUuidPK(rowSet.getString("FCustomerID")));
				ppmTempInfo.setCustomer(custInfo);
				//6.收费项目
				MoneyDefineInfo moneyDefineInfo=MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(rowSet.getString("FMoneydefineID")));
				ppmTempInfo.setChargeItem(moneyDefineInfo);
				//7.应收金额
				ppmTempInfo.setArAmout(new BigDecimal(rowSet.getDouble("FArAmout")));
				//8.已付金额
				ppmTempInfo.setPayedAmount(rowSet.getBigDecimal("FDCRECEIVEBILLENTRYAMOUNT"));
				//9.应缴费日期
				ppmTempInfo.setArDate(rowSet.getDate("BIZDATE"));
				//10.制单人
				UserInfo userInfo=UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(rowSet.getString("FCreatorid")));
				ppmTempInfo.setCreator(userInfo);
				
				//11.单据状态
				ppmTempInfo.setState(FDCBillStateEnum.AUDITTED);
				ppmTempInfo.setDescription("本单据由收款单{"+rowSet.getString("billNUMBER")+"生成@!}");
				//12.制单日期、审批人、审批日期,CU
				ppmTempInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
				//OrgUnitInfo orgInfo= ContextUtil.getCurrentOrgUnit(ctx);

				ppmTempInfo.setCreateTime(null);
				ppmTempInfo.setAuditor(null);
				ppmTempInfo.setAuditTime(null);
				ppmTempInfo.setCU(null);
				
				
				//生成临时应收单
				PPMTemporaryFactory.getLocalInstance(ctx).addnew(ppmTempInfo);
				
				
			}*/
		}catch(Exception ex){
			throw new BOSException(ex.getMessage());
		}finally{
			//rowSet=null;
		}
		
		
		
		
		
	}
	
	/**
	 * @author jetdai
	 * date:2009-12-08
	 * 描述：返回编码
	 * 参数 
	 * */
	private String getBillNumber(CoreBillBaseInfo billInfo,Context ctx){
		OrgUnitInfo orgInfo=null;
		String billNumber="";
		String orgID="";
		orgInfo=ContextUtil.getCurrentOrgUnit(ctx);
		if(orgInfo!=null){
			orgID=orgInfo.getId().toString();
		}
		try
		{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			if (iCodingRuleManager.isExist(billInfo, orgID)) {// 设置过编码规则
				billNumber=iCodingRuleManager.getNumber(billInfo, orgID, "");
				//billInfo.setNumber(iCodingRuleManager.getNumber(saleIssueBillInfo, souID, ""));
			} else { 
				
				// 抛出异常提示未设置过编码规则。
					/*IMetaDataLoader imeataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
					EntityObjectInfo entityObjectInfo = imeataLoader.getEntity(sou.getBOSType());
					String[] materialNames = new String[1];
					materialNames[0] = entityObjectInfo.getAlias();
					throw new SCMBillException(SCMBillException.NOCORDRULE, materialNames);*/
				//}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return billNumber;
	}
	
	
	/**
	 * @author jetdai
	 * date:2009-12-03
	 * 描述：返回一个SQL。查询收款单
	 * recIDS:ID串。。 类似于。。。（'aaa','bbbb'）
	 * */
	private String getCreateTempPayBillSql(String recIDS){
		String strSql="";
		StringBuffer sqlBuffer=new StringBuffer();
		
		sqlBuffer.append("select ta.*,tb.FArAmout from"+"\n");
		sqlBuffer.append("("+"\n");
		sqlBuffer.append("SELECT"+"\n"); 
		sqlBuffer.append("RECEIVINGBILL.FID AS ID, --单据ID"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBillStatus AS BILLSTATUS, --单据状态"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FNumber AS billNUMBER, --单据编码"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBizDate AS BIZDATE, --业务日期"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreateTime AS CREATETIME, --制单日期"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreatorid AS FCreatorid, --制单人"+"\n");
		sqlBuffer.append("customer.FID as FCustomerID,--客户ID"+"\n");
		sqlBuffer.append("ROOM.Fid AS ROOMid, --房间ID"+"\n");
		sqlBuffer.append("FDCRECEIVEBILL.FSellProjectID AS SELLPROJECTID, --销售项目ID"+"\n");
		sqlBuffer.append("MONEYDEFINE.FID AS FMoneydefineID,--分录 款项名称ID"+"\n");
		sqlBuffer.append("FDCRECEIVEBILLENTRY.FPayListID as FListID,"+"\n");
		sqlBuffer.append("sum(isnull(FDCRECEIVEBILLENTRY.FAmount,0)) AS FDCRECEIVEBILLENTRYAMOUNT --收款金额"+"\n");
		sqlBuffer.append("FROM T_CAS_ReceivingBill AS RECEIVINGBILL"+"\n");
		sqlBuffer.append("INNER JOIN T_SHE_FDCReceiveBill AS FDCRECEIVEBILL"+"\n");
		sqlBuffer.append("ON RECEIVINGBILL.FFdcReceiveBillID = FDCRECEIVEBILL.FID"+"\n");
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_FDCReceiveBillEntry AS FDCRECEIVEBILLENTRY"+"\n");
		sqlBuffer.append("ON RECEIVINGBILL.FID = FDCRECEIVEBILLENTRY.FReceivingBillID"+"\n");
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_Room AS ROOM"+"\n");
		sqlBuffer.append("ON FDCRECEIVEBILL.FRoomID = ROOM.FID"+"\n");
		
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_MoneyDefine AS MONEYDEFINE"+"\n");
		sqlBuffer.append("ON FDCRECEIVEBILLENTRY.FMoneyDefineID = MONEYDEFINE.FID"+"\n");
		
		sqlBuffer.append("LEFT OUTER JOIN T_SHE_FDCCustomer AS customer"+"\n");
		sqlBuffer.append("ON RECEIVINGBILL.FPayerID = customer.FSysCustomerID"+"\n");
		
		sqlBuffer.append("where"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FID in"+recIDS+"\n");
		sqlBuffer.append("group by "+"\n");
		sqlBuffer.append("RECEIVINGBILL.FID,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBillStatus,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FNumber,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FBizDate,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreateTime,"+"\n");
		sqlBuffer.append("RECEIVINGBILL.FCreatorid,"+"\n");
		sqlBuffer.append("customer.FID,"+"\n");
		sqlBuffer.append("ROOM.Fid ,"+"\n");
		sqlBuffer.append("MONEYDEFINE.FID,"+"\n");
		sqlBuffer.append("FDCRECEIVEBILLENTRY.FPayListID,"+"\n");
		sqlBuffer.append("FDCRECEIVEBILL.FSellProjectID"+"\n");
		sqlBuffer.append(") ta left outer join "+"\n");
		sqlBuffer.append("("+"\n");
		sqlBuffer.append("select t.FReceivingBillID as id,t.FPayListID as listID,sum(t.FArAmout) FArAmout from"+"\n"); 
		sqlBuffer.append("("+"\n");
		sqlBuffer.append("select distinct t2.FReceivingBillID,t2.FPayListID,isnull(t1.FArAmout,0) FArAmout from T_PPM_PPMGeneralAR t1"+"\n");
		sqlBuffer.append("inner join T_SHE_FDCReceiveBillEntry t2 on t1.fid=t2.FPayListID"+"\n");
		sqlBuffer.append("where t2.FReceivingBillID in"+recIDS+"\n");
		sqlBuffer.append("union all"+"\n");
		sqlBuffer.append("select distinct t2.FReceivingBillID,t2.FPayListID,isnull(t1.FArAmout,0) FArAmout from T_PPM_PPMMeasureAR t1"+"\n");
		sqlBuffer.append("inner join T_SHE_FDCReceiveBillEntry t2 on t1.fid=t2.FPayListID"+"\n");
		sqlBuffer.append("where t2.FReceivingBillID in"+recIDS+"\n");
		sqlBuffer.append(") t group by t.FReceivingBillID,t.FPayListID"+"\n");
		sqlBuffer.append(") tb "+"\n");
		sqlBuffer.append("on ta.ID=tb.id and ta.FListID=tb.listid"+"\n");

		strSql=sqlBuffer.toString();
		return strSql;
		
	}

	

	/**
	 * 通过销售项目去找自定义核算项目里面找 一样名字的 自定义核算项目
	 * @param fdcBill
	 * @return
	 * @throws BOSException 
	 */
	private FDCReceiveBillInfo setGeneralAsstActType(Context ctx, FDCReceiveBillInfo fdcBill) throws BOSException
	{
		if(fdcBill == null)
			return fdcBill;
		SellProjectInfo info = fdcBill.getSellProject();
		if(info != null && info.getName() != null && info.getName().trim().length() > 0)
		{
			String name = info.getName();
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name",name,CompareType.EQUALS));
			view.setFilter(filter);
			GeneralAsstActTypeCollection coll = GeneralAsstActTypeFactory.getLocalInstance(ctx).getGeneralAsstActTypeCollection(view);
			
			if(coll != null && coll.size() == 1)
			{
				GeneralAsstActTypeInfo type = coll.get(0);
				fdcBill.setAsstActType(type);
			}
		}
		
		return fdcBill;
	}

	/**
	 * 提交收款单，返回财务收款单ID
	 */
	protected String _submitByCasRev(Context ctx, IObjectValue casRev)
			throws BOSException, EASBizException
	{
		String receivingBillId = null;
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		if(receivingBillInfo.getExchangeRate()==null)
			receivingBillInfo.setExchangeRate(FDCHelper.ONE);
		if(receivingBillInfo.getLocalAmt()==null)
			receivingBillInfo.setLocalAmt(receivingBillInfo.getAmount());
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		fdcReceiveBillInfo = this.setGeneralAsstActType(ctx, fdcReceiveBillInfo);
		/*
		 * 根据所属系统的不同来收款
		 */
		MoneySysTypeEnum moneySysTypeEnum = fdcReceiveBillInfo.getMoneySysType();
		if(MoneySysTypeEnum.SalehouseSys.equals(moneySysTypeEnum))
		{
			receivingBillId = this.receiveMoneyInSHE(ctx,casRev);
		}
		else if(MoneySysTypeEnum.TenancySys.equals(moneySysTypeEnum))
		{
			receivingBillId = this.receiveMoneyInTEN(ctx,casRev);
		}
		else if(MoneySysTypeEnum.ManageSys.equals(moneySysTypeEnum))
		{
			receivingBillId = this.receiveMoneyInWuYe(ctx,casRev);
		}
		else
		{
			logger.error("服务端程序没有关于"+ moneySysTypeEnum.getAlias() +" 系统的收款处理！应该在客户端完成这个校验...");
		    throw new BOSException("程序逻辑错误，已记录日志！");
		}
		return receivingBillId;
	}
	/**
	 * 在物业系统中进行的收款操作
	 * @param ctx
	 * @param casRev
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private String receiveMoneyInWuYe(Context ctx, IObjectValue casRev) throws EASBizException,BOSException
	{

		String receivingBillId = null;
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		
		FDCReceiveBillEntryCollection fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
		ReceiveBillTypeEnum recBillType = fdcReceiveBillInfo.getBillTypeEnum();
		
		RoomInfo room = fdcReceiveBillInfo.getRoom();
	
		// 现在实际收款单已没有保存状态，直接提交的
		if (receivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
		{
			this.submit(ctx, fdcReceiveBillInfo);
			return null;
		}
		ReceivingBillInfo oldReceivingBillInfo = null;
		//判断在系统里面是是否已经存在的这个单据
		if (receivingBillInfo.getId() != null
				&& ReceivingBillFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(receivingBillInfo.getId())))
		{
			oldReceivingBillInfo = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(receivingBillInfo.getId()));
			if (oldReceivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
			{
				oldReceivingBillInfo = null;
			}
		}
		//如果是退款 或者结算
		if (ReceiveBillTypeEnum.refundment.equals(recBillType))
		{

//			一个临时的 payListId 比较值
			String tempComparison = "";
			
			for (int i = 0; i < fdcRecBillEntryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
				String payListId = entryInfo.getPayListId();
				//生成一个假 BOSUuid
				if(!BOSUuid.isValidLength(payListId,true))
				{
					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
				}
				
				MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();

				FilterInfo filter = new FilterInfo();
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
			
				filter.getFilterItems().add(new FilterItemInfo("id",payListId));

				PPMTemporaryCollection temporaryColl = PPMTemporaryFactory.getLocalInstance(ctx).getPPMTemporaryCollection(view);
				
				PPMMeasureARCollection measureColl = PPMMeasureARFactory.getLocalInstance(ctx).getPPMMeasureARCollection(view);
				
				PPMGeneralARCollection generalColl = PPMGeneralARFactory.getLocalInstance(ctx).getPPMGeneralARCollection(view);
				
				DepositBillCollection depositColl = DepositBillFactory.getLocalInstance(ctx).getDepositBillCollection(view);
				
				DepositWithdrawBillCollection withdrawColl = DepositWithdrawBillFactory.getLocalInstance(ctx).getDepositWithdrawBillCollection(view);

				if (temporaryColl.size() == 1)
				{
					PPMTemporaryInfo info = temporaryColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					
					PPMTemporaryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				}
				else if(measureColl.size() == 1)
				{

					PPMMeasureARInfo info = measureColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMMeasureARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(generalColl.size() == 1)
				{

					PPMGeneralARInfo info = generalColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMGeneralARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(depositColl.size() == 1)
				{


					DepositBillInfo info = depositColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActGatheringAmo() != null)
						actAmount = info.getActGatheringAmo();
					
					if(info.getDepositAmount() != null)
						appAmount = info.getDepositAmount();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actGatheringAmo");
					info.setActGatheringAmo(actAmount);
					DepositBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				}
				else if(withdrawColl.size() == 1)
				{



					DepositWithdrawBillInfo info = withdrawColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActRefundmentAm() != null)
						actAmount = info.getActRefundmentAm().abs();
					
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount.abs());
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount().abs() == null? FDCHelper.ZERO : entryInfo.getAmount().abs());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actRefundmentAm");
					info.setActRefundmentAm(actAmount);
					DepositWithdrawBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				
				}
				
				//替换比较值
				if(!tempComparison.equalsIgnoreCase(payListId))
				{
					tempComparison = payListId;
				}
			}
		
		}
		//收款
		else
		{
//			一个临时的 payListId 比较值
			String tempComparison = "";
			
			for (int i = 0; i < fdcRecBillEntryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
				String payListId = entryInfo.getPayListId();
				//生成一个假 BOSUuid
				if(!BOSUuid.isValidLength(payListId,true))
				{
					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
				}
				
				MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();

				FilterInfo filter = new FilterInfo();
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
			
				filter.getFilterItems().add(new FilterItemInfo("id",payListId));

				PPMTemporaryCollection temporaryColl = PPMTemporaryFactory.getLocalInstance(ctx).getPPMTemporaryCollection(view);
				
				PPMMeasureARCollection measureColl = PPMMeasureARFactory.getLocalInstance(ctx).getPPMMeasureARCollection(view);
				
				PPMGeneralARCollection generalColl = PPMGeneralARFactory.getLocalInstance(ctx).getPPMGeneralARCollection(view);
				
				DepositBillCollection depositColl = DepositBillFactory.getLocalInstance(ctx).getDepositBillCollection(view);
				
				DepositWithdrawBillCollection withdrawColl = DepositWithdrawBillFactory.getLocalInstance(ctx).getDepositWithdrawBillCollection(view);

				if (temporaryColl.size() == 1)
				{
					PPMTemporaryInfo info = temporaryColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					
					PPMTemporaryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				}
				else if(measureColl.size() == 1)
				{

					PPMMeasureARInfo info = measureColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMMeasureARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(generalColl.size() == 1)
				{

					PPMGeneralARInfo info = generalColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getPayedAmount() != null)
						actAmount = info.getPayedAmount();
					
					if(info.getArAmout() != null)
						appAmount = info.getArAmout();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("payedAmount");
					info.setPayedAmount(actAmount);
					PPMGeneralARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				}
				else if(depositColl.size() == 1)
				{


					DepositBillInfo info = depositColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActGatheringAmo() != null)
						actAmount = info.getActGatheringAmo();
					
					if(info.getDepositAmount() != null)
						appAmount = info.getDepositAmount();
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actGatheringAmo");
					info.setActGatheringAmo(actAmount);
					DepositBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				}
				else if(withdrawColl.size() == 1)
				{



					DepositWithdrawBillInfo info = withdrawColl.get(0);
					BigDecimal appAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;
					
					if(info.getActRefundmentAm() != null)
						actAmount = info.getActRefundmentAm();
					
					
					if(oldReceivingBillInfo != null)
					{
						BigDecimal oldAmount = this.getOldAmount(ctx, receivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);
						
						if(!tempComparison.equalsIgnoreCase(payListId))
						{
							actAmount = actAmount.subtract(oldAmount);
						}
					}
					else
					{
						
					}
					actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO : entryInfo.getAmount());
					
					SelectorItemCollection selColl = new SelectorItemCollection();
					selColl.add("actRefundmentAm");
					info.setActRefundmentAm(actAmount);
					DepositWithdrawBillFactory.getLocalInstance(ctx).updatePartial(info, selColl);
				
				
				
				}
				
				//替换比较值
				if(!tempComparison.equalsIgnoreCase(payListId))
				{
					tempComparison = payListId;
				}
			}
		}
		this.submit(ctx, fdcReceiveBillInfo);
		receivingBillId = ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo).toString();
		return receivingBillId;
	}
	/**
	 * 在租赁系统中进行收款的操作
	 * @param ctx
	 * @param casRev
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private String receiveMoneyInTEN(Context ctx, IObjectValue casRev) throws EASBizException,BOSException
	{
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
		
		FDCReceiveBillEntryCollection fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
		ReceiveBillTypeEnum recBillType = fdcReceiveBillInfo.getBillTypeEnum();
		
		RoomInfo room = fdcReceiveBillInfo.getRoom();
		
		// 现在实际收款单已没有保存状态，直接提交的
		if (receivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
		{
			this.submit(ctx, fdcReceiveBillInfo);
			return null;
		}
		ReceivingBillInfo oldReceivingBillInfo = null;
		//判断在系统里面是是否已经存在的这个单据,主要用来判断该提交是否是修改操作
		if (receivingBillInfo.getId() != null
				&& ReceivingBillFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(receivingBillInfo.getId())))
		{
			oldReceivingBillInfo = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(receivingBillInfo.getId()));
			if (oldReceivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
			{
				oldReceivingBillInfo = null;
			}
		}
		//TODO 结算类型 租赁中暂不实现。物业中的红冲收款，非事物方式，后面也需要须改。
		if(ReceiveBillTypeEnum.settlement.equals(recBillType)){
			
		}else if (ReceiveBillTypeEnum.refundment.equals(recBillType))//针对退款
		{
			// 一个临时的 payListId 比较值
			String tempComparison = "";

			for (int k = 0; k < fdcRecBillEntryColl.size(); k++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(k);
				String payListId = entryInfo.getPayListId();
				// 生成一个假 BOSUuid ,不用null去过滤
				if (!BOSUuid.isValidLength(payListId, true))
				{
					//租赁里面暂没有预收款，其收款分录一定会存在 payllistid，否则为逻辑错误或脏数据
					logger.error("逻辑错误，或则脏数据。收款分录没有付款明细ID.casRevNum:" + receivingBillInfo.getNumber());
					throw new BOSException("系统逻辑错误，已记录日志！");
//					payListId = BOSUuid.create(new TenancyRoomPayListEntryInfo().getBOSType()).toString();
				}
				
				if(GatheringObjectEnum.room.equals(fdcReceiveBillInfo.getGatheringObject())){
					//查找要退款的租赁合同上的那条付款明细
					FilterInfo tempFilter = new FilterInfo();
					EntityViewInfo tempView = new EntityViewInfo();
					tempView.getSelector().add("refundmentAmount");
					tempView.getSelector().add("canRefundmentAmount");
					tempView.getSelector().add("actAmount");
					tempView.getSelector().add("tenRoom.remainDepositAmount");
					tempView.getSelector().add("tenRoom.tenancy.remainDepositAmount");
					tempView.setFilter(tempFilter);

//					tempFilter.getFilterItems().add(new FilterItemInfo("tenRoom.room", room.getId().toString()));
					tempFilter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy", tenancyBillInfo.getId().toString()));
					tempFilter.getFilterItems().add(new FilterItemInfo("id", payListId));

					TenancyRoomPayListEntryCollection payListColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(tempView);
					if (payListColl.size() != 1)
					{
						logger.error("合同 " + tenancyBillInfo.getId() + "找不到对应的ID为 " + payListId + " 的付款明细！");
						throw new BOSException("系统逻辑错误，已记录日志！");
					} else
					{
						TenancyRoomPayListEntryInfo payListEntryInfo = payListColl.get(0);
						
						BigDecimal refundmentAmount = payListEntryInfo.getLimitAmount();	//TODO  Temp modify by Jeegan***************
						BigDecimal canRefundmentAmount = payListEntryInfo.getCanRefundmentAmount();
						BigDecimal actAmount = payListEntryInfo.getActAmount();
						
						if(refundmentAmount == null){
							refundmentAmount = FDCHelper.ZERO;
						}
						if(canRefundmentAmount == null){
							canRefundmentAmount = FDCHelper.ZERO;
						}
						
						if(actAmount == null){
							actAmount = FDCHelper.ZERO;
						}
						
						//表示这次退款金额增量
						BigDecimal chgAmount = entryInfo.getAmount();
						
						//如果是修改
						if(oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//对于相同的付款明细来讲，只要第一次计算即可
							if(!tempComparison.equalsIgnoreCase(payListId))
							{
								chgAmount = newAmount.subtract(oldAmount);
								refundmentAmount = refundmentAmount.add(newAmount).subtract(oldAmount);
								canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs()).add(oldAmount.abs());
								actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							//收款单删除时，这里可能会为0
							if(newAmount == null){
								newAmount = FDCHelper.ZERO;
							}
							refundmentAmount = refundmentAmount.add(newAmount);
							canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs());
							actAmount = actAmount.subtract(newAmount.abs());
						}
						
						//更新付款明细的退款金额.可退金额和实收金额
						payListEntryInfo.setRemissionAmount(refundmentAmount);	//TODO  Temp modify by Jeegan***************
						payListEntryInfo.setLimitAmount(canRefundmentAmount);	//TODO  Temp modify by Jeegan***************
						payListEntryInfo.setActRevAmount(actAmount);		//TODO  Temp modify by Jeegan***************
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("refundmentAmount");
						sels.add("canRefundmentAmount");
						sels.add("actAmount");
//						TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(payListEntryInfo, sels);
						
						//更新房间剩余押金和合同剩余押金
						
						TenancyRoomEntryInfo tenRoom = payListEntryInfo.getTenRoom();
						TenancyBillInfo tenancy = tenRoom.getTenancy();
						
						BigDecimal residualDepositInRoom = FDCHelper.ZERO;
						BigDecimal residualDepositInTenancy = FDCHelper.ZERO;
						if (tenRoom.getRemainDepositAmount() != null) {
							residualDepositInRoom = tenRoom.getRemainDepositAmount();
						}
						if (tenancy.getRemainDepositAmount() != null) {
							residualDepositInTenancy = tenancy.getRemainDepositAmount();
						}
						
						if(oldReceivingBillInfo != null){
							//由于修改时chgAmount计算了所有该付款明细对应的退款分录.故只在第一次更新
							if(!tempComparison.equalsIgnoreCase(payListId)){
								tenRoom.setRemainDepositAmount(residualDepositInRoom.subtract(chgAmount));
								tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
							}
						}else{
							tenRoom.setRemainDepositAmount(residualDepositInRoom.subtract(chgAmount));
							tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
						}
						
						SelectorItemCollection selColl = new SelectorItemCollection();
						selColl.add("remainDepositAmount");
						TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenRoom, selColl);
						
						SelectorItemCollection billSelColl = new SelectorItemCollection();
						billSelColl.add("remainDepositAmount");
						TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, billSelColl);
					}
					//				替换比较值
					if (!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}else if(GatheringObjectEnum.accessorialResource.equals(fdcReceiveBillInfo.getGatheringObject())){
					//查找要退款的租赁合同上的那条付款明细
					FilterInfo tempFilter = new FilterInfo();
					EntityViewInfo tempView = new EntityViewInfo();
					tempView.getSelector().add("refundmentAmount");
					tempView.getSelector().add("canRefundmentAmount");
					tempView.getSelector().add("actAmount");
					tempView.getSelector().add("tenAttachRes.tenancyBill.remainDepositAmount");
					tempView.setFilter(tempFilter);
					
					tempFilter.getFilterItems().add(new FilterItemInfo("id", payListId));
					
					TenAttachResourcePayListEntryCollection payListColl = TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).getTenAttachResourcePayListEntryCollection(tempView);
					if (payListColl.size() != 1)
					{
						logger.error("合同 " + tenancyBillInfo.getId() + "找不到对应的ID为 " + payListId + " 的付款明细！");
						throw new BOSException("系统逻辑错误，已记录日志！");
					} else
					{
						TenAttachResourcePayListEntryInfo payListEntryInfo = payListColl.get(0);
						
						BigDecimal refundmentAmount = payListEntryInfo.getRefundmentAmount();
						BigDecimal canRefundmentAmount = payListEntryInfo.getCanRefundmentAmount();
						BigDecimal actAmount = payListEntryInfo.getActAmount();
						
						if(refundmentAmount == null){
							refundmentAmount = FDCHelper.ZERO;
						}
						if(canRefundmentAmount == null){
							canRefundmentAmount = FDCHelper.ZERO;
						}
						
						if(actAmount == null){
							actAmount = FDCHelper.ZERO;
						}
						
						//表示这次退款金额增量
						BigDecimal chgAmount = entryInfo.getAmount();
						
						//如果是修改
						if(oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//对于相同的付款明细来讲，只要第一次计算即可
							if(!tempComparison.equalsIgnoreCase(payListId))
							{
								chgAmount = newAmount.subtract(oldAmount);
								refundmentAmount = refundmentAmount.add(newAmount).subtract(oldAmount);
								canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs()).add(oldAmount.abs());
								actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							//收款单删除时，这里可能会为0
							if(newAmount == null){
								newAmount = FDCHelper.ZERO;
							}
							refundmentAmount = refundmentAmount.add(newAmount);
							canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs());
							actAmount = actAmount.subtract(newAmount.abs());
						}
						
						//更新付款明细的退款金额.可退金额和实收金额
						payListEntryInfo.setRefundmentAmount(refundmentAmount);
						payListEntryInfo.setCanRefundmentAmount(canRefundmentAmount);
						payListEntryInfo.setActAmount(actAmount);
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("refundmentAmount");
						sels.add("canRefundmentAmount");
						sels.add("actAmount");
						TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).updatePartial(payListEntryInfo, sels);
						
						//更新合同剩余押金
						TenAttachResourceEntryInfo tenAttach = payListEntryInfo.getTenAttachRes();
						TenancyBillInfo tenancy = tenAttach.getTenancyBill();
						
						BigDecimal residualDepositInTenancy = FDCHelper.ZERO;

						if (tenancy.getRemainDepositAmount() != null) {
							residualDepositInTenancy = tenancy.getRemainDepositAmount();
						}
						
						if(oldReceivingBillInfo != null){
							//由于修改时chgAmount计算了所有该付款明细对应的退款分录.故只在第一次更新
							if(!tempComparison.equalsIgnoreCase(payListId)){
								tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
							}
						}else{
							tenancy.setRemainDepositAmount(residualDepositInTenancy.subtract(chgAmount));
						}
						
						SelectorItemCollection billSelColl = new SelectorItemCollection();
						billSelColl.add("remainDepositAmount");
						TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, billSelColl);
					}
					//				替换比较值
					if (!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
				
				
			}
		}
		//收款3
		else
		{
//			一个临时的 payListId 比较值
			String tempComparison = "";
			for (int i = 0; i < fdcRecBillEntryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
				String payListId = entryInfo.getPayListId();
				//生成一个假 BOSUuid
				if(!BOSUuid.isValidLength(payListId,true))
				{
					//租赁中暂没有预收款，如果payListId为空，肯定有问题
					logger.error("租赁收款分录没有对应付款明细ID， "+ payListId);
					throw new BOSException("系统逻辑错误，已记录日志！");
//					payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
				}
				
				if(GatheringObjectEnum.room.equals(fdcReceiveBillInfo.getGatheringObject())){
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("actAmount");
					view.getSelector().add("actPayDate");
					
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy", tenancyBillInfo.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));

					TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view);
					if (tenancyRoomPayListEntryColl.size() != 1)
					{
						logger.error("合同 "+tenancyBillInfo.getId()+"找不到对应的ID为 "+ payListId+" 的付款明细！");
						throw new BOSException("系统逻辑错误，已记录日志！");
					} else
					{
						TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(0);
						BigDecimal actAmount = FDCHelper.ZERO;
						
						// 剩余押金
						if (tenancyRoomPayListEntryInfo.getActAmount() != null)
						{
							actAmount = tenancyRoomPayListEntryInfo.getActAmount();
						}
						
						if (oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,receivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
							
							//对于相同的付款明细来讲，只要第一次计算即可
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								actAmount = actAmount.add(newAmount.subtract(oldAmount));
							}
						} else
						{
							actAmount = actAmount.add(entryInfo.getAmount());
						}
						
						//反写付款明细上的实收金额和实收日期
						tenancyRoomPayListEntryInfo.setActRevAmount(actAmount);	 //TODO  Temp modify by Jeegan***************
						tenancyRoomPayListEntryInfo.setActRevDate(receivingBillInfo.getBizDate());	//TODO  Temp modify by Jeegan***************
						if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0){
							tenancyRoomPayListEntryInfo.setActRevDate(null);		//TODO  Temp modify by Jeegan***************
						}
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("actAmount");
						sels.add("actPayDate");
//						TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomPayListEntryInfo, sels);
						
						/*
						// 如果是是押金的话，则反写租赁合同和房间分录上的剩余押金
						if (MoneyTypeEnum.DepositAmount.equals(moneyDefineInfo.getMoneyType())) {
							tenancyRoomEntryInfo.setRemainDepositAmount(residualDespoitInRoomEntry);
							SelectorItemCollection selColl = new SelectorItemCollection();
							selColl.add("remainDepositAmount");
							TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenancyRoomEntryInfo, selColl);

							tenancy.setRemainDepositAmount(residualDespoitInTenancyBill);
							SelectorItemCollection billSelColl = new SelectorItemCollection();
							billSelColl.add("remainDepositAmount");
							TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, billSelColl);
						}
						*/
					}
					
					//替换比较值
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}else if(GatheringObjectEnum.accessorialResource.equals(fdcReceiveBillInfo.getGatheringObject())){
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("actAmount");
					view.getSelector().add("actPayDate");
					
					view.setFilter(filter);
//					filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy", tenancyBillInfo.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));

					TenAttachResourcePayListEntryCollection tenancyAttachPayListEntryColl = TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).getTenAttachResourcePayListEntryCollection(view);
					if (tenancyAttachPayListEntryColl.size() != 1)
					{
						logger.error("合同 "+tenancyBillInfo.getId()+"找不到对应的ID为 "+ payListId+" 的付款明细！");
						throw new BOSException("系统逻辑错误，已记录日志！");
					} else
					{
						TenAttachResourcePayListEntryInfo tenancyAttachPayListEntryInfo = tenancyAttachPayListEntryColl.get(0);
						BigDecimal actAmount = FDCHelper.ZERO;
						// 剩余押金
						if (tenancyAttachPayListEntryInfo.getActAmount() != null)
						{
							actAmount = tenancyAttachPayListEntryInfo.getActAmount();
						}
						
						if (oldReceivingBillInfo != null)
						{
							BigDecimal oldAmount = this.getOldAmount(ctx,receivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
							
							//对于相同的付款明细来讲，只要第一次计算即可
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								actAmount = actAmount.add(newAmount.subtract(oldAmount));
							}
						} else
						{
							actAmount = actAmount.add(entryInfo.getAmount());
						}
						
						//反写付款明细上的实收金额和实收日期
						tenancyAttachPayListEntryInfo.setActAmount(actAmount);
						tenancyAttachPayListEntryInfo.setActPayDate(receivingBillInfo.getBizDate());
						if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0){
							tenancyAttachPayListEntryInfo.setActPayDate(null);
						}
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("actAmount");
						sels.add("actPayDate");
						TenAttachResourcePayListEntryFactory.getLocalInstance(ctx).updatePartial(tenancyAttachPayListEntryInfo, sels);
					}
					
					//替换比较值
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
			}
		}
		this.submit(ctx, fdcReceiveBillInfo);
		String receivingBillId = ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo).toString();
		return receivingBillId;
	}
	
	
	
	/**
	 * 判断是否是付首租款
	 * @param ctx
	 * @param seq
	 * @param tenancyBillInfo
	 * @param room
	 * @return
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private boolean isFirstAmountForTEN(Context ctx,int seq,TenancyBillInfo tenancyBillInfo,RoomInfo room) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		SorterItemCollection sorterItemColl = new SorterItemCollection();
		sorterItemColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorterItemColl);
		
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.RentAmount));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view);
	    
		if(tenancyRoomPayListEntryColl.get(0) != null)
		{
			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(0);
			if(seq != tenancyRoomPayListEntryInfo.getSeq())
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			logger.error("没有找到合同"+tenancyBillInfo.toString()+" 房间 "+room.toString() +" 的租金明细数据");
			return false;
		}
	}
	
	/**
	 * 判断是否是付首租款
	 * @param ctx
	 * @param payListId
	 * @param tenancyBillInfo
	 * @param room
	 * @return
	 * @throws BOSException
	 */
	private boolean isFirstAmountForTEN(Context ctx,String payListId,TenancyBillInfo tenancyBillInfo,RoomInfo room) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		SorterItemCollection sorterItemColl = new SorterItemCollection();
		sorterItemColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorterItemColl);
		
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.RentAmount));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view);
	    
		if(tenancyRoomPayListEntryColl.get(0) != null)
		{
			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(0);
			if(payListId.equalsIgnoreCase(tenancyRoomPayListEntryInfo.getId().toString()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			logger.error("没有找到合同"+tenancyBillInfo.toString()+" 房间 "+room.toString() +" 的租金明细数据");
			return false;
		}
	}
	
	
	/**
	 * 更改合同的状态
	 * @param ctx
	 * @param tenancyBillInfo
	 * @param tenancyBillStateEnum
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void changeTenancyBillState(Context ctx, TenancyBillInfo tenancyBillInfo,TenancyBillStateEnum tenancyBillStateEnum) throws EASBizException, BOSException
	{
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("tenancyState");
		
		tenancyBillInfo.setTenancyState(tenancyBillStateEnum);
		
		TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancyBillInfo,selColl);
	}
	
	private Set getFdcBillSetForCustomer(Context ctx, CustomerInfo customer) throws BOSException
	{
		Set fdcBillIdSet = new HashSet();
		
		if(customer == null)
			return fdcBillIdSet;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head");
		view.getSelector().add("head.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("customer",customer.getId().toString()));
		
		CustomerEntryCollection		customerColl = CustomerEntryFactory.getLocalInstance(ctx).getCustomerEntryCollection(view);
		
		for(int i = 0; i < customerColl.size(); i ++)
		{
			if(customerColl.get(i) != null && customerColl.get(i).getHead() != null && customerColl.get(i).getHead().getId() != null)
			{
				fdcBillIdSet.add(customerColl.get(i).getHead().getId().toString());
			}
		}
		
		return fdcBillIdSet;
	}
	
	
	/**
	 * 售楼系统中进行收款的操作
	 * @param ctx
	 * @param casRev
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private String receiveMoneyInSHE(Context ctx, IObjectValue casRev) throws EASBizException, BOSException
	{
		String receivingBillId = null;
		ReceivingBillInfo receivingBillInfo = (ReceivingBillInfo) casRev;
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		PurchaseInfo purchase = fdcReceiveBillInfo.getPurchase();
		FDCReceiveBillEntryCollection fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
		ReceiveBillTypeEnum recBillType = fdcReceiveBillInfo.getBillTypeEnum();
		
		// 现在实际收款单已没有保存状态，直接提交的
		if (receivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
		{
			this.submit(ctx, fdcReceiveBillInfo);
			return null;
		}
		ReceivingBillInfo oldReceivingBillInfo = null;
		
		FDCReceiveBillEntryCollection oldFdcRecEnryColl = null;
		//判断在系统里面是是否已经存在的这个单据
		if (receivingBillInfo.getId() != null
				&& ReceivingBillFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(receivingBillInfo.getId())))
		{
			SelectorItemCollection selColl = new SelectorItemCollection();
			selColl.add("*");
			selColl.add("fdcReceiveBillEntry.*");
			
			oldReceivingBillInfo = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(receivingBillInfo.getId()),selColl);
			oldFdcRecEnryColl = oldReceivingBillInfo.getFdcReceiveBillEntry();
			
			if (oldReceivingBillInfo.getBillStatus().equals(BillStatusEnum.SAVE))
			{
				oldReceivingBillInfo = null;
			}
		}
		PurchaseInfo purchaseInfo = fdcReceiveBillInfo.getPurchase();
	
		GatheringEnum gathering = fdcReceiveBillInfo.getGathering();
		//如果是诚意认购收款
		if(GatheringEnum.SinPurchase.equals(gathering)){
			//目前简单实现，主要是实现实付金额的反写
			//红冲，退款均先不管 TODO
			if(ReceiveBillTypeEnum.settlement.equals(recBillType)){
				throw new BOSException("暂不支持的操作.");
			} else if (ReceiveBillTypeEnum.refundment.equals(recBillType)) {
				String tempComparison = "";
				for (int i = 0; i < fdcRecBillEntryColl.size(); i++) {
					FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);

					String payListId = entryInfo.getPayListId();
					// 生成一个假 BOSUuid
					if (!BOSUuid.isValidLength(payListId, true)) {
						payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					}
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					view.getSelector().add("moneyDefine.*");
					view.setFilter(filter);

					filter.getFilterItems().add(new FilterItemInfo("id", payListId));
					SincerReceiveEntryCollection sinRevEntrys = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryCollection(view);
					if (sinRevEntrys.isEmpty()) {
						throw new BOSException("退款找不到诚意认购对应的付款明细！");
					}

					SincerReceiveEntryInfo sinRevEntry = sinRevEntrys.get(0);

					BigDecimal refundmentAmount = FDCHelper.ZERO;
					BigDecimal canRefundmentAmount = FDCHelper.ZERO;
					BigDecimal actAmount = FDCHelper.ZERO;

					if (sinRevEntry.getRefundmentAmount() != null) {
						refundmentAmount = sinRevEntry.getRefundmentAmount();
					}
					if (sinRevEntry.getCanRefundmentAmount() != null) {
						canRefundmentAmount = sinRevEntry.getCanRefundmentAmount();
					}
					if (sinRevEntry.getActPayAmount() != null) {
						actAmount = sinRevEntry.getActPayAmount();
					}

					BigDecimal balancedCanRefundmentAmount = FDCHelper.ZERO;
					
					// 如果是修改
					if (oldReceivingBillInfo != null) {
						BigDecimal oldAmount = this.getOldAmount(ctx, oldReceivingBillInfo.getId().toString(), payListId);
						BigDecimal newAmount = this.getNewAmount(ctx, fdcRecBillEntryColl, payListId);

						// 对于相同的付款明细来讲，只要第一次计算即可
						if (!tempComparison.equalsIgnoreCase(payListId)) {
							refundmentAmount = refundmentAmount.add(newAmount.abs()).subtract(oldAmount.abs());
							canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs()).add(oldAmount.abs());
							actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							
							balancedCanRefundmentAmount = newAmount.abs().subtract(oldAmount.abs());
						}
					} else {
						BigDecimal newAmount = entryInfo.getAmount();
						if (newAmount == null)
							newAmount = FDCHelper.ZERO;

						refundmentAmount = refundmentAmount.abs().add(newAmount.abs());
						canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs());
						actAmount = actAmount.subtract(newAmount.abs());
						
						balancedCanRefundmentAmount = newAmount.abs();
					}
					
//					sinRevEntry.setHasRefundmentAmount(refundmentAmount.abs());
//					sinRevEntry.setLimitAmount(canRefundmentAmount);
					sinRevEntry.setActRevAmount(actAmount);

					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("refundmentAmount");
					sels.add("canRefundmentAmount");
					sels.add("actPayAmount");
					SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinRevEntry, sels);
					
					//鉴于诚意金可退可冲的特殊性,需要将对应的收款单的可退金额进行修改
					if(balancedCanRefundmentAmount.compareTo(FDCHelper.ZERO) != 0){
						FDCReceiveBillEntryCollection fdcRevEntrys = getFdcRecEntryInfo(ctx, payListId);
						for(int m=0; m<fdcRevEntrys.size(); m++){
							FDCReceiveBillEntryInfo fdcRevEntry = fdcRevEntrys.get(m);
							BigDecimal amount = FDCHelper.toBigDecimal(fdcRevEntry.getAmount());
							BigDecimal canRefundmentAmountOfRevEntry = FDCHelper.toBigDecimal(fdcRevEntry.getCanCounteractAmount());
							BigDecimal counteractAmount = FDCHelper.toBigDecimal(fdcRevEntry.getCounteractAmount());//被红冲的金额
							
							BigDecimal maxCanRefunmentAmount = amount.subtract(counteractAmount);
//							BigDecimal minCanRefunmentAmount = counteractAmount;
							
							if(balancedCanRefundmentAmount.compareTo(FDCHelper.ZERO) > 0){
								if(canRefundmentAmountOfRevEntry.compareTo(balancedCanRefundmentAmount) >= 0){
									fdcRevEntry.setCanCounteractAmount(canRefundmentAmountOfRevEntry.subtract(balancedCanRefundmentAmount));
									
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									break;
								}else{
									balancedCanRefundmentAmount = balancedCanRefundmentAmount.subtract(canRefundmentAmountOfRevEntry);
									
									fdcRevEntry.setCanCounteractAmount(FDCHelper.ZERO);
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									continue;
								}
							}else{//如果是修改退款金额,使退款金额变小了.balancedCanRefundmentAmount为负数
								if(maxCanRefunmentAmount.subtract(canRefundmentAmountOfRevEntry).compareTo(balancedCanRefundmentAmount.abs()) >= 0){
									fdcRevEntry.setCanCounteractAmount(canRefundmentAmountOfRevEntry.add(balancedCanRefundmentAmount.abs()));
									
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									break;
								}else{
									balancedCanRefundmentAmount = balancedCanRefundmentAmount.add(maxCanRefunmentAmount.subtract(canRefundmentAmountOfRevEntry));
									
									fdcRevEntry.setCanCounteractAmount(maxCanRefunmentAmount);
									SelectorItemCollection upFdcEntrySels = new SelectorItemCollection();
									upFdcEntrySels.add("canCounteractAmount");
									FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(fdcRevEntry, upFdcEntrySels);
									continue;
								}
							}
						}	
					}
					
					//替换比较值
					if(!tempComparison.equalsIgnoreCase(payListId)){
						tempComparison = payListId;
					}
				}
			}else{
				//如果不是修改操作
				if(oldReceivingBillInfo == null){
					for(int i=0; i<fdcRecBillEntryColl.size(); i++){
						FDCReceiveBillEntryInfo fdcRecEntry = fdcRecBillEntryColl.get(i);
						String payId = fdcRecEntry.getPayListId();
						if(payId == null){
							throw new BOSException("找不到对应的付款明细");
						}
						
						SelectorItemCollection paySels = new SelectorItemCollection();
						SincerReceiveEntryInfo sinEntry = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
						
						BigDecimal srcActAmount = sinEntry.getActRevAmount();
						if(srcActAmount == null){
							srcActAmount = FDCHelper.ZERO;
						}
						BigDecimal thisAmount = fdcRecEntry.getAmount();
						if(thisAmount == null){
							thisAmount = FDCHelper.ZERO;
						}
						
						BigDecimal canRefundmentAmount = sinEntry.getCanRefundmentAmount();
						if(canRefundmentAmount == null){
							canRefundmentAmount = FDCHelper.ZERO;
						}
						
						sinEntry.setActRevAmount(srcActAmount.add(thisAmount));
//						sinEntry.setLimitAmount(canRefundmentAmount.add(thisAmount));
						
						SelectorItemCollection payUpdateSels = new SelectorItemCollection();
						payUpdateSels.add("actAmount");
						payUpdateSels.add("canRefundmentAmount");
						SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinEntry, payUpdateSels);											
					}
				}else{
					//根据paylistID封装新收款单上payListId的收款金额
					Map actAmountByPayId = new HashMap();
					for(int i=0; i<fdcRecBillEntryColl.size(); i++){
						FDCReceiveBillEntryInfo fdcRecEntry = fdcRecBillEntryColl.get(i);
						String payId = fdcRecEntry.getPayListId();
						if(payId == null){
							throw new BOSException("找不到对应的付款明细");
						}
						
						BigDecimal amount = fdcRecEntry.getAmount();
						if(amount == null) amount = FDCHelper.ZERO;
						
						if(actAmountByPayId.get(payId) == null){
							actAmountByPayId.put(payId, amount);	
						}else{
							BigDecimal src = (BigDecimal) actAmountByPayId.get(payId);
							actAmountByPayId.put(payId, src.add(amount));
						}
					}
					
					//根据paylistID封装原收款单上payListId的收款金额
					Map oldActAmountByPayId = new HashMap();
					for(int i=0; i<oldFdcRecEnryColl.size(); i++){
						FDCReceiveBillEntryInfo fdcRecEntry = oldFdcRecEnryColl.get(i);
						String payId = fdcRecEntry.getPayListId();
						if(payId == null){
							throw new BOSException("找不到对应的付款明细");
						}
						
						BigDecimal amount = fdcRecEntry.getAmount();
						if(amount == null) amount = FDCHelper.ZERO;
						
						if(oldActAmountByPayId.get(payId) == null){
							oldActAmountByPayId.put(payId, amount);	
						}else{
							BigDecimal src = (BigDecimal) oldActAmountByPayId.get(payId);
							oldActAmountByPayId.put(payId, src.add(amount));
						}
					}
					
					//比较新旧收款单的金额差别，如果有差别，将差额更新到付款明细的实收上
					for(Iterator itor = actAmountByPayId.keySet().iterator(); itor.hasNext(); ){
						String payId = (String) itor.next();
						BigDecimal amount = (BigDecimal) actAmountByPayId.get(payId);
						
						BigDecimal oldAmount = (BigDecimal) oldActAmountByPayId.get(payId);
						if(oldAmount == null){
							logger.error("收款单修改不能改分录条数啊。");
							continue;
						}
						
						BigDecimal balance = amount.subtract(oldAmount);
						if(balance.compareTo(FDCHelper.ZERO) == 0){
							continue;
						}
						
						SincerReceiveEntryInfo sinEntry = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
						
						BigDecimal srcActAmount = sinEntry.getActRevAmount();
						if(srcActAmount == null){
							srcActAmount = FDCHelper.ZERO;
						}
						
						BigDecimal canRefundmentAmount = sinEntry.getCanRefundmentAmount();
						
						sinEntry.setActRevAmount(srcActAmount.add(balance));
//						sinEntry.setLimitAmount(canRefundmentAmount.add(balance));
						
						SelectorItemCollection payUpdateSels = new SelectorItemCollection();
						payUpdateSels.add("actAmount");
						payUpdateSels.add("canRefundmentAmount");
						SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinEntry, payUpdateSels);
					}
				}
			}
		}else{
			if(ReceiveBillTypeEnum.settlement.equals(recBillType))
			{
				//对于红冲收款，主要是设置被红冲的收款单分录的可红冲金额和已红冲金额 
				//TODO 以前的代码是在客户端操作的，需要 搬到服务器端处理。。。
			}
			//如果是退款
			else if (ReceiveBillTypeEnum.refundment.equals(recBillType))
			{
				//一个临时的 payListId 比较值
				String tempComparison = "";
				String tempMoneyId = "";
				
				for(int i = 0; i < fdcRecBillEntryColl.size(); i ++)
				{
					FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
					
					MoneyDefineInfo money = entryInfo.getMoneyDefine();
					
					String payListId = entryInfo.getPayListId();
					//生成一个假 BOSUuid
					if(!BOSUuid.isValidLength(payListId,true))
					{
						payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					}
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					view.getSelector().add("moneyDefine.*");
					view.setFilter(filter);
					
					filter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo == null? null: purchaseInfo.getId().toString()));
					
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));
					
					PurchasePayListEntryCollection purchasePayListEntryColl  = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryCollection(view);
					
					if(purchasePayListEntryColl.size() > 1)
					{
						logger.error("在认购单 "+ purchaseInfo.toString()+"找到多条 id "+ payListId+" 的，付款明细项");
						throw new BOSException("系统数据错误，已记录日志！");
					}
					//非计划性付款和限定性款项
					else if(purchasePayListEntryColl.size() < 1)
					{
						//判断是否是 其他付款明细
						FilterInfo elseFilter = new FilterInfo();
						EntityViewInfo elseView = new EntityViewInfo();
						
						elseView.setFilter(elseFilter);
						
						elseFilter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo == null ? null : purchaseInfo.getId().toString()));
						elseFilter.getFilterItems().add(new FilterItemInfo("id",payListId));
						
						PurchaseElsePayListEntryCollection elsePayColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryCollection(elseView);
						
						BigDecimal actAmount = FDCHelper.ZERO;
						
						BigDecimal refundmentAmount = FDCHelper.ZERO;
						
						//计算可退金额
						BigDecimal canRefundmentAmount = FDCHelper.ZERO;
						
						
						PurchaseElsePayListEntryInfo elsePayInfo = null;
						if(elsePayColl.size() > 1)
						{
							logger.error("认购单为"+purchaseInfo.toString()+",的，其他应付款项ID:"+payListId+" 存在重复的数据...");
							throw new BOSException("系统数据错误，已记录日志！");
						}
						else if(elsePayColl.size() == 1)
						{
							elsePayInfo = elsePayColl.get(0);

							if (elsePayInfo.getRefundmentAmount()!= null)
							{
								refundmentAmount = elsePayInfo.getRefundmentAmount();
							}
							if(elsePayInfo.getCanRefundmentAmount() != null)
							{
								canRefundmentAmount = elsePayInfo.getCanRefundmentAmount();
							}
							
							if(elsePayInfo.getActPayAmount() != null)
							{
								actAmount = elsePayInfo.getActPayAmount();
							}
							
							if (oldReceivingBillInfo != null)
							{
								BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
								BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
						
								//对于相同的付款明细来讲，只要第一次计算即可
								if(!tempComparison.equalsIgnoreCase(payListId) )
								{
									refundmentAmount = refundmentAmount.abs().add(newAmount.abs()).subtract(oldAmount.abs());
									
									canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs()).add(oldAmount.abs());
									
									actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
								}
							}
							else
							{
								BigDecimal newAmount = entryInfo.getAmount();
								if(newAmount == null)
									newAmount = FDCHelper.ZERO;
								
								refundmentAmount = refundmentAmount.abs().add(newAmount.abs());
								
								canRefundmentAmount = canRefundmentAmount.abs().subtract(newAmount.abs());
								
								actAmount = actAmount.subtract(newAmount.abs());
							}
							
							
							elsePayInfo.setActRevAmount(actAmount);
						/*	if(FDCHelper.ZERO.compareTo(actAmount) == 0)
								elsePayInfo.setActPayDate(null);*/
								
							
							elsePayInfo.setLimitAmount(canRefundmentAmount.abs());
							elsePayInfo.setHasRefundmentAmount(refundmentAmount.abs());
							SelectorItemCollection sels = new SelectorItemCollection();
							sels.add("refundmentAmount");
							sels.add("canRefundmentAmount");
							sels.add("actPayAmount");
							//sels.add("actPayDate");
//							PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePayInfo, sels);
						}//像预收款一样的特殊款项的退款
						else
						{
							EntityViewInfo specialView = new EntityViewInfo();
							FilterInfo specialFilter = new FilterInfo();
							specialView.setFilter(specialFilter);
							
							specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
						
							//针对客户的收款对象
							if(GatheringEnum.CustomerRev.equals(gathering))
							{
								CustomerInfo customer = null;
								
								if(fdcReceiveBillInfo.getCustomerEntrys() != null && fdcReceiveBillInfo.getCustomerEntrys().get(0) != null)
									customer = fdcReceiveBillInfo.getCustomerEntrys().get(0).getCustomer();
								
								
								Set fdcBillIdSet = this.getFdcBillSetForCustomer(ctx,customer);
								
								if(fdcBillIdSet != null && fdcBillIdSet.size() > 0)
								{
									specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id", fdcBillIdSet,CompareType.INCLUDE));
								}
								else
								{
									specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",null));
								}
							}
							else
							{
								specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
							}
							
							
							
							specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
							specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
							specialFilter.getFilterItems().add(new FilterItemInfo("moneyDefine", money.getId().toString()));
							
							if(GatheringEnum.CustomerRev.equals(gathering))
							{
								specialFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.gathering",GatheringEnum.CUSTOMERREV_VALUE));
	
								specialFilter.setMaskString("#0 and #1 and (#2 or #3) and #4 and #5");
							}
							else
							{				
								specialFilter.setMaskString("#0 and #1 and (#2 or #3) and #4");
							}
							
							FDCReceiveBillEntryCollection recBillEntryColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(specialView);
							
							BigDecimal amount = entryInfo.getAmount();
							if(amount == null)
								amount = FDCHelper.ZERO;
							
							BigDecimal residualAmount = amount.abs();
							
							BigDecimal oldAmount = FDCHelper.ZERO;
							BigDecimal newAmount = FDCHelper.ZERO;
							String moneyDefineId = money.getId().toString();
							if (oldReceivingBillInfo != null)
							{
								FDCReceiveBillEntryCollection fdcEntrys = oldReceivingBillInfo.getFdcReceiveBillEntry();
								for(int m=0; m<fdcEntrys.size(); m++){
									FDCReceiveBillEntryInfo fdcEntry = fdcEntrys.get(m);
									if(moneyDefineId.equals(fdcEntry.getMoneyDefine().getId().toString())){
										oldAmount = oldAmount.add(fdcEntry.getAmount());
									}
								}
								
								for (int m = 0; m < fdcRecBillEntryColl.size(); m++) {
									FDCReceiveBillEntryInfo fdcEntry = fdcRecBillEntryColl.get(m);
									if(moneyDefineId.equals(fdcEntry.getMoneyDefine().getId().toString())){
										newAmount = newAmount.add(fdcEntry.getAmount());
									}
								}
								
//								oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
//								newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
								
								//这类预订金类型的，不能通过payListId来判断了
								//对于相同的付款明细来讲，只要第一次计算即可
								if(!tempMoneyId.equalsIgnoreCase(moneyDefineId))
								{
									//如果退款金额改小，则residualAmount为负数
									residualAmount = oldAmount.subtract(newAmount);
								}else{
									continue;
								}
							}
							
							for(int k = 0; k < recBillEntryColl.size(); k ++)
							{
								FDCReceiveBillEntryInfo info = recBillEntryColl.get(k);
								BigDecimal canCounteractAmount = info.getCanCounteractAmount();
								BigDecimal actPaymount = info.getAmount();
								if(actPaymount == null)
									actPaymount = FDCHelper.ZERO;
								
								if(canCounteractAmount == null)
									canCounteractAmount = FDCHelper.ZERO;
								
								if (residualAmount.compareTo(FDCHelper.ZERO) >= 0) {
									if (canCounteractAmount.compareTo(residualAmount) >= 0) {
										BigDecimal temp = canCounteractAmount.subtract(residualAmount);
										info.setCanCounteractAmount(temp);
//										info.setAmount(actPaymount.add(oldAmount).subtract(newAmount));
										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);

										break;
									} else {
										info.setCanCounteractAmount(FDCHelper.ZERO);

										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);

										residualAmount = residualAmount.subtract(canCounteractAmount);
									}
								}else{//负数的情况，其实是要增加预定金项的可退金额
									BigDecimal canAddedAmount = residualAmount.abs();
									
									if(actPaymount.subtract(canCounteractAmount).compareTo(canAddedAmount) >= 0){
										BigDecimal temp = canCounteractAmount.add(canAddedAmount);
										info.setCanCounteractAmount(temp);
										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
										break;
									}else{
										BigDecimal temp = actPaymount.subtract(canCounteractAmount);
										if(temp.compareTo(FDCHelper.ZERO) == 0){
											continue;
										}
										info.setCanCounteractAmount(actPaymount);
										SelectorItemCollection selColl = new SelectorItemCollection();
										selColl.add("canCounteractAmount");
//										selColl.add("amount");
										FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
										
										residualAmount = residualAmount.add(temp);
									}
								}
							}
							
							if(!tempMoneyId.equals(money.getId().toString())){
								tempMoneyId = money.getId().toString();
							}
							
						}
						//this.updateStateByisCompleteReceiveInSHE(ctx, receivingBillInfo, oldReceivingBillInfo, purchaseInfo, moneyDefineInfo,actAmount,apAmount);
					}//计划性款项
					else
					{
						PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(0);
					
						BigDecimal refundmentAmount = FDCHelper.ZERO;
						BigDecimal canRefundmentAmount = FDCHelper.ZERO;
						BigDecimal actAmount = FDCHelper.ZERO;
						
						if(purchasePayListEntryInfo.getRefundmentAmount() != null)
						{
							refundmentAmount = purchasePayListEntryInfo.getRefundmentAmount();
						}
						if(purchasePayListEntryInfo.getCanRefundmentAmount() != null)
						{
							canRefundmentAmount = purchasePayListEntryInfo.getCanRefundmentAmount();
						}
						if(purchasePayListEntryInfo.getActPayAmount() != null)
						{
							actAmount = purchasePayListEntryInfo.getActPayAmount();
						}
						
						//如果是修改
						if(oldReceivingBillInfo != null)
						{
							
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//对于相同的付款明细来讲，只要第一次计算即可
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								refundmentAmount = refundmentAmount.add(newAmount.abs()).subtract(oldAmount.abs());
								
								canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs()).add(oldAmount.abs());
								
								actAmount = actAmount.add(oldAmount.abs()).subtract(newAmount.abs());
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							if(newAmount == null)
								newAmount = FDCHelper.ZERO;
							
							refundmentAmount = refundmentAmount.abs().add(newAmount.abs());
							
							canRefundmentAmount = canRefundmentAmount.subtract(newAmount.abs());
							
							actAmount = actAmount.subtract(newAmount.abs());
						}
						purchasePayListEntryInfo.setHasRefundmentAmount(refundmentAmount.abs());
						
						purchasePayListEntryInfo.setLimitAmount(canRefundmentAmount);
						purchasePayListEntryInfo.setActRevAmount(actAmount);
					/*	if(FDCHelper.ZERO.compareTo(actAmount) == 0)
							purchasePayListEntryInfo.setActPayDate(null);*/
						
						SelectorItemCollection sels = new SelectorItemCollection();
						
						sels.add("refundmentAmount");
						sels.add("canRefundmentAmount");
						sels.add("actPayAmount");
						//sels.add("actPayDate");
//						PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purchasePayListEntryInfo, sels);
					}
					//替换比较值
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
			} //以下是计划性付款和非计划性付款
			else
			{
				//一个临时的 payListId 比较值
				String tempComparison = "";
				
				for(int i = 0; i < fdcRecBillEntryColl.size(); i ++)
				{
					FDCReceiveBillEntryInfo entryInfo = fdcRecBillEntryColl.get(i);
					String payListId = entryInfo.getPayListId();
					
					
					//生成一个假 BOSUuid
					if(!BOSUuid.isValidLength(payListId,true))
					{
						payListId = BOSUuid.create(new PurchasePayListEntryInfo().getBOSType()).toString();
					}
					
					MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();
					
					FilterInfo filter = new FilterInfo();
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					view.getSelector().add("moneyDefine.*");
					view.setFilter(filter);
					
					filter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo.getId().toString()));
					
					filter.getFilterItems().add(new FilterItemInfo("id",payListId));
					
					PurchasePayListEntryCollection purchasePayListEntryColl  = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryCollection(view);
					
					if(purchasePayListEntryColl.size() > 1)
					{
						logger.error("在认购单 "+ purchaseInfo.toString()+"找到多条 id "+ payListId+" 的，付款明细项");
						throw new BOSException("系统数据错误，已记录日志！");
					}
					//非计划性付款和限定性款项
					else if(purchasePayListEntryColl.size() < 1)
					{
						//判断是否是 其他付款明细
						FilterInfo elseFilter = new FilterInfo();
						EntityViewInfo elseView = new EntityViewInfo();
						
						elseView.setFilter(elseFilter);
						
						elseFilter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo.getId().toString()));
						elseFilter.getFilterItems().add(new FilterItemInfo("id",payListId));
						
						PurchaseElsePayListEntryCollection elsePayColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryCollection(elseView);
						
						BigDecimal apAmount = FDCHelper.ZERO;
						BigDecimal actAmount = FDCHelper.ZERO;
						PurchaseElsePayListEntryInfo elsePayInfo = null;
						if(elsePayColl.size() > 1)
						{
							logger.error("认购单为"+purchaseInfo.toString()+",的，其他应付款项ID:"+payListId+" 存在重复的数据...");
							throw new BOSException("系统数据错误，已记录日志！");
						}
						else if(elsePayColl.size() == 1)
						{
							elsePayInfo = elsePayColl.get(0);

							if (elsePayInfo.getActPayAmount() != null)
							{
								actAmount = elsePayInfo.getActPayAmount();
							}
							apAmount = elsePayInfo.getApAmount();
							if (apAmount == null)
							{
								apAmount = FDCHelper.ZERO;
							}
							
							BigDecimal canRefundmentAmount = FDCHelper.ZERO;
							if(elsePayInfo.getCanRefundmentAmount() != null)
								canRefundmentAmount = elsePayInfo.getCanRefundmentAmount();
							
							
							
							if (oldReceivingBillInfo != null)
							{
								BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
								BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
						
								//对于相同的付款明细来讲，只要第一次计算即可
								if(!tempComparison.equalsIgnoreCase(payListId) )
								{
									actAmount = actAmount.add(newAmount).subtract(oldAmount);
									
									canRefundmentAmount = canRefundmentAmount.add(newAmount).subtract(oldAmount);
								}
							}
							else
							{
								BigDecimal newAmount = entryInfo.getAmount();
								actAmount = actAmount.add(newAmount);
								
								canRefundmentAmount = canRefundmentAmount.add(newAmount);
							}
							
							elsePayInfo.setLimitAmount(canRefundmentAmount);
							elsePayInfo.setActRevAmount(actAmount);
							elsePayInfo.setActRevDate(receivingBillInfo.getBizDate());
							elsePayInfo.setLastUpdateTime(new Timestamp((new Date()).getTime()));
							
							
							if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0)
							{
							elsePayInfo.setActRevDate(null);
							}
							
							SelectorItemCollection sels = new SelectorItemCollection();
							sels.add("actPayAmount");
							sels.add("actPayDate");
							sels.add("canRefundmentAmount");
							sels.add("lastUpdateTime");
//							PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePayInfo, sels);
						}
						else
						{
						}
						
						this.updateStateByisCompleteReceiveInSHE(ctx, receivingBillInfo, oldReceivingBillInfo, purchaseInfo, actAmount,apAmount,entryInfo);
					}//计划性款项
					else
					{
						PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(0);
					
						BigDecimal appAmount = FDCHelper.ZERO;
						BigDecimal actAmount = FDCHelper.ZERO;
						
						if(purchasePayListEntryInfo.getApAmount() != null)
						{
							appAmount = purchasePayListEntryInfo.getApAmount();
						}
						if(purchasePayListEntryInfo.getActPayAmount() != null)
						{
							actAmount = purchasePayListEntryInfo.getActPayAmount();
						}
						
						//如果是修改
						if(oldReceivingBillInfo != null)
						{
							
							BigDecimal oldAmount = this.getOldAmount(ctx,oldReceivingBillInfo.getId().toString(),payListId);
							BigDecimal newAmount = this.getNewAmount(ctx,fdcRecBillEntryColl,payListId);
					
							//对于相同的付款明细来讲，只要第一次计算即可
							if(!tempComparison.equalsIgnoreCase(payListId) )
							{
								actAmount = actAmount.add(newAmount).subtract(oldAmount);
							}
						}
						else
						{
							BigDecimal newAmount = entryInfo.getAmount();
							actAmount = actAmount.add(newAmount);
						}
						purchasePayListEntryInfo.setActRevAmount(actAmount);
						purchasePayListEntryInfo.setActRevDate(receivingBillInfo.getBizDate());
						
						purchasePayListEntryInfo.setLastUpdateTime(new Timestamp((new Date()).getTime()));
						
						if(actAmount == null || actAmount.compareTo(FDCHelper.ZERO) == 0)
						{
							purchasePayListEntryInfo.setActRevDate(null);
						}
						
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("actPayAmount");
						sels.add("actPayDate");
						sels.add("lastUpdateTime");
//						PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purchasePayListEntryInfo, sels);
						
						this.updateStateByisCompleteReceiveInSHE(ctx, receivingBillInfo, oldReceivingBillInfo, purchaseInfo, actAmount,appAmount,entryInfo);
						
					    //收售楼款，对于第一笔 计划款项的判断，和认购单的改变
						if(this.isFirstPlanMoney(ctx,payListId,purchaseInfo))
						{
							this.setPurchaseStateByFirstPlanMoney(ctx,receivingBillInfo,purchaseInfo,actAmount,appAmount);
						}
					}
					//替换比较值
					if(!tempComparison.equalsIgnoreCase(payListId))
					{
						tempComparison = payListId;
					}
				}
			}
		}
		this.submit(ctx, fdcReceiveBillInfo);		
		receivingBillId = ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo).toString();
		this.rewriteCheque(ctx,fdcReceiveBillInfo,purchaseInfo,receivingBillInfo);
		//PurchaseFactory.getLocalInstance(ctx).receiveBill(purchaseInfo.getId());
//		if (purchaseInfo != null)
//		{
//			if (FDCBillStateEnum.AUDITTED.equals(purchaseInfo.getState()))
//			{
//				// 将该认购单的相关工作流停止
//				ProcessInstInfo instInfo = null;
//				ProcessInstInfo[] procInsts = null;
//				IEnactmentService service = EnactmentServiceFactory
//						.createEnactService(ctx);
//				procInsts = service.getProcessInstanceByHoldedObjectId(purchaseInfo.getId().toString());
//				for (int j = 0; j < procInsts.length; j++)
//				{
//					if ("open.running".equals(procInsts[j].getState()))
//					{
//						instInfo = procInsts[j];
//						service.abortProcessInst(instInfo.getProcInstId());
//					}
//				}
//			}
//		}
//		
		return receivingBillId;
	
	}
	
	/**
	 * 得到旧收款单，某一款项缴纳的值
	 * @param ctx
	 * @param recId
	 * @param payListId
	 * @return
	 * @throws BOSException
	 */
	private BigDecimal getOldAmount(Context ctx,String recId,String payListId) throws BOSException
	{
		BigDecimal oldAmount = FDCHelper.ZERO;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",recId));
		filter.getFilterItems().add(new FilterItemInfo("payListId",payListId));
		
		FDCReceiveBillEntryCollection 	fdcRecColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
		
		for(int i = 0; i < fdcRecColl.size(); i ++)
		{
			BigDecimal temp = fdcRecColl.get(i).getAmount();
			
			if(temp == null)
				temp = FDCHelper.ZERO;
			
		    oldAmount = oldAmount.add(temp);
		}
		if(oldAmount == null)
			oldAmount = FDCHelper.ZERO;
		return oldAmount;
	}
	
	/**
	 * 取得征缴收款的这个收款单，它某项收款明细要缴纳的值
	 * @param ctx
	 * @param fdcEntryColl
	 * @param payListId
	 * @return
	 */
	private BigDecimal getNewAmount(Context ctx,FDCReceiveBillEntryCollection fdcEntryColl,String payListId)
	{
		BigDecimal newAmount = FDCHelper.ZERO;
		
		for(int i = 0; i < fdcEntryColl.size(); i ++)
		{
			if(payListId.equalsIgnoreCase(fdcEntryColl.get(i).getPayListId()))
			{
				BigDecimal temp =  fdcEntryColl.get(i).getAmount();
				
				newAmount = newAmount.add(temp);
			}
		}
		return newAmount;
	}
	
	
	private FDCReceiveBillEntryCollection getFdcRecEntryInfo(Context ctx, String payListId) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
//		filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",recId));
		filter.getFilterItems().add(new FilterItemInfo("payListId",payListId));
		
		return FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
	}
	
	
	
	
	
	/**
	 * 判断当前收款单，是否是付款计划里面的第一条
	 * 注意引用该方法的地方，判断的前提是该条收款是计划性收款
	 * @param receivingBillInfo
	 * @param purchase
	 * @return
	 */
	private boolean isFirstPlanMoney(ReceivingBillInfo receivingBillInfo,PurchaseInfo purchase)
	{
		boolean debug = true;
		FDCReceiveBillInfo fdc = receivingBillInfo.getFdcReceiveBill();
		int seq = fdc.getSeq();
		PurchasePayListEntryCollection payColl = purchase.getPayListEntry();
		for(int i = 0; i < payColl.size(); i ++)
		{
			PurchasePayListEntryInfo info = payColl.get(i);
			int temp = info.getSeq();
			if(seq > temp)
			{
				debug = false;
				break;
			}
		}
		return debug;
	}
	

	/**
	 * 判断当前收款单，是否是付款计划里面的第一条
	 * 注意引用该方法的地方，判断的前提是该条收款是计划性收款
	 * @param payListId
	 * @param purchase
	 * @return
	 * @throws BOSException 
	 */
	private boolean isFirstPlanMoney(Context ctx, String payListId,PurchaseInfo purchase) throws BOSException
	{
		boolean debug = false;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("head",purchase.getId().toString()));
		view.getSelector().add("id");
		
		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorColl);
		
		PurchasePayListEntryCollection payColl = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryCollection(view);
		
		if(payColl.get(0) == null)
		{
			logger.error("在认购单："+purchase.toString()+" 中，找不到 ID 为："+ payListId+" 的付款明细...");
			throw new BOSException("出现错误，请查看服务端日志！");
		}
		
		if(payListId.equalsIgnoreCase(payColl.get(0).getId().toString()))
		{
			debug = true;
		}
		else
		{
			debug = false;
		}
		return debug;
	}
	
	/**
	 * 判断该房间，是否存在 审批状态下的退房单
	 * @param room
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isExistChangeBill(Context ctx,RoomInfo room) throws EASBizException, BOSException
	{
		boolean debug = false;
		
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("oldRoom",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		
		debug = ChangeRoomFactory.getLocalInstance(ctx).exists(filter);
		
		return debug;
	}
	
	
	/**
	 * 根据是否完成了第一个付款计划，来改变认购单的状态
	 * @param ctx
	 * @param receivingBillInfo
	 * @param purchaseInfo
	 * @param actAmount
	 * @param apAmount
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void setPurchaseStateByFirstPlanMoney(Context ctx,ReceivingBillInfo receivingBillInfo,PurchaseInfo purchaseInfo,BigDecimal actAmount,BigDecimal apAmount) throws EASBizException, BOSException
	{
		Date bizDate = receivingBillInfo.getBizDate();
		RoomSellStateEnum state = purchaseInfo.getRoom().getSellState();
		
		//如果存在换房单，则不去改变状态了。
		/*if(this.isExistChangeBill(ctx, purchaseInfo.getRoom()))
			return;*/
		//如果该 认购单的状态时作废的，就不用管了
		if(PurchaseStateEnum.ChangeRoomBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.QuitRoomBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.NoPayBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.ManualBlankOut.equals(purchaseInfo.getPurchaseState())
				|| PurchaseStateEnum.AdjustBlankOut.equals(purchaseInfo.getPurchaseState())
				)
			return;
		
		
		if (actAmount.compareTo(apAmount) >= 0)
		{
			if(!RoomSellStateEnum.Sign.equals(state))
			// 把收款的单据日期回写道房间的认购日期中去
			RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.Purchase, purchaseInfo.getId().toString(), bizDate);
		}//如果是删除了这第一笔收款，则变为 代售的状态
		else if(actAmount.compareTo(FDCHelper.ZERO) == 0)
		{
//			RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.OnShow, purchaseInfo.getId().toString(), bizDate);
			RoomStateFactory.setRoomOnShow(ctx, purchaseInfo.getRoom().getId().toString(), false);
		}
		else
		{
			//-- 房间转预定日期也要以收款日期为准  zhicheng_jin 090313
			RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.PrePurchase, purchaseInfo.getId().toString(), bizDate);
			//-------- over ----------
		}
	}

	/**
	 * 根据相应的实收金额，应收金额，来决定一些状态的变化
	 * @param ctx
	 * @param receivingBillInfo
	 * @param oldReceivingBillInfo
	 * @param purchaseInfo
	 * @param actAmount
	 * @param apAmount
	 * @param entryInfo TODO
	 * @throws BOSException
	 * @throws EASBizException
	 * @author laiquan_luo
	 */
	private void updateStateByisCompleteReceiveInSHE(Context ctx, ReceivingBillInfo receivingBillInfo, ReceivingBillInfo oldReceivingBillInfo, PurchaseInfo purchaseInfo, BigDecimal actAmount,BigDecimal apAmount,FDCReceiveBillEntryInfo entryInfo) throws BOSException, EASBizException
	{
		MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();
		
		RoomInfo room = purchaseInfo.getRoom();
		
		
		//达到预定金标准才改变状态
		if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))
		{
			if (RoomSellStateEnum.PrePurchase.equals(room.getSellState())
					|| RoomSellStateEnum.OnShow.equals(room.getSellState()))
			{
				if (this.isCompleteReceivePreMoney(ctx, entryInfo, purchaseInfo))
				{
					RoomStateFactory.setRoomSellState(ctx,RoomSellStateEnum.PrePurchase, purchaseInfo.getId().toString(),receivingBillInfo.getBizDate());
				}
				// 如果删除了该预收款，则要将房间置为待售状态
				else
				{
					RoomStateFactory.setRoomOnShow(ctx, purchaseInfo.getRoom().getId().toString(), false);
				}
			}
		}
		else if (moneyDefineInfo.getMoneyType().equals(MoneyTypeEnum.CompensateAmount))
		{
			this.changeAreaCompensate(ctx, receivingBillInfo, entryInfo);
		}
	}
	

	/**
	 * 更新面积补差的状态，判断当所受的面积补差的款项，大于应补差的金额，把面积补差的状态改为已收款的状态
	 * @param ctx
	 * @param receivingBillInfo
	 * @param entryInfo TODO
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void changeAreaCompensate(Context ctx, ReceivingBillInfo receivingBillInfo, FDCReceiveBillEntryInfo entryInfo)throws BOSException, EASBizException
	{
		BigDecimal apAmount = FDCHelper.ZERO;
		BigDecimal actAmount = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (receivingBillInfo.getId() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.id", receivingBillInfo.getId().toString(),CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", receivingBillInfo.getFdcReceiveBill().getPurchase().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.COMPENSATEAMOUNT_VALUE));
		
		FDCReceiveBillEntryCollection entryColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", receivingBillInfo.getFdcReceiveBill().getRoom().getId().toString()));
		view.setFilter(filter);
		RoomAreaCompensateCollection roomAreaCompensateColl = RoomAreaCompensateFactory.getLocalInstance(ctx).getRoomAreaCompensateCollection(view);
		if (roomAreaCompensateColl.size() == 1)
		{
			RoomAreaCompensateInfo roomAreaCompensateInfo = roomAreaCompensateColl.get(0);
			if (roomAreaCompensateInfo.getCompensateAmount() != null)
			{
				apAmount = roomAreaCompensateInfo.getCompensateAmount();
			}
			actAmount = FDCHelper.ZERO;
			for (int i = 0; i < entryColl.size(); i++)
			{
				FDCReceiveBillEntryInfo aRev = entryColl.get(i);
				BigDecimal amount = aRev.getAmount();
				if(amount == null)
					amount = FDCHelper.ZERO;
				actAmount = actAmount.add(amount);
			}
			actAmount = actAmount.add(entryInfo.getAmount() == null? FDCHelper.ZERO:entryInfo.getAmount());
			actAmount = actAmount.abs();
			apAmount = apAmount.abs();
			if (actAmount.compareTo(apAmount) >= 0)
			{
				roomAreaCompensateInfo.setCompensateState(RoomCompensateStateEnum.COMRECEIVED);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("compensateState");
				RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(roomAreaCompensateInfo, sels);
			}
			else
			{
				roomAreaCompensateInfo.setCompensateState(RoomCompensateStateEnum.COMAUDITTED);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("compensateState");
				RoomAreaCompensateFactory.getLocalInstance(ctx).updatePartial(roomAreaCompensateInfo, sels);
			}
		}
	}
	/**
	 * 反写单据
	 * @param ctx
	 * @param fdcReceiveBillInfo
	 * @param purchaseInfo
	 * @param receivingBillInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws UuidException
	 */
	private void rewriteCheque(Context ctx,FDCReceiveBillInfo fdcReceiveBillInfo,PurchaseInfo purchaseInfo,ReceivingBillInfo receivingBillInfo) throws EASBizException, BOSException, UuidException
	{
		// 反写收据相关信息
		ChequeInfo chequeInfo = fdcReceiveBillInfo.getCheque();
		if (chequeInfo != null)
		{
			SelectorItemCollection selectorItemColl = new SelectorItemCollection();
			selectorItemColl.add("name");
			selectorItemColl.add("building.name");
			selectorItemColl.add("building.sellProject.name");
			selectorItemColl.add("building.subarea.name");
			
			//增加诚意金收款后，对于诚意认购收款，这里会空指针 TODO 后面需要针对诚意认购的票据反写确定规则
			if(purchaseInfo != null){
				BOSUuid roomId = null;
				if(purchaseInfo.getRoom()!=null)
					roomId = purchaseInfo.getRoom().getId();
				if(roomId==null && fdcReceiveBillInfo.getRoom()!=null)
					roomId = fdcReceiveBillInfo.getRoom().getId();
				
				RoomInfo room = RoomFactory.getLocalInstance(ctx).getRoomInfo(
						new ObjectUuidPK(roomId), selectorItemColl);

				StringBuffer sb = new StringBuffer();
				sb.append(room.getBuilding().getSellProject().getName());
				sb.append("-");

				SubareaInfo subarea = room.getBuilding().getSubarea();
				if (subarea != null)
				{
					sb.append(subarea.getName());
					sb.append("-");
				}

				// sb.append(" 楼栋：");
				sb.append(room.getBuilding().getName());
				sb.append("-");

				// sb.append(" 房间：");
				sb.append(room.getNumber());
				sb.append("，");

				// sb.append(" 收款类型：");
				sb.append(receivingBillInfo.getRecBillType().getName());

				chequeInfo.setResume(sb.toString());
			}

			chequeInfo.setAmount(receivingBillInfo.getAmount());
			BigDecimal invoiceAmount = receivingBillInfo.getAmount();
			if (invoiceAmount == null)			{
				invoiceAmount = new BigDecimal("0");
			}else{
				Format u = NTNumberFormat.getInstance("rmb");
				if(invoiceAmount.compareTo(new BigDecimal("0"))>=0) {
					chequeInfo.setCapitalization("收:"+u.format(invoiceAmount));
				}else{					
					chequeInfo.setCapitalization("退:"+u.format(invoiceAmount.negate()));
				}
			}			
			
			chequeInfo.setStatus(ChequeStatusEnum.WrittenOff);
			chequeInfo.setPayer(receivingBillInfo.getPayerName());
			chequeInfo.setPayTime(new Timestamp(new Date().getTime()));
			UserInfo curUser = ContextUtil.getCurrentUserInfo(ctx);
			chequeInfo.setWrittenOffer(curUser);
			chequeInfo.setWrittenOffTime(new Timestamp(new Date().getTime()));

			SelectorItemCollection sles = new SelectorItemCollection();
			sles.add("resume");
			sles.add("amount");
			sles.add("status");
			sles.add("capitalization");

			sles.add("payer");
			sles.add("payTime");
			sles.add("writtenOffer");
			sles.add("writtenOffTime");
			ChequeFactory.getLocalInstance(ctx).updatePartial(chequeInfo, sles);
		}
	}

	/**
	 * 只要预收款大于零就OK了
	 * @author laiquan_luo
	 * @param entryInfo TODO
	 * @param purchase TODO
	 * @return
	 * @throws BOSException 
	 */
	private boolean isCompleteReceivePreMoney(Context ctx,FDCReceiveBillEntryInfo entryInfo, PurchaseInfo purchase) throws BOSException
	{
		boolean debug = false;
		
		BigDecimal amount = entryInfo.getAmount();
		MoneyDefineInfo moneyDefineInfo = entryInfo.getMoneyDefine();
		if(amount == null)
			amount = FDCHelper.ZERO;
		
		if(amount.compareTo(FDCHelper.ZERO) > 0)
		{
			debug = true;
		}
		else
		{
			FilterInfo filter = new FilterInfo();
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", moneyDefineInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.billStatus", BillStatusEnum.SAVE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.GATHERING_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.billTypeEnum",ReceiveBillTypeEnum.TRANSFER_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchase.getId().toString()));
			
			filter.setMaskString("#0 and #1 and (#2 or #3) and #4");
			FDCReceiveBillEntryCollection billColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
			
			Set set = new HashSet();
			for(int i = 0; i < billColl.size(); i ++)
			{
				if(billColl.get(i).getReceivingBill() != null && billColl.get(i).getReceivingBill().getId() != null)
				set.add(billColl.get(i).getReceivingBill().getId().toString().trim());
			}
			
			if(set.size() > 1)
			{
				debug = true;
			}
			else
			{
				debug = false;
			}
		}
			
		
		return debug;
		
	
	}
	
	/**
	 *  判断该合同是否收齐押金首租，这里只判断了 是否收齐首租，因为首租收齐了，那押金肯定收齐了。
	 * @param ctx
	 * @param tennacyBillInfo
	 * @return
	 * @throws BOSException
	 */
	private boolean isCompleteReceiveDepositAndFirstMoney(Context ctx,TenancyBillInfo tennacyBillInfo) throws BOSException
	{
		boolean debug = false;
		
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tennacyBillInfo.getId().toString()));
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getLocalInstance(ctx).getTenancyRoomEntryCollection(view);
		for(int i = 0; i < tenancyRoomEntryColl.size(); i ++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
			//有一个房间没有交齐，即返回false
			if(!this.isCompleteReceiveDepositAndFirstMoney(ctx,tenancyRoomEntryInfo))
			{
				debug = false;
				break;
			}
			{
				debug = true;
			}
		}
		return debug;
	}
	/**
	 * 判断该房间分录是否收齐押金首租，这里只判断了 是否收齐首租，因为首租收齐了，那押金肯定收齐了。
	 * @param ctx
	 * @param tenancyRoomEntryInfo
	 * @return
	 * @throws BOSException
	 */
	private boolean isCompleteReceiveDepositAndFirstMoney(Context ctx,TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException
	{	
		TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = this.getTheFirstRentAmount(ctx,tenancyRoomEntryInfo);
		if(tenancyRoomPayListEntryInfo == null)
		{
			logger.warn("在房间编号为" + tenancyRoomEntryInfo.getRoom().getNumber()+ "的房间下面，没有付款明细！系统直接标识了该合同未交押金首租！");
			return false;
		}
		BigDecimal appAmount = FDCHelper.ZERO;
		if(tenancyRoomPayListEntryInfo.getAppAmount() != null)
		{
			appAmount = tenancyRoomPayListEntryInfo.getAppAmount();
		}
		BigDecimal actAmount = FDCHelper.ZERO;
		if(tenancyRoomPayListEntryInfo.getActAmount() != null)
		{
			actAmount = tenancyRoomPayListEntryInfo.getActAmount();
		}
		if(actAmount.compareTo(appAmount) >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 找到一个房间下面是首租的的那条付款明细，而不是简单的固定通过  seq 为 1 来判断，这样做可以适应租赁付款明细的变动。
	 * @param tenancyBillInfo
	 * @return
	 * @throws BOSException 
	 */
	private TenancyRoomPayListEntryInfo getTheFirstRentAmount(Context ctx,TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException
	{
		TenancyRoomPayListEntryInfo returnValue = null;
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("moneyDefine.*");
		view.setFilter(filter);
		
		SorterItemCollection sorterColl = new SorterItemCollection();
		sorterColl.add(new SorterItemInfo("seq"));
		view.setSorter(sorterColl);
		
		filter.getFilterItems().add(new FilterItemInfo("tenRoom",tenancyRoomEntryInfo.getId().toString()));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryCollection(view); 
		if(tenancyRoomPayListEntryColl == null)
		{
			logger.warn("在房间编号为" + tenancyRoomEntryInfo.getRoom().getNumber()+ "的房间下面，没有付款明细！");
			return null;
		}
		//第一条出现的 租金 即是 首租
		for(int i = 0; i < tenancyRoomPayListEntryColl.size(); i ++)
		{
			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(i);
			if(MoneyTypeEnum.RentAmount.equals(tenancyRoomPayListEntryInfo.getMoneyDefine().getMoneyType()))
			{
				returnValue = tenancyRoomPayListEntryInfo;
				break;
			}
		}
		return returnValue;
	}

	/**
	 * 批量收款
	 * 
	 * @author laiquan_luo
	 */
	protected void _submitByCasRevColl(Context ctx, IObjectCollection casRevColl)
			throws BOSException, EASBizException
	{
		for (int i = 0; i < casRevColl.size(); i++)
		{
			this._submitByCasRev(ctx, casRevColl.getObject(i));
		}

	}

	
}