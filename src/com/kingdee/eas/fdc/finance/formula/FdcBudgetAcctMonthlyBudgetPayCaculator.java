package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * 
 * @author pengwei_hou
 * @date 2009-06-11
 * @work 期间偏移量未录入或者为0，项目月度预算表的该预算期间对应月份计划付款数据期间偏移量为N，则为项目月度预算表的预算期间+N期间对应月份计划付款数据
 * @method fdc_budget_acct_monthlyBudgetPay 项目月度预算表的该预算期间对应月份计划付款数据
 */
public class FdcBudgetAcctMonthlyBudgetPayCaculator implements IMethodBatchQuery,
		ICalculator {
	
	private Context ServerCtx = null;

	private ICalculateContextProvider context;

	/** 实体成本中心编码 */
	private String costCenterNumber = null;

	/** 成本科目长编码 */
	private String acctLongNumber = null;

	/** 预算期间编码 */
	private String budgetPeriod = null;
	
	/**期间偏移量*/
	private String periodStep = null;
	
	public Context getServerCtx()
	{
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	
	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}
	
	protected ICalculateContextProvider getProvider() {
		return this.context;
	}
	
	public boolean batchQuery(Map methods) {
		//获得从界面传递过来的相关参数
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_budget_acct_monthlyBudgetPay");
		
		IReportPropertyAdapter adapter = null;
		
		// 获取初始参数
		adapter = getProvider().getReportAdapter();
		
		String _currOrgUnitNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_ORG_NUMBER);
		String _currPeriodNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_PERIOD_NUMBER);
		
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();
				
				costCenterNumber = (String) ((Variant) obj[0]).getValue();
				acctLongNumber = (String) ((Variant) obj[1]).getValue();
				budgetPeriod = (String) ((Variant) obj[2]).getValue();
				Object value = ((Variant) obj[3]).getValue();
				if(value != null){
					periodStep = ((Variant) obj[3]).getValue().toString();
				} else {
					periodStep = "0";
				}
//				periodStep = (String) ((Variant) obj[3]).getValue();
				//如果取数公式的实体成本中心为空默认地让其为当前成本中心
				if (StringUtils.isEmpty(costCenterNumber)) {
					if(_currOrgUnitNumber==null){
						costCenterNumber="";
					}else{
						costCenterNumber = _currOrgUnitNumber;
					}
				}
				//如果预算期间为空默认地让其为预算系统当前期间
				if (StringUtils.isEmpty(budgetPeriod)) {
					budgetPeriod = _currPeriodNumber;
				}
//				if (StringUtils.isEmpty(periodStep)) {
//					periodStep = "0";
//				}
				try {
					// 通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_budget_acct_monthlyBudgetPay(
							costCenterNumber, acctLongNumber, budgetPeriod, periodStep);
					params.getParameter(i).setValue(amount);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 预算系统月度预算计划付款取数函数
	 * 期间偏移量未录入或者为0，项目月度预算表的该预算期间对应月份计划付款数据期间偏移量为N，则为项目月度预算表的预算期间+N期间对应月份计划付款数据
	 * @param costCenterNumber 成本中心长编码
	 * @param acctLongNumber 成本科目长编码
	 * @param budgetPeriod 预算期间(年，月)
	 * @param periodStep 期间偏移量
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException 
	 */
	public BigDecimal fdc_budget_acct_monthlyBudgetPay(String costCenterNumber,
			String acctLongNumber, String budgetPeriod,String periodStep) throws ParseException,
			BOSException, SQLException, EASBizException {
		BgPeriodInfo period = FDCBudgetAcctCaculatorHelper.getBgPeriod(ServerCtx, budgetPeriod);
//		Date transformDate = null ;
//		transformDate = FDCDateHelper.stringToDate(budgetPeriod);
		int step = Integer.parseInt(periodStep);
		if(step!=0){
			int month = period.getMonth()+step;
			period.setMonth(month);
//			transformDate = DateTimeUtils.addMonth(transformDate, step);
		}
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
//		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		List curProjectID = FDCBudgetAcctCaculatorHelper.initProjectIDList(ServerCtx, costCenterNumber);
		String projectID = "" ;
		BigDecimal result = FDCHelper.ZERO;
		if(curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
			List costAccountID = FDCBudgetAcctCaculatorHelper.initCostAccount(ServerCtx, projectID, acctLongNumber);
			result = FDCBudgetAcctCaculatorHelper.getMonthlyBudgetPay(ServerCtx,projectID,costAccountID, period);	
			if(result==null){
				result = FDCHelper.ZERO;
			}
		}
		return result;
	}

}
