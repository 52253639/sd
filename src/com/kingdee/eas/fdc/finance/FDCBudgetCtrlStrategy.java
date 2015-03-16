package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public abstract class FDCBudgetCtrlStrategy implements FDCBudgetConstants {
	private FDCBudgetParam param = null;
	private Context ctx = null;
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy");
	protected FDCBudgetCtrlStrategy(Context ctx, FDCBudgetParam param) {
		this.ctx = ctx;
		this.param = param;
	}
	protected void log(String info){
		this.logger.info("FDCBudgetCtrlStrategy "+info);
	}
	public Context getCtx() {
		return ctx;
	}

	public FDCBudgetParam getFDCBudgetParam() {
		return param;
	}

	protected Map getRetMap() {
		Map retMap = new HashMap();
		retMap.put("isStrictCtrl", Boolean.valueOf(getFDCBudgetParam().isStrictCtrl()));
		retMap.put("isContractPlanCtrl", Boolean.valueOf(getFDCBudgetParam().isContractPlanCtrl()));
		retMap.put("isAcctPlanCtrl", Boolean.valueOf(getFDCBudgetParam().isAcctPlanCtrl()));
		retMap.put("isBgSysCtrl", Boolean.valueOf(getFDCBudgetParam().isBgSysCtrl()));

		return retMap;
	}

	public Map invokeBudgetCtrl(PayRequestBillInfo payReqInfo, String state) throws BOSException, EASBizException {
		if(payReqInfo.getId()!=null&&payReqInfo.get("isFill")==null){
			//取科目预算
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("payDate");
			selector.add("contractId");
			selector.add("contractNo");
			selector.add("contractName");
			selector.add("state");
			selector.add("amount");
			selector.add("acctPays.*");
			selector.add("acctPays.payRequestBill.id");
			selector.add("acctPays.costAccount.id");
			selector.add("acctPays.costAccount.longNumber");
			selector.add("acctPays.costAccount.codingNumber");
			selector.add("acctPays.costAccount.displayName");
			selector.add("acctPays.costAccount.name");
			selector.add("acctPays.period.*");
			selector.add("orgUnit.id");
			selector.add("orgUnit.number");
			selector.add("orgUnit.name");
			selector.add("currency.id");
			selector.add("currency.number");
			
			PayRequestBillInfo info=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payReqInfo.getId()),selector);
			payReqInfo.put("acctPays", info.getAcctPays());
			if (state.equals(FDCBudgetConstants.STATE_AUDIT)) {
				payReqInfo.setState(info.getState());
			}
			if (!state.equals(FDCBudgetConstants.STATE_SUBMIT)) {
				payReqInfo.setAmount(info.getAmount());
				payReqInfo.setPayDate(info.getPayDate());
				payReqInfo.setCurrency(info.getCurrency());
				
			}
			payReqInfo.setContractId(info.getContractId());
			payReqInfo.setOrgUnit(info.getOrgUnit());
			payReqInfo.setContractName(info.getContractName());
			payReqInfo.setContractNo(info.getContractNo());
			
		}
		//暂时取消对无文本合同的控制
		BOSObjectType  withoutTextContract = BOSObjectType.create("3D9A5388");
		if(payReqInfo.getContractId()!=null&&BOSUuid.read(payReqInfo.getContractId()).getType().equals(withoutTextContract)){
			return null;
		}
		boolean acctCheck=true;
		if(state.equals(STATE_ADDNEW)||state.equals(STATE_UNAUDIT)||state.equals(STATE_DELETE)){
			acctCheck=false;
		}
		boolean isNoCostSplit=false;
		//财务类合同不做科目检查
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("id", payReqInfo.getContractId());
		filter.appendFilterItem("isCoseSplit", Boolean.FALSE);
		if(ContractBillFactory.getLocalInstance(ctx).exists(filter)){
			isNoCostSplit=true;
		}

		if(isNoCostSplit&&getFDCBudgetParam().isAcctCtrl()){
			return null;
		}
		if(getFDCBudgetParam().isAcctCtrl()&&acctCheck){
			if(payReqInfo.getAcctPays()==null||payReqInfo.getAcctPays().size()==0){
//				throw new EASBizException(new NumericExceptionSubItem("100","没有关联成本科目，请到功能菜单“业务”下，点击“关联成本科目计划”或“关联待签定合同”"));
				throw new EASBizException(new NumericExceptionSubItem("100","没有关联成本科目，请到功能菜单“业务”下，点击“月度请款”关联对应的成本科目计划或待签合同"));
			}
			Date payDate = payReqInfo.getPayDate();
			if(payDate!=null&&payReqInfo.getAcctPays().get(0).getPeriod()!=null){
				Date periodDate = payReqInfo.getAcctPays().get(0).getPeriod().toDate();
				periodDate=DateTimeUtils.truncateDate(periodDate);
				payDate=DateTimeUtils.truncateDate(payDate);
				if(periodDate.compareTo(payDate)<0){
					throw new EASBizException(new NumericExceptionSubItem("101","付款申请关联成本科目付款月度不能早于付款日期所在月"));
				}
			}
			BigDecimal tmpSum=FDCHelper.ZERO;
			for(Iterator iter=payReqInfo.getAcctPays().iterator();iter.hasNext();){
				PayRequestAcctPayInfo info=(PayRequestAcctPayInfo)iter.next();
				tmpSum=FDCHelper.add(tmpSum, info.getAmount());
				
//				//处理longnumber以便与预算匹配
//				String lgNumber = info.getCostAccount().getLongNumber();
//				if(lgNumber!=null){
//					lgNumber=lgNumber.replace('!', '.');
//				}
//				info.getCostAccount().setLongNumber(lgNumber);
				
			}
			BigDecimal payReqAmt = FDCHelper.toBigDecimal(payReqInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
			if(FDCHelper.subtract(payReqAmt, tmpSum).signum()!=0){
				throw new EASBizException(new NumericExceptionSubItem("100","付款申请单金额不等于关联成本科目申请金额之和"));
			}
		}
		FDCBudgetPeriodInfo period=getPeriod(payReqInfo);
		if (state == null) {
			throw new NullPointerException("null state!");
		}
		if (state.equals(STATE_ADDNEW)) {
			return invokeAddNewBudgetCtrl(payReqInfo, period);
		}
		if (state.equals(STATE_SUBMIT)) {
			return invokeSubmitBudgetCtrl(payReqInfo, period);
		}
		if (state.equals(STATE_AUDIT)) {
			return invokeAuditBudgetCtrl(payReqInfo, period);
		}
		if (state.equals(STATE_UNAUDIT)) {
			return invokeUnAuditBudgetCtrl(payReqInfo, period);
		}
		if (state.equals(STATE_DELETE)) {
			return invokeDeleteBudgetCtrl(payReqInfo, period);
		}
		return new HashMap();
	}
	public FDCBudgetPeriodInfo getPeriod(PayRequestBillInfo payReqInfo){
		if(getFDCBudgetParam().isAcctCtrl()){
			if(payReqInfo.getAcctPays()!=null&&payReqInfo.getAcctPays().size()>0){
				return payReqInfo.getAcctPays().get(0).getPeriod();
			}
		}
		if(payReqInfo.getPayDate()!=null){
			return FDCBudgetPeriodInfo.getPeriod(payReqInfo.getPayDate(), false);
		}
		return null;
	}
	protected abstract Map invokeAddNewBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;

	protected abstract Map invokeSubmitBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;

	protected abstract Map invokeAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;

	protected abstract Map invokeUnAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;

	protected abstract Map invokeDeleteBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;

	public final static class FDCBudgetParam {
		private boolean isStrictCtrl = false;
		private boolean isContractPlanCtrl = false;
		private boolean isAcctPlanCtrl = false;
		private boolean isBgSysCtrl = false;
		private boolean isBgSysAcctCtrl=false;

		private FDCBudgetParam(Map paramMap) {
			isStrictCtrl = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL);
			isContractPlanCtrl = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY);
			isAcctPlanCtrl = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY);
			isBgSysCtrl = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY);
			isBgSysAcctCtrl=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_BUDGET_CTRLCOSTACCOUNT);
		}

		public boolean isStrictCtrl() {
			return isStrictCtrl;
		}

		public boolean isContractPlanCtrl() {
			return isContractPlanCtrl;
		}

		public boolean isAcctPlanCtrl() {
			return isAcctPlanCtrl;
		}

		public boolean isBgSysCtrl() {
			return isBgSysCtrl;
		}
		/**
		 * 科目控制
		 * @return
		 */
		public boolean isAcctCtrl(){
			if(isAcctPlanCtrl){
				return true;
			}
			if(isBgSysCtrl&&isBgSysAcctCtrl){
				return true;
			}
			return false;
		}
		public static FDCBudgetParam getInstance(Context ctx,String companyId) throws EASBizException, BOSException {
			Map hmParam = getFDCBudgetParam(ctx, companyId);
			return getInstance(hmParam);
		}
		public static FDCBudgetParam getInstance(Map hmParam) throws EASBizException, BOSException {
			return new FDCBudgetParam(hmParam);
		}
		public static Map getFDCBudgetParam(Context ctx, String companyId) throws EASBizException, BOSException {
			IObjectPK comPK = new ObjectUuidPK(companyId);
			HashMap hmParamIn = new HashMap();
			// 预算控制参数
			// 合同计划控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY, comPK);
			// 成本科目付款计划控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY, comPK);
			// 严格控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL, comPK);
			// 预算系统控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY, comPK);
			// 预算系统控制时控制到成本/付款申请金额
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CTRLCOSTACCOUNT, comPK);
			// 显示成本列
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST, comPK);
			IParamControl pc;
			if (ctx != null)
				pc = ParamControlFactory.getLocalInstance(ctx);
			else
				pc = ParamControlFactory.getRemoteInstance();
			HashMap hmParam = pc.getParamHashMap(hmParamIn);
			return hmParam;
		}
	}

	public final static class FDCBudgetCtrlStrategyType {
		private String type = null;

		private FDCBudgetCtrlStrategyType(String type) {
			this.type = type;
		}

		public final static FDCBudgetCtrlStrategyType NOTCTRL = new FDCBudgetCtrlStrategyType("notCtrl");
		public final static FDCBudgetCtrlStrategyType CONTRACTPLANCTRL = new FDCBudgetCtrlStrategyType("contractPlanCtrl");
		public final static FDCBudgetCtrlStrategyType ACCTPLANCTRL = new FDCBudgetCtrlStrategyType("acctPlanCtrl");
		public final static FDCBudgetCtrlStrategyType BGSYSCTRL = new FDCBudgetCtrlStrategyType("bgSysCtrl");

		public boolean equals(Object obj) {
			if (obj instanceof FDCBudgetCtrlStrategyType) {
				this.type.equals(((FDCBudgetCtrlStrategyType) obj).type);
			}
			return false;
		}

		public static FDCBudgetCtrlStrategyType getFDCBudgetCtrlStrategyType(String type) {
			if (type.equals(CONTRACTPLANCTRL.type)) {
				return CONTRACTPLANCTRL;
			}
			if (type.equals(ACCTPLANCTRL.type)) {
				return ACCTPLANCTRL;
			}
			if (type.equals(BGSYSCTRL.type)) {
				return BGSYSCTRL;
			}
			return NOTCTRL;
		}

		public static FDCBudgetCtrlStrategyType getFDCBudgetCtrlStrategyType(Map paramMap) {
			String type = null;
			return getFDCBudgetCtrlStrategyType(type);
		}
	}
	
	public void setPass(Map retMap,boolean isPass){
		retMap.put(FDCBudgetConstants.RETKEY_PASS, Boolean.valueOf(isPass));
	}
	public void setNoPassDetail(Map retMap,Object detail){
		retMap.put(FDCBudgetConstants.RETKEY_NOPASSDETAIL, detail);
	}
}
