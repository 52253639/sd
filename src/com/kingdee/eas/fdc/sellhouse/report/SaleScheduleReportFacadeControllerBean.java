package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

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

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class SaleScheduleReportFacadeControllerBean extends AbstractSaleScheduleReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SaleScheduleReportFacadeControllerBean");

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
	    initColoum(header,col,"orgId",150,true);
	    initColoum(header,col,"company",150,false);
	    initColoum(header,col,"type",150,false);
	    initColoum(header,col,"plan",150,false);
	    initColoum(header,col,"act",170,false);
	    initColoum(header,col,"rate",150,false);
	    initColoum(header,col,"yearPlan",150,false);
	    initColoum(header,col,"yearAct",170,false);
	    initColoum(header,col,"yearRate",150,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"orgId","公司名称","销售分类","当月销售状况","当月销售状况","当月销售状况","年度销售状况","年度销售状况","年度销售状况"
	    		}
	    		,
	    		{
	    			"orgId","公司名称","销售分类","计划数（万元）","实际数（当月累计）（万元）","完成率","年度计划数（万元）","实际数（当年累计）（万元）","完成率"
				}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	String orgUnit=params.getString("orgUnit");
		RptRowSet signRS = executeQuery(getBaseTransaction(orgUnit,fromDate,toDate), null, ctx);
		params.setObject("signRs", signRS);
		
		RptRowSet onLoadRS = executeQuery(getOnLoadBaseTransaction(orgUnit,fromDate,toDate), null, ctx);
		params.setObject("onLoadRS", onLoadRS);
		
		RptRowSet revRS = executeQuery(getSheRevBill(orgUnit,fromDate,toDate), null, ctx);
		params.setObject("revRS", revRS);
		return params;
    }
    private String getBaseTransaction(String orgUnit,Date fromDate,Date toDate){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.orgId,t.number,t.company,max(case t.name when 'month' then round(t.amount,2) else 0 end)/10000 monthAmount,max(case t.name when 'year' then round(t.amount,2) else 0 end)/10000 yearAmount");
    	sb.append(" from (select 'month' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(sign.fcontractTotalAmount) amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId where sign.fbizState in('SignApple','SignAudit')");
		if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber");
    	
    	sb.append(" union all");
    	
    	sb.append(" select 'year' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(sign.fcontractTotalAmount) amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId where sign.fbizState in('SignApple','SignAudit')");
		if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber) t group by t.orgId,t.company,t.number order by t.number");
    	return sb.toString();
    }
    private String getOnLoadBaseTransaction(String orgUnit,Date fromDate,Date toDate){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.orgId,t.number,t.company,max(case t.name when 'month' then round(t.amount,2) else 0 end)/10000 monthAmount,max(case t.name when 'year' then round(t.amount,2) else 0 end)/10000 yearAmount");
    	sb.append(" from (select 'month' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(bo.fappAmount-isnull(act.factRevAmount,0)) amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
    	sb.append(" left join T_SHE_SignPayListEntry bo on bo.fheadId=sign.fid");
		sb.append(" left join t_she_moneyDefine md on bo.fmoneyDefineid=md.fid");
    	sb.append(" left join t_she_TranBusinessOverView act on bo.ftanPayListEntryId=act.fid");
    	sb.append(" where sign.fbizState in('SignApple','SignAudit') and md.fnumber not in('01','02','03','12','13','14','15','17','18','19','20','21','22','23','24','25')");
		if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber");
    	
    	sb.append(" union all");
    	
    	sb.append(" select 'year' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(bo.fappAmount-isnull(act.factRevAmount,0)) amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
    	sb.append(" left join T_SHE_SignPayListEntry bo on bo.fheadId=sign.fid");
		sb.append(" left join t_she_moneyDefine md on bo.fmoneyDefineid=md.fid");
    	sb.append(" left join t_she_TranBusinessOverView act on bo.ftanPayListEntryId=act.fid");
    	sb.append(" where sign.fbizState in('SignApple','SignAudit') and md.fnumber not in('01','02','03','12','13','14','15','17','18','19','20','21','22','23','24','25')");
		if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
		sb.append(" group by org.fid,org.fname_l2,org.flongNumber) t group by t.orgId,t.company,t.number order by t.number");
		return sb.toString();
    }
    private String getSheRevBill(String orgUnit,Date fromDate,Date toDate){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.orgId,t.number,t.company,max(case t.name when 'month' then round(t.amount,2) else 0 end)/10000 monthAmount,max(case t.name when 'year' then round(t.amount,2) else 0 end)/10000 yearAmount");
    	sb.append(" from (select 'month' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(isnull(entry.frevAmount,0)) amount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid");
    	sb.append(" left join t_org_baseUnit org on org.fid=revBill.fsaleOrgUnitId left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fnumber not in('01','12','17','18','19','20','21','22','23','24')");
		if(fromDate!=null){
			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber");
    	
    	sb.append(" union all");
    	
    	sb.append(" select 'year' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(isnull(entry.frevAmount,0)) amount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid");
    	sb.append(" left join t_org_baseUnit org on org.fid=revBill.fsaleOrgUnitId left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fnumber not in('01','12','17','18','19','20','21','22','23','24')");
		if(fromDate!=null){
			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber) t group by t.orgId,t.company,t.number order by t.number");
    	return sb.toString();
    }
}