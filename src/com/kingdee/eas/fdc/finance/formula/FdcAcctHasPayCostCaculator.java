package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * 
 * @author kelvin_yang
 * @work 房地产付款拆分已实现成本取数公式
 * @method fdc_acct_hasPaid_cost 月度录入：若合同未录入则返回该月所有付款拆分到该科目的已实现成本，
 * 								 		 若合同录入， 则返回该月该合同下所有付款拆分到该科目的已实现成本。
 *  							 若月度未录入：若合同未录入则返回所有付款拆分到该科目的已实现成本，
 *  										若合同录入，则返回该合同下所有付款拆分到该科目的已实现成本。
 */
public class FdcAcctHasPayCostCaculator implements ICalculator, IMethodBatchQuery {
	private Context ServerCtx = null;
	//公司编码
	private String fdcCompanyNumber = null ;
	//项目长编码
	private String prjNumber = null ;
	//成本科目长编码
	private String acctLongNumber = null ;
	//月度
	private String month = null;
	//合同
	private String conNumber = null;
	//CUid
	private String cuId = null;
	
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
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_hasPay_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[1]).getValue();
				acctLongNumber = (String) ((Variant) obj[2]).getValue();
				month = (String) ((Variant) obj[3]).getValue();
				conNumber = (String) ((Variant) obj[4]).getValue();

				try {
					//通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_acct_hasPay_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber , month, conNumber);
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
	 * 房地产付款拆分已实现成本取数公式
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber  项目长编码
	 * @param acctLongNumber 成本科目长编码
	 * @param conNumber 合同编码
	 * @param month 月度
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_hasPay_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month, String conNumber) throws ParseException, BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
//		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//由于是月度，所以应该对日期转化成日期没每个月的最后一天的时间
		Date lastDay = null ;
		Date firstDay = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//如果录入了月度
		if(!"".equals(month)){
			lastDay = df2.parse(month);
			//最后一天取最大时间
			lastDay = FDCDateHelper.getLastDayOfMonth(lastDay);
			firstDay = df2.parse(month);
			firstDay = FDCDateHelper.getFirstDayOfMonth(firstDay);
		}
		
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
		
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		BigDecimal result = FDCHelper.ZERO;
//		try {
//			initCU(projectID);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_FNC_PaymentSplitEntry entry ");
		builder.appendSql(" inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId ");
		builder.appendSql(" inner join T_FDC_CurProject prj on head.FCurProjectId = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
		if(!"".equals(conNumber)){
			builder.appendSql(" inner join T_CON_ContractBill con on head.FContractBillID = con.FID ");
		}
//		builder.appendSql(" where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? ");
//		builder.addParam(cuId);
		builder.appendSql(" where head.FIsInvalid=0 and head.FCurProjectId =? ");
		builder.addParam(projectID);
		builder.appendSql(" and head.FConWithoutTextID is null ");
		builder.appendSql( " and ( cost.FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', cost.FlongNumber ) = 1 ) " );
		if(!"".equals(conNumber)){
			builder.appendSql(" and con.FNumber = ?");
			builder.addParam(conNumber);
		}
		//如果录入月度
		if(lastDay != null && firstDay != null){
			
			builder.appendSql(" and  head.FCreateTime >= ?");
			builder.addParam(firstDay);
			builder.appendSql(" and head.FCreateTime < ?");
			builder.addParam(lastDay);
		}
		//叶子节点
		builder.appendSql(" and entry.FIsLeaf = ?");
		builder.addParam(new Integer(1));
		IRowSet rowSetTemp = builder.executeQuery(ServerCtx);
		
		while((rowSetTemp != null) && (rowSetTemp.next())){
			result = rowSetTemp.getBigDecimal("FAmount");
		}

		if(result == null){
			result = FDCHelper.ZERO;			
		}
		
		return result;
	}
	public void initCU(String prjId) throws Exception{
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select fcontrolUnitId from T_FDC_CurProject where fid=?");
		IRowSet rowSet=builder.executeQuery(ServerCtx);
		if(rowSet.size()>0){
			rowSet.next();
			this.cuId=rowSet.getString("fcontrolUnitId");
		}
	}
}
