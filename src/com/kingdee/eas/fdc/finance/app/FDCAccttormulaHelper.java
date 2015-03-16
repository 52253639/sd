package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @author xiaobin_li
 * @work: 实现房地产下的成本取数和资金计划取数
 * @method:fdc_acct_dyncost      (成本取数-动态成本取数公式) 
 * @method:fdc_acct_aimcost      (成本取数-目标成本取数公式)
 * @method:fdc_acct_budget_pay   (资金计划取数-预算付款取数公式)
 * @method:fdc_acct_plan_pay     (资金计划取数-计划付款取数公式)
 * @method:fdc_acct_actual_pay   (资金计划取数-实际付款取数公式)
 * @method:fdc_acct_budget_cost  (资金计划取数-预算成本取数公式)
 * @method:fdc_acct_plan_cost    (资金计划取数-计划成本取数公式)
 * @method:fdc_acct_actual_cost  (资金计划取数-实际成本取数公式)
 *            
 */
public class FDCAccttormulaHelper 
{
	/**
	 * 成本取数--动态成本取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return DynCostSnapShot中的dynCostAmt 
	 */
	public BigDecimal fdc_acct_dyncost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month) 
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
//		builder.appendSql(" select sum( FAimCostAmt ) as FAimCostAmt  from T_AIM_DynCostSnapShot where FCostAccountId in ( ");
		builder.appendSql(" select sum( FDynCostAmt ) as FDynCostAmt  from T_AIM_DynCostSnapShot where FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) ");
		
		//添加month(月度)约束条件
		builder.appendSql(" and ");
		builder.appendSql(" FSnapShotDate = ? ");
		builder.addParam( transformDate );
		
		builder.appendSql(" and FSavedType = ? ");
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FDynCostAmt");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 成本取数--目标成本取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return DynCostSnapShot中的aimCost
	 */
	public BigDecimal fdc_acct_aimcost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum( FAimCostAmt ) as FAimCostAmt  from T_AIM_DynCostSnapShot where FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) ");
		
		//添加month(月度)约束条件
		builder.appendSql(" and ");
		//如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" FSnapShotDate = ? ");
		builder.addParam( transformDate );
		
		builder.appendSql(" and FSavedType = ? ");
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAimCostAmt");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 资金计划取数--预算付款取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return T_FNC_FDCYearBudgetAcctEntry.FAmount总和
	 */
	public BigDecimal fdc_acct_budget_pay(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		builder.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//添加year(年度)约束条件
		//如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900) );
		
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		builder.appendSql( " and parent.FIsLatestVer = ? ");
		builder.addParam( new Integer(1));
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAmount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 资金计划取数--计划付款取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return T_FNC_FDCMonthBudgetAcctEntry.FAmount总和
	 */
	public BigDecimal fdc_acct_plan_pay(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//添加year(年度)约束条件
		//如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900));
		builder.appendSql( " and " );
		builder.appendSql(" period.FMonth = ? ");
		builder.addParam( new Integer(transformDate.getMonth()+1));
		
		//保存属性为已审批才可以取数
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		//当分录没有绑定时才可以相加
		builder.appendSql( " and entry.FIsAdd = ? " );
		builder.addParam( new Boolean(false));
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAmount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 资金计划取数--实际付款取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return 
	 */
	public BigDecimal fdc_acct_actual_pay(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql( " where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		builder.appendSql( " period.FYear = ? " );
		builder.addParam(new Integer(transformDate.getYear()+1900));
		
		builder.appendSql( " and period.FMonth = ? ");
		builder.addParam(new Integer(transformDate.getMonth()+1));
		
		builder.appendSql(" and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAmount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 资金计划取数--预算成本取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return T_FNC_FDCYearBudgetAcctEntry.FCost总和
	 */
	public BigDecimal fdc_acct_budget_cost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		builder.appendSql(" select sum(entry.FCost) as FCost  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//添加year(年度)约束条件
		//如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900));
		
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		builder.appendSql( " and parent.FIsLatestVer = ? ");
		builder.addParam( new Integer(1));
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FCost");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 资金计划取数--计划成本取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return T_FNC_FDCMonthBudgetAcctEntry.FCost总和
	 */
	public BigDecimal fdc_acct_plan_cost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
	
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(entry.FCost) as FCost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//添加year(年度)约束条件
		//如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900) );
		builder.appendSql( " and " );
		builder.appendSql(" period.FMonth = ? ");
		builder.addParam( new Integer(transformDate.getMonth()+1) );
		
		//保存属性为已审批才可以取数
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		//当分录没有绑定时才可以相加
		builder.appendSql( " and entry.FIsAdd = ? " );
		builder.addParam( new Boolean(false));
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FCost");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 资金计划取数--实际成本取数公式
	 * @param ctx              上下文
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber        项目长编码
	 * @param acctLongNumber   成本项目长编码
	 * @param month            月度
	 * @return 
	 */
	public BigDecimal fdc_acct_actual_cost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
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
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
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
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(snap.FRealizedValue) as FRealizedValue  from T_AIM_DynCostSnapShot snap ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = snap.FCostAccountId ");
		builder.appendSql( " where snap.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" snap.FSnapShotDate = ? ");
		builder.addParam( transformDate );
		
		builder.appendSql(" and snap.FSavedType = ? ");
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		
		//执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FRealizedValue");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
