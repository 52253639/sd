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
/**
 * 
 * @author kelvin_yang
 * @work 房地产待发生成本调整取数函数
 * @method fdc_acct_intending_cost 该科目该调整原因的待发生调整汇总，若成本科目没有录入，则为该工程项目该调整原因的待发生成本调整，
 *                                 若成本科目和调整原因都没有录入，则为该工程项目所有待发生成本调整累计
 */
public class FdcIntendingCostCaculator implements ICalculator,
		IMethodBatchQuery {
	private Context ServerCtx = null;

	
	private ICalculateContextProvider context;
	
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

	public boolean batchQuery(Map methods) {
		//获得从界面传递过来的相关参数
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_intending_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				 //公司编码
				String fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				//项目长编码
				String prjNumber = (String) ((Variant) obj[1]).getValue();
				//成本科目长编码
				String acctLongNumber = (String) ((Variant) obj[2]).getValue();
				//调整原因
				String adjustReason = (String) ((Variant) obj[3]).getValue();

				try {
					//通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_acct_intending_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber, adjustReason);
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
	 * 房地产待发生成本调整取数函数
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber  项目长编码
	 * @param acctLongNumber 成本科目长编码
	 * @param adjustReason 调整原因编码
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_intending_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String adjustReason) throws ParseException, BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		/**
		 * 将点替换成叹号
		 */
//		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		//因为成本科目非必录，所以必须判断一下
		if(!"".equals(acctLongNumber))
			acctLongNumber = acctLongNumber.replace('.','!');
		
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
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		
		//清除builder中的sql语句
		builder.clear();
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		//叶子节点
		builder.appendSql(" and FIsLeaf = ? ");
		builder.addParam(Boolean.TRUE);
		if(!"".equals(acctLongNumber)){
			builder.appendSql( " and ( ");
			builder.appendSql( " FlongNumber = ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " or charindex ( ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );			
		}	
		
		List costAccountID = new ArrayList();
		
		//之前为了重用上面资源所引起的一些问题，现在重新定义变量
		IRowSet rowSetRes = builder.executeQuery(ServerCtx);
		
		while((rowSetRes != null) && (rowSetRes.next()))
		{
			costAccountID.add(rowSetRes.getString("fid"));
		}
		builder.clear();
		
		BigDecimal result = FDCHelper.ZERO;
		if(costAccountID.size() <= 0)
			return result;
		builder.appendSql(" select sum(entry.FCostAmount) as FAmount from T_AIM_AdjustRecordEntry entry ");
		builder.appendSql(" inner join T_AIM_DynamicCost dynamic on dynamic.FID = entry.FParentID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = dynamic.FAccountID ");
		if(!"".equals(adjustReason)){
			builder.appendSql(" inner join T_FDC_AdjustReason reason on entry.FAdjustReasonID = reason.FID ");			
		}
		builder.appendSql( " where dynamic.FAccountID in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" )  ");
		if(!"".equals(adjustReason)){
			builder.appendSql(" and reason.FNumber = ?");
			builder.addParam(adjustReason);
		}
		IRowSet rowSetTemp = builder.executeQuery(ServerCtx);

		while((rowSetTemp != null) && (rowSetTemp.next()))
		{
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
}
