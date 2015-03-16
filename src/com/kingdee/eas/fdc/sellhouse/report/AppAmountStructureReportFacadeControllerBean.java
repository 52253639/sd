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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class AppAmountStructureReportFacadeControllerBean extends AbstractAppAmountStructureReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.AppAmountStructureReportFacadeControllerBean");
    
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
	    initColoum(header,col,"orgUnitId",100,true);
	    initColoum(header,col,"sellProjectId",100,true);
	    initColoum(header,col,"orgUnit",100,false);
	    initColoum(header,col,"sellProject",150,false);
	    
	    initColoum(header,col,"YQFQ",100,false);
	    initColoum(header,col,"YQSF",100,false);
	    initColoum(header,col,"YQZZAJ",100,false);
	    initColoum(header,col,"YQSYAJ",100,false);
	    initColoum(header,col,"YQGJJ",100,false);
	    initColoum(header,col,"YQSFFQ",100,false);
	    initColoum(header,col,"YQMJBC",100,false);
	    initColoum(header,col,"YQYCX",100,false);
	    initColoum(header,col,"YQXJ",100,false);
	    
	    initColoum(header,col,"NQFQ",100,false);
	    initColoum(header,col,"NQSF",100,false);
	    initColoum(header,col,"NQZZAJ",100,false);
	    initColoum(header,col,"NQSYAJ",100,false);
	    initColoum(header,col,"NQGJJ",100,false);
	    initColoum(header,col,"NQSFFQ",100,false);
	    initColoum(header,col,"NQMJBC",100,false);
	    initColoum(header,col,"NQYCX",100,false);
	    initColoum(header,col,"NQXJ",100,false);
	    
	    initColoum(header,col,"HJ",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"orgUnitId","sellProjectId","公司","项目"
	    			,"逾期","逾期","逾期","逾期","逾期","逾期","逾期","逾期","逾期"
	    			,"未逾期","未逾期","未逾期","未逾期","未逾期","未逾期","未逾期","未逾期","未逾期"
	    			,"合计"
	    		}
	    		,
	    		{
	    			"orgUnitId","sellProjectId","公司","项目"
	    			,"分期","首付","按揭","按揭","按揭","首付分期","面积补差","一次性","小计"
	    			,"分期","首付","按揭","按揭","按揭","首付分期","面积补差","一次性","小计"
	    			,"合计"
		    		
	    		}
	    		,
	    		{
	    			"orgUnitId","sellProjectId","公司","项目"
	    			,"分期","首付","住宅按揭","商业按揭","公积金","首付分期","面积补差","一次性","小计"
	    			,"分期","首付","住宅按揭","商业按揭","公积金","首付分期","面积补差","一次性","小计"
	    			,"合计"
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
    protected String getTypeSql(boolean isOver,String type,int loanType,String sellProjectId,Date fromSignDate,Date toSignDate,Date fromAppDate,Date toAppDate,Date fromActDate,Date toActDate){
    	StringBuffer where=new StringBuffer();
    	if(sellProjectId!=null&&!"".equals(sellProjectId)){
    		where.append(" and sp.fid in ("+sellProjectId+")");
		}else{
			where.append(" and sp.fid in ('null')");
		}
    	if(fromSignDate!=null){
    		where.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromSignDate))+ "'}");
		}
		if(toSignDate!=null){
			where.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toSignDate))+ "'}");
		}
		if(fromAppDate!=null){
			where.append(" and entry.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromAppDate))+ "'}");
		}
		if(toAppDate!=null){
			where.append(" and entry.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toAppDate))+ "'}");
		}
		if(fromActDate!=null){
			where.append(" and sherevbill.revDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromActDate))+ "'}");
		}
		if(toActDate!=null){
			where.append(" and sherevbill.revDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toActDate))+ "'}");
		}
		if(loanType==1){
			where.append(" and bp.fnumber in('001','005')");
		}else if(loanType==2){
			where.append(" and bp.fnumber in('002','003','007')");
		}
		if(isOver){
			where.append(" and datediff(day,entry.fappDate,now())>0 and (entry.fappAmount-entry.factRevAmount)>0");
		}else{
			where.append(" and (datediff(day,entry.fappDate,now())<=0 or (datediff(day,entry.fappDate,now())>0 and (entry.fappAmount-entry.factRevAmount)<=0))");
		}
		if(type.equals("'1'")||type.equals("'9'")){
			where.append(" and md.fname_l2='分期款'");
		}else if(type.equals("'2'")||type.equals("'10'")){
			where.append(" and md.fname_l2='首付款'");
		}else if(type.equals("'3'")||type.equals("'11'")){
			where.append(" and md.fname_l2='按揭款'");
		}else if(type.equals("'4'")||type.equals("'12'")){
			where.append(" and md.fname_l2='按揭款'");
		}else if(type.equals("'5'")||type.equals("'13'")){
			where.append(" and md.fname_l2='公积金'");
		}else if(type.equals("'6'")||type.equals("'14'")){
			where.append(" and md.fname_l2='首付分期款'");
		}else if(type.equals("'7'")||type.equals("'15'")){
			where.append(" and md.fname_l2='面积补差款'");
		}else if(type.equals("'8'")||type.equals("'16'")){
			where.append(" and md.fname_l2='一次性款'");
		}
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,sum(entry.fappAmount-entry.factRevAmount) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit')");
    	sb.append(" and entry.fid is not null");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
    	
    	return sb.toString();
    }
    protected String getSql(Context ctx,RptParams params){
    	StringBuffer sb=new StringBuffer();
    	String sellProjectId = (String) params.getObject("sellProject");
    	Date fromSignDate=(Date)params.getObject("fromSignDate");
    	Date toSignDate=(Date)params.getObject("toSignDate");
    	Date fromAppDate=(Date)params.getObject("fromAppDate");
    	Date toAppDate=(Date)params.getObject("toAppDate");
    	Date fromActDate=(Date)params.getObject("fromActDate");
    	Date toActDate=(Date)params.getObject("toActDate");
    	
    	sb.append(" select t.orgUnitId,t.sellProjectId,t.orgUnit,t.sellProject,");
    	sb.append(" max(case t.type when '1' then t.account else 0 end) YQFQ,max(case t.type when '2' then t.account else 0 end) YQSF,");
    	sb.append(" max(case t.type when '3' then t.account else 0 end) YQZZAJ,max(case t.type when '4' then t.account else 0 end) YQSYAJ,");
    	sb.append(" max(case t.type when '5' then t.account else 0 end) YQGJJ,max(case t.type when '6' then t.account else 0 end) YQSFFQ,");
    	sb.append(" max(case t.type when '7' then t.account else 0 end) YQMJBC,max(case t.type when '8' then t.account else 0 end) YQYCX,");

    	sb.append(" (max(case t.type when '1' then t.account else 0 end)+max(case t.type when '2' then t.account else 0 end)+max(case t.type when '3' then t.account else 0 end)+max(case t.type when '4' then t.account else 0 end)+");
    	sb.append(" max(case t.type when '5' then t.account else 0 end)+max(case t.type when '6' then t.account else 0 end)+max(case t.type when '7' then t.account else 0 end)+max(case t.type when '8' then t.account else 0 end)) YQXJ,");
    	
    	sb.append(" max(case t.type when '9' then t.account else 0 end) NQFQ,max(case t.type when '10' then t.account else 0 end) NQSF,");
    	sb.append(" max(case t.type when '11' then t.account else 0 end) NQZZAJ,max(case t.type when '12' then t.account else 0 end) NQSYAJ,");
    	sb.append(" max(case t.type when '13' then t.account else 0 end) NQGJJ,max(case t.type when '14' then t.account else 0 end) NQSFFQ,");
    	sb.append(" max(case t.type when '15' then t.account else 0 end) NQMJBC,max(case t.type when '16' then t.account else 0 end) NQYCX,");

    	sb.append(" (max(case t.type when '9' then t.account else 0 end)+max(case t.type when '10' then t.account else 0 end)+max(case t.type when '11' then t.account else 0 end)+max(case t.type when '12' then t.account else 0 end)+");
    	sb.append(" max(case t.type when '13' then t.account else 0 end)+max(case t.type when '14' then t.account else 0 end)+max(case t.type when '15' then t.account else 0 end)+max(case t.type when '16' then t.account else 0 end)) NQXJ,");
    	sb.append(" sum(t.account) HJ from(");
    	
    	sb.append(getTypeSql(true,"'1'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(true,"'2'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(true,"'3'",1,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(true,"'4'",2,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(true,"'5'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(true,"'6'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(true,"'7'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(true,"'8'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'9'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'10'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'11'",1,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'12'",2,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'13'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'14'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'15'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,"'16'",0,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" )t group by t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.orgUnitLongNumber");
    	sb.append(" order by t.orgUnitLongNumber,t.pspLongNumber");
    	return sb.toString();
    }
}