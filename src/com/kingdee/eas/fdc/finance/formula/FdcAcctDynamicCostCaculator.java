package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * 
 * @author kelvin_yang
 * @work 房地产工程项目动态成本取数
 * @method fdc_prj_dynamic_cost 若成本科目未录入，返回工程项目动态成本汇总数，成本科目录入，则返回工程项目该成本科目的动态成本
 */
public class FdcAcctDynamicCostCaculator implements IMethodBatchQuery,
		ICalculator {
	private Context ServerCtx = null;
	//公司编码
	private String fdcCompanyNumber = null ;
	//项目长编码
	private String prjNumber = null ;
	//成本科目长编码
	private String acctLongNumber = null ;
	
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
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_dynamic_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[1]).getValue();
				acctLongNumber = (String) ((Variant) obj[2]).getValue();

				try {
					//通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_acct_dynamic_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber);
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
	 * 房地产工程项目动态成本取数
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber  项目长编码
	 * @param acctLongNumber 成本科目长编码
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_dynamic_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber) throws ParseException, BOSException, SQLException
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
		BigDecimal result = FDCHelper.ZERO;
		//未录入科目编码
		if("".equals(acctLongNumber)){
			result = getDynCost(projectID,new HashSet());
		}else{
			builder.clear();
			builder.appendSql("select FID from T_FDC_CostAccount ");
			builder.appendSql(" where FCurProject = ? ");
			builder.addParam(projectID);
			//叶子节点
			builder.appendSql(" and FIsLeaf = ?");
			builder.addParam(new Integer(1));
			if(!"".equals(acctLongNumber)){
				builder.appendSql( " and ( ");
				builder.appendSql( " FlongNumber = ? ");
				builder.addParam(acctLongNumber);
				builder.appendSql( " or charindex ( ? ");
				builder.addParam(acctLongNumber);
				builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );			
			}	
			
			Set costAccountIds = new HashSet();
			
			//之前为了重用上面资源所引起的一些问题，现在重新定义变量
			IRowSet rowSetRes = builder.executeQuery(ServerCtx);
			
			while((rowSetRes != null) && (rowSetRes.next()))
			{
				costAccountIds.add(rowSetRes.getString("fid"));
			}
			if(costAccountIds.size() > 0){
				result = getDynCost(projectID,costAccountIds);
			}
		}
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
	/**
	 * 获得动态成本  动态成本 = 目标成本 - 调整成本
	 * @param prjId
	 * @param acctIds
	 * @return
	 * @throws BOSException
	 */
	protected BigDecimal getDynCost(String prjId,Set acctIds) throws BOSException {
		BigDecimal aimcost = getAimCost(prjId,acctIds);
		BigDecimal adjustCost = getAdjustCost(prjId,acctIds);
		return aimcost.add(adjustCost);

	}

	/** 
	 * 获得目标成本
	 * @param prjId 工程项目id
	 * @param acctIds 成本科目ids
	 * @return 
	 * @throws BOSException 
	 */
	private BigDecimal getAimCost(String prjId,Set acctIds) throws BOSException{
		if("".equals(prjId)){
			return FDCHelper.ZERO;
		}
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql(" select sum(FCostAmount) as amount from T_AIM_CostEntry entry ");
			builder.appendSql(" inner join T_AIM_AimCost head on head.fid=entry.fheadId ");
			builder.appendSql(" inner join T_FDC_CostAccount cost on entry.FCostAccountID = cost.FID ");
			builder.appendSql(" where head.FIsLastVersion=1 and ");
			builder.appendParam("head.FOrgOrProId", prjId);
			if(acctIds.size() > 0){
				builder.appendSql(" and entry.FCostAccountID in (");
				builder.appendParam(acctIds.toArray());
				builder.appendSql(" )  ");				
			}
			IRowSet rowSet=builder.executeQuery(ServerCtx);
			if(rowSet.size()==1){
				rowSet.next();
				return FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		return FDCHelper.ZERO;
	}
	/**
	 * 获得调整成本
	 * @param prjId 工程项目id
	 * @param acctIds 成本科目ids
	 * @return 
	 * @throws BOSException
	 */
	private BigDecimal getAdjustCost(String prjId,Set acctIds) throws BOSException {
		if("".equals(prjId)){
			return FDCHelper.ZERO;
		}
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select sum(FAdjustSumAmount) as amount from T_AIM_DynamicCost dyn");
			builder.appendSql(" inner join T_FDC_CostAccount acct on dyn.faccountid=acct.fid where ");
			builder.appendParam(" acct.fcurproject", prjId);
			if(acctIds.size() > 0){
				builder.appendSql(" and acct.Fid in (");
				builder.appendParam(acctIds.toArray());
				builder.appendSql(" )  ");				
			}
			IRowSet rowSet = builder.executeQuery(ServerCtx);
			if(rowSet.size()==1){
				rowSet.next();
				return FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return FDCHelper.ZERO;
	}

}
