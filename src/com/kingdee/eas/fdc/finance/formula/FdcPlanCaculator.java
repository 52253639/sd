package com.kingdee.eas.fdc.finance.formula;
/*
 * @(#)FdcPlanCaculator.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.IBgPeriod;

/**
 * 描述:
 * 
 * @author wangweidong date:2008-6-19
 *         <p>
 * @version EAS6.0
 */
public class FdcPlanCaculator implements ICalculator, IMethodBatchQuery {

	private ICalculateContextProvider context;

	private Context ServerCtx = null;

	/** 房地产合同付款计划接口 */
	private IContractPayPlan iContractPayPlan;

	/** 房地产合同接口 */
	private IContractBill iContractBill;
	
	//工程项目编码
	private String fdcProjectNumber;
	
	//期间编码
	private String planPeriodNumber;

	public FdcPlanCaculator() {

	}

	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}

	public boolean batchQuery(Map methods) {

		
		SortedParameterArray params = (SortedParameterArray)methods.get("fdc_plan");
		if(params!=null){
			for(int i=0;i<params.size();i++)
			{
			Parameter param = params.getParameter(i);
			Object[] obj = param.getArgs();
			
			fdcProjectNumber = (String)((Variant)obj[1]).getValue();
			planPeriodNumber = (String)((Variant)obj[2]).getValue();
		
		try {
			BigDecimal amount = fdc_plan(null,fdcProjectNumber, planPeriodNumber);
			params.getParameter(i).setValue(amount);
			
		} catch (EASBizException e) {
			e.printStackTrace();
			return false ; 
		} catch (BOSException e) {
			e.printStackTrace();
			return false ;
		}
		}
	}
		
		return true;
	}
	
	public Context getServerCtx() {
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx) {
		ServerCtx = serverCtx;
	}
	
	private IBgPeriod getBgPeriod() throws BOSException {
		return ServerCtx == null ? BgPeriodFactory.getRemoteInstance() : BgPeriodFactory.getLocalInstance(ServerCtx);
	}
	
	private IContractPayPlan getIContractPayPlan() throws BOSException {
		return iContractPayPlan != null ? iContractPayPlan : (iContractPayPlan = (ServerCtx != null ? ContractPayPlanFactory.getLocalInstance(ServerCtx)
				: ContractPayPlanFactory.getRemoteInstance()));
	}

	private IContractBill getIContractBill() throws BOSException {
		return iContractBill != null ? iContractBill : (iContractBill = (ServerCtx != null ? ContractBillFactory.getLocalInstance(ServerCtx)
				: ContractBillFactory.getRemoteInstance()));
	}
	
	/**
	 * 房地产资金计划取数公式_计划数
	 * 这个方法必须存在，而且参数必须和文件定义中的参数个数一致：否则报表无法判定取数方法是否存在
	 * @param fdcProject
	 * @param planPeriod
	 * @return BigDecimal
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public  BigDecimal fdc_plan(String xx,String fdcProject, String planPeriod) throws BOSException, EASBizException {

		IBgPeriod iBgPeriod = getBgPeriod();

		BgPeriodInfo info = null;

		BigDecimal sum = FDCHelper.ZERO;

		String oql = "SELECT * WHERE number = '" + planPeriod + "'";

		info = iBgPeriod.getBgPeriodInfo(oql);

		// 获取合同
		EntityViewInfo evi = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", fdcProject.replace('.', '!')));

		evi.getSelector().add("*");

		evi.setFilter(filter);

		ContractBillCollection contractBillCollection = getIContractBill().getContractBillCollection(evi);

		Set set = new HashSet();

		for (int j = 0; j < contractBillCollection.size(); j++) {
			set.add(contractBillCollection.get(j).getId().toString());
		}

		if (set.size() < 1)
			return sum;

		// 获取合同计划
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payDate", info.getBeginDate(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("payDate", info.getEndDate(), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId", set, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("*"));
		ContractPayPlanCollection cppc = getIContractPayPlan().getContractPayPlanCollection(evi);

		if (cppc != null) {
			for (int j = 0; j < cppc.size(); j++) {
				sum = sum.add(cppc.get(j).getPayAmount());
			}
			return sum;
		}

		return sum;
	}
}
