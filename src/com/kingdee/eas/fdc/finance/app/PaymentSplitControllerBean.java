package com.kingdee.eas.fdc.finance.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.ISplitImporter;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBaseData;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.app.ContractStateHelper;
import com.kingdee.eas.fdc.finance.IPaySplit4Voucher;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.ISplitImportHandler;
import com.kingdee.eas.fdc.finance.PaySplit4VoucherFactory;
import com.kingdee.eas.fdc.finance.PaymentCostSplitImportHandler;
import com.kingdee.eas.fdc.finance.PaymentCostSplitImporter;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public class PaymentSplitControllerBean extends
		AbstractPaymentSplitControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.PaymentSplitControllerBean");

	protected String _getLogInfo(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		PaymentSplitInfo info = (PaymentSplitInfo) super._getValue(ctx, pk);
		String retValue = "";
		if (info.getPaymentBill() != null) {
			String id = info.getPaymentBill().getId().toString();
			PaymentBillInfo test = PaymentBillFactory.getLocalInstance(ctx)
					.getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			if (test.getNumber() != null) {
				retValue = test.getNumber();
			}
		}
		return retValue;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#_addnew(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectCollection)
	 */
	protected Result _addnew(Context ctx, IObjectCollection colls)
			throws BOSException, EASBizException {
		return super._addnew(ctx, colls);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_addnew(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectPK, com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._addnew(ctx, pk, model);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_addnew(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		return super._addnew(ctx, model);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#_save(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectCollection)
	 */
	protected Result _save(Context ctx, IObjectCollection colls)
			throws BOSException, EASBizException {
		return super._save(ctx, colls);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_save(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectPK, com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._save(ctx, pk, model);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.app.CoreBillBaseControllerBean#_save(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// return super._save(ctx, model);
		PaymentSplitInfo bill = (PaymentSplitInfo) model;
		if(bill.isIsWorkLoadBill()){
			return _saveWorkLoadSplit(ctx, model);
		}else{
			//暂时将归属成本金额全部更新成0,以后付款申请单会控制保证
			String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();			
			Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId); //FDCUtils.getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
			boolean separtFormPayment=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
			boolean isInvoiceMrg=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
			if(!bill.isIsConWithoutText()&&separtFormPayment){
				//update by david_yang PT048096 2011.04.12 发票模式下 全项目动态成本标中已实现成本为空
//				bill.setCompletePrjAmt(FDCHelper.ZERO);
//				for(Iterator iter=bill.getEntrys().iterator();iter.hasNext();){
//					PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
//					entry.setAmount(FDCHelper.ZERO);
//				}
			}
			if(bill.isIsConWithoutText()&&isInvoiceMrg){
				//处理无文本的发票拆分金额
				bill.setInvoiceAmt(bill.getAmount());
				for(Iterator iter=bill.getEntrys().iterator();iter.hasNext();){
					PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
					entry.setInvoiceAmt(entry.getAmount());
					entry.setSplitedInvoiceAmt(entry.getSplitedCostAmt());
				}
			}
		}
		if (bill.getPaymentBill() != null && bill.getPaymentBill().getCompany() != null && bill.getPaymentBill().getCompany().getId() != null) {
			String id = bill.getPaymentBill().getCompany().getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("company.id", id));
			view.setFilter(filter);
			IBeforeAccountView iBefore = BeforeAccountViewFactory.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				BeforeAccountViewInfo info = iBefore.getBeforeAccountViewCollection(view).get(0);
				bill.setBeAccount(info);
			}
			// 设置orgUnit为付款单的公司ID by sxhong 2008-11-03 16:28:03
			FullOrgUnitInfo orgUnit = new FullOrgUnitInfo();
			orgUnit.setId(BOSUuid.read(id));
			bill.setOrgUnit(orgUnit);
		}

		if (bill.getPaymentBill() != null && bill.getPaymentBill().getContractBillId() != null) {
			String contractId = bill.getPaymentBill().getContractBillId();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
			view.setFilter(filter);
			IContractBaseData iBefore = ContractBaseDataFactory.getLocalInstance(ctx);
			boolean has = iBefore.exists(filter);
			if (has) {
				ContractBaseDataInfo info = iBefore.getContractBaseDataCollection(view).get(0);
				bill.setContractBaseData(info);
			}
			if (isConWithoutTxt(bill)) {
				bill.setConWithoutText(ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId))));
			} else {
				bill.setContractBill(ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId))));
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", bill.getPaymentBill().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("Fivouchered", Boolean.TRUE));
			view.setFilter(filter);
			int voucherSize = super.getPaymentSplitCollection(ctx, view).size();
			bill.setVoucherTimes(voucherSize);
		}

		if (bill.getPaymentBill() != null && bill.getPaymentBill().getPayerAccount() != null) {
			if (bill.getPaymentBill().getPayerAccount().isIsBank() || bill.getPaymentBill().getPayerAccount().isIsCash() || bill.getPaymentBill().getPayerAccount().isIsCashEquivalent()) {
				bill.setIsNeedTransit(true);
				bill.setTransitAccount(FDCUtils.getDefaultFDCParamAccount(ctx, bill.getPaymentBill().getCompany().getId().toString()));
			}
		}
		bill.setIslastVerThisPeriod(true);
		IObjectPK pk = super._save(ctx, bill);
		PaymentSplitInfo splitBillInfo = null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("createTime");
		selectorGet.add("paymentBill.id");
		selectorGet.add("paymentBill.curProject.id");
		selectorGet.add("paymentBill.CU.id");
		selectorGet.add("paymentBill.fdcPayReqID");
		selectorGet.add("paymentBill.company.id");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		selectorGet.add("period.beginDate");
		selectorGet.add("period.endDate");
		selectorGet.add("isConWithoutText");
		selectorGet.add("paymentBill.id");
		selectorGet.add("paymentBill.contractBillId");
		selectorGet.add("paymentBill.fdcPayType.payType.id");
		selectorGet.add("isProgress");
		selectorGet.add("splitState");
		splitBillInfo = super.getPaymentSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getPaymentBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjID, false);
		Date bookedDate = DateTimeUtils.truncateDate(splitBillInfo.getCreateTime());
		if (currentPeriod != null) {
			String payreqID = splitBillInfo.getPaymentBill().getFdcPayReqID();
			SelectorItemCollection reqPer = new SelectorItemCollection();
			reqPer.add("id");
			reqPer.add("period.number");
			reqPer.add("period.beginDate");
			reqPer.add("period.endDate");
			PayRequestBillInfo reqInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(payreqID)), reqPer);
			PeriodInfo contractPeriod = reqInfo.getPeriod();
			if (contractPeriod != null && contractPeriod.getNumber() > currentPeriod.getNumber()) {
				if (bookedDate.before(contractPeriod.getBeginDate())) {
					bookedDate = contractPeriod.getBeginDate();
				} else if (bookedDate.after(contractPeriod.getEndDate())) {
					bookedDate = contractPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getPaymentBill().getId().toString());
				builder.executeUpdate();
			} else if (currentPeriod != null) {
				if (bookedDate.before(currentPeriod.getBeginDate())) {
					bookedDate = currentPeriod.getBeginDate();
				} else if (bookedDate.after(currentPeriod.getEndDate())) {
					bookedDate = currentPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getPaymentBill().getId().toString());
				builder.executeUpdate();
			}
		}
		BOSUuid costBillId = splitBillInfo.getPaymentBill().getId();
		String companyId = splitBillInfo.getPaymentBill().getCompany().getId().toString();
		//红冲模式才调用删除 2009-05-20
		if (FDCUtils.IsFinacial(ctx, companyId)
				&& !FDCUtils.isAdjustVourcherModel(ctx, companyId)) {
			if (!splitBillInfo.isIsConWithoutText() && splitBillInfo.getPaymentBill() != null && splitBillInfo.getPaymentBill().getFdcPayType() != null
					&& splitBillInfo.getPaymentBill().getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID) && splitBillInfo.isIsProgress()) {
				EntityViewInfo viewPro = new EntityViewInfo();
				FilterInfo filterPro = new FilterInfo();
				filterPro.getFilterItems().add(new FilterItemInfo("paymentBill.contractBillId", splitBillInfo.getPaymentBill().getContractBillId()));
				filterPro.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				filterPro.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayType.payType.id", PaymentTypeInfo.progressID));
				viewPro.setFilter(filterPro);
				viewPro.getSelector().add("id");
				viewPro.getSelector().add("hisVoucher.id");
				viewPro.getSelector().add("paymentBill.id");
				PaymentSplitCollection payColl = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(viewPro);
				// 这个方法应该写成require的，保证事务一致性,隐藏危机，但是方法不可追溯，暂时因为补丁不修改了。
				for (Iterator iter = payColl.iterator(); iter.hasNext();) {
					PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
					deleteCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, element.getPaymentBill().getId());
					// InvalidCostSplitHelper.invalidCostSplit(ctx,
					// CostSplitBillTypeEnum.PAYMENTSPLIT,
					// element.getPaymentBill().getId(),
					// element.getId());
				}
			}
		}

		splitBillInfo.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx, splitBillInfo);
		collectCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, splitBillInfo.getPaymentBill(), splitBillInfo.getId(), bill.getEntrys());

		if (splitBillInfo.getPaymentBill().getContractBillId() != null && isConWithoutTxt(splitBillInfo)) {
			collectCostSplit(ctx, CostSplitBillTypeEnum.NOTEXTCONSPLIT, splitBillInfo.getPaymentBill(), splitBillInfo.getId(), bill.getEntrys());
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_Con_Contractwithouttext set FSplitState=? where fid=?");
			builder.addParam(splitBillInfo.getSplitState().getValue());
			builder.addParam(splitBillInfo.getPaymentBill().getContractBillId());
			builder.execute();
		}
		return pk;
	}
	
	private IObjectPK _saveWorkLoadSplit(Context ctx,IObjectValue model) throws EASBizException, BOSException{
		PaymentSplitInfo info=(PaymentSplitInfo)model;
		info.setIslastVerThisPeriod(true);
		IObjectPK pk = super._save(ctx, model);
		//设置期间
		String prjID = info.getCurProject().getId().toString();
		// 财务期间
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjID, false);
		//业务日期(制单日期即拆分日期)
		Date bookedDate = DateTimeUtils.truncateDate(info.getCreateTime());
		if (currentPeriod != null) {
			SelectorItemCollection tempSelector = new SelectorItemCollection();
			tempSelector.add("id");
			tempSelector.add("period.number");
			tempSelector.add("period.beginDate");
			tempSelector.add("period.endDate");
			/**
			 *  与罗忠慧讨论，取拆分对应单据工程量确认单的期间
			 *  错误：当成本月结后，冻结财务，则期间成下一期间，导致工程量与拆分期间不一致
			 */
			//ContractBillInfo contract=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getContractBill().getId().toString()),tempSelector);
			WorkLoadConfirmBillInfo billInfo =WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoadConfirmBillInfo(new ObjectUuidPK(info.getWorkLoadConfirmBill().getId().toString()),tempSelector);
			// 单据期间
			PeriodInfo billPeriod = billInfo.getPeriod();
			/**
			 *  单据期间 > 财务期间: 业务日期 < 单据期间开始日期，业务日期=单据期间开始日期;
			 *                      业务日期 > 单据期间结束日期，业务日期=单据期间结束日期.
			 *                      业务期间 = 单据期间
			 */
			if (billPeriod != null && billPeriod.getNumber() > currentPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(billPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(info.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FWorkLoadBillID=?");
				builder.addParam(billPeriod.getId().toString());
				builder.addParam(info.getId().toString());
				builder.addParam(info.getWorkLoadConfirmBill().getId().toString());
				builder.executeUpdate();
			} else if (currentPeriod != null) {
				/**
				 * 单据期间 < 财务期间: 业务日期   > 财务开始日期,业务日期=财务期间开始日期 
				 *                     业务日期 < 财务结束日期,业务日期=财务结束日期
				 *                     业务期间 = 财务期间
				 */
				if (bookedDate.before(currentPeriod.getBeginDate())) {
					bookedDate = currentPeriod.getBeginDate();
				} else if (bookedDate.after(currentPeriod.getEndDate())) {
					bookedDate = currentPeriod.getEndDate();
				}
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(FDCDateHelper.getSqlDate(bookedDate));
				builder.addParam(info.getId().toString());
				builder.executeUpdate();

				//将以前的拆分更新成不是最新版本
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FWorkLoadBillID=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(info.getId().toString());
				builder.addParam(info.getWorkLoadConfirmBill().getId().toString());
				builder.executeUpdate();
			}
		}
		
		info.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx, info);
		collectCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, info.getWorkLoadConfirmBill(), info.getId(), info.getEntrys());
		return pk;
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// 基类已实现调用 _delete(Context ctx, IObjectPK[] arrayPK)
		super._delete(ctx, pk);
	}
	public void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException,
			EASBizException {
		int i = 0;
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		for (i = 0; i < arrayPK.length; i++) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("paymentBill.id");
			selector.add("paymentBill.contractBillId");
			selector.add("workLoadConfirmBill.id");
			selector.add("isWorkLoadBill");
			PaymentSplitInfo splitBill = getPaymentSplitInfo(ctx, arrayPK[i],selector);
			if(splitBill.isIsWorkLoadBill()){
				BOSUuid workLoadId = splitBill.getWorkLoadConfirmBill().getId();
				deleteCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, workLoadId);
				
			}else{
				BOSUuid costBillId = splitBill.getPaymentBill().getId();
				deleteCostSplit(ctx, CostSplitBillTypeEnum.PAYMENTSPLIT, costBillId);
				if (splitBill.getPaymentBill().getContractBillId() != null
						&& isConWithoutTxt(splitBill)) {
					deleteCostSplit(ctx, CostSplitBillTypeEnum.NOTEXTCONSPLIT,
							costBillId);
					builder.clear();
					builder.appendSql("update T_Con_Contractwithouttext set FSplitState=? where fid=?");
					builder.addParam(CostSplitStateEnum.NOSPLIT_VALUE);
					builder.addParam(splitBill.getPaymentBill().getContractBillId());
					builder.execute();
				}
			}
		}
		super._delete(ctx, arrayPK);
	}

	public IObjectPK[] _delete(Context ctx, String oql) throws BOSException,
			EASBizException {
		return super._delete(ctx, oql);
	}


	/**
	 * 判断选择行是不是无文本合同，选择多行返回false
	 * 
	 * @author sxhong Date 2006-12-1
	 * @param table
	 * @return isConWithoutTxt
	 */
	private boolean isConWithoutTxt(PaymentSplitInfo billInfo) {
		BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
				.getBOSType();
		boolean isConWithoutTxt = false;
		isConWithoutTxt = BOSUuid.read(
				billInfo.getPaymentBill().getContractBillId()).getType()
				.equals(withoutTxtConBosType);
		return isConWithoutTxt;
	}

	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {

		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("beAccount.*");
		sic.add("beAccount.proAccount.*");
		sic.add("paymentBill.id");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.fdcPayType.*");
		sic.add("paymentBill.fiVouchered");
		sic.add("paymentBill.accountant");
		sic.add("paymentBill.voucher");
		sic.add("paymentBill.voucherType");
		sic.add("paymentBill.company.id");
		PaymentSplitInfo payBillInfo = (PaymentSplitInfo) getValue(ctx,
				srcBillPK, sic);
		if (!isConWithoutTxt(payBillInfo)) {
			ContractStateHelper.synToExecState(ctx, payBillInfo
					.getPaymentBill().getContractBillId());
		}
		IPaymentBill paymentBill = PaymentBillFactory.getLocalInstance(ctx);
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			// String companyId =
			// payBillInfo.getPaymentBill().getCompany().getId().toString();
			UserInfo userInfo = ContextHelperFactory.getLocalInstance(ctx)
					.getCurrentUser();
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				payBillInfo.getPaymentBill().setFiVouchered(true);
				payBillInfo.getPaymentBill().setAccountant(userInfo);
				// payBillInfo.setBillStatus(BillStatusEnum.VOUCHERED);
				VoucherInfo voucherInfo = (VoucherInfo) VoucherFactory
						.getLocalInstance(ctx).getValue(
								new ObjectStringPK(botRelation
										.getDestObjectID()));
				if(payBillInfo.getVoucherTimes()>0){
					VoucherTypeInfo type = FDCUtils.getDefaultFDCParamVoucherType(
							ctx, payBillInfo.getPaymentBill().getCompany().getId().toString());
					if(type!=null){
						FDCSQLBuilder builderUpdate = new FDCSQLBuilder(ctx);
						builderUpdate.clear();
						builderUpdate
								.appendSql("update t_gl_voucher set FVoucherTypeID=? where fid=?");
						builderUpdate.addParam(type.getId().toString());
						builderUpdate.addParam(voucherInfo.getId().toString());
						builderUpdate.executeUpdate();
					}
				}
				payBillInfo.getPaymentBill().setVoucher(voucherInfo);
				payBillInfo.getPaymentBill().setVoucherType(
						voucherInfo.getVoucherType());
				payBillInfo.getPaymentBill().setVoucherNumber(voucherInfo.getNumber());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("accountant");
				selector.add("voucher");
				selector.add("voucherType");
				selector.add("voucherNumber");
				paymentBill.updatePartial(payBillInfo.getPaymentBill(),
						selector);

			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				payBillInfo.getPaymentBill().setFiVouchered(false);
				payBillInfo.getPaymentBill().setAccountant(null);
				// payBillInfo.setBillStatus(BillStatusEnum.PAYED);
				payBillInfo.getPaymentBill().setVoucher(null);
				payBillInfo.getPaymentBill().setVoucherType(null);
				payBillInfo.getPaymentBill().setVoucherNumber(null);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("accountant");
				selector.add("voucher");
				selector.add("voucherType");
				selector.add("voucherNumber");
				paymentBill.updatePartial(payBillInfo.getPaymentBill(),
						selector);
			}
		}
	}

	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		DAPTransformResult result = super._generateVoucher(ctx, sourceBillCollection, botMappingPK);
		_afterVoucher(ctx, sourceBillCollection);
		return result;
	}

	protected void _generateVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {

		super._generateVoucher(ctx, sourceBillPk);
		IObjectCollection sourceBillCollection = new CoreBillBaseCollection();
		PaymentSplitInfo info = super.getPaymentSplitInfo(ctx, sourceBillPk);
		sourceBillCollection.addObject(info);
		_afterVoucher(ctx, sourceBillCollection);
	}

	protected DAPTransformResult _generateVoucher(Context ctx, IObjectPK[] sourceBillPkList,
			IObjectPK botMappingPK, SelectorItemCollection botpSelectors)
			throws BOSException, EASBizException {
		return super._generateVoucher(ctx, sourceBillPkList, botMappingPK,
				botpSelectors);
	}

	protected void _generateVoucher(Context ctx, IObjectPK[] sourceBillPkList)
			throws BOSException, EASBizException {
		super._generateVoucher(ctx, sourceBillPkList);
		int size = sourceBillPkList.length;
		IObjectCollection sourceBillCollection = new CoreBillBaseCollection();
		for (int i = 0; i < size; i++) {
			PaymentSplitInfo info = super.getPaymentSplitInfo(ctx,
					sourceBillPkList[i]);
			sourceBillCollection.addObject(info);
		}
		_afterVoucher(ctx, sourceBillCollection);
	}

	protected SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("transitAccount.id");
		sic.add("transitAccount.longNumber");
		sic.add("transitAccount.longName");
		sic.add("contractBaseData.id");
		sic.add("contractBaseData.number");
		sic.add("contractBaseData.name");
		// sic.add("paymentBill.*");
		sic.add("paymentBill.company.*");
		sic.add("paymentBill.currency.*");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.longNumber");
		sic.add("paymentBill.payerAccount.longName");
		sic.add("paymentBill.payerAccountBank.*");
		sic.add("paymentBill.actFdcPayeeName.*");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.curProject.projectStatus.id");
		sic.add("paymentBill.curProject.costOrg.*");
		sic.add("beAccount.*");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.beforeOtherAccount.id");
		sic.add("beAccount.beforeOtherAccount.longNumber");
		sic.add("beAccount.beforeOtherAccount.longName");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.settAccount.id");
		sic.add("beAccount.settAccount.longNumber");
		sic.add("beAccount.settAccount.longName");
		sic.add("entrys.*");
		sic.add("entrys.costAccount.*");
		sic.add("entrys.costAccount.curProject.longNumber");
		sic.add("entrys.costAccount.curProject.longName");
		sic.add("entrys.product.*");
		sic.add("entrys.apportionType.id");
		sic.add("entrys.accountView.id");
		sic.add("entrys.accountView.longNumber");
		sic.add("entrys.accountView.longName");
		sic.add("voucherEntrys.*");
		sic.add("voucherEntrys.transitAccount.id");
		sic.add("voucherEntrys.transitAccount.longNumber");
		sic.add("voucherEntrys.transitAccount.longName");
		sic.add("voucherEntrys.bankAccount.*");
		sic.add("voucherEntrys.accountView.id");
		sic.add("voucherEntrys.accountView.longNumber");
		sic.add("voucherEntrys.accountView.longName");
		sic.add("voucherEntrys.currency.*");
		return sic;
	}

	public SelectorItemCollection getSelectItemForBTP() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("transitAccount.id");
		sic.add("transitAccount.longNumber");
		sic.add("transitAccount.longName");
		sic.add("contractBaseData.id");
		sic.add("contractBaseData.number");
		sic.add("contractBaseData.name");
		// sic.add("paymentBill.*");
		sic.add("paymentBill.company.*");
		sic.add("paymentBill.currency.*");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.longNumber");
		sic.add("paymentBill.payerAccount.longName");
		sic.add("paymentBill.payerAccountBank.*");
		sic.add("paymentBill.actFdcPayeeName.*");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.curProject.projectStatus.id");
		sic.add("paymentBill.curProject.costOrg.*");
		sic.add("beAccount.*");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.beforeOtherAccount.id");
		sic.add("beAccount.beforeOtherAccount.longNumber");
		sic.add("beAccount.beforeOtherAccount.longName");
		sic.add("beAccount.proAccount.id");
		sic.add("beAccount.proAccount.longNumber");
		sic.add("beAccount.proAccount.longName");
		sic.add("beAccount.settAccount.id");
		sic.add("beAccount.settAccount.longNumber");
		sic.add("beAccount.settAccount.longName");
		sic.add("entrys.*");
		sic.add("entrys.costAccount.*");
		sic.add("entrys.costAccount.curProject.longNumber");
		sic.add("entrys.costAccount.curProject.longName");
		sic.add("entrys.product.*");
		sic.add("entrys.apportionType.id");
		sic.add("entrys.accountView.id");
		sic.add("entrys.accountView.longNumber");
		sic.add("entrys.accountView.longName");
		sic.add("voucherEntrys.*");
		sic.add("voucherEntrys.transitAccount.id");
		sic.add("voucherEntrys.transitAccount.longNumber");
		sic.add("voucherEntrys.transitAccount.longName");
		sic.add("voucherEntrys.bankAccount.*");
		sic.add("voucherEntrys.accountView.id");
		sic.add("voucherEntrys.accountView.longNumber");
		sic.add("voucherEntrys.accountView.longName");
		sic.add("voucherEntrys.currency.*");
		return sic;
	}

	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("paymentBill.id");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.fdcPayType.payType.id");
		sic.add("paymentBill.fiVouchered");
		PaymentSplitInfo payBillInfo = super.getPaymentSplitInfo(ctx,
				sourceBillPk, sic);
		if (!isConWithoutTxt(payBillInfo)) {
			if (payBillInfo.getPaymentBill() != null
					&& payBillInfo.getPaymentBill().getFdcPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType().getId().toString().equals(
									PaymentTypeInfo.progressID)) {
				FilterInfo filter = new FilterInfo();
				// filter.getFilterItems().add(new
				// FilterItemInfo("paymentBill.fdcPayType..payType.id",payBillInfo.getPaymentBill().getContractBillId()));
				filter.getFilterItems().add(
						new FilterItemInfo("isProgress", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId",
								payBillInfo.getPaymentBill()
										.getContractBillId()));
				if (super.exists(ctx, filter)) {
					throw new com.kingdee.eas.fdc.finance.PaymentSplitException(
							PaymentSplitException.HAD_SETTLE);
				}
			}
		}
		boolean success = super._deleteVoucher(ctx, sourceBillPk);
		if (success) {
			// PaymentSplitInfo payBillInfo = PaymentSplitFactory
			// .getLocalInstance(ctx).getPaymentSplitInfo(sourceBillPk);
			payBillInfo.setHisStatus(null);
			payBillInfo.setVoucherTimes(0);
			payBillInfo.setHisVoucher(null);
			SelectorItemCollection sele = new SelectorItemCollection();
			sele.add("hisStatus");
			sele.add("voucherTimes");
			sele.add("hisVoucher");
			PaymentSplitFactory.getLocalInstance(ctx).updatePartial(
					payBillInfo, sele);
		}
		return success;
	}

	protected void _traceData(Context ctx, List idList) throws BOSException,
			EASBizException {
		Set idSet = FDCHelper.list2Set(idList);
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("*");
		viewSplit.getSelector().add("voucherEntrys.*");
		viewSplit.getSelector().add("paymentBill.id");
		viewSplit.getSelector().add("paymentBill.payerAccount.id");
		viewSplit.getSelector().add("paymentBill.payerAccount.isBank");
		viewSplit.getSelector().add("paymentBill.payerAccount.isCash");
		viewSplit.getSelector()
				.add("paymentBill.payerAccount.isCashEquivalent");
		viewSplit.getSelector().add("paymentBill.company.id");
		viewSplit.getSelector().add("paymentBill.company.name");
		viewSplit.getSelector().add("paymentBill.company.number");
		viewSplit.getSelector().add("beAccount.*");
		viewSplit.getSelector().add("beAccount.proAccount.*");
		viewSplit.getSelector().add("beAccount.settAccount.*");
		PaymentSplitCollection splitColl = super.getPaymentSplitCollection(ctx,
				viewSplit);
		int splitSize = splitColl.size();
		PaymentSplitInfo splitInfo = new PaymentSplitInfo();
		IBeforeAccountView iBefore = BeforeAccountViewFactory
				.getLocalInstance(ctx);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("beAccount.*");
		selector.add("transitAccount.*");
		selector.add("isNeedTransit");
		selector.add("voucherTimes");
		SelectorItemCollection selectorEntry = new SelectorItemCollection();
		selectorEntry.add("transitAccount.*");
		selectorEntry.add("isNeedTransit");
		IPaySplit4Voucher voucherSplit = PaySplit4VoucherFactory
				.getLocalInstance(ctx);
		for (int i = 0; i < splitSize; i++) {
			splitInfo = (PaymentSplitInfo) splitColl.get(i);
			// if (splitInfo.getBeAccount() == null) {
			if (splitInfo.getPaymentBill() != null
					&& splitInfo.getPaymentBill().getCompany() != null
					&& splitInfo.getPaymentBill().getCompany().getId() != null) {
				String id = splitInfo.getPaymentBill().getCompany().getId()
						.toString();
				AccountViewInfo info = FDCUtils.getDefaultFDCParamAccount(ctx,
						id);
				if (splitInfo.getPaymentBill() != null
						&& splitInfo.getPaymentBill().getPayerAccount() != null
						&& (splitInfo.getPaymentBill().getPayerAccount()
								.isIsBank()
								|| splitInfo.getPaymentBill().getPayerAccount()
										.isIsCash() || splitInfo
								.getPaymentBill().getPayerAccount()
								.isIsCashEquivalent())) {
					splitInfo.setIsNeedTransit(true);
					if (info.getId() != null) {
						splitInfo.setTransitAccount(info);
					} else
						throw new FDCException(FDCException.NOACCOUNTVIEW);
				}
				for (int entry = 0, size = splitInfo.getVoucherEntrys().size(); entry < size; entry++) {
					if (splitInfo.getVoucherEntrys().get(entry)
							.isIsNeedTransit()) {
						if (info.getId() != null) {
							splitInfo.getVoucherEntrys().get(entry)
									.setTransitAccount(info);
							voucherSplit.updatePartial(splitInfo
									.getVoucherEntrys().get(entry),
									selectorEntry);
						} else
							throw new FDCException(FDCException.NOACCOUNTVIEW);
					}
				}
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("company.id", id));
				view.setFilter(filter);
				boolean has = iBefore.exists(filter);
				if (has) {
					BeforeAccountViewInfo account = iBefore
							.getBeforeAccountViewCollection(view).get(0);
					splitInfo.setBeAccount(account);
				} else
					throw new CostSplitException(CostSplitException.NOBEACCOUNT);
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.id", splitInfo
								.getPaymentBill().getId().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE));
				view.setFilter(filter);
				int voucherSize = super.getPaymentSplitCollection(ctx, view)
						.size();
				splitInfo.setVoucherTimes(voucherSize);
			}
			// }
			super.updatePartial(ctx, splitInfo, selector);
		}
		int size = idList.size();
		for (int i = 0; i < size; i++) {
			String id = idList.get(i).toString();
			setAccoutForSplit(ctx, id);
		}
	}

	private void setAccoutForSplit(Context ctx, String id)
			throws EASBizException, BOSException {
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory
				.getLocalInstance(ctx);
		IPaymentSplitEntry iPaymentSplitEntry = PaymentSplitEntryFactory
				.getLocalInstance(ctx);
		PaymentSplitEntryCollection coll;
		CostAccountWithAccountCollection entryColl = null;
		CostAccountWithAccountInfo entryInfo = new CostAccountWithAccountInfo();
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", id));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("seq"));
		view.getSelector().add(new SelectorItemInfo("costAccount.*"));
		view.getSelector().add(new SelectorItemInfo("costAccount.parent.id"));
		view.getSelector().add(new SelectorItemInfo("accountView.*"));
		coll = iPaymentSplitEntry.getPaymentSplitEntryCollection(view);
		int entrySize = coll.size();
		for (int j = 0; j < entrySize; j++) {
			PaymentSplitEntryInfo info = coll.get(j);
			CostAccountInfo costAcc = info.getCostAccount();
			getAccForEntry(info, costAcc, iCostAccountWithAccount, entryColl,
					entryInfo, iPaymentSplitEntry, iCostAccount);
		}
	}

	private void getAccForEntry(PaymentSplitEntryInfo info,
			CostAccountInfo costAcc,
			ICostAccountWithAccount iCostAccountWithAccount,
			CostAccountWithAccountCollection entryColl,
			CostAccountWithAccountInfo entryInfo,
			IPaymentSplitEntry iPaymentSplitEntry, ICostAccount iCostAccount)
			throws EASBizException, BOSException {
		EntityViewInfo entryView = new EntityViewInfo();
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(
				new FilterItemInfo("costAccount.id", costAcc.getId()));
		if (!iCostAccountWithAccount.exists(entryFilter)) {
			if (costAcc.getLevel() == 1) {
				throw new PaymentSplitException(
						PaymentSplitException.NO_ACCOUNT);
			} else {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("level");
				selector.add("parent.*");
				selector.add("curProject.name");
				selector.add("fullOrgUnit.name");
				if (costAcc.getParent().getId() != null) {
					CostAccountInfo parent = iCostAccount.getCostAccountInfo(
							new ObjectUuidPK(costAcc.getParent().getId()),
							selector);
					getAccForEntry(info, parent, iCostAccountWithAccount,
							entryColl, entryInfo, iPaymentSplitEntry,
							iCostAccount);
				} else {
					throw new PaymentSplitException(
							PaymentSplitException.ACCOUNT_WRONG);
				}
			}
		}
		entryView.setFilter(entryFilter);
		entryColl = iCostAccountWithAccount
				.getCostAccountWithAccountCollection(entryView);
		if (entryColl.size() == 1) {
			entryInfo = entryColl.get(0);
			if (entryInfo.getAccount() != null) {
				info.setAccountView(entryInfo.getAccount());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("accountView");
				iPaymentSplitEntry.updatePartial(info, selector);
			}
		}
	}

	protected void _afterVoucher(Context ctx,
			IObjectCollection sourceBillCollection) throws BOSException,
			EASBizException {
		CoreBillBaseCollection coll = (CoreBillBaseCollection) sourceBillCollection;
		int size = coll.size();
		PaymentSplitInfo info = new PaymentSplitInfo();
		for (int i = 0; i < size; i++) {
			info = (PaymentSplitInfo) coll.get(i);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("hisStatus.*");
			sic.add("hisVoucher.*");
			sic.add("beAccount.*");
			sic.add("beAccount.proAccount.*");
			sic.add("paymentBill.id");
			sic.add("paymentBill.contractBillId");
			sic.add("paymentBill.fdcPayType.payType.id");
			sic.add("paymentBill.fiVouchered");
			sic.add("paymentBill.accountant");
			sic.add("paymentBill.voucher.*");
			sic.add("paymentBill.voucherType");
			sic.add("paymentBill.company");
			sic.add("paymentBill.curProject.projectStatus.*");
			PaymentSplitInfo payBillInfo = PaymentSplitFactory
					.getLocalInstance(ctx).getPaymentSplitInfo(
							new ObjectUuidPK(info.getId()), sic);
			payBillInfo.setHisStatus(payBillInfo.getPaymentBill()
					.getCurProject().getProjectStatus());
			int times = payBillInfo.getVoucherTimes();
			payBillInfo.setVoucherTimes(times + 1);
			payBillInfo
					.setHisVoucher(payBillInfo.getPaymentBill().getVoucher());
			SelectorItemCollection sele = new SelectorItemCollection();
			sele.add("hisStatus");
			sele.add("voucherTimes");
			sele.add("hisVoucher");
			super.updatePartial(ctx, payBillInfo, sele);
			IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
			if (!payBillInfo.isIsConWithoutText()
					&& payBillInfo.getPaymentBill() != null
					&& payBillInfo.getPaymentBill().getFdcPayType() != null
					&& payBillInfo.getPaymentBill().getFdcPayType()
							.getPayType().getId().toString().equals(
									PaymentTypeInfo.settlementID)
					&& payBillInfo.isIsProgress()) {

				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId",
								payBillInfo.getPaymentBill()
										.getContractBillId()));
				filter.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.INVALID_VALUE,
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("paymentBill.fdcPayType.payType.id",
								PaymentTypeInfo.progressID));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("hisVoucher.id");
				view.getSelector().add("paymentBill.id");
				PaymentSplitCollection payColl = PaymentSplitFactory
						.getLocalInstance(ctx).getPaymentSplitCollection(view);
				List idList = new ArrayList();
				VoucherInfo newVoucher = new VoucherInfo();
				for (Iterator iter = payColl.iterator(); iter.hasNext();) {
					PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", element
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", element.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					mapping.getSorter().add(new SorterItemInfo("date"));
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					if (relationColl.size() > 0) {
						BOTRelationInfo botInfo = relationColl.get(relationColl
								.size() - 1);
						SelectorItemCollection voucherSelector = new SelectorItemCollection();
						voucherSelector.add("id");
						voucherSelector.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(BOSUuid.read(botInfo
										.getDestObjectID())), voucherSelector);
						if (!oldInfo.isHasReversed()) {
							idList.add(botInfo.getDestObjectID());
							element.setIsRedVouchered(true);
							SelectorItemCollection selector = new SelectorItemCollection();
							selector.add("hisVoucher");
							selector.add("isRedVouchered");
							super.updatePartial(ctx, element, selector);
						}
					}
				}
				if (idList.size() > 0) {
					IObjectPK pk = voucher.reverseSaveBatch(idList);
					PaySplitUtilFacadeFactory.getLocalInstance(ctx)
							.traceReverseVoucher(pk);
				}
			}
		}
	}

	protected void _traceSplitByPay(Context ctx, String id)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		if(super.exists(ctx,filter)){
			PaymentSplitCollection coll = super.getPaymentSplitCollection(ctx, view);
			for(Iterator it = coll.iterator();it.hasNext();){
				PaymentSplitInfo info = (PaymentSplitInfo) it.next();
				setAccoutForSplit(ctx, info.getId().toString());
			}
		}
	}
	//目前只有工程量调用 
	protected IObjectValue _getNewData(Context ctx, Map param)
			throws BOSException ,EASBizException{
		String contractId=null;
		String projectId=null;
		String paymentId=(String)param.get("paymentId");
		String workLoadBillId=(String)param.get("workLoadBillId");
		if(paymentId==null&&workLoadBillId==null){
			throw new NullPointerException("paymentid and workloadId can't all be null!");
		}

		PaymentBillInfo paymentInfo=null;
		WorkLoadConfirmBillInfo workLoadInfo = null;
		

		if (paymentId != null) {
			// TODO 补全付款信息
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("contractBillId");
			paymentInfo = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(paymentId), selector);
			contractId=paymentInfo.getContractBillId();
		} else if (workLoadBillId != null) {
			// 补全工程量单据信息
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("contractBill.id");
			selector.add("contractBill.curProject.id");
			selector.add("number");
			selector.add("hasSettled");
			selector.add("workLoad");
			selector.add("period.id");
			selector.add("period.number");
			workLoadInfo = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoadConfirmBillInfo(new ObjectUuidPK(workLoadBillId), selector);
			contractId=workLoadInfo.getContractBill().getId().toString();
			if(workLoadInfo.isHasSettled()){
				//24、	结算后的工程量拆分时，加上校验，必须结算单拆分后才可以拆分此工程量确认单。否则无法带出保修款的拆分
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.appendSql("select 1 from T_Con_SettlementCostSplit where fsettlementBillId in (select fid from T_Con_ContractSettlementBill where fcontractbillid=? and fisFinalSettle=1) and fstate<>? ");
				builder.addParam(contractId);
				builder.addParam(FDCBillStateEnum.INVALID_VALUE);
				if(!builder.isExist()){
					throw new EASBizException(new NumericExceptionSubItem("100","结算后的工程量拆分必须在结算单拆分后才可以拆分！"));
				}
			}
		}
		PeriodInfo period=null;
		if(projectId==null){
			period=null;
		}else{
			period=FDCUtils.getCurrentPeriod(ctx, projectId, false);
		}
		ISplitImporter importer=new PaymentCostSplitImporter(ctx,contractId,period);
		PaymentSplitInfo info=(PaymentSplitInfo)importer.importSplit();
		
		if(paymentId!=null){
			info.setPaymentBill(paymentInfo);
			info.setIsWorkLoadBill(false);
		}else if(workLoadBillId!=null){
			//补全工程量单据信息
			info.setWorkLoadConfirmBill(workLoadInfo);
			info.setIsWorkLoadBill(true);
			info.setIsProgress(false);
			info.setPayAmount(FDCHelper.ZERO);
			info.setCompletePrjAmt(workLoadInfo.getWorkLoad());
		}
		ISplitImportHandler handler=new PaymentCostSplitImportHandler(ctx);
		handler.handle(info);
		
		return info;
	}
}