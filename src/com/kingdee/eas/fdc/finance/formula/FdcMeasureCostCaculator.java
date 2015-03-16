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
 * @work 房地产可研测算成本取数
 * @method fdc_measure_cost 若成本科目未录入，返回工程项目可研成本测算该版本的成本汇总数，
 * 							若成本科目录入，则返回工程项目该可研成本测算该版本上该成本科目的成本
 */
public class FdcMeasureCostCaculator implements ICalculator, IMethodBatchQuery {

	private Context ServerCtx = null;
	//公司编码
	private String fdcCompanyNumber = null ;
	//项目长编码
	private String prjNumber = null ;
	//成本科目长编码
	private String acctLongNumber = null ;
	//可研测算版本号
	private String verNo = null;
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
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_measure_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[1]).getValue();
				acctLongNumber = (String) ((Variant) obj[2]).getValue();
				verNo = (String) ((Variant) obj[3]).getValue();

				try {
					BigDecimal amount = this.fdc_measure_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber, verNo);
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
	 * 房地产可研测算成本取数公式
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber  项目长编码
	 * @param acctLongNumber 成本科目长编码
	 * @param verNo 可研测算版本号
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_measure_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String verNo) throws ParseException, BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
//		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		verNo = verNo.replace('.','!');
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
		
		//清除builder中的sql语句
		builder.clear();
		
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
				
		BigDecimal result = FDCHelper.ZERO;
		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_AIM_MeasureEntry entry ");
		builder.appendSql(" inner join T_AIM_MeasureCost head on entry.FHeadID = head.FID ");
		builder.appendSql(" inner join T_FDC_CurProject prj on head.FProjectID = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
		//工程项目id
		builder.appendSql(" where head.FProjectId = ?");
		builder.addParam(projectID);
		//可研成本测算
		builder.appendSql(" and head.FIsAimMeasure = ?");
		builder.addParam(new Integer(0));
		builder.appendSql(" and head.FVersionNumber = ?");
		builder.addParam(verNo);
		//若录入了成本科目编码
		if(!"".equals(acctLongNumber)){
			builder.appendSql( " and ( cost.FlongNumber = ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " or charindex ( ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " ||'!', cost.FlongNumber ) = 1 ) " );
		}
			
		IRowSet rowSetTemp = builder.executeQuery(ServerCtx);

		while((rowSetTemp != null) && (rowSetTemp.next())){
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		if(result == null){
			result = FDCHelper.ZERO;			
		}
		
		return result;
	}

}
