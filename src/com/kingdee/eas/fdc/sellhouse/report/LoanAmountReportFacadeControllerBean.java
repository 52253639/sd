package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
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

public class LoanAmountReportFacadeControllerBean extends AbstractLoanAmountReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.LoanAmountReportFacadeControllerBean");
    
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
	    initColoum(header,col,"sellProjectName",180,false);
	    initColoum(header,col,"amount",180,false);
	    initColoum(header,col,"revAmount",180,false);
	    initColoum(header,col,"stockAmount",180,false);
	    initColoum(header,col,"rate",180,false);
	    initColoum(header,col,"revRate",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"id","项目","本期新增报送银行按揭金额","本期新增银行按揭回款","前期存量银行按揭回款","本期新增回款比率","	回款比率"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String sellProject = (String) params.getObject("sellProject");
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	StringBuffer sellProjectWhere= new StringBuffer();
		if(sellProject!=null&&!"".equals(sellProject)){
			sellProjectWhere.append(" and sign.fsellProjectId in ("+sellProject+")");
		}else{
			sellProjectWhere.append(" and sign.fsellProjectId in ('null')");
		}
		StringBuffer dateWhere= new StringBuffer();
    	if(fromDate!=null){
    		dateWhere.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			dateWhere.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		
		StringBuffer revDateWhere= new StringBuffer();
		if(fromDate!=null){
			revDateWhere.append(" and sherevbill.revDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			revDateWhere.append(" and sherevbill.revDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select t.sellProjectId,sp.fname_l2 sellProjectName,max(case t.name when 'amount' then isnull(t.amount,0) else 0 end) amount,max(case t.name when 'revAmount' then isnull(t.amount,0) else 0 end) revAmount,max(case t.name when 'stockAmount' then isnull(t.amount,0) else 0 end) stockAmount,(case when max(case t.name when 'amount' then isnull(t.amount,0) else 0 end)=0 then 0 else max(case t.name when 'revAmount' then isnull(t.amount,0) else 0 end)/max(case t.name when 'amount' then isnull(t.amount,0) else 0 end) end)*100 rate,(case when max(case t.name when 'amount' then isnull(t.amount,0) else 0 end)=0 then 0 else (max(case t.name when 'revAmount' then isnull(t.amount,0) else 0 end)+max(case t.name when 'stockAmount' then isnull(t.amount,0) else 0 end))/max(case t.name when 'amount' then isnull(t.amount,0) else 0 end) end)*100 revRate");
    	sb.append(" from (select 'amount' name,sum(fappAmount) amount,sign.fsellProjectId sellProjectId from t_she_signPayListEntry entry left join t_she_signManage sign on entry.fheadId=sign.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
    	sb.append(" where sign.fbizState in('SignApple','SignAudit') and md.fmoneyType ='LoanAmount'");
    	sb.append(sellProjectWhere);
    	sb.append(dateWhere);
    	sb.append("group by sign.fsellProjectId");
    	sb.append(" union all select 'revAmount' name,sum(sherevbill.actRevAmount) amount,sign.fsellProjectId sellProjectId from t_she_tranBusinessOverView entry left join t_she_transaction base on entry.fheadid=base.fid left join t_she_signManage sign on base.fbillId=sign.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
    	sb.append(" left join (select revmap.famount as actRevAmount,revmap.FPayListEntryId,revbill.fbizDate as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" where entry.ftype='Pay' and md.fmoneyType ='LoanAmount' and sign.fbizState in('SignApple','SignAudit')");
    	sb.append(sellProjectWhere);
    	sb.append(dateWhere);
    	sb.append(revDateWhere);
    	sb.append("group by sign.fsellProjectId");
    	sb.append(" union all select 'stockAmount' name,sum(sherevbill.actRevAmount) amount,sign.fsellProjectId sellProjectId from t_she_tranBusinessOverView entry left join t_she_transaction base on entry.fheadid=base.fid left join t_she_signManage sign on base.fbillId=sign.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
    	sb.append(" left join (select revmap.famount as actRevAmount,revmap.FPayListEntryId,revbill.fbizDate as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" where entry.ftype='Pay' and md.fmoneyType ='LoanAmount' and sign.fbizState in('SignApple','SignAudit')");
    	sb.append(sellProjectWhere);
    	if(fromDate!=null){
    		sb.append(" and sign.fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
    	sb.append(revDateWhere);
    	sb.append("group by sign.fsellProjectId)t left join t_she_sellProject sp on sp.fid=t.sellProjectId group by t.sellProjectId,sp.fname_l2");
    	
    	RptRowSet rowSet = executeQuery(sb.toString(), null,ctx);
    	params.setObject("rowset", rowSet);
		return params;
    }

    
}