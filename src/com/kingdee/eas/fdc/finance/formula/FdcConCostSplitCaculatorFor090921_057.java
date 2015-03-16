package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;

public class FdcConCostSplitCaculatorFor090921_057 implements
		IMethodBatchQuery, ICalculator {
	private Context ServerCtx = null;
	// 公司编码
	private String fdcCompanyNumber = null;
	// 项目长编码
	private String prjNumber = null;
	// 成本科目长编码
	private String acctLongNumber = null;
	// 合同编码
	private String conNumber = null;
	// 开始期间
	private String beginPeriod = null;
	// 结束期间
	private String endPeriod = null;
	// 显示所有组织
	private boolean displayAllCompany = false;
	private ICalculateContextProvider context;

	public Context getServerCtx() {
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx) {
		ServerCtx = serverCtx;
	}

	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();

	}

	public boolean batchQuery(Map methods) {
		// 获得从界面传递过来的相关参数
		SortedParameterArray params = (SortedParameterArray) methods
				.get("fdc_acct_conCost_split_filterByDate");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[2]).getValue();
				acctLongNumber = (String) ((Variant) obj[3]).getValue();
				conNumber = (String) ((Variant) obj[4]).getValue();
				beginPeriod = (String) ((Variant) obj[5]).getValue();
				endPeriod = (String) ((Variant) obj[6]).getValue();

				try {
					// 通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_acct_conCost_split_filterByDate(
							fdcCompanyNumber, false, prjNumber, acctLongNumber,
							conNumber, beginPeriod, endPeriod);
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
	 * 房地产合同成本拆分取数公式
	 * 
	 * @param fdcCompanyNumber
	 *            公司编码
	 * @param prjNumber
	 *            项目长编码
	 * @param acctLongNumber
	 *            成本科目长编码
	 * @param conNumber
	 *            合同编码
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_conCost_split_filterByDate(String fdcCompanyNumber,
			boolean displayAllCompany, String prjNumber, String acctLongNumber,
			String conNumber, String beginPeriod, String endPeriod)
			throws ParseException, BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		/**
		 * 将点替换成叹号
		 */
		// fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.', '!');
		acctLongNumber = acctLongNumber.replace('.', '!');

		/**
		 * 通过T_ORG_CostCenter中的number和fdcCompanyNumber,获取FCostCenterId
		 * 然后在T_FDC_CurProject和FCostCenterId对应的集合中，找FlongNumber和prjNumber
		 * 对应结果集，然后返回fid集合，即通过公司和项目选取当前项目的ID
		 */
		builder
				.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder
				.appendSql(" inner join T_ORG_BaseUnit costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FLongNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * 将当前项目的ID放在List中，便于在成本科目中查找对应的成本科目
		 */
		List curProjectID = new ArrayList();

		IRowSet rowSet = builder.executeQuery(ServerCtx);

		while ((rowSet != null) && (rowSet.next())) {
			curProjectID.add(rowSet.getString("fid"));
		}

		// 清除builder中的sql语句
		builder.clear();

		String projectID = "";
		if (curProjectID != null && curProjectID.size() == 1) {
			projectID = curProjectID.get(0).toString();
		}
		BigDecimal result = FDCHelper.ZERO;
		BigDecimal conSplitAmt = FDCHelper.ZERO;
		BigDecimal changeSplitAmt = FDCHelper.ZERO;
		BigDecimal settleSplitAmt = FDCHelper.ZERO;
		BigDecimal paySplitAmt = FDCHelper.ZERO;
		// 合同拆分
		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_CON_ContractCostSplitEntry entry ");
		builder.appendSql(" inner join T_CON_ContractCostSplit split on entry.FParentID = split.FID ");
		builder.appendSql(" inner join T_FDC_CurProject prj on split.FCurProjectID = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
//		if(!"".equals(conNumber)){
			builder.appendSql(" inner join T_CON_ContractBill con on con.fid = split.FContractBillID ");
//		}
		// 工程项目id
		builder.appendSql(" where  split.FIsInvalid=0 and  split.FCurProjectId = ?");
		builder.addParam(projectID);
		builder.appendSql(" and split.FCreateTime < {ts '"+FDCDateHelper.DateToString(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)))+"'}");
//		builder.addParam(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)));
		builder.appendSql(" and split.FCreateTime > {ts '"+beginPeriod+"'}");
//		builder.addParam(FDCDateHelper.stringToDate(beginPeriod));
		builder.appendSql(" and ( cost.FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" ||'!', cost.FlongNumber ) = 1 ) ");
		//对于该公式，合同未非必录项，此时取数维度为：工程项目+成本科目
		if (!"".equals(conNumber)) {
			builder.appendSql(" and con.FNumber = ?");
			builder.addParam(conNumber);
		}
		// 叶子节点
		builder.appendSql(" and entry.FIsLeaf = ?");
		builder.addParam(new Integer(1));
		builder.appendSql(" and con.FHasSettled = 0  ");//未最终结算
		IRowSet rowSetTemp = builder.executeQuery(ServerCtx);

		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			conSplitAmt = rowSetTemp.getBigDecimal("FAmount");
		}
		
		// 合同的 变更拆分
		builder.clear();
		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_CON_ConChangeSplitEntry entry ");
		builder.appendSql(" inner join T_CON_ConChangeSplit split on entry.FParentID = split.FID ");
		builder.appendSql(" inner join T_FDC_CurProject prj on split.FCurProjectID = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
//		if(!"".equals(conNumber)){
			builder.appendSql(" inner join T_CON_ContractBill con on con.fid = split.FContractBillID ");
//		}
		// 工程项目id
		builder.appendSql(" where  split.FIsInvalid=0 and  split.FCurProjectId = ?");
		builder.addParam(projectID);
		builder.appendSql(" and split.FCreateTime < {ts '"+FDCDateHelper.DateToString(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)))+"'}");
//		builder.addParam(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)));
		builder.appendSql(" and split.FCreateTime > {ts '"+beginPeriod+"'}");
//		builder.addParam(FDCDateHelper.stringToDate(beginPeriod));
		builder.appendSql(" and ( cost.FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" ||'!', cost.FlongNumber ) = 1 ) ");
		//对于该公式，合同未非必录项，此时取数维度为：工程项目+成本科目
		if (!"".equals(conNumber)) {
			builder.appendSql(" and con.FNumber = ?");
			builder.addParam(conNumber);
		}
		// 叶子节点
		builder.appendSql(" and entry.FIsLeaf = ?");
		builder.addParam(new Integer(1));
		builder.appendSql(" and con.FHasSettled = 0  ");//未最终结算
		IRowSet rowSetTemp2=builder.executeQuery(ServerCtx);

		while ((rowSetTemp2 != null) && (rowSetTemp2.next())) {
			changeSplitAmt = rowSetTemp2.getBigDecimal("FAmount");
		}
		//合同的结算拆分
		builder.clear();
		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_CON_SettlementCostSplitEntry entry ");
		builder.appendSql(" inner join T_CON_SettlementCostSplit split on entry.FParentID = split.FID ");
		builder.appendSql(" inner join T_FDC_CurProject prj on split.FCurProjectID = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
//		if(!"".equals(conNumber)){
			builder.appendSql(" inner join T_CON_ContractBill con on con.fid = split.FContractBillID ");
//		}
		// 工程项目id
		builder.appendSql(" where  split.FIsInvalid=0 and  split.FCurProjectId = ?");
		builder.addParam(projectID);
		builder.appendSql(" and split.FCreateTime < {ts '"+FDCDateHelper.DateToString(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)))+"'}");
//		builder.addParam(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)));
		builder.appendSql(" and split.FCreateTime > {ts '"+beginPeriod+"'}");
//		builder.addParam(FDCDateHelper.stringToDate(beginPeriod));
		builder.appendSql(" and ( cost.FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" ||'!', cost.FlongNumber ) = 1 ) ");
		//对于该公式，合同未非必录项，此时取数维度为：工程项目+成本科目
		if (!"".equals(conNumber)) {
			builder.appendSql(" and con.FNumber = ?");
			builder.addParam(conNumber);
		}
		// 叶子节点
		builder.appendSql(" and entry.FIsLeaf = ?");
		builder.addParam(new Integer(1));
		builder.appendSql(" and con.FHasSettled = 1  ");//最终结算
		IRowSet rowSetTemp3=builder.executeQuery(ServerCtx);

		while ((rowSetTemp3 != null) && (rowSetTemp3.next())) {
			settleSplitAmt = rowSetTemp3.getBigDecimal("FAmount");
		}
		
		//无文本合同的付款拆分
		builder.clear();
		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_FNC_PaymentSplitEntry entry ");
		builder.appendSql(" inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId ");
		builder.appendSql(" inner join T_FDC_CurProject prj on head.FCurProjectId = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
//		if(!"".equals(conNumber)){
			builder.appendSql(" inner join T_CON_ContractWithoutText con on head.FConWithoutTextID = con.FID ");
//		}
		builder.appendSql(" where head.FIsInvalid=0 and head.FCurProjectId =? ");
		builder.addParam(projectID);
		builder.appendSql(" and head.FCreateTime < {ts '"+FDCDateHelper.DateToString(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)))+"'}");
//		builder.addParam(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)));
		builder.appendSql(" and head.FCreateTime > {ts '"+beginPeriod+"'}");
//		builder.addParam(FDCDateHelper.stringToDate(beginPeriod));
		builder.appendSql( " and ( cost.FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', cost.FlongNumber ) = 1 ) " );
		if(!"".equals(conNumber)){
			builder.appendSql(" and con.FNumber = ?");
			builder.addParam(conNumber);
		}
		//叶子节点
		builder.appendSql(" and entry.FIsLeaf = ?");
		builder.addParam(new Integer(1));
		IRowSet rowSetTemp4 = builder.executeQuery(ServerCtx);

		while ((rowSetTemp4 != null) && (rowSetTemp4.next())) {
			paySplitAmt = rowSetTemp4.getBigDecimal("FAmount");
		}
		
		if (result == null) {
			result = FDCHelper.ZERO;
		}else{
			result = FDCHelper.toBigDecimal(FDCHelper.add(FDCHelper.add(FDCHelper.add(conSplitAmt, changeSplitAmt), settleSplitAmt), paySplitAmt));
		}
		return result;
	}

}
