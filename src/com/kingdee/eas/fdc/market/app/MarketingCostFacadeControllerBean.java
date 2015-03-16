package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;

import java.sql.Timestamp;


import com.kingdee.bos.*;
import com.kingdee.eas.base.param.ParamException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.market.client.MarketingCostUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;


public class MarketingCostFacadeControllerBean extends AbstractMarketingCostFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.MarketingCostFacadeControllerBean");
    
    protected RptParams _init(Context ctx, RptParams params){
    	
    	RptParams pp = new RptParams();
		pp.setString("title", params.getString("init_title") + "BBB");
		return pp;
    }
    
    
    protected RptParams _createTempTable(Context ctx, RptParams params)
	throws BOSException, EASBizException {
    	
    	Timestamp bizDateBegin = (Timestamp) params
		.getObject(MarketingCostUI.BIZDATEBEGIN);
    	
    	Timestamp bizDateEnd = (Timestamp)params
		.getObject(MarketingCostUI.BIZDATEEND); 
    	
    	String sellproject = (String) params
		.getObject(MarketingCostUI.SELLPROJECT);
    	
//    	String allid=(String)params.getString(MarketingCostRptListUI."allid");

    	// 删除旧临时表(结构可能与新查询不同,本例子临时表结构不变)
		dropTable(params.getString("tempTable"), ctx);
		String tempTable = this.getTempTableName("报表例子", ctx);

    	
    	StringBuffer strSql = new StringBuffer();

		strSql.append("SELECT market.FID FID,market.FNumber    FNUMBER , market.FName    FNAME , mp.FName    MOVEMENTPLAN ,  \r\n");
		strSql.append("market.FOrgName    ORGNAME , market.FMovemntTheme    MOVEMNTTHEME ,    \r\n");
		strSql.append("mt.FName_l2    MARKETTYPENAME , market.FContractNumber    CONTRACTNUMBER ,     \r\n");
		strSql.append("market.FPlanTotalMoney    PLANTOTALMONEY , market.FFactTotalMoney    FACTTOTALMONEY ,     \r\n");
		strSql.append("market.FFactPayment    FACTPAYMENT ,market.FFactTotalMoney -market.FFactPayment nopayment,    \r\n");
		strSql.append("market.FBeginDate    BEGINDATE ,market.FEndDate    ENDDATE     \r\n");
		strSql.append("    \r\n");
		strSql.append(" into " + tempTable + " ");
		strSql.append("FROM T_MAR_MarketManage   market     \r\n");
		strSql.append("LEFT OUTER JOIN T_ORG_Company   company ON market.FCompanyID = company.FID     \r\n");
		strSql.append("LEFT OUTER JOIN T_MAR_MarketType   mt ON market.FMarkettypeID = mt.FID    \r\n");
		strSql.append("INNER JOIN T_SHE_SellProject   spt ON market.FSellProject = spt.FID    \r\n");
		strSql.append("INNER JOIN T_MAR_MovementPlan   mp ON market.FMarketPlan = mp.FID    \r\n");
		strSql.append("Where market.FBizDate between to_date('"+bizDateBegin+"') and to_date('"+bizDateEnd+"')   \r\n");

		
		logger.info("输出sql"+strSql.toString());
		
//		System.out.println("输出sql"+strSql.toString());
		 
		// 创建临时表
		executeCreateAsSelectInto(strSql.toString(), null, ctx);

		// 统计总行数
		String countSql = "select count(1) cc from " + tempTable;
		RptRowSet rs = this.executeQuery(countSql, null, ctx);
		rs.next();
		int count = rs.getInt(0);

		// 设置返回数据
		RptParams result = new RptParams();
		result.setString("tempTable", tempTable);
		// result.setObject("header",this.Header());
		result.setInt("verticalCount", count);
		return result;
    	
    }
   
	protected RptParams _getTableList(Context ctx, RptParams params, String ids)
			throws BOSException, ParamException {
//    	Timestamp bizDateBegin = (Timestamp) params
//		.getObject(MarketingCostUI.BIZDATEBEGIN);
//    	
//    	Timestamp bizDateEnd = (Timestamp)params
//		.getObject(MarketingCostUI.BIZDATEEND); 
//    	
//    	String sellproject = (String) params
//		.getObject(MarketingCostUI.SELLPROJECT);
//    	
////    	String allid=(String)params.getString(MarketingCostRptListUI."allid");
//
//    	// 删除旧临时表(结构可能与新查询不同,本例子临时表结构不变)
//		dropTable(params.getString("tempTable"), ctx);
//		String tempTable = this.getTempTableName("报表例子", ctx);
//
//    	
//    	StringBuffer strSql = new StringBuffer();
//
//		strSql.append("SELECT a.FID       FID,b.FName_l2        MARKETTYPENAME,'暂无数据' Media,  \r\n");
//		strSql.append("a.FBizDate        BIZDATE,a.FContractNumber CONTRACTNUMBER,a.FPlanTotalMoney PLANTOTALMONEY,   \r\n");
//		strSql.append("a.FFactTotalMoney FACTTOTALMONEY, a.FFactPayment    FACTPAYMENT,    \r\n");
//		strSql.append("a.FFactTotalMoney-a.FFactPayment  nopayment    \r\n");
//		strSql.append("    \r\n");
////		strSql.append("c.FID             MEDIAID    \r\n");
//		strSql.append(" into " + tempTable + "  ");
//		strSql.append("FROM T_MAR_MarketManage  a     \r\n");
//		strSql.append("LEFT OUTER JOIN T_MAR_MarketType  b ON a.FMarkettypeID =b.FID    \r\n");
////		strSql.append("LEFT JOIN T_MAR_MarketManageMedia  c ON a.FID =c.FParentID    \r\n");
//		strSql.append("Where a.FBizDate between '"+bizDateBegin+"' and '"+bizDateEnd+"'   \r\n");
//		strSql.append(" and a.FSellProject= '"+sellproject+"'    \r\n");
//		strSql.append(" and b.FID in ( '"+ids+"' )   \r\n");
////		System.out.println("输出sql"+strSql.toString());
//		
//		logger.info("输出sql"+strSql.toString());
//		 
//		// 创建临时表
//		executeCreateAsSelectInto(strSql.toString(), null, ctx);
//
//		// 统计总行数
//		String countSql = "select count(1) cc from " + tempTable;
//		RptRowSet rs = this.executeQuery(countSql, null, ctx);
//		rs.next();
//		int count = rs.getInt(0);
//
//		// 设置返回数据
//		RptParams result = new RptParams();
//		result.setString("tempTable", tempTable);
//		// result.setObject("header",this.Header());
//		result.setInt("verticalCount", count);
//
		return null;

	}
	 /**
	 * 分页查询数据 在这里查询必要的数据
	 */
	protected RptParams _query(Context ctx, RptParams params, int from, int len)
			throws BOSException, EASBizException {
		RptRowSet rs = this.executeQuery("select * from "+ params.getString("tempTable") ,null,from, len, ctx);
		RptParams pp = new RptParams();
		pp.setObject("rowset", rs);
		return pp;
	}
}