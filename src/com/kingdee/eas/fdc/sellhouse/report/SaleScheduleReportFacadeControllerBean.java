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
	    			"orgId","公司名称","销售分类","销售状况","销售状况","销售状况","年度销售状况","年度销售状况","年度销售状况"
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
		
		RptRowSet onLoadRSUnLoan = executeQuery(getOnLoadBaseTransactionUnLoan(orgUnit,fromDate,toDate), null, ctx);
		params.setObject("onLoadRSUnLoan", onLoadRS);
		
		RptRowSet revRS = executeQuery(getSheRevBill(orgUnit,fromDate,toDate), null, ctx);
		params.setObject("revRS", revRS);
		return params;
    }
    private String getBaseTransaction(String orgUnit,Date fromDate,Date toDate){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.orgId,t.number,t.company,max(case t.name when 'month' then round(t.amount,2) else 0 end)/10000 monthAmount,max(case t.name when 'year' then round(t.amount,2) else 0 end)/10000 yearAmount");
    	sb.append(" from (select 'month' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(sign.fcontractTotalAmount) amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId where sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
    	sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and sign.fid=tt.fnewId )");
    	if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber");
    	
    	sb.append(" union all");
    	
    	sb.append(" select 'year' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(sign.fcontractTotalAmount) amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId where sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(toDate)))+ "'}");
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
    	sb.append(" select org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(isnull(t.amount,0))/10000 amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
    	sb.append(" left join (select base.fbillid,sum(tbov.fappAmount-isnull(tbov.factRevAmount,0)) amount from t_she_transaction base left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='违约金' group by base.fbillid) t on t.fbillid=sign.fid");
    	sb.append(" where sign.fBizState in('SignApple','SignAudit')");
    	sb.append(" and EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
    	if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber");
		return sb.toString();
    }
    private String getOnLoadBaseTransactionUnLoan(String orgUnit,Date fromDate,Date toDate){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(isnull(t.amount,0))/10000 amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
    	sb.append(" left join (select base.fbillid,sum(tbov.fappAmount-isnull(tbov.factRevAmount,0)) amount from t_she_transaction base left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='违约金' group by base.fbillid) t on t.fbillid=sign.fid");
    	sb.append(" where sign.fBizState in('SignApple','SignAudit')");
    	sb.append(" and NOT EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
    	if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber");
		return sb.toString();
    }
    private String getSheRevBill(String orgUnit,Date fromDate,Date toDate){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.orgId,t.number,t.company,max(case t.name when 'month' then round(t.amount,2) else 0 end)/10000 monthAmount,max(case t.name when 'year' then round(t.amount,2) else 0 end)/10000 yearAmount");
    	sb.append(" from (select 'month' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(isnull(entry.famount,0)) amount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid");
    	sb.append(" left join t_org_baseUnit org on org.fid=revBill.fsaleOrgUnitId left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
		if(fromDate!=null){
			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber");
    	
    	sb.append(" union all");
    	
    	sb.append(" select 'year' name,org.fid orgId,org.flongNumber number,org.fname_l2 company,sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) amount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid");
    	sb.append(" left join t_org_baseUnit org on org.fid=revBill.fsaleOrgUnitId left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
		if(toDate!=null){
			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(toDate)))+ "'}");
			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(orgUnit!=null){
			sb.append(" and org.fid in("+orgUnit+")");
		}
    	sb.append(" group by org.fid,org.fname_l2,org.flongNumber) t group by t.orgId,t.company,t.number order by t.number");
    	return sb.toString();
    }
}