package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.excel.model.util.ObjectCache;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 预算系统取数公式辅助类
 * 
 * @author pengwei_hou
 * @date 2009-06-10
 */
public class FDCBudgetAcctCaculatorHelper {

	/**
	 * 根据成本中心长编码从工程项目与成本中心对应关系表中获取工程项目ID
	 * 
	 * @param ctx
	 * @param costCenterNumber
	 *            成本中心长编码
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static List initProjectIDList(Context ctx, String costCenterNumber)
			throws BOSException, SQLException {
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select withCost.FCurProjectID from T_FDC_ProjectWithCostCenterOU withCost ");
		builder
				.appendSql("inner join T_ORG_CostCenter costCenter on withCost.FCostCenterOUID = costCenter.fid ");
		builder.appendSql("where ");
		builder.appendParam("costCenter.FNumber", costCenterNumber);

		List curProjectID = new ArrayList();
		IRowSet rowSet = builder.executeQuery();
		while ((rowSet != null) && (rowSet.next())) {
			curProjectID.add(rowSet.getString("FCurProjectID"));
		}
		builder.clear();
		return curProjectID;
	}

	/**
	 * 获取工程项目科目
	 * 
	 * @param ctx
	 * @param projectID
	 *            工程ID
	 * @param acctLongNumber
	 *            科目长编码
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static List initCostAccount(Context ctx, String projectID,
			String acctLongNumber) throws BOSException, SQLException {
		/**
		 * 将点替换成叹号
		 */
		acctLongNumber = acctLongNumber.replace('.', '!');
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql(" and ( ");
		builder.appendSql(" FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) and fisleaf=1" );

		List costAccountID = new ArrayList();

		// 之前为了重用上面资源所引起的一些问题，现在重新定义变量
		IRowSet rowSetRes = builder.executeQuery();
		while ((rowSetRes != null) && (rowSetRes.next())) {
			costAccountID.add(rowSetRes.getString("fid"));
		}
		builder.clear();
		return costAccountID;
	}

	/**
	 * 根据科目ID与期间获取预算年表实际付款数
	 * 
	 * @param ctx
	 * @param costAccountID
	 *            科目ID
	 * @param period
	 *            日期
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getAnnualBudgetPay(Context ctx,
			List costAccountID, BgPeriodInfo period)
			throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		BigDecimal result = FDCHelper.ZERO;
		builder
				.appendSql(" select sum(item.FAmount) as FAmount  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcctItem item on entry.fid=item.fentryid where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		// 添加year(年度)约束条件
		// 如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(period.getYear()));
//		builder.appendSql("and item.fmonth = ? ");
//		builder.addParam(new Integer(period.getMonth()));

		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		builder.appendSql(" and parent.FIsLatestVer = ? ");
		builder.addParam(new Integer(1));

		IRowSet rowSetTemp = builder.executeQuery();

		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		if (result == null) {
			result = FDCHelper.ZERO;
		}
		return result;
	}

	/**
	 * 根据科目ID与期间获取预算年表实际成本数
	 * 
	 * @param ctx
	 * @param costAccountID
	 *            科目ID
	 * @param transformDate
	 *            日期
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getAnnualBudgetCost(Context ctx,
			List costAccountID, BgPeriodInfo period)
			throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		BigDecimal result = FDCHelper.ZERO;
		builder
				.appendSql(" select sum(item.FCost) as FCost  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcctItem item on entry.fid=item.fentryid where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		// 添加year(年度)约束条件
		// 如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(period.getYear()));
//		builder.appendSql("and item.fmonth = ? ");
//		builder.addParam(new Integer(period.getMonth()));

		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		builder.appendSql(" and parent.FIsLatestVer = ? ");
		builder.addParam(new Integer(1));

		IRowSet rowSetTemp = builder.executeQuery();

		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FCost");
		}
		if (result == null) {
			result = FDCHelper.ZERO;
		}
		return result;
	}
	
	/**
	 * 实际付款取该预算期间对应月份发生的实际付款数
	 * 
	 * @param ctx
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getActualPay1(Context ctx, List costAccountID,
			Date transformDate) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder
				.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder
				.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql(" where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(transformDate.getYear() + 1900));

		builder.appendSql(" and period.FMonth = ? ");
		builder.addParam(new Integer(transformDate.getMonth() + 1));

		builder.appendSql(" and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		IRowSet rowSetTemp = builder.executeQuery();
		BigDecimal result = FDCHelper.ZERO;
		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		return result;
	}
	
	/**
	 * 实际付款取该预算期间对应月份发生的实际付款数
	 * 
	 * @param ctx
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getActualPay(Context ctx, List costAccountID,
			BgPeriodInfo period) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder
				.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder
				.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql(" where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(period.getYear()));

		builder.appendSql(" and period.FMonth = ? ");
		builder.addParam(new Integer(period.getMonth()));

		builder.appendSql(" and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		IRowSet rowSetTemp = builder.executeQuery();
		BigDecimal result = FDCHelper.ZERO;
		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		return result;
	}

	/**
	 * 预算系统年初截至截止期间实际付款取数函数
	 * 
	 * @param ctx
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getUpToActualPay(Context ctx, List costAccountID,
			Date startDate, Date endDate) throws BOSException, SQLException {
		endDate = FDCDateHelper.addDays(endDate, 1);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder
				.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder
				.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql(" where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		builder.appendSql(" ( period.FEndDate between ");
		builder.appendParam(new java.sql.Date(startDate.getTime()));
		builder.appendSql(" and ");
		builder.appendParam(new java.sql.Date(endDate.getTime()));

		builder.appendSql(" ) and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		IRowSet rowSetTemp = builder.executeQuery();
		BigDecimal result = FDCHelper.ZERO;
		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = FDCHelper.add(result, rowSetTemp.getBigDecimal("FAmount"));
		}
		return result;
	}

	/**
	 * 项目月度预算表的该预算期间对应月份计划成本数据
	 * 
	 * @param ctx
	 * @param projectID
	 * @param costAccountID
	 * @param period
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getMonthlyBudgetCost(Context ctx,
			String projectID, List costAccountID, BgPeriodInfo period)
			throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 判断取数是取当前月还是上月,或者上两月
		Integer year = new Integer(period.getYear());
		Integer month2 = new Integer(period.getMonth());
		int lastMonth = 0;
		builder.clear();
		builder.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ? and fisLatestVer=1 ");
		// 保存属性为已审批才可以取数

		builder.addParam(projectID);
		builder.addParam(year);
		builder.addParam(month2);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		if (builder.isExist()) {
			lastMonth = 0;
		} else {
			int myYear = year.intValue();
			int myMonth = month2.intValue();
			if (month2.intValue() == 1) {
				myYear = year.intValue() - 1;
				myMonth = 12;
			} else {
				myYear = year.intValue();
				myMonth = month2.intValue() - 1;
			}
			builder.clear();
			builder
					.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent  ");
			builder
					.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
			builder
					.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");

			// 保存属性为已审批才可以取数

			builder.addParam(projectID);
			builder.addParam(new Integer(myYear));
			builder.addParam(new Integer(myMonth));
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			if (builder.isExist()) {
				lastMonth = 1;
				year = new Integer(myYear);
				month2 = new Integer(myMonth);
			} else {
				if (month2.intValue() == 1) {
					myYear = year.intValue() - 1;
					myMonth = 11;
				} else if (month2.intValue() == 2) {
					myYear = year.intValue() - 1;
					myMonth = 12;
				} else {
					myYear = year.intValue();
					myMonth = month2.intValue() - 2;
				}
				builder.clear();
				builder
						.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
				builder
						.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");
				builder.addParam(projectID);
				builder.addParam(new Integer(myYear));
				builder.addParam(new Integer(myMonth));
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				// 保存属性为已审批才可以取数
				if (builder.isExist()) {
					lastMonth = 2;
					year = new Integer(myYear);
					month2 = new Integer(myMonth);
				} else {
					return FDCHelper.ZERO;
				}
			}
		}
		builder.clear();

		// 执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO;

		if (costAccountID.size() > 0) {
			if (lastMonth == 0) {
				builder
						.appendSql(" select sum(entry.fcost) as fcost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" where  fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// 添加year(年度)约束条件
				// 如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// 保存属性为已审批才可以取数
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// 当分录没有绑定时才可以相加
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));

				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("fcost");
				}
			} else {
				builder
						.appendSql(" select sum(item.fcost) as fcost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcctItem item on item.fentryid=entry.fid");
				builder
						.appendSql(" where fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// 添加year(年度)约束条件
				// 如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// 保存属性为已审批才可以取数
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// 当分录没有绑定时才可以相加
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));
				builder.appendSql(" and item.fmonth=?");
				builder.addParam(new Integer(period.getMonth()));
				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("fcost");
				}
			}

		}
		return result;
	}

	/**
	 * 预算系统月度预算计划付款取数函数<pre>
	 * 由于月度付款计划是三月联动计划，所以在设计此方法时做过如下处理:<pre>
	 * 如果用户选择的当前期间没有记录就往后挪一月即判断上个月是否有记录，如果上个月还是没再往前挪一月即判断上两个月是否有数据。如若还是没有记录就直接返回0<pre>
	 * 同时要注意如果当前月就记录，就取表T_FNC_FDCMonthBudgetAcctEntry中的amount(付款审批数)；如果取得是上个月或者是上两个月的数据就表从T_FNC_FDCMonthBudgetAcctItem
	 * 里取amount(付款申报数) <pre> by Cassiel_peng 2009-11-30
	 */
	public static BigDecimal getMonthlyBudgetPay(Context ctx, String projectID,
			List costAccountID, BgPeriodInfo period) throws BOSException,
			SQLException {
		if(period.getId()==null){
			return FDCHelper.ZERO;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 判断取数是取当前月还是上月,或者上两月
		Integer year = new Integer(period.getYear());
		Integer month2 = new Integer(period.getMonth());
		int lastMonth = 0;
		builder.clear();
		builder.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ? and fisLatestVer=1 ");
		// 保存属性为已审批才可以取数

		builder.addParam(projectID);
		builder.addParam(year);
		builder.addParam(month2);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		if (builder.isExist()) {
			lastMonth = 0;
		} else {
			int myYear = year.intValue();
			int myMonth = month2.intValue();
			if (month2.intValue() == 1) {
				myYear = year.intValue() - 1;
				myMonth = 12;
			} else {
				myYear = year.intValue();
				myMonth = month2.intValue() - 1;
			}
			builder.clear();
			builder
					.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent  ");
			builder
					.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
			builder
					.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");

			// 保存属性为已审批才可以取数

			builder.addParam(projectID);
			builder.addParam(new Integer(myYear));
			builder.addParam(new Integer(myMonth));
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			if (builder.isExist()) {
				lastMonth = 1;
				year = new Integer(myYear);
				month2 = new Integer(myMonth);
			} else {
				if (month2.intValue() == 1) {
					myYear = year.intValue() - 1;
					myMonth = 11;
				} else if (month2.intValue() == 2) {
					myYear = year.intValue() - 1;
					myMonth = 12;
				} else {
					myYear = year.intValue();
					myMonth = month2.intValue() - 2;
				}
				builder.clear();
				builder
						.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
				builder
						.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");
				builder.addParam(projectID);
				builder.addParam(new Integer(myYear));
				builder.addParam(new Integer(myMonth));
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				// 保存属性为已审批才可以取数
				if (builder.isExist()) {
					lastMonth = 2;
					year = new Integer(myYear);
					month2 = new Integer(myMonth);
				} else {
					return FDCHelper.ZERO;
				}
			}
		}
		builder.clear();

		// 执行Sql语句获取结果集，如果结果集为空，则返回FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO;

		if (costAccountID.size() > 0) {
			if (lastMonth == 0) {
				builder
						.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" where  fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// 添加year(年度)约束条件
				// 如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// 保存属性为已审批才可以取数
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// 当分录没有绑定时才可以相加
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));

				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("FAmount");
				}
			} else {
				builder
						.appendSql(" select sum(item.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcctItem item on item.fentryid=entry.fid");
				builder
						.appendSql(" where fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// 添加year(年度)约束条件
				// 如果获取的日期值不为空，则进行截断操作获取年和月(KSQL中)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// 保存属性为已审批才可以取数
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// 当分录没有绑定时才可以相加
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));
				/*builder.appendSql(" and item.fmonth=?");
				builder.addParam(new Integer(lastMonth));*/
				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("FAmount");
				}
			}

		}
		return result;
	}

	/**
	 * 预算系统年初截至截止期间计划付款取数函数
	 * 
	 * @param ctx
	 * @param projectID
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getUpToBudgetPay(Context ctx, List costAccountID,
			Date startDate, Date endDate) throws BOSException, SQLException {
		endDate = FDCDateHelper.addDays(endDate, 1);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		BigDecimal result = FDCHelper.ZERO;
		if (costAccountID.size() > 0) {
			builder
					.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
			builder
					.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
			builder
					.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
			builder
					.appendSql(" where  fisLatestVer=1 and entry.FCostAccountId in ( ");
			builder.appendParam(costAccountID.toArray());
			builder.appendSql(" ) and ");

			builder.appendSql(" ( period.FEndDate between ");
			builder.appendParam(new java.sql.Date(startDate.getTime()));
			builder.appendSql(" and ");
			builder.appendParam(new java.sql.Date(endDate.getTime()));

			// 保存属性为已审批才可以取数
			builder.appendSql(" ) and parent.FState = ? ");
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

			//当分录没有绑定时才可以相加
			builder
					.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
			builder.addParam(new Boolean(false));

			IRowSet rowSetTemp = builder.executeQuery();

			while ((rowSetTemp != null) && (rowSetTemp.next())) {
				result = rowSetTemp.getBigDecimal("FAmount");
			}

		}
		return result;
	}
	/**
	 * 预算期间
	 * @param serverCtx
	 * @param periodNumber
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static BgPeriodInfo getBgPeriod(Context serverCtx, String periodNumber) throws BOSException, EASBizException {
		IBgPeriod iBgPeriod = serverCtx == null ? BgPeriodFactory.getRemoteInstance() : BgPeriodFactory.getLocalInstance(serverCtx);
		//Oh my god,竟然不处理为空的情况就直接作为参数，更为可恨的是没有捕获异常   by Cassiel_peng 2009-11-30
		if(periodNumber==null){
			periodNumber="";
		}
//		return iBgPeriod.getBgPeriodInfo("SELECT * WHERE number = '" + periodNumber + "'")==null?new BgPeriodInfo():iBgPeriod.getBgPeriodInfo("SELECT * WHERE number = '" + periodNumber + "'");
		try {
			return iBgPeriod.getBgPeriodInfo("SELECT * WHERE number = '" + periodNumber + "'");
		} catch (ObjectNotFoundException e) {
			return new BgPeriodInfo();
		}
	}
	
}
