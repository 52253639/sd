package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActGroupDetailCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashPayInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashRec;
import com.kingdee.eas.fi.cas.AssItemsForCashRecInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IPaymentBillType;
import com.kingdee.eas.fi.cas.IReceivingBillType;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeCollection;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.ReceivingBillTypeCollection;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.app.ArApRecPayServerHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class ReceiveGatherControllerBean extends AbstractReceiveGatherControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherControllerBean");
    
    ArApRecPayServerHelper arapHelper = new ArApRecPayServerHelper();
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
    }
    /*
     * 出纳汇总单提交时改写对应收款明细上的是否汇总字段
     */
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	ReceiveGatherInfo revGatherInfo = (ReceiveGatherInfo)model;
//    	revGatherInfo = this.getRevGatherInfo(ctx, revGatherInfo.getId());
    	ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
    	SelectorItemCollection sel=new SelectorItemCollection();
    	sel.add("parent.*");
    	for(int i=0;i<revEntryColl.size();i++)
    	{
    		ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
    		SHERevBillEntryInfo sheRevEntryInfo = SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revEntryInfo.getSheRevBill().getId()), sel);
    		sheRevEntryInfo.setIsGathered(true);
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("isGathered");
    		SHERevBillFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo, sels);
    		
    		sheRevEntryInfo.getParent().setIsGathered(true);
    		SHERevBillFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo.getParent(), sels);
    	}
    	return super._submit(ctx, model);
    }
    
    /*
     *银行放款生成的出纳汇总单不允许删除、收付款单直接生成的汇总单删除时
     *要清除对应收付款单上是否汇总字段 
     */
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
//    	EntityViewInfo view = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("receiveGather.id", pk.toString(),CompareType.EQUALS));
//    	view.setFilter(filter);
//    	BankPaymentCollection bankColl = BankPaymentFactory.getLocalInstance(ctx).getBankPaymentCollection(view);
//    	if(bankColl.size()>0)
//    	{
//    		throw new EASBizException(new NumericExceptionSubItem("100","银行放款生成的出纳汇总单不允许直接删除，放款单反审批会自动删除！"));
//    	}else
//    	{
    		ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, BOSUuid.read(pk.toString()));
        	ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
        	
        	SelectorItemCollection sel=new SelectorItemCollection();
        	sel.add("parent.*");
        	
        	for(int i=0;i<revEntryColl.size();i++)
        	{
        		ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
        		SHERevBillEntryInfo sheRevEntryInfo = SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revEntryInfo.getSheRevBill().getId()), sel);
        		sheRevEntryInfo.setIsGathered(false);
        		SelectorItemCollection sels = new SelectorItemCollection();
        		sels.add("isGathered");
        		SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo, sels);
        		
        		sheRevEntryInfo.getParent().setIsGathered(false);
        		SHERevBillFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo.getParent(), sels);
        	}
//    	}   		   	   	
    	super._delete(ctx, pk);
    }
    
    /*
     * 汇总单审批时自动生成对应的出纳收款单
     */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
//    	ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, billId);
    	this.createReceivingBill(ctx, billId);
    	super._audit(ctx, billId);
    }
    
    /*
     * 汇总单反审批时删除对应的出纳收款单,并且要更改对应汇总是否生成出纳单据的状态
     */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, billId);
    	delReceiveingBill(ctx,revGatherInfo);
    	updateReceiveGatTOUnaudit(ctx,revGatherInfo);
    	super._unAudit(ctx, billId);
    }
    
    private void updateReceiveGatTOUnaudit(Context ctx,ReceiveGatherInfo revGatherInfo) throws EASBizException, BOSException
    {
    	revGatherInfo.setFiRevOrPay(false);
		revGatherInfo.setReceivingBill(null);
		revGatherInfo.setPaymentBill(null);
		ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
		for(int i=0;i<revEntryColl.size();i++)
		{
			ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
			revEntryInfo.setReceivingBillEntry(null);
			revGatherInfo.getEntry().add(revEntryInfo);
		}
		//出纳收款单删除时，更改房地产汇总单是否生成出纳单据的状态以及删除和房地产汇总单及汇总单分录的关联
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("fiRevOrPay");
		sels.add("receivingBill");
		sels.add("paymentBill");
		sels.add("entry.receivingBillEntry");
		ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, sels);
    }
    
    private void delReceiveingBill(Context ctx,ReceiveGatherInfo revGatherInfo) throws EASBizException, BOSException
    {
    	ReceivingBillInfo  receivingBillInfo = revGatherInfo.getReceivingBill();
    	if(receivingBillInfo!=null)
    	{
    		FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("id", receivingBillInfo.getId().toString(),CompareType.EQUALS));
        	if(ReceivingBillFactory.getLocalInstance(ctx).exists(filter)){
    			throw new EASBizException(new NumericExceptionSubItem("100","请先删除对应的出纳收款单"));
        	}
    	}  
    	PaymentBillInfo  paymentBillInfo = revGatherInfo.getPaymentBill();
    	if(paymentBillInfo!=null)
    	{
    		FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("id", paymentBillInfo.getId().toString(),CompareType.EQUALS));
        	if(PaymentBillFactory.getLocalInstance(ctx).exists(filter)){
    			throw new EASBizException(new NumericExceptionSubItem("100","请先删除对应的出纳付款款单"));
        	}
    	}  
    }
    
    protected void _createReceivingBill(Context ctx, BOSUuid billID) throws BOSException, EASBizException{
		ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, billID);
		if(revGatherInfo.getRevBillType().equals(RevBillTypeEnum.refundment)){
			this.createPayBill(ctx, revGatherInfo);
		}else{
			this.createReceivingBill(ctx, revGatherInfo);
		}
	}
    private void createPayBill(Context ctx,ReceiveGatherInfo revGatherInfo)throws EASBizException, BOSException {
    	PaymentBillInfo payment=new PaymentBillInfo();
    	revGatherInfo.setPaymentBill(payment);
    	revGatherInfo.setFiRevOrPay(true);
    	//得到出纳系统的默认的收款类型
    	PaymentBillTypeInfo paymentBillTypeInfo = getPaymentBillType(ctx);
			
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		CtrlUnitInfo cuInfo = ContextUtil.getCurrentCtrlUnit(ctx);
		
		payment.setPayBillType(paymentBillTypeInfo);
		payment.setSourceType(SourceTypeEnum.CASH);
		payment.setSourceSysType(SourceTypeEnum.CASH);
		//公司
		payment.setCompany(revGatherInfo.getCompany());
		//业务日期
		payment.setBizDate(revGatherInfo.getBizDate());
		//汇率
		payment.setExchangeRate(revGatherInfo.getExchangeRate());
		//币别
		payment.setCurrency(revGatherInfo.getCurrency());
		//收款金额
		payment.setActPayAmt(revGatherInfo.getSumAmount());
		
		payment.setCreator(userInfo);
		payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		payment.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		payment.setLastUpdateUser(userInfo);
		payment.setCU(cuInfo);
		
		payment.setNumber(getNumber(ctx,payment,revGatherInfo));
		
		/**
		 * 添加收款人
		 */
//		CustomerInfo custInfo  = revGatherInfo.getCustomer();
//		if(custInfo!=null){
//			receivingBillInfo.setPayerID(custInfo.getId().toString());
//			receivingBillInfo.setPayerNumber(custInfo.getNumber());
//			receivingBillInfo.setPayerName(custInfo.getName());
//		}
		//折本位币
		BigDecimal actrecLocAmt = FDCHelper.ZERO;
		BigDecimal ecchangeRate = FDCHelper.ONE;
		
		if(revGatherInfo.getExchangeRate()!=null){
			ecchangeRate = revGatherInfo.getExchangeRate();
		}
		
		actrecLocAmt  = FDCHelper.multiply(revGatherInfo.getSumAmount(), ecchangeRate);
		
		payment.setActPayLocAmt(actrecLocAmt);
		
		//收款科目
		if(revGatherInfo.getRevAccount()!=null){
			payment.setPayerAccount(revGatherInfo.getRevAccount());	
		}
		
		// 检查科目合法性
//		try {
//			arapHelper.verifyAccountView(ctx, revGatherInfo.getRevAccount(), receivingBillInfo
//					.getCurrency(), receivingBillInfo.getCompany());
//		} catch (EASBizException e) {
//			logger.error(e.getMessage()+"科目不按指定币别核算，请选择其它币别！");
//			throw new BOSException("科目不按指定币别核算，请选择其它币别！");
//		}
		
		//收款账户
		if(revGatherInfo.getAccountBank()!=null){
			payment.setPayerAccountBank(revGatherInfo.getAccountBank());	
		}
		payment.setPayerBank(revGatherInfo.getBank());
		
		//结算方式
		payment.setSettlementType(revGatherInfo.getSettlementType());
		//结算号
		payment.setSettlementNumber(revGatherInfo.getSettlementNumber());
		
		/**
		 * 设置非空字段
		 */
		payment.setPaymentBillType(CasRecPayBillTypeEnum.RealType);
		payment.setIsExchanged(false);
		payment.setIsInitializeBill(false);
		payment.setIsImport(false);
		payment.setFiVouchered(false);
		payment.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		payment.setIsAppointVoucher(false);
		payment.setIsCoopBuild(false);
//		receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
		
		//单据状态
		payment.setBillStatus(BillStatusEnum.SAVE);
		//原始单据id
		payment.setSourceBillId(revGatherInfo.getId().toString());
		
		payment.setDescription(revGatherInfo.getProject().getName()+"款项");
		
		SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("asstActGpDt.asstActType.*");
    	sels.add("asstActGpDt.*");
		/**
		 * 以下是分录内容
		 */
		ReceiveGatherEntryCollection revGatherEntryColl = revGatherInfo.getEntry();
		for(int i=0;i<revGatherEntryColl.size();i++)
		{
			ReceiveGatherEntryInfo revGatherEntryInfo = revGatherEntryColl.get(i);
			PaymentBillEntryInfo payBillEntryInfo = new PaymentBillEntryInfo();
			payBillEntryInfo.setActualAmt(revGatherEntryInfo.getRevAmount().negate());
			payBillEntryInfo.setSeq(i+1);
			
			if(revGatherEntryInfo.getOppAccount()!=null)
			{
				payBillEntryInfo.setOppAccount(revGatherEntryInfo.getOppAccount());
				if(revGatherEntryInfo.getOppAccount().getCAA()!=null){
					AsstAccountInfo account=AsstAccountFactory.getLocalInstance(ctx).getAsstAccountInfo(new ObjectUuidPK(revGatherEntryInfo.getOppAccount().getCAA().getId()),sels);
					AsstActGroupDetailCollection aacol=account.getAsstActGpDt();
					CRMHelper.sortCollection(aacol, "seq", true);
					
					for(int j=0;j<aacol.size();j++){
						AsstActTypeInfo asstActType=aacol.get(j).getAsstActType();
						AssItemsForCashPayInfo ass=new AssItemsForCashPayInfo();
						ass.setTableName(asstActType.getRealtionDataObject());
						ass.setMappingFileds(asstActType.getMappingFieldName());
						ass.setAsstActType(asstActType);
						ass.setIsSelected(false);
						ass.setSeq(aacol.get(j).getSeq());
						ass.setEntrySeq(i+1);
						
						if(asstActType.getRealtionDataObject().equals("T_BD_Customer")){
							if(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer()!=null){
								ass.setFromID(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getNumber());
								ass.setIsSelected(true);
							}
						}else if(asstActType.getRealtionDataObject().equals("T_SHE_Room")){
							if(revGatherEntryInfo.getRoom()!=null){
								ass.setFromID(revGatherEntryInfo.getRoom().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getRoom().getNumber());
								ass.setIsSelected(true);
							}
						}
						payBillEntryInfo.getAssItemsEntries().add(ass);
					}
				}
			}	
			payBillEntryInfo.setRemark(revGatherEntryInfo.getRemark());
			payment.getEntries().add(payBillEntryInfo);
		}
		PaymentBillFactory.getLocalInstance(ctx).save(payment);
		
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("fiRevOrPay");
		updateSels.add("paymentBill");
		_updatePartial(ctx, revGatherInfo, updateSels);
    }
    /*
     * 根据汇总单生成出纳收款单一对一生成
     */
    private void createReceivingBill(Context ctx,ReceiveGatherInfo revGatherInfo) throws EASBizException, BOSException
    {
    	ReceivingBillInfo  receivingBillInfo = new ReceivingBillInfo();
    	revGatherInfo.setReceivingBill(receivingBillInfo);
    	revGatherInfo.setFiRevOrPay(true);
    	//得到出纳系统的默认的收款类型
		ReceivingBillTypeInfo receivingBillTypeInfo = getReceivingBillType(ctx);
			
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		CtrlUnitInfo cuInfo = ContextUtil.getCurrentCtrlUnit(ctx);
		
		receivingBillInfo.setRecBillType(receivingBillTypeInfo);
		receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
		receivingBillInfo.setSourceSysType(SourceTypeEnum.CASH);
		//公司
		receivingBillInfo.setCompany(revGatherInfo.getCompany());
		//业务日期
		receivingBillInfo.setBizDate(revGatherInfo.getBizDate());
		//汇率
		receivingBillInfo.setExchangeRate(revGatherInfo.getExchangeRate());
		//币别
		receivingBillInfo.setCurrency(revGatherInfo.getCurrency());
		//收款金额
		receivingBillInfo.setActRecAmt(revGatherInfo.getSumAmount());
		
		receivingBillInfo.setCreator(userInfo);
		receivingBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		receivingBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		receivingBillInfo.setLastUpdateUser(userInfo);
		receivingBillInfo.setCU(cuInfo);
		
		receivingBillInfo.setNumber(getNumber(ctx,receivingBillInfo,revGatherInfo));
		
		/**
		 * 添加收款人
		 */
//		CustomerInfo custInfo  = revGatherInfo.getCustomer();
//		if(custInfo!=null){
//			receivingBillInfo.setPayerID(custInfo.getId().toString());
//			receivingBillInfo.setPayerNumber(custInfo.getNumber());
//			receivingBillInfo.setPayerName(custInfo.getName());
//		}
		//折本位币
		BigDecimal actrecLocAmt = FDCHelper.ZERO;
		BigDecimal ecchangeRate = FDCHelper.ONE;
		
		if(revGatherInfo.getExchangeRate()!=null){
			ecchangeRate = revGatherInfo.getExchangeRate();
		}
		
		actrecLocAmt  = FDCHelper.multiply(revGatherInfo.getSumAmount(), ecchangeRate);
		
		receivingBillInfo.setActRecLocAmt(actrecLocAmt);
		
		//收款科目
		if(revGatherInfo.getRevAccount()!=null){
			receivingBillInfo.setPayeeAccount(revGatherInfo.getRevAccount());	
		}
		
		// 检查科目合法性
//		try {
//			arapHelper.verifyAccountView(ctx, revGatherInfo.getRevAccount(), receivingBillInfo
//					.getCurrency(), receivingBillInfo.getCompany());
//		} catch (EASBizException e) {
//			logger.error(e.getMessage()+"科目不按指定币别核算，请选择其它币别！");
//			throw new BOSException("科目不按指定币别核算，请选择其它币别！");
//		}
		
		//收款账户
		if(revGatherInfo.getAccountBank()!=null){
			receivingBillInfo.setPayeeAccountBank(revGatherInfo.getAccountBank());	
		}
		receivingBillInfo.setPayeeBank(revGatherInfo.getBank());
		
		//结算方式
		receivingBillInfo.setSettlementType(revGatherInfo.getSettlementType());
		//结算号
		receivingBillInfo.setSettlementNumber(revGatherInfo.getSettlementNumber());
		
		/**
		 * 设置非空字段
		 */
		receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.RealType);
		receivingBillInfo.setIsExchanged(false);
		receivingBillInfo.setIsInitializeBill(false);
		receivingBillInfo.setIsImport(false);
		receivingBillInfo.setFiVouchered(false);
		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		receivingBillInfo.setIsAppointVoucher(false);
		receivingBillInfo.setIsCoopBuild(false);
//		receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
		
		//单据状态
		receivingBillInfo.setBillStatus(BillStatusEnum.SAVE);
		//原始单据id
		receivingBillInfo.setSourceBillId(revGatherInfo.getId().toString());
		
		/**
		 * 以下是分录内容
		 */
		ReceiveGatherEntryCollection revGatherEntryColl = revGatherInfo.getEntry();
		
		SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("asstActGpDt.asstActType.*");
    	sels.add("asstActGpDt.*");
    	
		for(int i=0;i<revGatherEntryColl.size();i++)
		{
			ReceiveGatherEntryInfo revGatherEntryInfo = revGatherEntryColl.get(i);
			ReceivingBillEntryInfo receBillEntryInfo = new ReceivingBillEntryInfo();
			revGatherEntryInfo.setReceivingBillEntry(receBillEntryInfo);
			receBillEntryInfo.setActualAmt(revGatherEntryInfo.getRevAmount());
			receBillEntryInfo.setSeq(i+1);
			if(revGatherEntryInfo.getOppAccount()!=null)
			{
				receBillEntryInfo.setOppAccount(revGatherEntryInfo.getOppAccount());
				if(revGatherEntryInfo.getOppAccount().getCAA()!=null){
					AsstAccountInfo account=AsstAccountFactory.getLocalInstance(ctx).getAsstAccountInfo(new ObjectUuidPK(revGatherEntryInfo.getOppAccount().getCAA().getId()),sels);
					AsstActGroupDetailCollection aacol=account.getAsstActGpDt();
					CRMHelper.sortCollection(aacol, "seq", true);
					for(int j=0;j<aacol.size();j++){
						AsstActTypeInfo asstActType=aacol.get(j).getAsstActType();
						AssItemsForCashRecInfo ass=new AssItemsForCashRecInfo();
						ass.setTableName(asstActType.getRealtionDataObject());
						ass.setMappingFileds(asstActType.getMappingFieldName());
						ass.setAsstActType(asstActType);
						ass.setIsSelected(false);
						ass.setSeq(aacol.get(j).getSeq());
						ass.setEntrySeq(i+1);
						
						if(asstActType.getRealtionDataObject().equals("T_BD_Customer")){
							if(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer()!=null){
								ass.setFromID(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getNumber());
								ass.setIsSelected(true);
							}
						}else if(asstActType.getRealtionDataObject().equals("T_SHE_Room")){
							if(revGatherEntryInfo.getRoom()!=null){
								ass.setFromID(revGatherEntryInfo.getRoom().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getRoom().getNumber());
								ass.setIsSelected(true);
							}
						}
						receBillEntryInfo.getAssItemsEntries().add(ass);
					}
				}
			}	
			if(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer()!=null){
				receBillEntryInfo.setRemark(revGatherEntryInfo.getRemark()+revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getName());
			}else{
				receBillEntryInfo.setRemark(revGatherEntryInfo.getRemark());
			}
			if(i==0){
				receivingBillInfo.setDescription(receBillEntryInfo.getRemark());
			}
			receivingBillInfo.getEntries().add(receBillEntryInfo);
		}
		ReceivingBillFactory.getLocalInstance(ctx).save(receivingBillInfo);
		
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("fiRevOrPay");
		updateSels.add("receivingBill");
		updateSels.add("entry.receivingBillEntry");
		_updatePartial(ctx, revGatherInfo, updateSels);
    }  
    
    /**
	 * 得到出纳系统的默认的收款类型
	 * @return
	 */
	private ReceivingBillTypeInfo getReceivingBillType(Context ctx){
		ReceivingBillTypeInfo typeInfo = null;
		ReceivingBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IReceivingBillType iReceivingBillType;
		try {
			iReceivingBillType = ReceivingBillTypeFactory.getLocalInstance(ctx);			
			coll = iReceivingBillType.getReceivingBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"得到出纳系统的默认的收款类型!");
		}
		

		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}
	 /**
	 * 得到出纳系统的默认的收款类型
	 * @return
	 */
	private PaymentBillTypeInfo getPaymentBillType(Context ctx){
		PaymentBillTypeInfo typeInfo = null;
		PaymentBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IPaymentBillType iPaymentBillType;
		try {
			iPaymentBillType = PaymentBillTypeFactory.getLocalInstance(ctx);			
			coll = iPaymentBillType.getPaymentBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"得到出纳系统的默认的收款类型!");
		}
		
		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}
	/**
	 * 设置编码		
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private String getNumber(Context ctx,ReceivingBillInfo  receivingBillInfo,ReceiveGatherInfo revGatherInfo) throws BOSException, EASBizException
	{		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			receivingBillInfo.setNumber(retNumber);
		}else
		{
			retNumber = revGatherInfo.getNumber()+"_toReceiving";
		}
		
		return retNumber;
	}	
	/**
	 * 设置编码		
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private String getNumber(Context ctx,PaymentBillInfo  receivingBillInfo,ReceiveGatherInfo revGatherInfo) throws BOSException, EASBizException
	{		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			receivingBillInfo.setNumber(retNumber);
		}else
		{
			retNumber = revGatherInfo.getNumber()+"_toPayment";
		}
		
		return retNumber;
	}
	
	//汇总单生成凭证时同步更新出纳收款单的是否生成凭证，不允许重复生成
	protected DAPTransformResult _generateVoucher(Context ctx, IObjectCollection sourceBillCollection,
			IObjectPK botMappingPK) throws BOSException, EASBizException {		
		CoreBillBaseCollection coll = (CoreBillBaseCollection) sourceBillCollection;
		for (Iterator it = coll.iterator(); it.hasNext();) {
			ReceiveGatherInfo info = (ReceiveGatherInfo) it.next();
			info = this.getRevGatherInfo(ctx,info.getId());
			ReceivingBillInfo revInfo = info.getReceivingBill();
			revInfo.setFiVouchered(true);
			SelectorItemCollection updateSels = new SelectorItemCollection();
			updateSels.add("fiVouchered");
			ReceivingBillFactory.getLocalInstance(ctx).updatePartial(revInfo, updateSels);
		}
		return super._generateVoucher(ctx, sourceBillCollection, botMappingPK);						
	}	
	
	protected void _delReceivingToRev(Context ctx, IObjectValue info) throws BOSException, EASBizException {
		ReceivingBillInfo recBillInfo = (ReceivingBillInfo)info;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id", recBillInfo.getId().toString(),CompareType.EQUALS));
    	view.setFilter(filter);
    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
    	if(revGatherColl.size()>0)
    	{
    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(0);
    		revGatherInfo.setFiRevOrPay(false);
    		revGatherInfo.setReceivingBill(null);
    		ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
    		for(int i=0;i<revEntryColl.size();i++)
    		{
    			ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
    			revEntryInfo.setReceivingBillEntry(null);
    			revGatherInfo.getEntry().add(revEntryInfo);
    		}
    		//出纳收款单删除时，更改房地产汇总单是否生成出纳单据的状态以及删除和房地产汇总单及汇总单分录的关联
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("fiRevOrPay");
    		sels.add("receivingBill");
    		sels.add("entry.receivingBillEntry");
    		ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, sels);
    	}
	}
	
	//出纳收款单生成凭证更新汇总单是否生成凭证
	protected void _createVoucherToRev(Context ctx, ArrayList revList) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",listToSet(revList),CompareType.INCLUDE));
    	view.setFilter(filter);
    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
    	for(int i=0;i<revGatherColl.size();i++)
    	{
    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(i); 
    		revGatherInfo.setFiVouchered(true);
    		//出纳收款单生成凭证时，更新汇总单对应的生成凭证
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("fiVouchered");
    		ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, sels);
    	}
	}
	private Set listToSet(ArrayList list)
	{
		Set set = new HashSet();
		for(int i=0;i<list.size();i++)
		{
			set.add(list.get(i));
		}
		return set;
	}
	
	//出纳收款单批量删除凭证
	protected void _delVoucherToRev(Context ctx, ArrayList revList) throws BOSException, EASBizException {
		for(int i=0;i<revList.size();i++)
		{
			_delVoucherToRev(ctx,new ObjectUuidPK((String)revList.get(i)));
		}		
	}
	
	protected void _delVoucherToRev(Context ctx, IObjectPK sourceBillPk) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id", sourceBillPk.toString(),CompareType.EQUALS));
    	view.setFilter(filter);
    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
    	if(revGatherColl.size()>0)
    	{
    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(0);    		
    		_deleteVoucher(ctx, new ObjectUuidPK(revGatherInfo.getId()));
    	}else
    	{
    		//说明是出纳收款单生成
    		Set idRevSet = FMHelper.getSrcBillIdCollBySrcId(ctx, sourceBillPk.toString());
			ReceivingBillFactory.getLocalInstance(ctx).deleteVoucher(sourceBillPk);
			//出纳收款单可能是多张收款单生成一张凭证，找出有哪些收款单对应了汇总单
			EntityViewInfo view2 = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",idRevSet,CompareType.INCLUDE));
	    	view2.setFilter(filter2);
	    	ReceiveGatherCollection revGatherColl2 = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view2);
	    	for(int i=0;i<revGatherColl2.size();i++)
	    	{
	    		ReceiveGatherInfo revGatherInfo = revGatherColl2.get(i);
	    		revGatherInfo.setFiVouchered(false);
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add("fiVouchered");
				ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, updateSels);
	    	}
    	}
	}
	
	//删除凭证的时候同步更新出纳收款单的是否生成凭证
	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk) throws BOSException, EASBizException {
		ReceiveGatherInfo info = this.getRevGatherInfo(ctx,BOSUuid.read(sourceBillPk.toString()));
		ReceivingBillInfo revInfo = info.getReceivingBill();
		//查找凭证是 由汇总单生成还是出纳收款单生成
		Set idSet = FMHelper.getSrcBillIdCollBySrcId(ctx, sourceBillPk.toString());
		if(idSet.size()>0)
		{
			//说明是汇总单生成
			
			revInfo.setFiVouchered(false);
			SelectorItemCollection updateSels = new SelectorItemCollection();
			updateSels.add("fiVouchered");
			ReceivingBillFactory.getLocalInstance(ctx).updatePartial(revInfo, updateSels);
			return super._deleteVoucher(ctx, sourceBillPk);	
		}else
		{
			//说明是出纳收款单生成
			//那么找出对应的所有的源单据ID集合
			Set idRevSet = FMHelper.getSrcBillIdCollBySrcId(ctx, revInfo.getId().toString());
			ReceivingBillFactory.getLocalInstance(ctx).deleteVoucher(new ObjectUuidPK(revInfo.getId()));
			//出纳收款单可能是多张收款单生成一张凭证，找出有哪些收款单对应了汇总单
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",idRevSet,CompareType.INCLUDE));
	    	view.setFilter(filter);
	    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
	    	for(int i=0;i<revGatherColl.size();i++)
	    	{
	    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(i);
	    		revGatherInfo.setFiVouchered(false);
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add("fiVouchered");
				ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, updateSels);
	    	}
			
			return true;
		}
		
					
	}
    
    private ReceiveGatherInfo getRevGatherInfo(Context ctx,BOSUuid billId) throws EASBizException, BOSException
    {
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("id");
    	sels.add("revBillType");
    	sels.add("sumAmount");
    	sels.add("settlementNumber");
    	sels.add("gatherType");
    	sels.add("fiVouchered");
    	sels.add("fiRevOrPay");
    	sels.add("exchangeRate");
    	sels.add("bizDate");
    	sels.add("description");
    	
    	sels.add("project.id");
    	sels.add("project.name");
    	sels.add("project.number");
    	
    	sels.add("bank.id");
    	sels.add("bank.name");
    	sels.add("bank.number");
    	
    	sels.add("accountBank.id");
    	sels.add("accountBank.name");
    	sels.add("accountBank.number");
    	
    	sels.add("settlementType.id");
    	sels.add("settlementType.name");
    	sels.add("settlementType.number");
    	
    	sels.add("revAccount.id");
    	sels.add("revAccount.name");
    	sels.add("revAccount.number");
    	
    	sels.add("oppAccount.id");
    	sels.add("oppAccount.name");
    	sels.add("oppAccount.number");
    	
    	sels.add("company.id");
    	sels.add("company.name");
    	sels.add("company.number");
    	
    	sels.add("currency.id");
    	sels.add("currency.name");
    	sels.add("currency.number");
    	
    	sels.add("receivingBill.id");
    	
    	sels.add("entry.id");
    	sels.add("entry.customerDisName");
    	sels.add("entry.settlementNumber");
    	sels.add("entry.cusAccountBankNumber");
    	sels.add("entry.revAmount");
    	
    	sels.add("entry.sheRevBill.id");
    	sels.add("entry.sheRevBill.parent.sysCustomer.*");
    	
    	sels.add("entry.moneyDefine.id");
    	sels.add("entry.moneyDefine.name");
    	sels.add("entry.moneyDefine.number");
    	sels.add("entry.receiptNumber");
    	sels.add("entry.invoiceNumber");
    	
    	sels.add("entry.room.id");
    	sels.add("entry.room.name");
    	sels.add("entry.room.number");
    	
    	sels.add("entry.settlementType.id");
    	sels.add("entry.settlementType.name");
    	sels.add("entry.settlementType.number");
    	
    	sels.add("entry.oppAccount.id");
    	sels.add("entry.oppAccount.name");
    	sels.add("entry.oppAccount.number");
    	sels.add("entry.oppAccount.CAA.*");
    	
    	sels.add("entry.remark");
   	
    	return (ReceiveGatherInfo) getValue(ctx, new ObjectUuidPK(billId),sels);
    }
    
    
	/**
	 * 设置审批中状态
	 */
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
	    ReceiveGatherInfo rev = new ReceiveGatherInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.AUDITTING);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);
	}

    /**
	 * 设置提交状态
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		ReceiveGatherInfo rev = new ReceiveGatherInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);		
	}    
    
    
}