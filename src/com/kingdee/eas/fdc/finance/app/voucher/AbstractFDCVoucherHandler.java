package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public abstract class AbstractFDCVoucherHandler{
	static final String PARTAOFBILLS = "partAOfBills_";
	static final String DEDUCTBILLS = "deductBills_";
	static final String PARTACONFIRMBILLS = "partAConfirmBills_";
	static final String COMPENSATIONBILLS = "compensationBills_";
	static final String GUERDONBILLS = "guerdonBills_";
	private Context ctx = null;
	private IFDCVoucherEntryCreator creator=null;
	protected Context getCtx() {
		return this.ctx;
	}

	protected void setCtx(Context ctx) {
		this.ctx = ctx;
	}
	protected IFDCVoucherEntryCreator getFDCVoucherEntryCreator(){
		return this.creator;
	}
	
	protected void setFDCVoucherEntryCreator(IFDCVoucherEntryCreator creator){
		this.creator = creator;
	}
	
	/***
	 * 一体化的模式
	 */
	protected String financial = "";
	/***
	 * 简单一体化处理扣款违约
	 */
	protected boolean financialExtend = false;
	/***
	 * 工程量与付款分离
	 */
	protected boolean isSeparate = false;
	/***
	 * 复杂一体化处理发票
	 */
	protected boolean isInvoiceMrg = false;
	/**
	 * 简单模式处理发票
	 */
	protected boolean isSimpleInvoice = false;
	/**
	 * 以发票金额为准计开发成本时，凭证记录应付账款的冲抵过程
	 */
	protected boolean isInvoiceOffset = false;
	/**
	 * 简单模式生成凭证是否校验拆分
	 */
	protected boolean isPaymentSplit = false;
	
	/***
	 * 已经同步过分录的bills
	 */
	protected Map hasSynchBills = null;
	/***
	 * 全局的一体化科目设置
	 */
	protected BeforeAccountViewInfo beforeAccountViewInfo = null;
	/***
	 * 成本科目对应的会计科目
	 * 空间换时间
	 * 缓存所有的成本科目对应的会计科目
	 * [用付款单对应的工程项目过滤]
	 */
	protected Map costAccountWithAccountMap = null; 
	/***
	 * 扣款对应的科目设置Map
	 */
	protected Map deductAccountMap = null; 
	/***
	 * 需要更新的成本付款拆分的idSet
	 */
	protected Set needUpdatePaymentSplitSet = null;
	/***
	 * 需要更新的非成本付款拆分的idSet
	 */
	protected Set needUpdatePaymentNoCostSplitSet = null;
	

	/****
	 * 项目未获取
	 * 1）进度款 
	 * 借：预付账款_开发成本_其他成本 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) throws EASBizException, BOSException {
		return getFDCVoucherEntryCreator().getProgressAccountView(key);
	}

	/***
	 * 项目未获取
	 * 2）结算款（第一笔）
	 * * 借：预付账款_开发成本_其他成本 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException, BOSException {
		return getFDCVoucherEntryCreator().getFirstSettleAccountView(key);
	}
	
	/***
	 * 项目未获取
	 * 3) 结算款（第二笔）
	 * * 借：应付账款_开发成本_进度款（一体化科目设置对应应付进度款） 
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {
		return getFDCVoucherEntryCreator().getSettleAccountView(key);
	}
	
	/***
	 * 项目未获取
	 * 4）保修款
	 * 借：应付账款_开发成本_保修款
	 * 周勇
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return getFDCVoucherEntryCreator().getGrtAccountView(key);
	}
	
	
	protected void init(Map param) throws BOSException{
		needUpdatePaymentSplitSet = (Set)param.get("needUpdatePaymentSplitSet");
		needUpdatePaymentNoCostSplitSet = (Set)param.get("needUpdatePaymentNoCostSplitSet");
		
		//通过付款单批量取得生成分录所需要的所有值，包括付款单扣款，违约,奖励，付款拆分等信息,
		//注意：付款拆分有文本以及非文本
		
		/***
		 * 一体化模式
		 */
		financial = (String)param.get("financial");
		financialExtend = param.get("financialExtend")!=null?((Boolean)param.get("financialExtend")).booleanValue():false;
		isSeparate = param.get("isSeparate")!=null?((Boolean)param.get("isSeparate")).booleanValue():false;
		isInvoiceMrg = param.get("isInvoiceMrg")!=null?((Boolean)param.get("isInvoiceMrg")).booleanValue():false;
		isSimpleInvoice = param.get("simpleInvoice")!=null?((Boolean)param.get("simpleInvoice")).booleanValue():false;
		isInvoiceOffset = param.get("invoiceOffset")!=null?((Boolean)param.get("invoiceOffset")).booleanValue():false;
		isPaymentSplit = param.get("isPaymentSplit")!=null?((Boolean)param.get("isPaymentSplit")).booleanValue():false;
		/***
		 * 已经同步过的bills
		 */
		hasSynchBills = (Map)param.get("hasSynchBills");

		
		deductAccountMap = (Map)param.get("deductAccountMap");
		beforeAccountViewInfo = (BeforeAccountViewInfo)param.get("beforeAccountViewInfo");
		costAccountWithAccountMap = (Map)param.get("costAccountWithAccountMap");
		
	}

	/**
	 * 按项目状态通过成本科目查找对应的会计科目
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	protected AccountViewInfo getAccountView(CostAccountInfo costAccount,PaymentBillInfo paymentBillInfo,boolean isProgress,boolean isConWithoutText) throws BOSException, EASBizException{
		return getFDCVoucherEntryCreator().getAccountView(costAccount, paymentBillInfo, isProgress, isConWithoutText);
	}
	
	/**
	 * 通过成本科目查找对应的发票科目会计
	 * 发票科目与项目状态无关
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	protected AccountViewInfo getInvoiceAccountView(CostAccountInfo costAccount) throws EASBizException{
		return getFDCVoucherEntryCreator().getInvoiceAccountView(costAccount);
	}
	
	/**
	 * 不管项目状态通过成本科目查找对应的会计科目
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	protected AccountViewInfo getAccountWithCostAccount(CostAccountInfo costAccountInfo) throws EASBizException {
		return getFDCVoucherEntryCreator().getAccountWithCostAccount(costAccountInfo);
	}
	
	/**
	 * 取本位币，使用缓存进行处理
	 * 缓存级别为Ctx，生成的数据保存后清除
	 *  by sxhong 2009-07-21 19:24:03
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected CurrencyInfo getBaseCurrency() throws BOSException, EASBizException {
		if(getFDCVoucherEntryCreator().getCacheMap().get("baseCurrency")==null){
			getFDCVoucherEntryCreator().getCacheMap().put("baseCurrency", FDCHelper.getBaseCurrency(getCtx()));
		}
		return (CurrencyInfo)getFDCVoucherEntryCreator().getCacheMap().get("baseCurrency");
	}
	/**
	 * 预付款
	 * @param contractID
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected boolean isAdvance(String contractID) throws EASBizException, BOSException{
		return isSeparate&&FDCUtils.isContractBill(ctx, contractID);
	}

	/**
	 * dataMap中的payedAmtMap和 invoiceAmtMap 需重新取一次数，因为如果是多张付款单合并生成凭证时，这两个数没有取到
	 * 
	 * @author owen_wen 2011-07-07
	 * @param dataMap
	 * @param paymentBillId
	 * @throws BOSException
	 */
	protected void initSplitBillInfos(Map dataMap, String paymentBillId) throws BOSException {
		Set paymentBillIds = new HashSet();
		paymentBillIds.add(paymentBillId);
		if (this.creator instanceof AbstractFDCVoucherEntryCreator) {
			((AbstractFDCVoucherEntryCreator) creator).initSplitBillInfos(dataMap, paymentBillIds);
		}
	}
}
