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
import com.kingdee.util.StringUtils;
/**
 * 
 * @author pengwei_hou
 * @date 2009-06-11
 * @work 若录入了截止期间，获取该年度年初到截止期间之间的实际付款数累计。
 * 若未录入截止期间，则获取预算当前期间年度年初到预算系统当前期间之间的实际付款数累计。
 * @method fdc_budget_acct_upToActualPay 预算系统年初截至截止期间实际付款取数函数
 */
public class FdcBudgetAcctUpToActualPayCaculator implements ICalculator, IMethodBatchQuery 
{

	private Context ServerCtx = null;

	private ICalculateContextProvider context;

	/** 成本中心编码 */
	private String costCenterNumber = null;

	/** 成本科目长编码 */
	private String acctLongNumber = null;

	/** 截止期间编码 */
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
		this.ServerCtx = context.getServerContext();
	}
	
	protected ICalculateContextProvider getProvider() {
		return this.context;
	}
	
	public boolean batchQuery(Map methods)
	{
//		获得从界面传递过来的相关参数
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_budget_acct_upToActualPay");
		
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
					BigDecimal amount = this.fdc_budget_acct_upToActualPay(
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
	 * 若录入了截止期间，获取该年度年初到截止期间之间的实际付款数累计。
	 * 若未录入截止期间，则获取预算当前期间年度年初到预算系统当前期间之间的实际付款数累计。
	 * @param costCenterNumber
	 *            实体成本中心长编码
	 * @param acctLongNumber
	 *            成本科目长编码
	 * @param budgetPeriod
	 *            预算期间
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	public BigDecimal fdc_budget_acct_upToActualPay(String costCenterNumber,
			String acctLongNumber, String budgetPeriod) throws ParseException,
			BOSException, SQLException, EASBizException {
		BgPeriodInfo period = FDCBudgetAcctCaculatorHelper.getBgPeriod(ServerCtx, budgetPeriod);
		Date startDate = null;
//		Date endDate = null;
//		endDate = FDCDateHelper.stringToDate(budgetPeriod);
//		// 由于是月度，所以应该对日期转化成日期每个月的最后一天的时间
//		endDate = FDCDateHelper.getLastDayOfMonth2(endDate);
		startDate = FDCDateHelper.getFirstYearDate(period.getYear());
//		startDate = FDCDateHelper.getLastDayOfMonth2(startDate);
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
				result = FDCBudgetAcctCaculatorHelper.getUpToActualPay(ServerCtx, costAccountID, startDate, period.getEndDate());
			}
			if(result == null)
			{
				result = FDCHelper.ZERO ;
			}
		}
		return result;
	}
}
