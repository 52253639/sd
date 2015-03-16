package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.framework.report.util.SqlParams;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

public class SignAccountReportFacadeControllerBean extends AbstractSignAccountReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SignAccountReportFacadeControllerBean");
    protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
    {
        return null;
    }
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    initColoum(header,col,"sellProjectId",100,true);
	    initColoum(header,col,"sellProjectName",100,false);
	    initColoum(header,col,"productTypeId",100,true);
	    initColoum(header,col,"productTypeName",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"buildArea",100,false);
	    initColoum(header,col,"lastYearBuildArea",100,true);
	    initColoum(header,col,"lastMonthBuildArea",100,true);
	    initColoum(header,col,"roomArea",100,false);
	    initColoum(header,col,"lastYearRoomArea",100,true);
	    initColoum(header,col,"lastMonthRoomArea",100,true);
	    initColoum(header,col,"buildAreaT",100,false);
	    initColoum(header,col,"roomAreaT",100,false);
	    initColoum(header,col,"buildAreaH",100,false);
	    initColoum(header,col,"roomAreaH",100,false);
	    initColoum(header,col,"account",100,false);
	    initColoum(header,col,"lastYearAccount",100,true);
	    initColoum(header,col,"lastMonthAccount",100,true);
	    initColoum(header,col,"accountT",100,false);
	    initColoum(header,col,"accountH",100,false);
	    initColoum(header,col,"buildPrice",100,false);
	    initColoum(header,col,"roomPrice",100,false);
	    initColoum(header,col,"buildPriceT",100,false);
	    initColoum(header,col,"roomPriceT",100,false);
	    initColoum(header,col,"buildPriceH",100,false);
	    initColoum(header,col,"roomPriceH",100,false);
	    initColoum(header,col,"revAccount",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    			"实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约",
	    			"实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约","实际签约"
	    			,"实际签约","实际签约","实际签约","实际签约","实际签约","实际签约"
	    		}
	    		,
	    		{
	    			"sellProjectId","项目","productTypeId","业态","套数","建筑面积","去年同期建筑面积","上月建筑面积","套内面积","去年同期套内面积","上月套内面积","建筑面积同比","套内面积同比","建筑面积环比",
	    			"套内面积环比","金额","去年同期金额","上月金额","金额同比","金额环比","建筑均价","套内均价","建筑均价同比","套内均价同比","建筑均价环比","套内均价环比","收款"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		params.setInt("count", rowSet.getRowCount());
		return params;
    }
    private Date getPreDate(Date date,int year,int month){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month);
    	return cal.getTime();
    }
    protected String getSql(Context ctx,RptParams params){
    	SellProjectInfo sellProject = (SellProjectInfo) params.getObject("sellProject");
    	String sellProjectStr=null;
    	if(sellProject!=null){
    		try {
    			sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(ctx,sellProject));
			} catch (BOSException e) {
				e.printStackTrace();
			} 
    	}
    	ProductTypeInfo productType=(ProductTypeInfo)params.getObject("productType");
    	String productTypeId=null;
    	if(productType!=null){
    		productTypeId=productType.getId().toString();
    	}
    	String org=(String) params.getObject("org");
    	
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	Date lastYearFromDate=null;
    	Date lastYearToDate=null;
    	
    	Date lastMonthFromDate=null;
    	Date lastMonthToDate=null;
    	
    	if(fromDate!=null){
    		Calendar cal = new GregorianCalendar();
            cal.setTime(fromDate);
    		lastYearFromDate=getPreDate(fromDate,cal.get(Calendar.YEAR)-1,cal.get(Calendar.MONTH));
    		lastMonthFromDate=getPreDate(fromDate,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1);
    	}
    	if(toDate!=null){
    		Calendar cal = new GregorianCalendar();
            cal.setTime(toDate);
    		lastYearToDate=getPreDate(toDate,cal.get(Calendar.YEAR)-1,cal.get(Calendar.MONTH));
    		lastMonthToDate=getPreDate(toDate,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1);
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,t.amount,t.buildArea,t1.buildArea lastYearBuildArea,t2.buildArea lastMonthBuildArea,t.roomArea,t1.roomArea lastYearRoomArea,t2.roomArea lastMonthRoomArea,isnull((t.buildArea-t1.buildArea)*100/t1.buildArea,0) buildAreaT,isnull((t.roomArea-t1.roomArea)*100/t1.roomArea,0) roomAreaT,");
    	sb.append(" isnull((t.buildArea-t2.buildArea)*100/t2.buildArea,0) buildAreaH,isnull((t.roomArea-t2.roomArea)*100/t2.roomArea,0) roomAreaH,t.account,t1.account lastYearAccount,t2.account lastMonthAccount,isnull((t.account-t1.account)*100/t1.account,0) accountT,isnull((t.account-t2.account)*100/t2.account,0) accountH,");
    	sb.append(" t.buildPrice,t.roomPrice,isnull((t.buildPrice-t1.buildPrice)*100/t1.buildPrice,0) buildPriceT,isnull((t.roomPrice-t1.roomPrice)*100/t1.roomPrice,0) roomPriceT,isnull((t.buildPrice-t2.buildPrice)*100/t2.buildPrice,0) buildPriceH,isnull((t.roomPrice-t2.roomPrice)*100/t2.roomPrice,0) roomPriceH,isnull(revBill.revAccount,0) revAccount from (");
    	getBaseTransaction(sb,fromDate,toDate);
    	sb.append(" )t");
    	sb.append(" left join (");
    	getSheRevBill(sb,fromDate,toDate);
    	sb.append(" )revBill on revBill.sellProjectId=t.sellProjectId and revBill.productTypeId=t.productTypeId");
    	sb.append(" left join (");
    	getBaseTransaction(sb,lastYearFromDate,lastYearToDate);
    	sb.append(" )t1 on t1.sellProjectId=t.sellProjectId and t1.productTypeId=t.productTypeId");
    	sb.append(" left join (");
    	getBaseTransaction(sb,lastMonthFromDate,lastMonthToDate);
    	sb.append(" )t2 on t2.sellProjectId=t.sellProjectId and t2.productTypeId=t.productTypeId where 1=1 ");
    	if(sellProjectStr!=null){
			sb.append(" and t.sellProjectId in ("+sellProjectStr+")");
		}else if(org!=null){
			sb.append(" and t.sellProjectId in ("+org+")");
		}else{
			sb.append(" and t.sellProjectId in ('null')");
		}
		if(productTypeId!=null){
			sb.append(" and t.productTypeId ='"+productTypeId+"'");
		}
		return sb.toString();
    }
    private StringBuffer getBaseTransaction(StringBuffer sb,Date fromDate,Date toDate){
    	sb.append(" select sign.fsellProjectid sellProjectId,sellproject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,count(*) amount,");
    	sb.append(" sum(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)) account,");
    	sb.append(" sum((case when sign.FAreaCompensate is null then (case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) else room.FActualBuildingArea end )) buildArea,");
    	sb.append(" sum((case when sign.FAreaCompensate is null then (case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualBuildingArea end) else room.fActualRoomArea end )) roomArea,");
    	
    	sb.append(" (sum(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0))/sum((case when sign.FAreaCompensate is null then (case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) else room.FActualBuildingArea end ))) buildPrice,");
    	sb.append(" (sum(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0))/sum((case when sign.FAreaCompensate is null then (case sign.fsellType when 'PlanningSell' then sign.fstrdPlanRoomArea when 'PreSell' then sign.froomArea else sign.fstrdActualBuildingArea end) else room.fActualRoomArea end ))) roomPrice");
    	
    	sb.append(" from t_she_signManage sign");
    	sb.append(" left join t_she_sellProject sellproject on sellProject.fid=sign.fsellProjectid left join t_she_room room on room.fid=sign.froomid  left join t_she_building build on build.fid=room.fbuildingid left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
    	sb.append(" where sign.fbizState in('SignApple','SignAudit')");
    	if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" group by productType.fid,sign.fsellProjectid,sellProject.fname_l2,productType.fname_l2");
		return sb;
    }
    private void getSheRevBill(StringBuffer sb,Date fromDate,Date toDate){
    	sb.append(" select revBill.fsellProjectid sellProjectId,productType.fid productTypeId,sum(entry.famount+entry.frevAmount) as revAccount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join t_she_building build on build.fid=room.fbuildingid left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(fromDate!=null){
			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" group by productType.fid,revBill.fsellProjectid");
    }
}