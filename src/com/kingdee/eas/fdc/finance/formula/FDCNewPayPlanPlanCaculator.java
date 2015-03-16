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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCNewPayPlanPlanCaculator  implements IMethodBatchQuery, ICalculator{
	private Context ServerCtx = null;

	private ICalculateContextProvider context;

	private String fdcCompanyNumber = null;// 公司

	private String fdcProject;// 工程项目

	private String costAccountF7;// 成本科目

	private String month;// 月份

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
		SortedParameterArray params = (SortedParameterArray) methods
				.get("fdc_budget_cost_plan");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();
				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				fdcProject = (String) ((Variant) obj[1]).getValue();
				costAccountF7 = (String) ((Variant) obj[2]).getValue();
				month = (String) ((Variant) obj[3]).getValue();
				try {
					BigDecimal amount = this.fdc_budget_cost_plan(
							fdcCompanyNumber, fdcProject, costAccountF7, month);
					params.getParameter(i).setValue(amount);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}

	public BigDecimal fdc_budget_cost_plan(String fdcCompanyNumber,
			String prjNumber, String acctLongNumber, String month)
			throws ParseException, BOSException, SQLException {

		FDCSQLBuilder builder = new FDCSQLBuilder();

		// 将点替换成叹号
		prjNumber = prjNumber.replace('.', '!');
		acctLongNumber = acctLongNumber.replace('.', '!');

		builder
				.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder
				.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
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

		builder.clear();

		/**
		 * 通过上述获得的当前项目ID和T_FDC_CostAccount中的当前项目ID进行匹配
		 * 再加上成本科目长编码的约束，可以确定所需成本科目的ID
		 */
		String projectID = "";
		if (curProjectID != null && curProjectID.size() == 1) {
			projectID = curProjectID.get(0).toString();
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql(" and ( ");
		builder.appendSql(" FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" ||'!', FlongNumber ) = 1 ) ");

		List costAccountID = new ArrayList();

		IRowSet rowSetRes = builder.executeQuery(ServerCtx);

		while ((rowSetRes != null) && (rowSetRes.next())) {
			costAccountID.add(rowSetRes.getString("fid"));
		}

		builder.clear();

		int firstMark = month.indexOf('-');
		int lastMark = month.lastIndexOf('-');
		String year = month.substring(0,firstMark);
		String _month = month.substring(firstMark+1,lastMark);
		
		BigDecimal returnVal = FDCHelper.ZERO;

		builder.appendSql("select sum(FSplitAmt) total from T_FNC_DepConPayPlanSplitItem item   ");
		builder.appendSql("inner join T_FNC_DepConPayPlanSplitEntry entry on entry.FID = item.FEntryID  ");
		builder.appendSql("inner join T_FDC_CostAccount acct on Acct.FID = entry.FCostAccountID  ");
		builder.appendSql("inner join T_FDC_CurProject prj on prj.FID = acct.FCurProject  ");
		builder.appendSql("where prj.FID = ?  ");
		builder.addParam(projectID);
		builder.appendSql( " and ( acct.FlongNumber = ?  ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ?  ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', acct.FlongNumber ) = 1 ) " );
		builder.appendSql(" and  item.fyear = ?  ");
		builder.appendSql(" and item.fmonth = ?  ");
		builder.addParam(new Integer(year));
		builder.addParam(new Integer(_month));
		IRowSet _rowSet = builder.executeQuery(ServerCtx);

		if (_rowSet != null && _rowSet.size() == 1) {
			_rowSet.next();
			returnVal = _rowSet.getBigDecimal("total");
		}
		if (returnVal == null) {
			returnVal = FDCHelper.ZERO;
		} else {
			returnVal = FDCHelper.toBigDecimal(returnVal);
		}
		return returnVal;
	}
}
