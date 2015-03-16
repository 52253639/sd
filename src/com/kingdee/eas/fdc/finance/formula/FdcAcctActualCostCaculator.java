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
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriod;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;

/**
 * @author xiaobin_li
 * @work: 实现房地产下的成本取数和资金计划取数
 * @method:fdc_acct_actual_cost  (资金计划取数-实际成本取数公式)
 */

public class FdcAcctActualCostCaculator implements ICalculator, IMethodBatchQuery 
{
	private Context ServerCtx = null;
	
	private String fdcCompanyNumber = null ;
	private String prjNumber = null ;
	private String acctLongNumber = null ;
	private String month = null;
	
	private ICalculateContextProvider context;
	public FdcAcctActualCostCaculator()
	{
		
	}
	public Context getServerCtx()
	{
		return ServerCtx;
	}
	
	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	
	public void initCalculateContext(ICalculateContextProvider context)
	{
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}
	
	public boolean batchQuery(Map methods)
	{
		SortedParameterArray params = (SortedParameterArray) methods
				.get("fdc_acct_actual_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[1]).getValue();
				acctLongNumber = (String) ((Variant) obj[2]).getValue();
				month = (String) ((Variant) obj[3]).getValue();

				try {
					BigDecimal amount = this.fdc_acct_actual_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber, month);
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
	 * 资金计划取数--实际成本取数公式
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return 
	 * @throws SQLException 
	 * @throws ParseException 
	 * @throws BOSException 
	 */
	public BigDecimal fdc_acct_actual_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month) throws SQLException, ParseException, BOSException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
//		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!'); //组织编码不用替换，与项目不同
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		transformDate = df2.parse(month);
		
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * 通过T_ORG_CostCenter中的number和fdcCompanyNumber,获取FCostCenterId
		 * 然后在T_FDC_CurProject和FCostCenterId对应的集合中，找FlongNumber和prjNumber
		 * 对应结果集，然后返回fid集合，即通过公司和项目选取当前项目的ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);
		
		/**
		 * 将当前项目的ID放在List中，便于在成本科目中查找对应的成本科目
		 */
		List curProjectID = new ArrayList();
		
		IRowSet rowSet = builder.executeQuery(ServerCtx);
		
		while((rowSet != null) && (rowSet.next()))
		{
			curProjectID.add(rowSet.getString("fid"));
		}
		
		//清除builder中的sql语句
		builder.clear();
		
		/**
		 * 通过上述获得的当前项目ID和T_FDC_CostAccount中的当前项目ID进行匹配
		 * 再加上成本科目长编码的约束，可以确定所需成本科目的ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) and fisleaf=1" );
		
		
		List costAccountID = new ArrayList();

		IRowSet rowSetRes = builder.executeQuery(ServerCtx);
		
		while((rowSetRes != null) && (rowSetRes.next()))
		{
			costAccountID.add(rowSetRes.getString("fid"));
		}
		
		builder.clear() ;
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		
		if(costAccountID.size() > 0)
		{
			//取数方式有问题，先调整  by sxhong 2009-02-09 15:08:14
			String prjId=projectID;
			FDCBudgetPeriodInfo period=FDCBudgetPeriodInfo.getPeriod(transformDate, false);
			Map toPeriodCostMap=FDCBudgetAcctHelper.getToPeriodCost(ServerCtx, prjId, period);
			Map curPeriodCostMap=FDCHelper.getDifMap(toPeriodCostMap, FDCBudgetAcctHelper.getLastPeriodCost(ServerCtx, prjId, period));
			
			for(Iterator iter=curPeriodCostMap.keySet().iterator();iter.hasNext();){
				String key=(String)iter.next();
				if(costAccountID.contains(key)){
					result=FDCNumberHelper.add(result, curPeriodCostMap.get(key));
				}
			}
			
/*			builder.appendSql(" select sum(snap.FRealizedValue) as FRealizedValue  from T_AIM_DynCostSnapShot snap ");
			builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = snap.FCostAccountId ");
			builder.appendSql( " where snap.FCostAccountId in ( ");
			builder.appendParam(costAccountID.toArray());
			builder.appendSql(" ) and ");
			
			//如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
			builder.appendSql(" snap.FSnapShotDate = ? ");
			builder.addParam( transformDate );
			
			builder.appendSql(" and snap.FSavedType = ? ");
			builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
			
			IRowSet rowSetTemp = builder.executeQuery(ServerCtx);
			
			while((rowSetTemp != null) && (rowSetTemp.next()))
			{
				result = rowSetTemp.getBigDecimal("FRealizedValue");
			}*/
		}
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
}
