package com.kingdee.eas.fdc.market.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectAreaSellReportFacadeControllerBean extends AbstractProjectAreaSellReportFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.market.report.ProjectAreaSellReportFacadeControllerBean");
    
    private SellProjectInfo sellProject = new SellProjectInfo();
    
    protected Map _getDatas(Context ctx, Map params)throws BOSException, EASBizException
    {
    	Map rowSet = new HashMap();
    	rowSet.put("rowAmount", getAmount(ctx,params));
    	rowSet.put("rowContract", getContract(ctx,params));
    	rowSet.put("payMent", getPayMent(ctx,params));
    	return rowSet;
    }
    
    private IRowSet getAmount(Context ctx,Map params){
    	IRowSet set = null;
    	FDCSQLBuilder sqlBuild = new FDCSQLBuilder();
    	try {
    		String sql = "";
    		sql = sql + starSql();
    		for(int i=1;i<13;i++){
    			sql = sql + getBaseTransaction(ctx,String.valueOf(i),params);
    			if(i<12){
    				sql = sql + " union all ";
    			}
    		}
    		sql = sql + endSql();
    		sqlBuild.appendSql(sql);
	    	set = sqlBuild.executeQuery(ctx);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return set;
    }
    
    private String starSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select tt.month month,tt.nums taoshu,tt.preArea jianzhu, tt.preRoom taonei,tt.amount jine,tt.amount/tt.preArea jianjun,tt.amount/tt.preRoom taojun ");
    	sql.append("from (");
    	return sql.toString();
    }
    
    private String endSql(){
    	StringBuffer sql = new StringBuffer();
    	sql.append(") tt");
    	return sql.toString();
    }
    
    private String getBaseTransaction(Context ctx,String month,Map params){
    	SellProjectInfo sellProject = (SellProjectInfo) params.get("sellProject");
    	String sellProjectStr=null;
    	if(sellProject!=null){
    		try {
    			sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(ctx,sellProject));
			} catch (BOSException e) {
				e.printStackTrace();
			} 
    	}
		String org=(String) params.get("org");
		String sellProjectWhere="";
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("select "+month+" month,count(sm.fid) nums,sum(case sm.fsellType when 'PlanningSell' then sm.fstrdPlanBuildingArea when 'PreSell' then sm.fbulidingArea ");
    	sql.append("else sm.fstrdActualBuildingArea end) preArea,sum(case sm.fsellType when 'PlanningSell' then sm.fstrdPlanRoomArea when 'PreSell' then ");
    	sql.append("sm.fRoomArea else sm.fstrdActualRoomArea end) preRoom,sum(sm.Fdealtotalamount) amount ");
    	sql.append("from t_she_signManage sm ");
    	sql.append("where sm.fbizstate in ('SignAudit','SignApple') ");
    	//TODO
//    	if(((SellProjectInfo)params.get("sellProject")).get("id")!=null){
//			sql.append("and sm.fsellprojectid in (select fid from t_she_sellproject where t_she_sellproject.fparentid in ('"+((SellProjectInfo)params.get("sellProject")).get("id")+"'))");
//		}
    	if(sellProjectStr!=null){
    		sql.append("and sm.fsellprojectid in ("+sellProjectStr+")");
		}else if(org!=null){
			sql.append("and sm.fsellprojectid in ("+org+")");
		}else{
			sql.append("and sm.fsellprojectid in ('null')");
		}
    	//按照一年的12个月来查询
    	if(params.get("year")!=null){
    		String date = params.get("year").toString();
    		date = date + month;
    		
    		sql.append("and CONCAT(CONVERT(varchar,YEAR(sm.fbusAdscriptionDate)),CONVERT(varchar,MONTH(sm.fbusAdscriptionDate))) = "+date+" ");
		}
		return sql.toString();
    }
    
    private IRowSet getContract(Context ctx,Map params){
    	IRowSet set = null;
    	FDCSQLBuilder sqlBuild = new FDCSQLBuilder();
    	try {
    		
    		String sql = "";
    		for(int i=1;i<13;i++){
    			sql = sql + getNullContractAmount(String.valueOf(i),params);
    			sql = sql + getContractAmount(String.valueOf(i),params);
    			if(i<12){
    				sql = sql + " union all ";
    			}
    		}
    		sql = "select sum(tt.sums) amount,tt.time1 from (" + sql;
    		sql = sql + ") tt group by tt.time1 order by tt.time1 asc";
    		sqlBuild.appendSql(sql);
	    	set = sqlBuild.executeQuery(ctx);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return set;
    }

    private String getNullContractAmount(String month,Map params){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select sum(cwt.foriginalamount) sums,cwt.fid contractid,CONCAT(CONVERT(varchar,YEAR(cwt.fbookeddate)),CONVERT(varchar,MONTH(cwt.fbookeddate))) time1 from T_CON_ContractWithoutText cwt ");
    	sql.append("where cwt.forgunitid in (");
    	sql.append(getContractId_1(month,params));
    	sql.append("group by CONCAT(CONVERT(varchar,YEAR(cwt.fbookeddate)),CONVERT(varchar,MONTH(cwt.fbookeddate))),cwt.fid ");
    	sql.append("union all ");
    	return sql.toString();
    }
    	
    private String getContractAmount(String month,Map params){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select sum(cb.foriginalamount) sums,cb.fid contractid,CONCAT(CONVERT(varchar,YEAR(cb.fbookeddate)),CONVERT(varchar,MONTH(cb.fbookeddate))) time1 from T_CON_ContractBill cb ");
    	sql.append("where cb.forgunitid in (");
    	sql.append(getContractId_2(month,params));
    	sql.append("group by CONCAT(CONVERT(varchar,YEAR(cb.fbookeddate)),CONVERT(varchar,MONTH(cb.fbookeddate))),cb.fid ");
    	return sql.toString();
    }
    
    private StringBuffer getContractId_1(String month,Map params){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select fid from t_org_admin where flongnumber like ('"+params.get("longNumber")+"%')) ");
    	//按照一年的12个月来查询
    	if(params.get("year")!=null){
    		String date = params.get("year").toString();
    		date = date + month;
    		sql.append("and CONCAT(CONVERT(varchar,YEAR(cwt.fbookeddate)),CONVERT(varchar,MONTH(cwt.fbookeddate)))= "+date+" ");
		}
    	return sql;
    }
    
    private StringBuffer getContractId_2(String month,Map params){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select fid from t_org_admin where flongnumber like ('"+params.get("longNumber")+"%')) ");
    	//按照一年的12个月来查询
    	if(params.get("year")!=null){
    		String date = params.get("year").toString();
    		date = date + month;
    		sql.append("and CONCAT(CONVERT(varchar,YEAR(cb.fbookeddate)),CONVERT(varchar,MONTH(cb.fbookeddate)))= "+date+" ");
		}
    	return sql;
    }
    
    private IRowSet getPayMent(Context ctx,Map params){
    	IRowSet set = null;
    	FDCSQLBuilder sqlBuild = new FDCSQLBuilder();
    	try {
    		String sql = getPayAmount(params);
    		sqlBuild.appendSql(sql);
	    	set = sqlBuild.executeQuery(ctx);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return set;
    }
    
    private String getPayAmount(Map params){
    	StringBuffer sql = new StringBuffer();
    	sql.append("select sum(pb.factpaylocamt) actpaylocamt,CONCAT(CONVERT(varchar,YEAR(pb.fbizdate)),CONVERT(varchar,MONTH(pb.fbizdate))) time2 from t_cas_paymentbill pb ");
    	if(params.get("year")!=null){
    		String date = params.get("year").toString();
    		sql.append("where YEAR(pb.fbizdate) = "+date+" ");
		}
    	sql.append("and pb.fcontractbillid in ( ");
    	sql.append("select cwt.fid from T_CON_ContractWithoutText cwt ");
    	sql.append("where cwt.forgunitid in(select fid from t_org_admin where flongnumber like ('"+params.get("longNumber")+"%')) ");
    	sql.append("union all ");
    	sql.append("select cb.fid from T_CON_ContractBill cb ");
    	sql.append("where cb.forgunitid in(select fid from t_org_admin where flongnumber like ('"+params.get("longNumber")+"%')) ");
    	sql.append(") group by CONCAT(CONVERT(varchar,YEAR(pb.fbizdate)),CONVERT(varchar,MONTH(pb.fbizdate))) ");
    	return sql.toString();
    }
}