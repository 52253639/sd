package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriod;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;

/**
 * @author pengwei_hou
 * @work: 预算系统实际成本取数函数
 * @method:fdc_budget_acct_actualCost 实际成本取该预算期间对应月份发生的成本（全项目动态成本表上已实现产值列）累计历史月:当月-上月 未来月:最新-上月
 */

public class FdcBudgetAcctActualCostCaculator implements ICalculator, IMethodBatchQuery 
{

	private Context ServerCtx = null;

	private ICalculateContextProvider context;

	/** 成本中心编码 */
	private String costCenterNumber = null;

	/** 成本科目长编码 */
	private String acctLongNumber = null;

	/** 预算期间编码 */
	private String budgetPeriod = null;
	
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
	
	public boolean batchQuery(Map methods)
	{
//		获得从界面传递过来的相关参数
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_budget_acct_actualCost");
		
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
				
				if (StringUtils.isEmpty(costCenterNumber)) {
					if(_currOrgUnitNumber==null){
						costCenterNumber="";
					}else{
						costCenterNumber = _currOrgUnitNumber;
					}
				}
				if (StringUtils.isEmpty(budgetPeriod)) {
					budgetPeriod = _currPeriodNumber;
				}
				try {
					// 通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_budget_acct_actualCost(
							costCenterNumber, acctLongNumber, budgetPeriod);
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
	 * 预算系统实际成本取数函数
	 * 
	 * @param costCenterNumber
	 *            实体成本中心长编码
	 * @param acctLongNumber
	 *            成本项目长编码
	 * @param budgetPeriod
	 *            预算期间
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	public BigDecimal fdc_budget_acct_actualCost(String costCenterNumber,
			String acctLongNumber, String budgetPeriod) throws ParseException,
			BOSException, SQLException, EASBizException {
//		Date transformDate = null;
//		transformDate = FDCDateHelper.stringToDate(budgetPeriod);
//		// 由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
//		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		BgPeriodInfo bdPeriod = FDCBudgetAcctCaculatorHelper.getBgPeriod(ServerCtx, budgetPeriod);
		List curProjectID = FDCBudgetAcctCaculatorHelper.initProjectIDList(
				ServerCtx, costCenterNumber);
		String projectID = "" ;
		BigDecimal result = FDCHelper.ZERO;
		if(curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
			List costAccountID = FDCBudgetAcctCaculatorHelper.initCostAccount(ServerCtx, projectID, acctLongNumber);
			if(costAccountID.size() > 0)
			{
				//取数方式有问题，先调整  by sxhong 2009-02-09 15:08:14
				String prjId=projectID;
				FDCBudgetPeriodInfo period=FDCBudgetPeriodInfo.getPeriod(bdPeriod.getEndDate(), false);
				Map toPeriodCostMap=FDCBudgetAcctHelper.getToPeriodCost(ServerCtx, prjId, period);
				Map curPeriodCostMap=FDCHelper.getDifMap(toPeriodCostMap, FDCBudgetAcctHelper.getLastPeriodCost(ServerCtx, prjId, period));
				
				for(Iterator iter=curPeriodCostMap.keySet().iterator();iter.hasNext();){
					String key=(String)iter.next();
					if(costAccountID.contains(key)){
						result=FDCNumberHelper.add(result, curPeriodCostMap.get(key));
					}
				}
			}
			if(result == null)
			{
				result = FDCHelper.ZERO ;
			}
		}
				
		return result;
	}
}
