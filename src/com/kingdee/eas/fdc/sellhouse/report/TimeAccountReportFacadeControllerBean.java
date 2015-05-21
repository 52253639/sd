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
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;

import java.util.Date;
import java.util.Set;

public class TimeAccountReportFacadeControllerBean extends AbstractTimeAccountReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.TimeAccountReportFacadeControllerBean");
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
	    initColoum(header,col,"sellProjectName",140,false);
	    initColoum(header,col,"productTypeId",100,true);
	    initColoum(header,col,"productTypeName",120,false);
	    initColoum(header,col,"tel",60,false);
	    initColoum(header,col,"visit",60,false);
	    initColoum(header,col,"monthTel",60,false);
	    initColoum(header,col,"monthVisit",60,false);
	    initColoum(header,col,"yearTel",60,false);
	    initColoum(header,col,"yearVisit",60,false);
	    
	    initColoum(header,col,"preAmount",60,true);
	    initColoum(header,col,"preArea",100,true);
	    initColoum(header,col,"preAccount",140,true);
	    initColoum(header,col,"preAverage",100,true);
	    initColoum(header,col,"quitPreAmount",60,true);
	    initColoum(header,col,"quitPreArea",100,true);
	    initColoum(header,col,"quitPreAccount",140,true);
	    initColoum(header,col,"quitPreAverage",100,true);
	    initColoum(header,col,"purAmount",60,true);
	    initColoum(header,col,"purArea",100,true);
	    initColoum(header,col,"purAccount",140,true);
	    initColoum(header,col,"purAverage",100,true);
	    initColoum(header,col,"signPreAmount",60,true);
	    initColoum(header,col,"signPreArea",100,true);
	    initColoum(header,col,"signPreAccount",140,true);
	    initColoum(header,col,"signPreAverage",100,true);
	    initColoum(header,col,"unSignPurAmount",60,true);
	    initColoum(header,col,"unSignPurArea",100,true);
	    initColoum(header,col,"unSignPurAccount",140,true);
	    initColoum(header,col,"unSignPurAverage",100,true);
	    initColoum(header,col,"signPurAmount",60,true);
	    initColoum(header,col,"signPurArea",100,true);
	    initColoum(header,col,"signPurAccount",140,true);
	    initColoum(header,col,"signPurAverage",100,true);
	    initColoum(header,col,"directSignAmount",60,true);
	    initColoum(header,col,"directSignArea",100,true);
	    initColoum(header,col,"directSignAccount",140,true);
	    initColoum(header,col,"directSignAverage",100,true);
	    initColoum(header,col,"quitPurAmount",60,true);
	    initColoum(header,col,"quitPurArea",100,true);
	    initColoum(header,col,"quitPurAccount",140,true);
	    initColoum(header,col,"quitPurAverage",100,true);
	    initColoum(header,col,"signAmount",60,false);
	    initColoum(header,col,"signArea",100,false);
	    initColoum(header,col,"signAccount",140,false);
	    initColoum(header,col,"signAverage",100,true);
	    
	    initColoum(header,col,"compensateAmount",60,false);
	    initColoum(header,col,"compensateArea",100,false);
	    initColoum(header,col,"compensateAccount",140,false);
	    initColoum(header,col,"compensateAreaTotal",140,false);
	    initColoum(header,col,"compensateAccountTotal",140,false);
	    
	    initColoum(header,col,"signBackAccount",140,false);
	    
	    initColoum(header,col,"quitSignAmount",60,false);
	    initColoum(header,col,"quitSignArea",100,false);
	    initColoum(header,col,"quitSignAccount",140,false);
	    initColoum(header,col,"quitSignAverage",100,true);
	    initColoum(header,col,"monthSignAmount",60,true);
	    initColoum(header,col,"monthSignArea",100,true);
	    initColoum(header,col,"monthSignAccount",140,true);
	    initColoum(header,col,"monthSignAverage",100,true);
	    
	    initColoum(header,col,"mcompensateAmount",60,true);
	    initColoum(header,col,"mcompensateArea",100,true);
	    initColoum(header,col,"mcompensateAccount",140,true);
	    initColoum(header,col,"mcompensateAreaTotal",140,true);
	    initColoum(header,col,"mcompensateAccountTotal",140,true);
	    
	    initColoum(header,col,"monthSignBackAccount",140,true);
	    initColoum(header,col,"monthSignPlan",100,true);
	    initColoum(header,col,"monthSignPlanRate",100,true);
	    initColoum(header,col,"monthSignBackAccountPlan",100,true);
	    initColoum(header,col,"monthSignBackAccountPlanRate",100,true);
	    initColoum(header,col,"monthSignMarketAccount",100,true);
	    
	    initColoum(header,col,"yearSignAmount",60,true);
	    initColoum(header,col,"yearSignArea",100,true);
	    initColoum(header,col,"yearSignAccount",140,true);
	    initColoum(header,col,"yearSignAverage",100,true);
	    
	    initColoum(header,col,"ycompensateAmount",60,true);
	    initColoum(header,col,"ycompensateArea",100,true);
	    initColoum(header,col,"ycompensateAccount",140,true);
	    initColoum(header,col,"ycompensateAreaTotal",140,true);
	    initColoum(header,col,"ycompensateAccountTotal",140,true);
	    
	    initColoum(header,col,"yearSignBackAccount",140,true);
	    initColoum(header,col,"yearSignPlan",100,true);
	    initColoum(header,col,"yearSignPlanRate",100,true);
	    initColoum(header,col,"yearSignBackAccountPlan",100,true);
	    initColoum(header,col,"yearSignBackAccountPlanRate",100,true);
	    
	    initColoum(header,col,"accUnSignPreAmount",60,true);
	    initColoum(header,col,"accUnSignPreArea",100,true);
	    initColoum(header,col,"accUnSignPreAccount",140,true);
	    initColoum(header,col,"accUnSignPreAverage",100,true);
	    
	    initColoum(header,col,"accUnSignPurAmount",60,true);
	    initColoum(header,col,"accUnSignPurArea",100,true);
	    initColoum(header,col,"accUnSignPurAccount",140,true);
	    initColoum(header,col,"accUnSignPurAverage",100,true);
	    
	    initColoum(header,col,"accSignAmount",60,true);
	    initColoum(header,col,"accSignArea",100,true);
	    initColoum(header,col,"accSignAccount",140,true);
	    initColoum(header,col,"accSignAverage",100,true);
	    initColoum(header,col,"accSignBackAccount",140,true);
	    initColoum(header,col,"accSignReceAccount",140,true);
	    initColoum(header,col,"accSignReceAccountT",140,true);
	    initColoum(header,col,"accSignReceAccountY",140,true);
	   
	    header.setLabels(new Object[][]{ 
	    		{
	    		    "sellProjectId","项目","productTypeId","业态","来电","来访","本月来电","本月来访","本年来电","本年来访",
	    			"小订","小订","小订","小订",
	    			"退小订","退小订","退小订","退小订",
	    			"认购","认购","认购","认购",
	    			"已转签约小订","已转签约小订","已转签约小订","已转签约小订",
	    			"未转签约认购","未转签约认购","未转签约认购","未转签约认购",
	    			"已转签约认购","已转签约认购","已转签约认购","已转签约认购",
	    			"直签","直签","直签","直签",
	    			"退认购","退认购","退认购","退认购",
	    			"签约","签约","签约","签约","签约","签约","签约","签约","签约","签约",
	    			"退签约","退签约","退签约","退签约",
	    			"月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约","月度签约",
	    			"年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约","年度签约",
	    			"累计未转签约小订","累计未转签约小订","累计未转签约小订","累计未转签约小订",
	    			"累计未转签约认购","累计未转签约认购","累计未转签约认购","累计未转签约认购",
	    			"累计签约","累计签约","累计签约","累计签约","累计签约","累计签约","累计签约","累计签约"
	    		}
	    		,
	    		{
	    			"sellProjectId","项目","productTypeId","业态","来电","来访","本月来电","本月来访","本年来电","本年来访",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价","补差套数","补差面积","补差金额","面积小计","金额小计","回款金额",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价","补差套数","补差面积","补差金额","面积小计","金额小计","回款金额","签约计划","签约计划完成率","回款计划","回款计划完成率","	营销费用",
	    			"套数","面积","金额","均价","补差套数","补差面积","补差金额","面积小计","金额小计","回款金额","签约计划","签约计划完成率","回款计划","回款计划完成率",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价","回款金额","应收款","应收款（正常）","应收款（逾期）"
	    		}
	    		,
	    		{
	    			"sellProjectId","项目","productTypeId","业态","来电","来访","本月来电","本月来访","本年来电","本年来访",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价","补差套数","补差面积","补差金额","（面积+补差面积）","（金额+补差金额）","回款金额",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价","补差套数","补差面积","补差金额","（面积+补差面积）","（金额+补差金额）","回款金额","签约计划","签约计划完成率","回款计划","回款计划完成率","	营销费用",
	    			"套数","面积","金额","均价","补差套数","补差面积","补差金额","（面积+补差面积）","（金额+补差金额）","回款金额","签约计划","签约计划完成率","回款计划","回款计划完成率",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价",
	    			"套数","面积","金额","均价","回款金额","应收款","应收款（正常）","应收款（逾期）"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		return params;
    }
    private StringBuffer getSelectSql(StringBuffer sb,String name,boolean isLock,String quitName){
    	if(isLock){
    		sb.append(" max(case t1.name when '"+name+"' then round(t1.preAmount,2) else 0 end)- max(case t1.name when '"+quitName+"' then round(t1.preAmount,2) else 0 end) "+name+"Amount,");
        	sb.append(" max(case t1.name when '"+name+"' then round(t1.preArea,2) else 0 end) - max(case t1.name when '"+quitName+"' then round(t1.preArea,2) else 0 end) "+name+"Area,");
        	sb.append(" max(case t1.name when '"+name+"' then round(t1.preAccount,2) else 0 end) - max(case t1.name when '"+quitName+"' then round(t1.preAccount,2) else 0 end) "+name+"Account,");
        	sb.append(" (case (max(case t1.name when '"+name+"' then round(t1.preArea,2) else 0 end) - max(case t1.name when '"+quitName+"' then round(t1.preArea,2) else 0 end)) when 0 then 0 else (max(case t1.name when '"+name+"' then round(t1.preAccount,2) else 0 end)- max(case t1.name when '"+quitName+"' then round(t1.preAccount,2) else 0 end))/(max(case t1.name when '"+name+"' then round(t1.preArea,2) else 0 end) - max(case t1.name when '"+quitName+"' then round(t1.preArea,2) else 0 end)) end )"+name+"Average,");
    	}else{
    		sb.append(" max(case t1.name when '"+name+"' then round(t1.preAmount,2) else 0 end) "+name+"Amount,");
        	sb.append(" max(case t1.name when '"+name+"' then round(t1.preArea,2) else 0 end) "+name+"Area,");
        	sb.append(" max(case t1.name when '"+name+"' then round(t1.preAccount,2) else 0 end) "+name+"Account,");
        	sb.append(" max(case t1.name when '"+name+"' then round(t1.preAverage,2) else 0 end) "+name+"Average,");
    	}
    	return sb;
    }
    private StringBuffer getSelectSql(StringBuffer sb,String name){
		sb.append(" sum(tt."+name+"Amount) "+name+"Amount,");
		sb.append(" sum(tt."+name+"Area) "+name+"Area,");
		sb.append(" sum(tt."+name+"Account) "+name+"Account,");
		sb.append(" (case when sum(tt."+name+"Area) =0 then 0 else sum(tt."+name+"Account)/sum(tt."+name+"Area) end)"+name+"Average,");
    	return sb;
    }
    private void getVisitSql(StringBuffer sb,Date fromDate,Date toDate,int type,String sellProjectId,String org){
    	StringBuilder where = new StringBuilder();
    	if(sellProjectId!=null){
    		where.append(" and cct.fsellProjectId in ("+sellProjectId+")");
		}else if(org!=null){
			where.append(" and cct.fsellProjectId in ("+org+")");
		}else{
			where.append(" and cct.fsellProjectId in ('null')");
		}
    	where.append(" and cc.fid is not null");
    	
    	StringBuilder dateWhere = new StringBuilder();
    	dateWhere.append(" having 1=1");
		if(type==0){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
		
    	sb.append(" left join (select t.sellProjectId,count(*) cctAmount from (");
    	sb.append(" select cct.fsellProjectId sellProjectId from t_she_commerceChanceTrack cct left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
		sb.append(where);
		sb.append(" group by cct.fsellProjectId,cct.fcommerceChanceid");
		sb.append(dateWhere);
		sb.append(" union all");
		sb.append(" select sp.fid sellProjectId from t_she_commerceChanceTrack cct left join t_she_sellProject sp on sp.fparentid=cct.fsellProjectid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
		sb.append(where);
		sb.append(" group by sp.fid,cct.fcommerceChanceid");
		sb.append(dateWhere);
		sb.append(")t group by t.sellProjectId)");
    }
    private void getTelSql(StringBuffer sb,Date fromDate,Date toDate,int type,String sellProjectId,String org){
    	StringBuilder where = new StringBuilder();
    	if(sellProjectId!=null){
    		where.append(" and cct.fsellProjectId in ("+sellProjectId+")");
		}else if(org!=null){
			where.append(" and cct.fsellProjectId in ("+org+")");
		}else{
			where.append(" and cct.fsellProjectId in ('null')");
		}
    	where.append(" and cc.fid is not null ");
    	
    	StringBuilder dateWhere = new StringBuilder();
    	dateWhere.append(" having 1=1");
		if(type==0){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
		
    	sb.append(" left join (select t.sellProjectId,count(*) cctAmount from (");
		sb.append(" select cct.fsellProjectId sellProjectId from t_she_commerceChanceTrack cct left join (select min(cct.ftrackDate) ftrackDate ,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW' group by cct.fcommerceChanceid) cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='TELEPHONE' and (cct.ftrackDate<cct1.ftrackDate or cct1.ftrackDate is null)");
		sb.append(where);
		sb.append(" group by cct.fsellProjectId,cct.fcommerceChanceid");
		sb.append(dateWhere);
		sb.append(" union all");
		sb.append(" select sp.fid sellProjectId from t_she_commerceChanceTrack cct left join t_she_sellProject sp on sp.fparentid=cct.fsellProjectid left join (select min(cct.ftrackDate) ftrackDate ,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW' group by cct.fcommerceChanceid) cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='TELEPHONE' and (cct.ftrackDate<cct1.ftrackDate or cct1.ftrackDate is null)");
		sb.append(where);
		sb.append(" group by sp.fid,cct.fcommerceChanceid");
		sb.append(dateWhere);
		sb.append(")t group by t.sellProjectId)");
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
    	
    	String unSignPurDate="";
    	if(fromDate!=null&&toDate!=null){
    		unSignPurDate=unSignPurDate+"and (fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'} or fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'})";
		}else if(fromDate!=null&&toDate==null){
			unSignPurDate=unSignPurDate+"and fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
		}else if(fromDate==null&&toDate!=null){
			unSignPurDate=unSignPurDate+"and fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
		}
    	
    	String signPurDate="";
    	if(fromDate!=null){
    		signPurDate=signPurDate+" and fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
		}
		if(toDate!=null){
			signPurDate=signPurDate+" and fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
		}
    	String sellProjectWhere="";
		if(sellProjectStr!=null){
			sellProjectWhere=" and fsellProjectId in ("+sellProjectStr+")";
		}else if(org!=null){
			sellProjectWhere=" and fsellProjectId in ("+org+")";
		}else{
			sellProjectWhere=" and fsellProjectId in ('null')";
		}
		
		boolean isLock=params.getBoolean("isLock");
		
		boolean isFitment=params.getBoolean("isFitment");
		
    	StringBuffer sb = new StringBuffer();
    	if(!params.getBoolean("isAll")){
    		sb.append(" select tt.sellProjectId,tt.sellProjectName,tt.productTypeId,tt.productTypeName,sum(isnull(tt.tel,0)) tel,sum(isnull(tt.visit,0)) visit,sum(isnull(tt.monthTel,0)) monthTel,sum(isnull(tt.monthVisit,0)) monthVisit,sum(isnull(tt.yearTel,0)) yearTel,sum(isnull(tt.yearVisit,0)) yearVisit,");
    		getSelectSql(sb,"pre");
    		getSelectSql(sb,"quitPre");
    		getSelectSql(sb,"pur");
    		getSelectSql(sb,"signPre");
    		getSelectSql(sb,"unSignPur");
    		getSelectSql(sb,"signPur");
    		getSelectSql(sb,"directSign");
    		getSelectSql(sb,"quitPur");
    		getSelectSql(sb,"sign");
        	sb.append(" sum(tt.compensateAmount)compensateAmount,sum(tt.compensateArea)compensateArea,sum(tt.compensateAccount)compensateAccount,sum(tt.compensateArea+tt.signArea)compensateTotalArea,sum(tt.compensateAccount+tt.signAccount)compensateTotalAccount,sum(tt.signBackAccount) signBackAccount,");
        	getSelectSql(sb,"quitSign");
    		getSelectSql(sb,"monthSign");
    		sb.append(" sum(tt.mcompensateAmount)mcompensateAmount,sum(tt.mcompensateArea)mcompensateArea,sum(tt.mcompensateAccount)mcompensateAccount,sum(tt.mcompensateArea+tt.monthSignArea)mcompensateTotalArea,sum(tt.mcompensateAccount+tt.monthSignAccount)mcompensateTotalAccount,sum(tt.monthSignBackAccount) monthSignBackAccount,");
    		sb.append(" ' ' monthSignPlan,' ' monthSignPlanRate,' ' monthSignBackAccountPlan,' ' monthSignBackAccountPlanRate,' ' monthSignMarketAccount,");
        	getSelectSql(sb,"yearSign");
        	sb.append(" sum(tt.ycompensateAmount)ycompensateAmount,sum(tt.ycompensateArea)ycompensateArea,sum(tt.ycompensateAccount)ycompensateAccount,sum(tt.ycompensateArea+tt.yearSignArea)ycompensateTotalArea,sum(tt.ycompensateAccount+tt.yearSignAccount)ycompensateTotalAccount,sum(tt.yearSignBackAccount) yearSignBackAccount,");
        	sb.append(" ' ' yearSignPlan,' ' yearSignPlanRate,' ' yearSignBackAccountPlan,' ' yearSignBackAccountPlanRate,");
        	getSelectSql(sb,"accUnSignPre");
        	getSelectSql(sb,"accUnSignPur");
        	getSelectSql(sb,"accSign");
        	sb.append(" sum(tt.accSignBackAccount) accSignBackAccount,");
        	sb.append(" sum(tt.accSignReceAccount) accSignReceAccount,");
        	sb.append(" sum(tt.accSignReceAccountT) accSignReceAccountT,");
        	sb.append(" sum(tt.accSignReceAccountY) accSignReceAccountY from (");
    	}
    	sb.append(" select t1.sellProjectId,t1.sellProjectName,t1.productTypeId,t1.productTypeName,isnull(t1.tel,0) tel,isnull(t1.visit,0) visit,isnull(t1.mtel,0) monthTel,isnull(t1.mvisit,0) monthVisit,isnull(t1.ytel,0) yearTel,isnull(t1.yvisit,0) yearVisit,");
    	
    	getSelectSql(sb,"pre",isLock,"quitPre");
    	getSelectSql(sb,"quitPre",false,null);
//    	getSelectSql(sb,"pur");
    	if(isLock){
    		sb.append(" max(case t1.name when 'signPre' then round(t1.preAmount,2) else 0 end)-max(case t1.name when 'quitSignPre' then round(t1.preAmount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preAmount,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.preAmount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preAmount,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.preAmount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preAmount,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.preAmount,2) else 0 end) purAmount,");
        	
        	sb.append(" max(case t1.name when 'signPre' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitSignPre' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.preArea,2) else 0 end) purArea,");
        	
        	sb.append(" max(case t1.name when 'signPre' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitSignPre' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.preAccount,2) else 0 end) purAccount,");
        	
        	sb.append(" (case");
        	sb.append(" (max(case t1.name when 'signPre' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitSignPre' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.preArea,2) else 0 end)) when 0 then 0 ");
        	
        	sb.append(" else (max(case t1.name when 'signPre' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitSignPre' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.preAccount,2) else 0 end))/");
        	sb.append(" (max(case t1.name when 'signPre' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitSignPre' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitUnSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preArea,2) else 0 end)-max(case t1.name when 'quitDirectSign' then round(t1.preArea,2) else 0 end)) end )purAverage,");
    	}else{
    		sb.append(" max(case t1.name when 'signPre' then round(t1.preAmount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preAmount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preAmount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preAmount,2) else 0 end) purAmount,");
        	
        	sb.append(" max(case t1.name when 'signPre' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preArea,2) else 0 end) purArea,");
        	
        	sb.append(" max(case t1.name when 'signPre' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preAccount,2) else 0 end) purAccount,");
        	
        	sb.append(" (case");
        	sb.append(" (max(case t1.name when 'signPre' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preArea,2) else 0 end)) when 0 then 0 ");
        	
        	sb.append(" else (max(case t1.name when 'signPre' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preAccount,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preAccount,2) else 0 end))/");
        	sb.append(" (max(case t1.name when 'signPre' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'unSignPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'signPur' then round(t1.preArea,2) else 0 end)+");
        	sb.append(" max(case t1.name when 'directSign' then round(t1.preArea,2) else 0 end)) end )purAverage,");
    	}
    	getSelectSql(sb,"signPre",isLock,"quitSignPre");
    	getSelectSql(sb,"unSignPur",isLock,"quitUnSignPur");
    	getSelectSql(sb,"signPur",isLock,"quitSignPur");
    	getSelectSql(sb,"directSign",isLock,"quitDirectSign");
    	getSelectSql(sb,"quitPur",false,null);
    	getSelectSql(sb,"sign",isLock,"quitSign");
    	if(isLock){
        	sb.append(" t1.compensateAmount,t1.compensateArea,t1.compensateAccount,(t1.compensateArea+max(case t1.name when 'sign' then round(t1.preArea,2) else 0 end) - max(case t1.name when 'quitSign' then round(t1.preArea,2) else 0 end))compensateTotalArea,(t1.compensateAccount+max(case t1.name when 'sign' then round(t1.preAccount,2) else 0 end) - max(case t1.name when 'quitSign' then round(t1.preAccount,2) else 0 end))compensateTotalAccount,t1.signBackAccount,");
    	}else{
        	sb.append(" t1.compensateAmount,t1.compensateArea,t1.compensateAccount,(t1.compensateArea+max(case t1.name when 'sign' then round(t1.preArea,2) else 0 end))compensateTotalArea,(t1.compensateAccount+max(case t1.name when 'sign' then round(t1.preAccount,2) else 0 end))compensateTotalAccount,t1.signBackAccount,");
    	}
    	getSelectSql(sb,"quitSign",false,null);
    	getSelectSql(sb,"monthSign",isLock,"quitMonthSign");
    	if(isLock){
        	sb.append(" t1.mcompensateAmount,t1.mcompensateArea,t1.mcompensateAccount,(t1.mcompensateArea+max(case t1.name when 'monthSign' then round(t1.preArea,2) else 0 end) - max(case t1.name when 'quitMonthSign' then round(t1.preArea,2) else 0 end))mcompensateTotalArea,(t1.mcompensateAccount+max(case t1.name when 'monthSign' then round(t1.preAccount,2) else 0 end) - max(case t1.name when 'quitMonthSign' then round(t1.preAccount,2) else 0 end))mcompensateTotalAccount,t1.monthSignBackAccount,");
    	}else{
        	sb.append(" t1.mcompensateAmount,t1.mcompensateArea,t1.mcompensateAccount,(t1.mcompensateArea+max(case t1.name when 'monthSign' then round(t1.preArea,2) else 0 end))mcompensateTotalArea,(t1.mcompensateAccount+max(case t1.name when 'monthSign' then round(t1.preAccount,2) else 0 end))mcompensateTotalAccount,t1.monthSignBackAccount,");
    	}
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preArea else 0 end) monthSignPlan,");
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preAccount else 0 end) monthSignPlanRate,");
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preAverage else 0 end) monthSignBackAccountPlan,");
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preAmount else 0 end) monthSignBackAccountPlanRate,");
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preArea else 0 end) monthSignMarketAccount,");
    	
    	
    	sb.append(" ' ' monthSignPlan,' ' monthSignPlanRate,' ' monthSignBackAccountPlan,' ' monthSignBackAccountPlanRate,' ' monthSignMarketAccount,");
    	
    	getSelectSql(sb,"yearSign",isLock,"quitYearSign");
    	if(isLock){
        	sb.append(" t1.ycompensateAmount,t1.ycompensateArea,t1.ycompensateAccount,(t1.ycompensateArea+max(case t1.name when 'yearSign' then round(t1.preArea,2) else 0 end) - max(case t1.name when 'quitYearSign' then round(t1.preArea,2) else 0 end))ycompensateTotalArea,(t1.ycompensateAccount+max(case t1.name when 'yearSign' then round(t1.preAccount,2) else 0 end) - max(case t1.name when 'quitYearSign' then round(t1.preAccount,2) else 0 end))ycompensateTotalAccount,t1.yearSignBackAccount,");
    	}else{
        	sb.append(" t1.ycompensateAmount,t1.ycompensateArea,t1.ycompensateAccount,(t1.ycompensateArea+max(case t1.name when 'yearSign' then round(t1.preArea,2) else 0 end))ycompensateTotalArea,(t1.ycompensateAccount+max(case t1.name when 'yearSign' then round(t1.preAccount,2) else 0 end))ycompensateTotalAccount,t1.yearSignBackAccount,");
    	}

//    	sb.append(" max(case t1.name when 'monthSign' then t1.preArea else 0 end) yearSignPlan,");
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preAccount else 0 end) yearSignPlanRate,");
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preAverage else 0 end) yearSignBackAccountPlan,");
//    	sb.append(" max(case t1.name when 'monthSign' then t1.preAmount else 0 end) yearSignBackAccountPlanRate,");
    	
    	sb.append(" ' ' yearSignPlan,' ' yearSignPlanRate,' ' yearSignBackAccountPlan,' ' yearSignBackAccountPlanRate,");
    	
    	
    	getSelectSql(sb,"accUnSignPre",isLock,"quitAccUnSignPre");
    	getSelectSql(sb,"accUnSignPur",isLock,"quitAccUnSignPur");
//    	getSelectSql(sb,"accSign",isLock,"quitAccSign");
    	getSelectSql(sb,"accSign",false,null);
    	sb.append(" isnull(t1.accSignBackAccount,0) accSignBackAccount,");
    	
    	if(isLock){
    		sb.append(" max(case t1.name when 'accSign' then round(t1.preAccount,2) else 0 end)-max(case t1.name when 'quitAccSign' then round(t1.preAccount,2) else 0 end)-isnull(t1.accSignBackAccount,0) accSignReceAccount,");
        	sb.append(" max(case t1.name when 'accSign' then round(t1.receAccountT,2) else 0 end) accSignReceAccountT,");
        	sb.append(" max(case t1.name when 'accSign' then round(t1.preAccount,2) else 0 end) -max(case t1.name when 'quitAccSign' then round(t1.preAccount,2) else 0 end)-isnull(t1.accSignBackAccount,0)-max(case t1.name when 'accSign' then round(t1.receAccountT,2) else 0 end) accSignReceAccountY");
    	}else{
    		sb.append(" max(case t1.name when 'accSign' then round(t1.preAccount,2) else 0 end) -isnull(t1.accSignBackAccount,0) accSignReceAccount,");
        	sb.append(" max(case t1.name when 'accSign' then round(t1.receAccountT,2) else 0 end) accSignReceAccountT,");
        	sb.append(" max(case t1.name when 'accSign' then round(t1.preAccount,2) else 0 end) -isnull(t1.accSignBackAccount,0)-max(case t1.name when 'accSign' then round(t1.receAccountT,2) else 0 end) accSignReceAccountY");
    	}
    	sb.append(" from (");
    	if(!params.getBoolean("isAll")){
    		sb.append(" select (case when ppsp.fparentId is null then t.sellProjectId else ppsp.fparentId end) sellProjectId,(case when pname.fname_l2 is null then t.sellProjectName else pname.fname_l2 end) sellProjectName,t.productTypeId,t.productTypeName,t.name,tel.cctAmount tel,visit.cctAmount visit,mtel.cctAmount mtel,mvisit.cctAmount mvisit,ytel.cctAmount ytel,yvisit.cctAmount yvisit,count(*) preAmount,sum(t.preArea) preArea,sum(t.preAccount) preAccount,(case when sum(t.preArea)=0 then 0 else sum(t.preAccount)/sum(t.preArea) end) preAverage,");
    	}else{
    		sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,t.name,tel.cctAmount tel,visit.cctAmount visit,mtel.cctAmount mtel,mvisit.cctAmount mvisit,ytel.cctAmount ytel,yvisit.cctAmount yvisit,count(*) preAmount,sum(t.preArea) preArea,sum(t.preAccount) preAccount,(case when sum(t.preArea)=0 then 0 else sum(t.preAccount)/sum(t.preArea) end) preAverage,");
    	}
    	sb.append(" sum(t.receAccountT) receAccountT,isnull(compensate.compensateAmount,0) compensateAmount,isnull(compensate.compensateArea,0) compensateArea,isnull(compensate.compensateAccount,0) compensateAccount,signBackAccount.backAccount signBackAccount,isnull(mcompensate.compensateAmount,0) mcompensateAmount,isnull(mcompensate.compensateArea,0) mcompensateArea,isnull(mcompensate.compensateAccount,0) mcompensateAccount,monthSignBackAccount.backAccount monthSignBackAccount,isnull(ycompensate.compensateAmount,0) ycompensateAmount,isnull(ycompensate.compensateArea,0) ycompensateArea,isnull(ycompensate.compensateAccount,0) ycompensateAccount,yearSignBackAccount.backAccount yearSignBackAccount,accSignBackAccount.backAccount accSignBackAccount from (");
		if(isLock){
			getBaseTransaction(sb,"'pre'","t_she_prePurchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreApple','PreAudit','ToPur','ToSign','QRNullify'",null,fromDate,toDate,0,sellProjectStr,org,isFitment);
		}else{
			getBaseTransaction(sb,"'pre'","t_she_prePurchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreApple','PreAudit','ToPur','ToSign'",null,fromDate,toDate,0,sellProjectStr,org,isFitment);
		}
		sb.append(" union all");
    	getChange(sb,"'quitPre'","t_she_prePurchaseManage","'4AUDITTED'",null,fromDate,toDate,0,sellProjectStr,org);
    	sb.append(" union all");
//    	getBaseTransaction(sb,"'pur'","t_she_purchaseManage","'PurApple','PurAudit'",null,fromDate,toDate,0);
//    	sb.append(" union all");
//    	getBaseTransaction(sb,"'pur'","t_she_signManage","'SignApple','SignAudit'",null,fromDate,toDate,0);
//    	sb.append(" union all");
    	if(isLock){
    		getBaseTransaction(sb,"'signPre'","t_she_prePurchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify') "+signPurDate+sellProjectWhere+" and "+getLockChangeSrcId(fromDate,toDate,0,sellProjectStr,org)+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    		sb.append(" union all");
        	getChange(sb,"'quitSignPre'","t_she_prePurchaseManage","'4AUDITTED'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+signPurDate+sellProjectWhere+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org);
    	}else{
    		getBaseTransaction(sb,"'signPre'","t_she_prePurchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') "+signPurDate+sellProjectWhere+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    	}
		sb.append(" union all");
		if(isLock){
			getBaseTransaction(sb,"'unSignPur'","t_she_purchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PurApple','PurAudit','QRNullify'",null,fromDate,toDate,0,sellProjectStr,org,isFitment);
    		sb.append(" union all");
        	getChange(sb,"'quitUnSignPur'","t_she_purchaseManage","'4AUDITTED'","(EXISTS (select fid from t_she_purchaseManage t where fbizState in('QRNullify') and pre.fid=t.fId))",fromDate,toDate,0,sellProjectStr,org);
    	}else{
    		getBaseTransaction(sb,"'unSignPur'","t_she_purchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PurApple','PurAudit'",null,fromDate,toDate,0,sellProjectStr,org,isFitment);
    	}
		if(!unSignPurDate.equals("")){
    		sb.append(" union all");
    		if(isLock){
    			getBaseTransaction(sb,"'unSignPur'","t_she_purchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify') "+unSignPurDate+sellProjectWhere+" and "+getLockChangeSrcId(fromDate,toDate,0,sellProjectStr,org)+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    			sb.append(" union all");
        		getChange(sb,"'quitUnSignPur'","t_she_purchaseManage","'4AUDITTED'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+unSignPurDate+sellProjectWhere+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org);
    		}else{
    			getBaseTransaction(sb,"'unSignPur'","t_she_purchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') "+unSignPurDate+sellProjectWhere+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    		}
    	}
    	sb.append(" union all");
    	if(isLock){
    		getBaseTransaction(sb,"'signPur'","t_she_purchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify') "+signPurDate+sellProjectWhere+" and "+getLockChangeSrcId(fromDate,toDate,0,sellProjectStr,org)+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    		sb.append(" union all");
        	getChange(sb,"'quitSignPur'","t_she_purchaseManage","'4AUDITTED'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+signPurDate+sellProjectWhere+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org);
    	}else{
    		getBaseTransaction(sb,"'signPur'","t_she_purchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') "+signPurDate+sellProjectWhere+" and pre.fid=t.fsrcId))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    	}
		sb.append(" union all");
		if(isLock){
			getBaseTransaction(sb,"'directSign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'","(pre.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where 1=1 and pre.fsrcId=t.fid "+sellProjectWhere+" union select fid from t_she_purchaseManage t where 1=1 and pre.fsrcId=t.fid "+sellProjectWhere+"))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    		sb.append(" union all");
        	getChange(sb,"'quitDirectSign'","t_she_signManage","'4AUDITTED'","(pre.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where 1=1 and pre.fsrcId=t.fid"+sellProjectWhere+" union select fid from t_she_purchaseManage t where 1=1 and pre.fsrcId=t.fid "+sellProjectWhere+"))",fromDate,toDate,0,sellProjectStr,org);
    	}else{
    		getBaseTransaction(sb,"'directSign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'","(pre.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where 1=1 and pre.fsrcId=t.fid "+sellProjectWhere+" union select fid from t_she_purchaseManage t where 1=1 and pre.fsrcId=t.fid "+sellProjectWhere+"))",fromDate,toDate,0,sellProjectStr,org,isFitment);
    	}
		sb.append(" union all");
    	getChange(sb,"'quitPur'","t_she_purchaseManage","'4AUDITTED'",null,fromDate,toDate,0,sellProjectStr,org);
    	sb.append(" union all");
    	if(isLock){
    		getBaseTransaction(sb,"'sign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'",null,fromDate,toDate,0,sellProjectStr,org,isFitment);
    	}else{
    		getBaseTransaction(sb,"'sign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,fromDate,toDate,0,sellProjectStr,org,isFitment);
    	}
		sb.append(" union all");
    	getChange(sb,"'quitSign'","t_she_signManage","'4AUDITTED'",null,fromDate,toDate,0,sellProjectStr,org);
    	sb.append(" union all");
    	if(isLock){
    		getBaseTransaction(sb,"'monthSign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'",null,fromDate,toDate,1,sellProjectStr,org,isFitment);
    		sb.append(" union all");
        	getChange(sb,"'quitMonthSign'","t_she_signManage","'4AUDITTED'",null,fromDate,toDate,1,sellProjectStr,org);
    	}else{
    		getBaseTransaction(sb,"'monthSign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,fromDate,toDate,1,sellProjectStr,org,isFitment);
    	}
		sb.append(" union all");
		if(isLock){
			getBaseTransaction(sb,"'yearSign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'",null,fromDate,toDate,2,sellProjectStr,org,isFitment);
			sb.append(" union all");
			getChange(sb,"'quitYearSign'","t_she_signManage","'4AUDITTED'",null,fromDate,toDate,2,sellProjectStr,org);
		}else{
			getBaseTransaction(sb,"'yearSign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,fromDate,toDate,2,sellProjectStr,org,isFitment);
		}
		sb.append(" union all");
    	if(isLock){
    		getBaseTransaction(sb,"'accUnSignPre'","t_she_prePurchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreApple','PreAudit','QRNullify'",null,fromDate,toDate,3,sellProjectStr,org,isFitment);
    		sb.append(" union all");
        	getChange(sb,"'quitAccUnSignPre'","t_she_prePurchaseManage","'4AUDITTED'",null,fromDate,toDate,3,sellProjectStr,org);
    	}else{
    		getBaseTransaction(sb,"'accUnSignPre'","t_she_prePurchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreApple','PreAudit'",null,fromDate,toDate,3,sellProjectStr,org,isFitment);
    	}
    	sb.append(" union all");
    	if(isLock){
    		getBaseTransaction(sb,"'accUnSignPur'","t_she_purchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PurApple','PurAudit','QRNullify'",null,fromDate,toDate,3,sellProjectStr,org,isFitment);
    		sb.append(" union all");
        	getChange(sb,"'quitAccUnSignPur'","t_she_purchaseManage","'4AUDITTED'",null,fromDate,toDate,3,sellProjectStr,org);
    	}else{
    		getBaseTransaction(sb,"'accUnSignPur'","t_she_purchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PurApple','PurAudit'",null,fromDate,toDate,3,sellProjectStr,org,isFitment);
    	}
    	sb.append(" union all");
//    	if(isLock){
//    		getBaseTransaction(sb,"'accSign'","t_she_signManage","'SignApple','SignAudit','QRNullify'",null,fromDate,toDate,3,sellProjectStr,org);
//    		sb.append(" union all");
//        	getChange(sb,"'quitAccSign'","t_she_signManage","'2SUBMITTED','4AUDITTED'",null,fromDate,toDate,3,sellProjectStr,org);
//    	}else{
    		getBaseTransaction(sb,"'accSign'","t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,fromDate,toDate,3,sellProjectStr,org,isFitment);
//    	}
    	sb.append(" ) t ");
    	
    	getTelSql(sb,fromDate,toDate,0,sellProjectStr,org);
    	sb.append(" tel on tel.sellProjectId=t.sellProjectId ");

    	getVisitSql(sb,fromDate,toDate,0,sellProjectStr,org);
    	sb.append(" visit on visit.sellProjectId=t.sellProjectId ");
    	
    	getTelSql(sb,fromDate,toDate,1,sellProjectStr,org);
    	sb.append(" mtel on mtel.sellProjectId=t.sellProjectId ");
    	
    	getVisitSql(sb,fromDate,toDate,1,sellProjectStr,org);
    	sb.append(" mvisit on mvisit.sellProjectId=t.sellProjectId ");
    	
    	getTelSql(sb,fromDate,toDate,2,sellProjectStr,org);
    	sb.append(" ytel on ytel.sellProjectId=t.sellProjectId ");
    	
    	getVisitSql(sb,fromDate,toDate,2,sellProjectStr,org);
    	sb.append(" yvisit on yvisit.sellProjectId=t.sellProjectId ");
    	
    	getCompensateBill(sb,fromDate,toDate,0,sellProjectStr,org);
    	sb.append(" compensate on compensate.sellProjectId=t.sellProjectId and compensate.productTypeId=t.productTypeId");
    	
    	getSheRevBill(sb,fromDate,toDate,0,sellProjectStr,org,isFitment);
    	sb.append(" signBackAccount on signBackAccount.sellProjectId=t.sellProjectId and signBackAccount.productTypeId=t.productTypeId");
    	
    	getCompensateBill(sb,fromDate,toDate,1,sellProjectStr,org);
    	sb.append(" mcompensate on mcompensate.sellProjectId=t.sellProjectId and mcompensate.productTypeId=t.productTypeId");
    	
    	getSheRevBill(sb,fromDate,toDate,1,sellProjectStr,org,isFitment);
    	sb.append(" monthSignBackAccount on monthSignBackAccount.sellProjectId=t.sellProjectId and monthSignBackAccount.productTypeId=t.productTypeId");
    	
    	getCompensateBill(sb,fromDate,toDate,2,sellProjectStr,org);
    	sb.append(" ycompensate on ycompensate.sellProjectId=t.sellProjectId and ycompensate.productTypeId=t.productTypeId");
    	
    	getSheRevBill(sb,fromDate,toDate,2,sellProjectStr,org,isFitment);
    	sb.append(" yearSignBackAccount on yearSignBackAccount.sellProjectId=t.sellProjectId and yearSignBackAccount.productTypeId=t.productTypeId");
    	
    	getSheRevBill(sb,fromDate,toDate,3,sellProjectStr,org,isFitment);
    	sb.append(" accSignBackAccount on accSignBackAccount.sellProjectId=t.sellProjectId and accSignBackAccount.productTypeId=t.productTypeId");
    	
    	if(!params.getBoolean("isAll")){
    		sb.append(" left join t_she_sellProject ppsp on ppsp.fid=t.sellProjectId");
    		sb.append(" left join t_she_sellProject pname on pname.fid=ppsp.fparentid");
    		sb.append(" group by t.name,case when ppsp.fparentId is null then t.sellProjectId else ppsp.fparentId end,case when pname.fname_l2 is null then t.sellProjectName else pname.fname_l2 end,t.productTypeId,t.productTypeName,tel.cctAmount,visit.cctAmount,mtel.cctAmount,mvisit.cctAmount,ytel.cctAmount,yvisit.cctAmount,compensate.compensateAmount,compensate.compensateArea,compensate.compensateAccount,signBackAccount.backAccount,mcompensate.compensateAmount,mcompensate.compensateArea,mcompensate.compensateAccount,monthSignBackAccount.backAccount,ycompensate.compensateAmount,ycompensate.compensateArea,ycompensate.compensateAccount,yearSignBackAccount.backAccount,accSignBackAccount.backAccount ) t1 where 1=1");
    	}else{
    		sb.append(" group by t.name,t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,tel.cctAmount,visit.cctAmount,mtel.cctAmount,mvisit.cctAmount,ytel.cctAmount,yvisit.cctAmount,compensate.compensateAmount,compensate.compensateArea,compensate.compensateAccount,signBackAccount.backAccount,mcompensate.compensateAmount,mcompensate.compensateArea,mcompensate.compensateAccount,monthSignBackAccount.backAccount,ycompensate.compensateAmount,ycompensate.compensateArea,ycompensate.compensateAccount,yearSignBackAccount.backAccount,accSignBackAccount.backAccount ) t1 where 1=1");
    	}
		if(productTypeId!=null){
			sb.append(" and t1.productTypeId ='"+productTypeId+"'");
		}
		sb.append(" group by t1.sellProjectId,t1.sellProjectName,t1.productTypeId,t1.productTypeName,t1.tel,t1.visit,t1.mtel,t1.mvisit,t1.ytel,t1.yvisit,t1.compensateAmount,t1.compensateArea,t1.compensateAccount,t1.signBackAccount,t1.mcompensateAmount,t1.mcompensateArea,t1.mcompensateAccount,t1.monthSignBackAccount,t1.ycompensateAmount,t1.ycompensateArea,t1.ycompensateAccount,t1.yearSignBackAccount,t1.accSignBackAccount order by t1.sellProjectName,t1.productTypeName ");
		if(!params.getBoolean("isAll")){
			sb.append(" )tt group by tt.sellProjectId,tt.sellProjectName,tt.productTypeId,tt.productTypeName order by tt.sellProjectName,tt.productTypeName ");
		}
		return sb.toString();
    	
    }
    private void getCompensateBill(StringBuffer sb,Date fromDate,Date toDate,int type,String sellProjectId,String org){
    	sb.append(" left join (select revBill.fsellProjectid sellProjectId,productType.fid productTypeId,count(*) as compensateAmount,sum(isnull(room.factualBuildingArea,0)-isnull(room.fbuildingArea,0)) as compensateArea,sum(isnull(entry.factAmount,0)) as compensateAccount from T_SHE_CompensateRoomList entry left join T_SHE_RoomAreaCompensate revBill on revBill.fid=entry.fheadid left join t_she_room room on room.fid=entry.froomid");
    	sb.append(" left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid ");
    	sb.append(" where revBill.fcompensateState in('COMSUBMIT','COMAUDITTED') ");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.FCompensateDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.FCompensateDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.FCompensateDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.FCompensateDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.FCompensateDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.FCompensateDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}else if(org!=null){
			sb.append(" and revBill.fsellProjectId in ("+org+")");
		}else{
			sb.append(" and revBill.fsellProjectId in ('null')");
		}
    	sb.append(" group by revBill.fsellProjectid,productType.fid)");
    }
    private void getSheRevBill(StringBuffer sb,Date fromDate,Date toDate,int type,String sellProjectId,String org,boolean isFitment){
    	sb.append(" left join (select revBill.fsellProjectid sellProjectId,productType.fid productTypeId,sum(isnull(entry.frevAmount,0)) as backAccount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(!isFitment){
    		sb.append(" and md.fnumber !='024'");
    	}
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}else if(org!=null){
			sb.append(" and revBill.fsellProjectId in ("+org+")");
		}else{
			sb.append(" and revBill.fsellProjectId in ('null')");
		}
    	sb.append(" group by revBill.fsellProjectid,productType.fid)");
    }
    //tpye=0 >=fromDate <=toDate,type=1 >=fromDate 到月底,type=2 >=fromDate 到年底,type=3 <=toDate
    private void getBaseTransaction(StringBuffer sb,String name,String table,String state,String where,Date fromDate,Date toDate,int type,String sellProjectId,String org,boolean isFitment){
    	sb.append(" select "+name+" name,sellProject.fname_l2 sellProjectName,productType.fname_l2 productTypeName,");
		if(name.equals("'accSign'")){
			if(isFitment){
				sb.append(" (case when pre.FAreaCompensate is null then (case pre.fsellType when 'PlanningSell' then pre.fstrdPlanBuildingArea when 'PreSell' then pre.fbulidingArea else pre.fstrdActualBuildingArea end) else room.FActualBuildingArea end ) preArea,");
	    		sb.append(" pre.fcontractTotalAmount+isnull(pre.FAreaCompensate,0)+isnull(pre.FPlanningCompensate,0)+isnull(pre.FCashSalesCompensate,0) preAccount,");
			}else{
				sb.append(" (case when pre.FAreaCompensate is null then (case pre.fsellType when 'PlanningSell' then pre.fstrdPlanBuildingArea when 'PreSell' then pre.fbulidingArea else pre.fstrdActualBuildingArea end) else room.FActualBuildingArea end ) preArea,");
	    		sb.append(" pre.fcontractTotalAmount+isnull(pre.FAreaCompensate,0)+isnull(pre.FPlanningCompensate,0)+isnull(pre.FCashSalesCompensate,0)-isnull(fit.fitAmount,0) preAccount,");
			}
    	}else{
    		if(isFitment){
				sb.append(" (case pre.fsellType when 'PlanningSell' then pre.fstrdPlanBuildingArea when 'PreSell' then pre.fbulidingArea else pre.fstrdActualBuildingArea end) preArea,");
        		sb.append(" pre.fcontractTotalAmount preAccount,");
    		}else{
    			if(table.equals("t_she_signManage")){
    				sb.append(" (case pre.fsellType when 'PlanningSell' then pre.fstrdPlanBuildingArea when 'PreSell' then pre.fbulidingArea else pre.fstrdActualBuildingArea end) preArea,");
            		sb.append(" pre.fcontractTotalAmount-isnull(fit.fitAmount,0) preAccount,");
    			}else{
    				sb.append(" (case pre.fsellType when 'PlanningSell' then pre.fstrdPlanBuildingArea when 'PreSell' then pre.fbulidingArea else pre.fstrdActualBuildingArea end) preArea,");
            		sb.append(" pre.fcontractTotalAmount preAccount,");
    			}
    		}
    	}
    	if(type==1||type==2||(type==0&&table.equals("t_she_signManage"))){
    		sb.append(" 0 receAccountT,");
    	}else if(type==3&&table.equals("t_she_signManage")){
    		sb.append(" isnull(t1.receAccountT,0) receAccountT,");
    	}else{
    		sb.append(" 0 receAccountT,");
    	}
    	sb.append(" ' ' plans,' ' planRate ,' ' backAccountPlan,' ' backAccountPlanRate,' ' marketAccount,productType.fid productTypeId,pre.fsellProjectid sellProjectId from "+table+" pre");
    	sb.append(" left join t_she_sellProject sellproject on sellProject.fid=pre.fsellProjectid left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
//    	if(type==1||type==2||(type==3&&table.equals("t_she_signManage"))||(type==0&&table.equals("t_she_signManage"))){
//    		sb.append(" left join (select base.fbillid,sum(tbov.fappAmount-tbov.factRevAmount) receAccount from t_she_transaction base left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='违约金' group by base.fbillid) t on t.fbillid=pre.fid");
//    	}
    	if(type==3&&table.equals("t_she_signManage")){
    		sb.append(" left join (select base.fbillid,sum(tbov.fappAmount-isnull(tbov.factRevAmount,0)) receAccountT from t_she_transaction base left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='违约金' and DATEDIFF(day,  tbov.FAppDate, getdate())<=0 group by base.fbillid) t1 on t1.fbillid=pre.fid");
    	}
    	if(type==2){
    		
    	}
    	if(!isFitment&&table.equals("t_she_signManage")){
    		sb.append(" left join (select sum(fappAmount) fitAmount,fheadId from T_SHE_SignPayListEntry entry left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId where md.fnumber='024' group by fheadid) fit on fit.fheadId=pre.fid");
    	}
    	sb.append(" where pre.fbizState in("+state+") and productType.fid is not null");
    	sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and pre.fid=tt.fnewId )");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and pre.fsellProjectId in ("+sellProjectId+")");
		}else if(org!=null){
			sb.append(" and pre.fsellProjectId in ("+org+")");
		}else{
			sb.append(" and pre.fsellProjectId in ('null')");
		}
    	if(where!=null)
    		sb.append(" and "+where);
    }
    private void getChange(StringBuffer sb,String name,String table,String state,String where,Date fromDate,Date toDate,int type,String sellProjectId,String org){
    	sb.append(" select "+name+" name,sellProject.fname_l2 sellProjectName,productType.fname_l2 productTypeName,");
    	if(table.equals("t_she_signManage")){
    		sb.append(" (case when pre.FAreaCompensate is null then (case pre.fsellType when 'PlanningSell' then pre.fstrdPlanBuildingArea when 'PreSell' then pre.fbulidingArea else pre.fstrdActualBuildingArea end) else room.FActualBuildingArea end ) preArea,");
    		sb.append(" pre.fcontractTotalAmount+isnull(pre.FAreaCompensate,0)+isnull(pre.FPlanningCompensate,0)+isnull(pre.FCashSalesCompensate,0) preAccount,");
    	}else{
    		sb.append(" (case pre.fsellType when 'PlanningSell' then pre.fstrdPlanBuildingArea when 'PreSell' then pre.fbulidingArea else pre.fstrdActualBuildingArea end) preArea,");
    		sb.append(" pre.fcontractTotalAmount preAccount,");
    	}
    	sb.append(" 0 receAccountT,' ' plans,' ' planRate ,' ' backAccountPlan,' ' backAccountPlanRate,' ' marketAccount,productType.fid productTypeId,pre.fsellProjectid sellProjectId from "+table+" pre");
    	sb.append(" left join t_she_sellProject sellproject on sellProject.fid=pre.fsellProjectid left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid right join t_she_changeManage change on change.fsrcid=pre.fid where change.fstate in("+state+") and change.fbizType='quitRoom' and productType.fid is not null");
		if(type==0){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
		if(sellProjectId!=null){
			sb.append(" and change.fsellProjectId in ("+sellProjectId+")");
		}else if(org!=null){
			sb.append(" and change.fsellProjectId in ("+org+")");
		}else{
			sb.append(" and change.fsellProjectId in ('null')");
		}
		if(where!=null)
    		sb.append(" and "+where);
    }
    private String getLockChangeSrcId(Date fromDate,Date toDate,int type,String sellProjectId,String org){
    	String change="(NOT EXISTS (select fsrcId from t_she_changeManage tt where fstate in('4AUDITTED') and fbizType='quitRoom' and tt.fsrcId=t.fid ";
    	if(type==0){
    		if(fromDate!=null){
    			change=change+" and fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
    		}
    		if(toDate!=null){
    			change=change+" and fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			change=change+" and fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}";
    			change=change+" and fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}";
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			change=change+" and fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}";
    			change=change+" and fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}";
    		}
    	}
    	if(sellProjectId!=null){
    		change=change+" and fsellProjectId in ("+sellProjectId+")";
		}else if(org!=null){
			change=change+" and fsellProjectId in ("+org+")";
		}else{
			change=change+" and fsellProjectId in ('null')";
		}
    	change=change+"))";
    	return change;
    }
}