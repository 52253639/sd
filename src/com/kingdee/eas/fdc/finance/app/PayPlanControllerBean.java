package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.scm.common.ExpenseTypeCollection;
import com.kingdee.eas.basedata.scm.common.ExpenseTypeFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.TableManagerFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.PayPlanCollection;
import com.kingdee.eas.fdc.finance.PayPlanFactory;
import com.kingdee.eas.fdc.finance.PayPlanInfo;
import com.kingdee.eas.fdc.finance.PlanEntryCollection;
import com.kingdee.eas.fdc.finance.PlanEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.report.util.DBUtil;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.LowTimer;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.util.db.SQLUtils;

public class PayPlanControllerBean extends AbstractPayPlanControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.PayPlanControllerBean");

	protected void _audit(Context ctx, Map param) throws BOSException,
			EASBizException {
	/*	String id = param.get("id").toString();
		Set notNullSet = (Set) param.get("notNullSet");
		String prjId = param.get("prjId").toString();
		for (Iterator iter = notNullSet.iterator(); iter.hasNext();) {
			String year_month = iter.next().toString();
			int yearIndex = year_month.indexOf('_');
			String year = year_month.substring(0, yearIndex);
			String month = year_month.substring(yearIndex + 1, year_month
					.length());
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_FNC_ContractPayPlan set   FAuditorID = ?  where   MONTH(FPayDate) = ?  and YEAR(FPayDate)=?  \n ");
			builder.appendSql("and FCurProjectID=? \n");
			builder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
			builder.addParam(new Integer(month));
			builder.addParam(new Integer(year));
			builder.addParam(prjId);
			builder.executeUpdate();
		}*/
		String id = param.get("id").toString();
		String year_month = param.get("year_month").toString();
		String prjId = param.get("prjId").toString();
		int yearIndex = year_month.indexOf('_');
		String year = year_month.substring(0, yearIndex);
		String month = year_month.substring(yearIndex + 1, year_month.length());
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_FNC_ContractPayPlan set   FAuditorID = ?  where   MONTH(FPayDate) = ?  and YEAR(FPayDate)=?  \n ");
		builder.appendSql("and FCurProjectID=? \n");
		builder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
		builder.addParam(new Integer(month));
		builder.addParam(new Integer(year));
		builder.addParam(prjId);
		builder.executeUpdate();

		PayPlanInfo payPlan = this.getPayPlanInfo(ctx, new ObjectUuidPK(id));
		payPlan.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		payPlan.setAuditDate(DateTimeUtils.truncateDate(new Date()));
		this.update(ctx, new ObjectUuidPK(id), payPlan);
	}

	protected void _unAudit(Context ctx, Map param) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
	/*	String id = param.get("id").toString();
		Set notNullSet = (Set) param.get("notNullSet");
		String prjId = param.get("prjId").toString();
		for (Iterator iter = notNullSet.iterator(); iter.hasNext();) {
			String year_month = iter.next().toString();
			int yearIndex = year_month.indexOf('_');
			String year = year_month.substring(0, yearIndex);
			String month = year_month.substring(yearIndex + 1, year_month
					.length());
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_FNC_ContractPayPlan set   FAuditorID = ?  where   MONTH(FPayDate) = ?  and YEAR(FPayDate)=?  \n ");
			builder.appendSql("and FCurProjectID=? \n");
			builder.addParam(null);
			builder.addParam(new Integer(month));
			builder.addParam(new Integer(year));
			builder.addParam(prjId);
			builder.executeUpdate();
		}*/
		String id = param.get("id").toString();
		String year_month = param.get("year_month").toString();
		String prjId = param.get("prjId").toString();
		int yearIndex = year_month.indexOf('_');
		String year = year_month.substring(0, yearIndex);
		String month = year_month.substring(yearIndex + 1, year_month.length());
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_FNC_ContractPayPlan set   FAuditorID = ?  where   MONTH(FPayDate) = ?  and YEAR(FPayDate)=?  \n ");
		builder.appendSql("and FCurProjectID=? \n");
		builder.addParam(null);
		builder.addParam(new Integer(month));
		builder.addParam(new Integer(year));
		builder.addParam(prjId);
		builder.executeUpdate();
		
		PayPlanInfo payPlan = this.getPayPlanInfo(ctx, new ObjectUuidPK(id));
		payPlan.setAuditor(null);
		payPlan.setAuditDate(null);
		this.update(ctx, new ObjectUuidPK(id), payPlan);
	}

	// private BigDecimal amount=FDCHelper.ZERO;
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		PayPlanInfo payPlan = this
				.getPayPlanInfo(ctx, new ObjectUuidPK(billId));
		payPlan.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		payPlan.setAuditDate(DateTimeUtils.truncateDate(new Date()));
		this.update(ctx, new ObjectUuidPK(billId), payPlan);
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		PayPlanInfo payPlan = this
				.getPayPlanInfo(ctx, new ObjectUuidPK(billId));
		payPlan.setAuditor(null);
		payPlan.setAuditDate(null);
		this.update(ctx, new ObjectUuidPK(billId), payPlan);
	}

	// protected String getTempTableName(String desc,Context ctx) throws
	// BOSException {
	// return
	// TableManagerFacadeFactory.getLocalInstance(ctx).getTableName(desc);
	// }

	protected int executeCreateAsSelectInto(String sql, SqlParams parameters,
			Context ctx) throws BOSException {
		Connection con = this.getConnection(ctx);
		try {
			return DBUtil.executeSelectInto(sql, parameters, con);
		} catch (SQLException e) {
			e.setNextException(new SQLException("SQL: " + sql));
			logger.error("Execute select into failed.(" + sql + ")", e);
			throw new SQLDataException(e);
		} finally {
			SQLUtils.cleanup(con);
		}
	}

	// 获取数据
	protected Map _getData(Context ctx, String orgUnitId, Map param)
			throws BOSException, EASBizException {

		Map data = new HashMap();
		FullOrgUnitInfo info = FullOrgUnitFactory.getLocalInstance(ctx)
				.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));

		// 获取一个临时表
		// create table tem_ec_basewbs (fid varchar(44),fiscost int)
		String createSQL = "create table FDC_payplan (fid varchar(44),fhassettled int) ";
		String tempTable = null;
		try {
			tempTable = TempTablePool.getInstance(ctx).createTempTable(
					createSQL);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new BOSException(e1);
		}
		if (tempTable == null) {
			return null;
		}
		// String tempTable = getTempTableName("FDC_payplan", ctx);

		LowTimer low = new LowTimer();
		low.reset();

		Integer startYear = ((Integer) param.get("startYear"));
		Integer startMonth = ((Integer) param.get("startMonth"));

		// Integer endYear = ((Integer)param.get("endYear"));
		// Integer endMonth = ((Integer)param.get("endMonth"));

		// 付款计划
		// , endYear, endMonth
		PayPlanInfo payPlan = initPlanData(ctx, info, startYear, startMonth);

		logger.info("0付款计划initPlanData:  " + low.msValue());
		low.reset();

		// 已付款
		Map curPayMap = null;
		Map conLastAmountMap = null;
		ContractBillCollection contractBills = null;
		Map contractPayPlanMap = null;
		Map payRequestMap = null;
		Map allPayMap = null;

		// 待签订合同付款计划
		Map dConPayPlanMap = null;

		try {

			// 合同
			// , endYear, endMonth
			contractBills = getContractColl(ctx, data, orgUnitId, startYear,
					startMonth, tempTable);

			logger.info("1合同getContractColl:  " + low.msValue());
			low.reset();

			// 合同付款计划
			contractPayPlanMap = getContractPayPlanMap(ctx, contractBills,
					tempTable);

			logger.info("2合同付款计划getContractPayPlanMap: " + low.msValue());
			low.reset();

			// 付款申请单累计金额
			payRequestMap = getRequestMap(ctx, contractBills, tempTable);

			logger.info("3付款申请单累计金额getRequestMap: " + low.msValue());
			low.reset();

			// , endYear, endMonth
			curPayMap = getCurPaymentAmountMap(ctx, contractBills, startYear,
					startMonth, tempTable);

			logger.info("4已付款getCurPaymentAmountMap: " + low.msValue());
			low.reset();

			allPayMap = getAllPaymentAmountMap(ctx, contractBills, tempTable);

			logger.info("5已付款getAllPaymentAmountMap: " + low.msValue());
			low.reset();

			// 合同最新造价
			conLastAmountMap = getConLastAmountMap(ctx, contractBills,
					tempTable);

			logger.info("6合同最新造价getConLastAmountMap: " + low.msValue());

			// 待签订合同付款计划
			dConPayPlanMap = getDConPayPlanMap(ctx, info, tempTable);

			logger.info("7待签订合同付款计划getDConPayPlanMap: " + low.msValue());
			low.reset();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}

		// 合同分工程项目累计
		Map contractMap = getContractProMap(contractBills);

		logger.info("合同分工程项目累计getContractProMap: " + low.msValue());
		low.reset();

		// 付款计划分工程项目+事项
		Map dataMap = getPlanMap(payPlan);

		logger.info("付款计划分工程项目+事项getPlanMap: " + low.msValue());
		low.reset();

		ExpenseTypeCollection expenseTypes = ExpenseTypeFactory
				.getLocalInstance(ctx).getExpenseTypeCollection();

		data.put("payPlan", payPlan);
		data.put("contractPayPlanMap", contractPayPlanMap);
		data.put("payRequestMap", payRequestMap);
		data.put("curPayMap", curPayMap);
		data.put("allPayMap", allPayMap);
		data.put("conLastAmountMap", conLastAmountMap);
		data.put("contractMap", contractMap);

		data.put("dataMap", dataMap);
		data.put("expenseTypes", expenseTypes);

		// 待签订合同付款计划
		data.put("dConPayPlanMap", dConPayPlanMap);

		TempTablePool.getInstance(ctx).releaseTable(tempTable);

		return data;
	}

	// 付款计划
	private PayPlanInfo initPlanData(Context ctx, FullOrgUnitInfo orgInfo,
			Integer startYear, Integer startMonth) throws BOSException,
			EASBizException {

		PayPlanInfo payPlan;

		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", orgInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year", startYear));
		filter.getFilterItems().add(new FilterItemInfo("month", startMonth));
		view.getSelector().add("*");
		view.getSelector().add("entrys.*");

		SorterItemCollection sorter = view.getSorter();
		sorter.add(new SorterItemInfo("year"));
		sorter.add(new SorterItemInfo("month"));
		// sorter.add(new SorterItemInfo("entrys.seq"));

		PayPlanCollection planCollection = PayPlanFactory.getLocalInstance(ctx)
				.getPayPlanCollection(view);
		if (planCollection.size() > 0) {
			payPlan = planCollection.get(0);
			for (int i = 1; i < planCollection.size(); i++) {
				PayPlanInfo info = planCollection.get(i);
				info.setYear(0);
				info.setMonth(0);
				PayPlanFactory.getLocalInstance(ctx).update(
						new ObjectUuidPK(info.getId()), info);
			}
		} else {
			payPlan = new PayPlanInfo();
			payPlan.setFullOrgUnit(orgInfo);
			payPlan.setYear(startYear.intValue());
			payPlan.setMonth(startMonth.intValue());
		}

		return payPlan;
	}

	// 合同
	private ContractBillCollection getContractColl(Context ctx, Map data,
			String orgId, Integer startYear, Integer startMonth,
			String tempTable) throws BOSException, SQLException,
			EASBizException {

		boolean viewPlanByCostcenter = FDCUtils.getDefaultFDCParamByKey(ctx,
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(),
				FDCConstants.FDC_PARAM_VIEWPLAN);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, startYear.intValue());
		cal.set(Calendar.MONTH, startMonth.intValue());
		cal.set(Calendar.DATE, 1);
		// Date date = DateTimeUtils.truncateDate(cal.getTime());
		// Timestamp time = new Timestamp(date.getTime());

		// insert into tem_ec_basewbs select fid ,fiscost from t_ec_basewbs
		String sql = "insert into  "
				+ tempTable
				+ "  (fid,fhassettled)  (  select a.Fid ,a.fhassettled from T_Con_ContractBill a "
				+ " inner join t_fdc_curproject b on a.fcurprojectid=b.fid "
				+ (viewPlanByCostcenter ? "where a.forgunitid=? "
						: " where a.frespdeptid=? ")
				+ " and b.fisenabled=1 and a.fisamtwithoutcost=0  )";
		// SqlParams sp = new SqlParams();
		// sp.addString(orgId);
		DbUtil.execute(ctx, sql, new Object[] { orgId });
		// 数据插入临时表
		// executeCreateAsSelectInto( sql, sp, ctx);

		String selectSql = "select a.Fid id,a.fcurprojectId projectId,a.fnumber conNumber,a.fname conName,"
				+ " a.famount amount,a.fsettleamt settleamt ,a.fhassettled hassettled,  					"
				+ " c.fnumber sNumber,c.fname_l2 sName from T_Con_ContractBill a  	"
				+ " inner join t_fdc_curproject b on a.fcurprojectid=b.fid 			"
				+ " inner join t_bd_supplier c on a.fpartbid=c.fid 					"
				+ (viewPlanByCostcenter ? "where a.forgunitid=? "
						: " where a.frespdeptid=? ")
				+ " and b.fisenabled=1 and a.fisamtwithoutcost=0		";

		// for(int i=0;i<contractBills.size();i++){
		// ContractBillInfo bill= contractBills.get(i);
		// CurProjectInfo project = bill.getCurProject();
		//			
		// if(projectId.contains(project.getId().toString())){
		// continue ;
		// }else{
		// projectId.add(project.getId().toString());
		// }
		// }
		IRowSet rs = DbUtil
				.executeQuery(ctx, selectSql, new Object[] { orgId });

		ContractBillCollection contractBills = new ContractBillCollection();
		List projectIdList = new ArrayList();

		while (rs != null && rs.next()) {
			ContractBillInfo bill = new ContractBillInfo();

			bill.setId(BOSUuid.read(rs.getString("id")));
			bill.setNumber(rs.getString("conNumber"));
			bill.setName(rs.getString("conName"));
			bill.setAmount(rs.getBigDecimal("amount"));
			bill.setSettleAmt(rs.getBigDecimal("settleamt"));
			bill.setHasSettled(rs.getBoolean("hassettled"));

			SupplierInfo sup = new SupplierInfo();
			sup.setNumber(rs.getString("sNumber"));
			sup.setName(rs.getString("sName"));
			bill.setPartB(sup);

			String projectId = rs.getString("projectId");
			CurProjectInfo project = new CurProjectInfo();
			project.setId(BOSUuid.read(rs.getString("projectId")));
			bill.setCurProject(project);

			contractBills.add(bill);

			if (projectIdList.contains(projectId)) {
				continue;
			} else {
				projectIdList.add(projectId);
			}
		}

		data.put("projectCol", projectIdList);

		return contractBills;
	}

	// 没有改金大侠以前写的getContractColl()方法,而是重载了一个方法. by cassiel_peng 2009-12-17
	private ContractBillCollection getContractColl(Context ctx, Map data,
			String orgId, Set conTypeIds, Integer startYear,
			Integer startMonth, String adminId, String supplierId,
			String tempTable) throws BOSException, SQLException,
			EASBizException {

		boolean viewPlanByCostcenter = FDCUtils.getDefaultFDCParamByKey(ctx,
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(),
				FDCConstants.FDC_PARAM_VIEWPLAN);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, startYear.intValue());
		cal.set(Calendar.MONTH, startMonth.intValue());
		cal.set(Calendar.DATE, 1);

		String sql = "insert into  "
				+ tempTable
				+ "  (fid,fhassettled)  (  select a.Fid ,a.fhassettled from T_Con_ContractBill a "
				+ " inner join t_fdc_curproject b on a.fcurprojectid=b.fid "
				+ (viewPlanByCostcenter ? "where a.forgunitid=? "
						: " where a.frespdeptid=? ")
				+ " and b.fisenabled=1 and a.fisamtwithoutcost=0  )";
		DbUtil.execute(ctx, sql, new Object[] { orgId });

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select a.Fid id,a.fcurprojectId projectId,a.fnumber conNumber,a.fname conName,a.famount amount,a.fsettleamt settleamt  ");
		builder
				.appendSql("  ,a.fhassettled hassettled, c.fnumber sNumber,c.fname_l2 sName,d.fname_l2 contractTypeName,e.fname_l2 respdept from T_Con_ContractBill a  ");
		builder
				.appendSql("  inner join t_fdc_curproject b on a.fcurprojectid=b.fid  ");
		builder.appendSql("  inner join t_bd_supplier c on a.fpartbid=c.fid ");
		builder
				.appendSql("  inner join t_fdc_contracttype d on d.fid=a.fcontracttypeid ");
		builder.appendSql("  inner join t_org_admin e on e.fid=a.FRespDeptID ");
		builder.appendSql("  inner join T_BD_Supplier f on f.fid=a.FPartBID ");
		if (viewPlanByCostcenter) {
			builder.appendSql("where a.forgunitid=? ");
			builder.addParam(orgId);
		} else {
			builder.appendSql("where a.frespdeptid=? ");
			builder.addParam(orgId);
		}
		// 责任部门
		if (adminId != null && !"".equals(adminId)) {
			builder.appendSql(" and a.FRespDeptID=? ");
			builder.addParam(adminId);
		}
		// 乙方单位
		if (supplierId != null && !"".equals(supplierId)) {
			builder.appendSql(" and a.FPartBID=? ");
			builder.addParam(supplierId);
		}

		builder.appendSql("  and b.fisenabled=1 and a.fisamtwithoutcost=0	 ");
		if (conTypeIds != null && conTypeIds.size() > 0) {// 一定要如此处理，避免空指针和数组异常
			builder.appendParam("  and a.fcontracttypeid ", conTypeIds
					.toArray());
		}
		IRowSet rs = builder.executeQuery();

		ContractBillCollection contractBills = new ContractBillCollection();

		while (rs != null && rs.next()) {
			ContractBillInfo bill = new ContractBillInfo();

			bill.setId(BOSUuid.read(rs.getString("id")));
			bill.setNumber(rs.getString("conNumber"));
			bill.setName(rs.getString("conName"));
			bill.setAmount(rs.getBigDecimal("amount"));
			bill.setSettleAmt(rs.getBigDecimal("settleamt"));
			bill.setHasSettled(rs.getBoolean("hassettled"));

			SupplierInfo sup = new SupplierInfo();
			sup.setNumber(rs.getString("sNumber"));
			sup.setName(rs.getString("sName"));
			bill.setPartB(sup);

			// 合同类型
			ContractTypeInfo contractTypeInfo = new ContractTypeInfo();
			contractTypeInfo.setName(rs.getString("contractTypeName"));
			bill.setContractType(contractTypeInfo);
			// 责任部门
			AdminOrgUnitInfo resprept = new AdminOrgUnitInfo();
			resprept.setName(rs.getString("respdept"));
			bill.setRespDept(resprept);

			String projectId = rs.getString("projectId");
			CurProjectInfo project = new CurProjectInfo();
			project.setId(BOSUuid.read(rs.getString("projectId")));
			bill.setCurProject(project);

			contractBills.add(bill);
		}
		/*
		 * 客户端中合同费用项目的数量是由data.put("projectCol",projectIdList);中的projectIdList数量决定的
		 * ,但是在上面那句SQL 查询出来的因为加了合同类型和责任部门以及乙方单位的过滤，导致结果不准确。故单独查询。
		 */
		String selectSql = "select a.Fid id,a.fcurprojectId projectId from T_Con_ContractBill a  	"
				+ "  inner join t_fdc_curproject b on a.fcurprojectid=b.fid"
				+ "  inner join t_bd_supplier c on a.fpartbid=c.fid"
				+ (viewPlanByCostcenter ? " where a.forgunitid=? "
						: " where a.frespdeptid=? ")
				+ "  and b.fisenabled=1 and a.fisamtwithoutcost=0		";
		IRowSet _rs = DbUtil.executeQuery(ctx, selectSql,
				new Object[] { orgId });

		List projectIdList = new ArrayList();
		while (_rs != null && _rs.next()) {
			String projectId = _rs.getString("projectId");
			if (projectIdList.contains(projectId)) {
				continue;
			} else {
				projectIdList.add(projectId);
			}
			data.put("projectCol", projectIdList);
		}
		return contractBills;
	}

	// 合同付款计划
	private Map getContractPayPlanMap(Context ctx,
			ContractBillCollection contractBills, String tempTable)
			throws BOSException {
		Map contractPayPlanMap = new HashMap();
		if (contractBills.size() == 0) {
			return contractPayPlanMap;
		}
		// Set contractIdSet = new HashSet();
		// for (int i = 0; i < contractBills.size(); i++) {
		// contractIdSet.add(contractBills.get(i).getId().toString());
		// }

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("payDate");
		view.getSelector().add("contractId.id");
		view.getSelector().add("payAmount");
		view.getSelector().add("description");

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
		// new FilterItemInfo("contractId", contractIdSet,CompareType.INCLUDE));
				new FilterItemInfo("contractId", "(select fid from "
						+ tempTable + ")", CompareType.INNER));

		ContractPayPlanCollection contractPayPlanCollection = ContractPayPlanFactory
				.getLocalInstance(ctx).getContractPayPlanCollection(view);

		for (int i = 0; i < contractPayPlanCollection.size(); i++) {
			ContractPayPlanInfo info = contractPayPlanCollection.get(i);
			Date payDate = info.getPayDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(payDate);

			String key = info.getContractId().getId().toString()
					+ cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH);
			if (contractPayPlanMap.containsKey(key)) {

				ContractPayPlanInfo contractPayPlanInfo = (ContractPayPlanInfo) contractPayPlanMap
						.get(key);

				info.setPayAmount(info.getPayAmount().add(
						contractPayPlanInfo.getPayAmount()));
			}

			contractPayPlanMap.put(key, info);
		}

		return contractPayPlanMap;
	}

	// 待签订合同付款计划
	private Map getDConPayPlanMap(Context ctx, FullOrgUnitInfo orgInfo,
			String tempTable) throws BOSException {
		Map dConPayPlanMap = new HashMap();
		// int itemQuantity = 0; //判断待签订合同的条数
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo fi = new FilterInfo();
		view.getSelector().add("parent.curProject.id");
		view.getSelector().add("parent.fdcPeriod.*");
		// view.getSelector().add("parent.orgUnit.id");
		view.getSelector().add("itemType");
		view.getSelector().add("amount");
		view.setFilter(fi);
		fi.appendFilterItem("itemType",
				FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
		// int itemQuantity = 0;
		FDCMonthBudgetAcctEntryCollection acctCollection = FDCMonthBudgetAcctEntryFactory
				.getLocalInstance(ctx).getFDCMonthBudgetAcctEntryCollection(
						view);
		Iterator it = acctCollection.iterator();
		while (it.hasNext()) {
			FDCMonthBudgetAcctEntryInfo info = (FDCMonthBudgetAcctEntryInfo) it
					.next();
			BigDecimal amount = FDCHelper.ZERO;

			// if(info.getParent().getCurProject()!=null){
			// if(info.getItemType().toString().equalsIgnoreCase("待签订合同")){
			if (info.getParent().getCurProject().getId() != null
					&& info.getAmount() != null) {
				String key = info.getParent().getCurProject().getId()
						.toString()
						+ info.getParent().getFdcPeriod().getYear()
						+ ""
						+ (info.getParent().getFdcPeriod().getMonth() - 1);
				if (/* itemQuantity>0 && */dConPayPlanMap.containsKey(key)) {
					amount = (BigDecimal) dConPayPlanMap.get(key);
					// if(info.getAmount()!=null){
					amount = amount.add(info.getAmount());
					// }
					dConPayPlanMap.put(key, amount);
				} else {
					// if(info.getAmount()!=null){
					amount = amount.add(info.getAmount());
					// }
					// if(info.getAmount()!=null){
					dConPayPlanMap.put(key, amount);
					// itemQuantity++;
					// }
				}
				// }
			}
		}
		return dConPayPlanMap;
	}

	// 付款申请单累计金额
	private Map getRequestMap(Context ctx,
			ContractBillCollection contractBills, String tempTable)
			throws BOSException {
		Map payRequestMap = new HashMap();
		if (contractBills.size() == 0) {
			return payRequestMap;
		}
		// Set contractIdSet = new HashSet();
		// for (int i = 0; i < contractBills.size(); i++) {
		// contractIdSet.add(contractBills.get(i).getId().toString());
		// }
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("amount"));
		view.getSelector().add(new SelectorItemInfo("contractId"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(
		// new FilterItemInfo("contractId", contractIdSet,CompareType.INCLUDE));
				new FilterItemInfo("contractId", "(select fid from "
						+ tempTable + ")", CompareType.INNER));

		PayRequestBillCollection payRequestBillCollection = PayRequestBillFactory
				.getLocalInstance(ctx).getPayRequestBillCollection(view);
		for (int i = 0; i < payRequestBillCollection.size(); i++) {
			PayRequestBillInfo info = payRequestBillCollection.get(i);
			String contractId = info.getContractId();
			// 空值判断，否则会出现中断
			if (info.getAmount() != null) {
				BigDecimal value = info.getAmount();
				if (payRequestMap.containsKey(contractId)) {
					BigDecimal sum = (BigDecimal) payRequestMap.get(contractId);
					payRequestMap.put(contractId, sum.add(value));
				} else {
					payRequestMap.put(contractId, value);
				}
			}
		}
		return payRequestMap;
	}

	Integer endYear = null;
	Integer endMonth = null;

	// 已付款
	public Map getCurPaymentAmountMap(Context ctx,
			ContractBillCollection contractBills, Integer startYear,
			Integer startMonth, String tempTable) throws BOSException,
			SQLException {
		Map payMap = new HashMap();
		if (contractBills == null || contractBills.size() == 0) {
			return payMap;
		}
		// 已付款过滤日期应该是以小于endYear和endMonth计算
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, endYear.intValue());
		cal.set(Calendar.MONTH, endMonth.intValue());
		cal.set(Calendar.DATE, 1);
		Date beginDate = DateTimeUtils.truncateDate(cal.getTime());
		String sql = "select FContractBillId,sum(FAmount) amount From T_CAS_Paymentbill where ";
		sql += " FPayDate < {" + TypeConversionUtils.objToDate(beginDate)
				+ "} and FBillStatus=" + BillStatusEnum.PAYED_VALUE
				+ " and FContractBillId in ";
		sql += "(select fid from " + tempTable + ")";
		sql += "group by FContractBillId";
		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql).executeSQL();
		while (rs.next()) {
			String contractId = rs.getString("FContractBillId");
			BigDecimal amount = rs.getBigDecimal("amount");
			payMap.put(contractId, amount);
		}
		return payMap;
	}

	// 全部付款
	public Map getAllPaymentAmountMap(Context ctx,
			ContractBillCollection contractBills, String tempTable)
			throws BOSException, SQLException {
		Map payMap = new HashMap();
		if (contractBills == null || contractBills.size() == 0) {
			return payMap;
		}
		String sql = "select FContractBillId,sum(FAmount) amount From T_CAS_Paymentbill where ";
		sql += " FBillStatus=" + BillStatusEnum.PAYED_VALUE
				+ " and FContractBillId in ";
		// for (int i = 0; i < contractBills.size(); i++) {
		// String id = contractBills.get(i).getId().toString();
		// if (i != 0) {
		// sql += ",";
		// }
		// sql += "'" + id + "'";
		// }
		// sql += ")";
		sql += "(select fid from " + tempTable + ")";
		sql += "group by FContractBillId";
		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql).executeSQL();
		while (rs.next()) {
			String contractId = rs.getString("FContractBillId");
			BigDecimal amount = rs.getBigDecimal("amount");
			payMap.put(contractId, amount);
		}
		return payMap;
	}

	// 合同最新造价
	private Map getConLastAmountMap(Context ctx,
			ContractBillCollection contractBills, String tempTable)
			throws BOSException, SQLException {
		Map lastAmountMap = new HashMap();
		if (contractBills.size() == 0) {
			return lastAmountMap;
		}

		Map changeAmountMap = new HashMap();
		Map guerdonAmtMap = new HashMap();
		Map compenseAmtMap = new HashMap();
		Map deductAmtMap = new HashMap();

		// 变更
		String changeSql = "select FHASSETTLED,sum(FAMOUNT) FAMOUNT ,sum(FBALANCEAMOUNT) FBALANCEAMOUNT,FCONTRACTBILLID from T_CON_CONTRACTCHANGEBILL a "
				+ " where FCONTRACTBILLID in (select fid from  "
				+ tempTable
				+ " where fhassettled=0 ) group by FHASSETTLED,FCONTRACTBILLID";
		IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, changeSql)
				.executeSQL();
		while (rs != null && rs.next()) {

			boolean isHasSettled = rs.getBoolean("FHASSETTLED");
			String conId = rs.getString("FCONTRACTBILLID");

			BigDecimal amount = null;
			if (isHasSettled) {
				amount = rs.getBigDecimal("FBALANCEAMOUNT");
			} else {
				amount = rs.getBigDecimal("FAMOUNT");
			}

			if (amount != null) {
				if (changeAmountMap.containsKey(conId)) {
					BigDecimal bmount = (BigDecimal) changeAmountMap.get(conId);
					amount = amount.add(bmount);
				}
				changeAmountMap.put(conId, amount);
			}
		}

		// 奖励
		String guerdonSql = "select sum(famount) as amount,fcontractid from T_CON_GuerdonBill where  fstate = '4AUDITTED' AND fisGuerdoned = 1  "
				+ " and fcontractid in (select fid from  "
				+ tempTable
				+ " where fhassettled=0 ) group by  fcontractid ";
		rs = SQLExecutorFactory.getLocalInstance(ctx, guerdonSql).executeSQL();
		while (rs != null && rs.next()) {
			String conId = rs.getString("fcontractid");
			if (rs.getBigDecimal("amount") != null) {
				guerdonAmtMap.put(conId, rs.getBigDecimal("amount"));
			}
		}

		// 违约
		String compensationSql = "select sum(famount) as amount,fcontractid from T_CON_CompensationBill where  fstate = '4AUDITTED' AND fisCompensated = 1  "
				+ " and fcontractid in (select fid from  "
				+ tempTable
				+ " where fhassettled=0 ) group by  fcontractid ";
		rs = SQLExecutorFactory.getLocalInstance(ctx, compensationSql)
				.executeSQL();
		while (rs != null && rs.next()) {
			String conId = rs.getString("fcontractid");
			if (rs.getBigDecimal("amount") != null) {
				compenseAmtMap.put(conId, rs.getBigDecimal("amount"));
			}
		}

		// 奖励
		String deductSql = "select sum(a.famount) as amount,b.fcontractid from T_CON_DeductOfPayReqBill a "
				+ " inner join T_CON_PayRequestBill  b on a.fpayRequestBillId=b.fid  "
				+ " where b.fcontractid in (select fid  from "
				+ tempTable
				+ " where fhassettled=0 ) group by  b.fcontractid ";
		rs = SQLExecutorFactory.getLocalInstance(ctx, deductSql).executeSQL();
		while (rs != null && rs.next()) {
			String conId = rs.getString("fcontractid");
			if (rs.getBigDecimal("amount") != null) {
				deductAmtMap.put(conId, rs.getBigDecimal("amount"));
			}
		}

		for (int i = 0; i < contractBills.size(); i++) {
			ContractBillInfo contract = contractBills.get(i);
			BigDecimal lastAmount = FDCHelper.ZERO;
			BigDecimal conAmount = contract.getAmount();
			if (conAmount == null) {
				conAmount = FDCHelper.ZERO;
			}

			String contractId = contract.getId().toString();
			if (contract.isHasSettled()) {
				lastAmount = contract.getSettleAmt();
			} else {
				BigDecimal changeAmount = FDCHelper.ZERO;
				BigDecimal guerdonAmt = FDCHelper.ZERO;
				BigDecimal compenseAmt = FDCHelper.ZERO;
				BigDecimal deductAmt = FDCHelper.ZERO;

				if (changeAmountMap.containsKey(contractId)) {
					changeAmount = (BigDecimal) changeAmountMap.get(contractId);
				}
				if (guerdonAmtMap.containsKey(contractId)) {
					guerdonAmt = (BigDecimal) guerdonAmtMap.get(contractId);
				}
				if (compenseAmtMap.containsKey(contractId)) {
					compenseAmt = (BigDecimal) compenseAmtMap.get(contractId);
				}
				if (deductAmtMap.containsKey(contractId)) {
					deductAmt = (BigDecimal) deductAmtMap.get(contractId);
				}

				lastAmount = conAmount
						.add(FDCHelper.toBigDecimal(changeAmount));
				lastAmount = lastAmount.add(guerdonAmt).subtract(compenseAmt)
						.subtract(deductAmt);
			}

			lastAmountMap.put(contractId, lastAmount);
		}
		//			
		// if (!contract.isHasSettled()) {
		//
		// //变更
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("contractBill.id",info.getId().toString()));
		// filter.getFilterItems().add(new FilterItemInfo("state",
		// FDCBillStateEnum.AUDITTED_VALUE));
		// filter.getFilterItems().add(new FilterItemInfo("state",
		// FDCBillStateEnum.VISA_VALUE));
		// filter.getFilterItems().add(new FilterItemInfo("state",
		// FDCBillStateEnum.ANNOUNCE_VALUE));
		// filter.setMaskString("#0 and (#1 or #2 or #3)");
		// view.setFilter(filter);
		// view.getSelector().add("amount");
		// view.getSelector().add("balanceAmount");
		// view.getSelector().add("hasSettled");
		// IObjectCollection collection = ContractChangeBillFactory
		// .getLocalInstance(ctx).getContractChangeBillCollection(view);
		// ContractChangeBillInfo billInfo;
		//
		// BigDecimal changeAmount = FDCHelper.ZERO;
		// for (Iterator iter = collection.iterator(); iter.hasNext();) {
		// billInfo = (ContractChangeBillInfo) iter.next();
		// if (billInfo.getAmount() != null) {
		// if(billInfo.isHasSettled()){
		// changeAmount = changeAmount.add(billInfo.getBalanceAmount());
		// }else{
		// changeAmount = changeAmount.add(billInfo.getAmount());
		// }
		// }
		// }
		//				
		// //奖励
		// BigDecimal guerdonAmt=FDCHelper.ZERO;
		// BigDecimal compenseAmt=FDCHelper.ZERO;
		// BigDecimal deductAmt=FDCHelper.ZERO;
		// FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		// builder.appendSql(
		// "select sum(famount) as amount from T_CON_GuerdonBill where  fcontractid =? AND fstate = ? AND fisGuerdoned = 1"
		// );
		// builder.addParam(info.getId().toString());
		// builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		// IRowSet rowSet = builder.executeQuery();
		// if(rowSet.size()==1){
		// rowSet.next();
		// guerdonAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
		// }
		//				
		// //违约
		// builder.clear();
		// builder.appendSql(
		// "select sum(famount) as amount from T_CON_CompensationBill where  fcontractid =? AND fstate = ? AND fisCompensated = 1"
		// );
		// builder.addParam(info.getId().toString());
		// builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		// rowSet = builder.executeQuery();
		// if(rowSet.size()==1){
		// rowSet.next();
		// compenseAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
		// }
		//				
		// //扣款
		// builder.clear();
		// builder.appendSql(
		// "select sum(famount) as amount from T_CON_DeductOfPayReqBill " +
		// "where fpayRequestBillId in (select fid from T_CON_PayRequestBill where fcontractid=?)"
		// );
		// builder.addParam(info.getId().toString());
		// rowSet = builder.executeQuery();
		// if(rowSet.size()==1){
		// rowSet.next();
		// deductAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
		// }
		//								
		// /*
		// * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的)
		// * by sxhong 2007/09/28
		// */
		// lastAmount = conAmount.add(FDCHelper.toBigDecimal(changeAmount));
		// lastAmount =
		// lastAmount.add(guerdonAmt).subtract(compenseAmt).subtract(deductAmt);
		//			
		// } else {
		// lastAmount = contract.getSettleAmt();
		// }
		//			
		// lastAmountMap.put(info.getId().toString(), lastAmount);
		// }

		return lastAmountMap;
	}

	// 合同分工程项目累计
	private Map getContractProMap(ContractBillCollection contractBills) {
		Map contractMap = new HashMap();
		for (int i = 0; i < contractBills.size(); i++) {
			ContractBillInfo info = contractBills.get(i);
			String projectId = info.getCurProject().getId().toString();
			if (contractMap.containsKey(projectId)) {
				ContractBillCollection cons = (ContractBillCollection) contractMap
						.get(projectId);
				cons.add(info);
			} else {
				ContractBillCollection cons = new ContractBillCollection();
				cons.add(info);
				contractMap.put(projectId, cons);
			}
		}
		return contractMap;
	}

	// 付款计划分工程项目+事项
	private Map getPlanMap(PayPlanInfo payPlan) throws BOSException {
		Map dataMap = new TreeMap();
		PlanEntryCollection entrys = payPlan.getEntrys();
		for (int i = 0; i < entrys.size(); i++) {
			PlanEntryInfo info = entrys.get(i);
			String projectId = info.getProjectId();
			String expenseTypeId = info.getExpenseTypeId();
			PlanEntryCollection col = new PlanEntryCollection();
			if (dataMap.containsKey(projectId + expenseTypeId)) {
				col = (PlanEntryCollection) dataMap.get(projectId
						+ expenseTypeId);
			}
			col.add(info);
			dataMap.put(projectId + expenseTypeId, col);
		}
		return dataMap;
	}

	protected Map _getData(Context ctx, Set orgIds, Map param)
			throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		endYear = ((Integer) param.get("endYear"));
		endMonth = ((Integer) param.get("endMonth"));
		if (orgIds == null || orgIds.size() == 0) {
			return new HashMap();
		} else {
			Map dataMap = new HashMap();
			for (Iterator it = orgIds.iterator(); it.hasNext();) {
				String orgId = (String) it.next();
				Map tempMap = _getData(ctx, orgId, param);
				Set tempKeySet = tempMap.keySet();
				for (Iterator keys = tempKeySet.iterator(); keys.hasNext();) {
					String key = (String) keys.next();
					dataMap.put(orgId + "_" + key, tempMap.get(key));
				}
			}

			return dataMap;
		}

	}

	/*
	 * 重载:因为在月度付款计划里增加了合同类型数，取得右表的付款计划相关数据的时候还需要根据合同类型来进行过滤 by cassiel_peng
	 * 2009-12-17
	 */
	protected Map _getData(Context ctx, Set orgIds, Set conTypeIdS, Map param)
			throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		endYear = ((Integer) param.get("endYear"));
		endMonth = ((Integer) param.get("endMonth"));
		if (orgIds == null || orgIds.size() == 0) {
			return new HashMap();
		} else {
			Map dataMap = new HashMap();
			for (Iterator it = orgIds.iterator(); it.hasNext();) {
				String orgId = (String) it.next();
				Map tempMap = _getData(ctx, orgId, conTypeIdS, param);
				Set tempKeySet = tempMap.keySet();
				for (Iterator keys = tempKeySet.iterator(); keys.hasNext();) {
					String key = (String) keys.next();
					dataMap.put(orgId + "_" + key, tempMap.get(key));
				}
			}
			return dataMap;
		}
	}

	/*
	 * 重载:因为在月度付款计划里增加了合同类型数，取得右表的付款计划相关数据的时候还需要根据合同类型来进行过滤 by cassiel_peng
	 * 2009-12-17
	 */

	protected Map _getData(Context ctx, String orgUnitId, Set conTypeIds,
			Map param) throws BOSException, EASBizException {

		Map data = new HashMap();
		FullOrgUnitInfo info = FullOrgUnitFactory.getLocalInstance(ctx)
				.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));

		// 获取一个临时表
		// create table tem_ec_basewbs (fid varchar(44),fiscost int)
		String createSQL = "create table FDC_payplan (fid varchar(44),fhassettled int) ";
		String tempTable = null;
		try {
			tempTable = TempTablePool.getInstance(ctx).createTempTable(
					createSQL);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new BOSException(e1);
		}
		if (tempTable == null) {
			return null;
		}
		// String tempTable = getTempTableName("FDC_payplan", ctx);

		LowTimer low = new LowTimer();
		low.reset();

		Integer startYear = ((Integer) param.get("startYear"));
		Integer startMonth = ((Integer) param.get("startMonth"));
		String adminId = (String) param.get("adminId");
		String supplierId = (String) param.get("supplierId");

		// Integer endYear = ((Integer)param.get("endYear"));
		// Integer endMonth = ((Integer)param.get("endMonth"));

		// 付款计划
		// , endYear, endMonth
		PayPlanInfo payPlan = initPlanData(ctx, info, startYear, startMonth);

		logger.info("0付款计划initPlanData:  " + low.msValue());
		low.reset();

		// 已付款
		Map curPayMap = null;
		Map conLastAmountMap = null;
		ContractBillCollection contractBills = null;
		Map contractPayPlanMap = null;
		Map payRequestMap = null;
		Map allPayMap = null;

		// 待签订合同付款计划
		Map dConPayPlanMap = null;

		try {

			// 合同
			// , endYear, endMonth
			contractBills = getContractColl(ctx, data, orgUnitId, conTypeIds,
					startYear, startMonth, adminId, supplierId, tempTable);

			logger.info("1合同getContractColl:  " + low.msValue());
			low.reset();

			// 合同付款计划
			contractPayPlanMap = getContractPayPlanMap(ctx, contractBills,
					tempTable);

			logger.info("2合同付款计划getContractPayPlanMap: " + low.msValue());
			low.reset();

			// 付款申请单累计金额
			payRequestMap = getRequestMap(ctx, contractBills, tempTable);

			logger.info("3付款申请单累计金额getRequestMap: " + low.msValue());
			low.reset();

			// , endYear, endMonth
			curPayMap = getCurPaymentAmountMap(ctx, contractBills, startYear,
					startMonth, tempTable);

			logger.info("4已付款getCurPaymentAmountMap: " + low.msValue());
			low.reset();

			allPayMap = getAllPaymentAmountMap(ctx, contractBills, tempTable);

			logger.info("5已付款getAllPaymentAmountMap: " + low.msValue());
			low.reset();

			//合同最新造价
			conLastAmountMap = getConLastAmountMap(ctx,contractBills,tempTable);
			
			logger.info("6合同最新造价getConLastAmountMap: " + low.msValue());
			
			//待签订合同付款计划
	        dConPayPlanMap = getDConPayPlanMap(ctx,info,tempTable);
			
			logger.info("7待签订合同付款计划getDConPayPlanMap: " + low.msValue());
	        low.reset();
		} catch (SQLException e) {		
			e.printStackTrace();
			throw new BOSException(e);
		} 	

		// 合同分工程项目累计
		Map contractMap = getContractProMap(contractBills);

		logger.info("合同分工程项目累计getContractProMap: " + low.msValue());
		low.reset();

		// 付款计划分工程项目+事项
		Map dataMap = getPlanMap(payPlan);

		logger.info("付款计划分工程项目+事项getPlanMap: " + low.msValue());
		low.reset();

		ExpenseTypeCollection expenseTypes = ExpenseTypeFactory
				.getLocalInstance(ctx).getExpenseTypeCollection();

		data.put("payPlan", payPlan);
		data.put("contractPayPlanMap", contractPayPlanMap);
		data.put("payRequestMap", payRequestMap);
		data.put("curPayMap", curPayMap);
		data.put("allPayMap", allPayMap);
		data.put("conLastAmountMap", conLastAmountMap);
		data.put("contractMap", contractMap);

		data.put("dataMap", dataMap);
		data.put("expenseTypes", expenseTypes);

		// 待签订合同付款计划
		data.put("dConPayPlanMap", dConPayPlanMap);

		TempTablePool.getInstance(ctx).releaseTable(tempTable);

		return data;
	}
}