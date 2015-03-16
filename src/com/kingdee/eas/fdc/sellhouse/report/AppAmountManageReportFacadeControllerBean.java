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

public class AppAmountManageReportFacadeControllerBean extends AbstractAppAmountManageReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.AppAmountManageReportFacadeControllerBean");
    
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
	    initColoum(header,col,"bpName",100,false);
	    
	    initColoum(header,col,"YQamount1",100,false);
	    initColoum(header,col,"YQaccount1",100,false);
	    
	    initColoum(header,col,"YQamount2",100,false);
	    initColoum(header,col,"YQaccount2",100,false);
	   
	    initColoum(header,col,"YQamount3",100,false);
	    initColoum(header,col,"YQaccount3",100,false);
	   
	    initColoum(header,col,"YQamount4",100,false);
	    initColoum(header,col,"YQaccount4",100,false);
	    
	    initColoum(header,col,"YQamount5",100,false);
	    initColoum(header,col,"YQaccount5",100,false);
	    
	    initColoum(header,col,"YQamount6",100,false);
	    initColoum(header,col,"YQaccount6",100,false);
	  
	    initColoum(header,col,"YQamount7",100,false);
	    initColoum(header,col,"YQaccount7",100,false);
	   
	    initColoum(header,col,"YQamount8",100,false);
	    initColoum(header,col,"YQaccount8",100,false);
	   
	    initColoum(header,col,"YQamount9",100,false);
	    initColoum(header,col,"YQaccount9",100,false);
	   
	    initColoum(header,col,"YQamount10",100,false);
	    initColoum(header,col,"YQaccount10",100,false);
	    
	    initColoum(header,col,"YQamount11",100,false);
	    initColoum(header,col,"YQaccount11",100,false);
	  
	    
	    initColoum(header,col,"NQamount1",100,false);
	    initColoum(header,col,"NQaccount1",100,false);
	    
	    initColoum(header,col,"NQamount2",100,false);
	    initColoum(header,col,"NQaccount2",100,false);
	   
	    initColoum(header,col,"NQamount3",100,false);
	    initColoum(header,col,"NQaccount3",100,false);
	   
	    initColoum(header,col,"NQamount4",100,false);
	    initColoum(header,col,"NQaccount4",100,false);
	    
	    initColoum(header,col,"NQamount5",100,false);
	    initColoum(header,col,"NQaccount5",100,false);
	    
	    initColoum(header,col,"NQamount6",100,false);
	    initColoum(header,col,"NQaccount6",100,false);
	  
	    initColoum(header,col,"NQamount7",100,false);
	    initColoum(header,col,"NQaccount7",100,false);
	   
	    initColoum(header,col,"NQamount8",100,false);
	    initColoum(header,col,"NQaccount8",100,false);
	   
	    initColoum(header,col,"NQamount9",100,false);
	    initColoum(header,col,"NQaccount9",100,false);
	   
	    initColoum(header,col,"NQamount10",100,false);
	    initColoum(header,col,"NQaccount10",100,false);
	    
	    initColoum(header,col,"NQamount11",100,false);
	    initColoum(header,col,"NQaccount11",100,false);
	    
	    initColoum(header,col,"amount1",100,false);
	    initColoum(header,col,"account1",100,false);
	    
	    initColoum(header,col,"amount2",100,false);
	    initColoum(header,col,"account2",100,false);
	   
	    initColoum(header,col,"amount3",100,false);
	    initColoum(header,col,"account3",100,false);
	   
	    initColoum(header,col,"amount4",100,false);
	    initColoum(header,col,"account4",100,false);
	    
	    initColoum(header,col,"amount5",100,false);
	    initColoum(header,col,"account5",100,false);
	    
	    initColoum(header,col,"amount6",100,false);
	    initColoum(header,col,"account6",100,false);
	  
	    initColoum(header,col,"amount7",100,false);
	    initColoum(header,col,"account7",100,false);
	   
	    initColoum(header,col,"amount8",100,false);
	    initColoum(header,col,"account8",100,false);
	   
	    initColoum(header,col,"amount9",100,false);
	    initColoum(header,col,"account9",100,false);
	   
	    initColoum(header,col,"amount10",100,false);
	    initColoum(header,col,"account10",100,false);
	    
	    initColoum(header,col,"amount11",100,false);
	    initColoum(header,col,"account11",100,false);
	    
	    initColoum(header,col,"amount12",100,false);
	    initColoum(header,col,"account12",100,false);
	    
	    initColoum(header,col,"amount13",100,false);
	    initColoum(header,col,"account13",100,false);
	    
	    header.setLabels(new Object[][]{
	    		{
	    			"orgUnitId","sellProjectId","公司","项目","类型"
	    			,"逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款","逾期应收款"
	    			,"未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款","未逾期应收款"
	    			,"合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计","合计"
	    		}
	    		,
	    		{
	    			"orgUnitId","sellProjectId","公司","项目","类型"
	    			,"分期付款逾期","分期付款逾期","分期付款逾期","分期付款逾期","按揭（草签）","按揭（草签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","合计","合计"	
	    			,"分期付款","分期付款","分期付款","分期付款","按揭（草签）","按揭（草签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","合计","合计"	
	    			,"分期付款","分期付款","分期付款","分期付款","分期付款","分期付款","分期付款","分期付款","按揭（草签）","按揭（草签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","按揭（网签）","合计","合计"	
	    		}
	    		,
	    		{
	    			"orgUnitId","sellProjectId","公司","项目","类型"
	    			,"客户原因逾期","客户原因逾期","工抵房逾期","工抵房逾期","按揭（草签）","按揭（草签）","首付未齐","首付未齐","首付齐，未签贷款合同","首付齐，未签贷款合同","已签贷款合同，资料未齐","已签贷款合同，资料未齐","资料齐，送审阶段","资料齐，送审阶段","审批通过，抵押中","审批通过，抵押中","等待放款","等待放款","小计","小计","合计","合计"	
	    			,"未逾期","未逾期","工抵房未逾期","工抵房未逾期","按揭（草签）","按揭（草签）","首付未齐","首付未齐","首付齐，未签贷款合同","首付齐，未签贷款合同","已签贷款合同，资料未齐","已签贷款合同，资料未齐","资料齐，送审阶段","资料齐，送审阶段","审批通过，抵押中","审批通过，抵押中","等待放款","等待放款","小计","小计","合计","合计"	
	    			,"客户原因逾期","客户原因逾期","未逾期","未逾期","工抵房逾期","工抵房逾期","工抵房未逾期","工抵房未逾期","按揭（草签）","按揭（草签）","首付未齐","首付未齐","首付齐，未签贷款合同","首付齐，未签贷款合同","已签贷款合同，资料未齐","已签贷款合同，资料未齐","资料齐，送审阶段","资料齐，送审阶段","审批通过，抵押中","审批通过，抵押中","等待放款","等待放款","小计","小计","合计","合计"	
	    		}
	    		,
	    		{
	    			"orgUnitId","sellProjectId","公司","项目","类型"
	    			,"套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额"	
	    			,"套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额"	
	    			,"套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额"	
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, ctx);
		params.setObject("rowset", rowSet);
		return params;
    }
    protected String getTypeSql(boolean isOver,String sellProjectId,Date fromSignDate,Date toSignDate,Date fromAppDate,Date toAppDate,Date fromActDate,Date toActDate){
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
		if(isOver){
			where.append(" and entry.fappDate < sysdate and (entry.fappAmount-entry.factRevAmount)>0");
		}else{
			where.append(" and (entry.fappDate >= sysdate or (entry.fappDate < sysdate and (entry.fappAmount-entry.factRevAmount)<=0))");
		}
		int i=0;
		if(!isOver){
			i=9;
		}
		
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.orgUnitLongNumber,t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.type,t.bpNumber,t.bpName,count(t.amount) amount,sum(t.account) account from(");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(++i)+"' type,cast(bp.fnumber AS varchar(100)) bpNumber,cast(bp.fname_l2 AS varchar(100)) bpName,sign.FTransactionID id,count(*) amount,sum(entry.fappAmount-entry.factRevAmount) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
        sb.append(" left join T_SHE_OverdueDescribe OD ON OD.FTransOviewId=entry.fid");
	    sb.append(" left join T_SHE_OverdueCause OS ON OD.FOverdueSortID = OS.FID");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit')");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=0 and OD.FCREATETIME = (select max(FCREATETIME) from T_SHE_OverdueDescribe od2 where od2.FTransOviewId = od.FTransOviewId) and os.fnumber='001' and md.fname_l2 in('首付分期款','分期款')");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,bp.fnumber,bp.fname_l2,sign.FTransactionID");
    	sb.append(" )t group by t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.orgUnitLongNumber,t.bpNumber,t.bpName,t.type");
    	
    	sb.append(" union all");
    	sb.append(" select t.orgUnitLongNumber,t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.type,t.bpNumber,t.bpName,count(t.amount) amount,sum(t.account) account from(");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(i)+"' type,'010' bpNumber,'小计' bpName,sign.FTransactionID id,count(*) amount,sum(entry.fappAmount-entry.factRevAmount) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
        sb.append(" left join T_SHE_OverdueDescribe OD ON OD.FTransOviewId=entry.fid");
	    sb.append(" left join T_SHE_OverdueCause OS ON OD.FOverdueSortID = OS.FID");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit')");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=0 and OD.FCREATETIME = (select max(FCREATETIME) from T_SHE_OverdueDescribe od2 where od2.FTransOviewId = od.FTransOviewId) and os.fnumber='001' and md.fname_l2 in('首付分期款','分期款')");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,sign.FTransactionID");
    	sb.append(" )t group by t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.orgUnitLongNumber,t.bpNumber,t.bpName,t.type");
    	
    	sb.append(" union all");
    	sb.append(" select t.orgUnitLongNumber,t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.type,t.bpNumber,t.bpName,count(t.amount) amount,sum(t.account) account from(");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(++i)+"' type,cast(bp.fnumber AS varchar(100)) bpNumber,cast(bp.fname_l2 AS varchar(100)) bpName,sign.FTransactionID id,count(*) amount,sum(entry.fappAmount-entry.factRevAmount) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit')");
    	sb.append(" and entry.fid is not null and sign.fisWorkRoom=1 and md.fname_l2 in('首付分期款','分期款')");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,bp.fnumber,bp.fname_l2,sign.FTransactionID");
    	sb.append(" )t group by t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.orgUnitLongNumber,t.bpNumber,t.bpName,t.type");
    	
    	
    	sb.append(" union all");
    	sb.append(" select t.orgUnitLongNumber,t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.type,t.bpNumber,t.bpName,count(t.amount) amount,sum(t.account) account from(");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(i)+"' type,'010' bpNumber,'小计' bpName,sign.FTransactionID id,count(*) amount,sum(entry.fappAmount-entry.factRevAmount) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit')");
    	sb.append(" and entry.fid is not null and sign.fisWorkRoom=1 and md.fname_l2 in('首付分期款','分期款')");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,sign.FTransactionID");
    	sb.append(" )t group by t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.orgUnitLongNumber,t.bpNumber,t.bpName,t.type");
    	
    	sb.append(" union all");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(++i)+"' type,cast(bp.fnumber AS varchar(100)) bpNumber,cast(bp.fname_l2 AS varchar(100)) bpName, count(*) amount,sum(entry.fappAmount-entry.factRevAmount) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit')");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=0 and md.fname_l2 in('按揭款','公积金')");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,bp.fnumber,bp.fname_l2");
    	
    	sb.append(" union all");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(i)+"' type,'010' bpNumber,'小计' bpName, count(*) amount,sum(entry.fappAmount-entry.factRevAmount) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit')");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=0 and md.fname_l2 in('按揭款','公积金')");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
    	
    	sb.append(" union all");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(++i)+"' type,cast(bp.fnumber AS varchar(100)) bpNumber,cast(bp.fname_l2 AS varchar(100)) bpName, count(*) amount,sum(factualLoanAmt) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit') and FAFMortgagedState=0 and md.fname_l2='首付款'");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=1 and entry2.fid is not null and entry2.factRevAmount!=entry2.fappAmount");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,bp.fnumber,bp.fname_l2");
    	
    	sb.append(" union all");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(i)+"' type,'010' bpNumber,'小计' bpName, count(*) amount,sum(factualLoanAmt) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit') and FAFMortgagedState=0 and md.fname_l2='首付款'");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=1 and entry2.fid is not null and entry2.factRevAmount!=entry2.fappAmount");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
    	
    	sb.append(" union all");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(++i)+"' type,cast(bp.fnumber AS varchar(100)) bpNumber,cast(bp.fname_l2 AS varchar(100)) bpName, count(*) amount,sum(factualLoanAmt) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit') and FAFMortgagedState=0 and md.fname_l2='首付款'");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=1 and entry2.fid is not null and entry2.fid is not null and entry2.factRevAmount=entry2.fappAmount");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,bp.fnumber,bp.fname_l2");
    	
    	sb.append(" union all");
    	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(i)+"' type,'010' bpNumber,'小计' bpName, count(*) amount,sum(factualLoanAmt) account from");
    	sb.append(" t_she_signManage sign left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit') and FAFMortgagedState=0 and md.fname_l2='首付款'");
    	sb.append(" and entry.fid is not null and sign.FIsOnRecord=1 and entry2.fid is not null and entry2.fid is not null and entry2.factRevAmount=entry2.fappAmount");
    	sb.append(where);
    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
    	
    	String[] approach=new String[]{"'已签贷款合同，资料未齐'","'资料齐，送审阶段'","'审批通过，抵押中'","'办理完成'"};
    	for(int k=0;k<approach.length;k++){
    		sb.append(" union all");
        	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
        	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(++i)+"' type,cast(bp.fnumber AS varchar(100)) bpNumber,cast(bp.fname_l2 AS varchar(100)) bpName, count(*) amount,sum(factualLoanAmt) account from");
        	sb.append(" t_she_signManage sign left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
        	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
        	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
        	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit') and FAFMortgagedState!=4 and FApproach="+approach[k]);
        	sb.append(" and entry.fid is not null and sign.FIsOnRecord=1");
        	sb.append(where);
        	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber,bp.fnumber,bp.fname_l2");
    	
        	sb.append(" union all");
        	sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
        	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,'"+(i)+"' type,'010' bpNumber,'小计' bpName, count(*) amount,sum(factualLoanAmt) account from");
        	sb.append(" t_she_signManage sign left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
        	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
        	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
        	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where sign.fbizState in('SignApple','SignAudit') and FAFMortgagedState!=4 and FApproach="+approach[k]);
        	sb.append(" and entry.fid is not null and sign.FIsOnRecord=1");
        	sb.append(where);
        	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
    	
    	}
    	
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
    	
    	sb.append(" /*dialect*/ select t.orgUnitId,t.sellProjectId,t.orgUnit,t.sellProject,t.bpName,max(case t.type when '1' then t.amount else 0 end) YQamount1,max(case t.type when '1' then t.account else 0 end) YQaccount1,");
    	sb.append(" max(case t.type when '2' then t.amount else 0 end) YQamount2,max(case t.type when '2' then t.account else 0 end) YQaccount2,");
    	sb.append(" max(case t.type when '3' then t.amount else 0 end) YQamount3,max(case t.type when '3' then t.account else 0 end) YQaccount3,");
    	sb.append(" max(case t.type when '4' then t.amount else 0 end) YQamount4,max(case t.type when '4' then t.account else 0 end) YQaccount4,");
    	sb.append(" max(case t.type when '5' then t.amount else 0 end) YQamount5,max(case t.type when '5' then t.account else 0 end) YQaccount5,");
    	sb.append(" max(case t.type when '6' then t.amount else 0 end) YQamount6,max(case t.type when '6' then t.account else 0 end) YQaccount6,");
    	sb.append(" max(case t.type when '7' then t.amount else 0 end) YQamount7,max(case t.type when '7' then t.account else 0 end) YQaccount7,");
    	sb.append(" max(case t.type when '8' then t.amount else 0 end) YQamount8,max(case t.type when '8' then t.account else 0 end) YQaccount8,");
    	sb.append(" max(case t.type when '9' then t.amount else 0 end) YQamount9,max(case t.type when '9' then t.account else 0 end) YQaccount9,");
    	
    	sb.append(" (max(case t.type when '4' then t.amount else 0 end)+max(case t.type when '5' then t.amount else 0 end)+max(case t.type when '6' then t.amount else 0 end)+max(case t.type when '7' then t.amount else 0 end)+max(case t.type when '8' then t.amount else 0 end)+max(case t.type when '9' then t.amount else 0 end)) YQamount10,");
    	sb.append(" (max(case t.type when '4' then t.account else 0 end)+max(case t.type when '5' then t.account else 0 end)+max(case t.type when '6' then t.account else 0 end)+max(case t.type when '7' then t.account else 0 end)+max(case t.type when '8' then t.account else 0 end)+max(case t.type when '9' then t.account else 0 end)) YQaccount10,");
    	sb.append(" (max(case t.type when '1' then t.amount else 0 end)+max(case t.type when '2' then t.amount else 0 end)+max(case t.type when '3' then t.amount else 0 end)+max(case t.type when '4' then t.amount else 0 end)+max(case t.type when '5' then t.amount else 0 end)+max(case t.type when '6' then t.amount else 0 end)+max(case t.type when '7' then t.amount else 0 end)+max(case t.type when '8' then t.amount else 0 end)+max(case t.type when '9' then t.amount else 0 end)) YQamount11,");
    	sb.append(" (max(case t.type when '1' then t.account else 0 end)+max(case t.type when '2' then t.account else 0 end)+max(case t.type when '3' then t.account else 0 end)+max(case t.type when '4' then t.account else 0 end)+max(case t.type when '5' then t.account else 0 end)+max(case t.type when '6' then t.account else 0 end)+max(case t.type when '7' then t.account else 0 end)+max(case t.type when '8' then t.account else 0 end)+max(case t.type when '9' then t.account else 0 end)) YQaccount11,");
    	
    	sb.append(" max(case t.type when '10' then t.amount else 0 end) NQamount1,max(case t.type when '10' then t.account else 0 end) NQaccount1,");
    	sb.append(" max(case t.type when '11' then t.amount else 0 end) NQamount2,max(case t.type when '11' then t.account else 0 end) NQaccount2,");
    	sb.append(" max(case t.type when '12' then t.amount else 0 end) NQamount3,max(case t.type when '12' then t.account else 0 end) NQaccount3,");
    	sb.append(" max(case t.type when '13' then t.amount else 0 end) NQamount4,max(case t.type when '13' then t.account else 0 end) NQaccount4,");
    	sb.append(" max(case t.type when '14' then t.amount else 0 end) NQamount5,max(case t.type when '14' then t.account else 0 end) NQaccount5,");
    	sb.append(" max(case t.type when '15' then t.amount else 0 end) NQamount6,max(case t.type when '15' then t.account else 0 end) NQaccount6,");
    	sb.append(" max(case t.type when '16' then t.amount else 0 end) NQamount4,max(case t.type when '16' then t.account else 0 end) NQaccount7,");
    	sb.append(" max(case t.type when '17' then t.amount else 0 end) NQamount5,max(case t.type when '17' then t.account else 0 end) NQaccount8,");
    	sb.append(" max(case t.type when '18' then t.amount else 0 end) NQamount6,max(case t.type when '18' then t.account else 0 end) NQaccount9,");
    	
    	sb.append(" (max(case t.type when '13' then t.amount else 0 end)+max(case t.type when '14' then t.amount else 0 end)+max(case t.type when '15' then t.amount else 0 end)+max(case t.type when '16' then t.amount else 0 end)+max(case t.type when '17' then t.amount else 0 end)+max(case t.type when '18' then t.amount else 0 end)) NQamount10,");
    	sb.append(" (max(case t.type when '13' then t.account else 0 end)+max(case t.type when '14' then t.account else 0 end)+max(case t.type when '15' then t.account else 0 end)+max(case t.type when '16' then t.account else 0 end)+max(case t.type when '17' then t.account else 0 end)+max(case t.type when '18' then t.account else 0 end)) NQaccount10,");
    	sb.append(" (max(case t.type when '10' then t.amount else 0 end)+max(case t.type when '11' then t.amount else 0 end)+max(case t.type when '12' then t.amount else 0 end)+max(case t.type when '13' then t.amount else 0 end)+max(case t.type when '14' then t.amount else 0 end)+max(case t.type when '15' then t.amount else 0 end)+max(case t.type when '16' then t.amount else 0 end)+max(case t.type when '17' then t.amount else 0 end)+max(case t.type when '18' then t.amount else 0 end)) NQamount11,");
    	sb.append(" (max(case t.type when '10' then t.account else 0 end)+max(case t.type when '11' then t.account else 0 end)+max(case t.type when '12' then t.account else 0 end)+max(case t.type when '13' then t.account else 0 end)+max(case t.type when '14' then t.account else 0 end)+max(case t.type when '15' then t.account else 0 end)+max(case t.type when '16' then t.account else 0 end)+max(case t.type when '17' then t.account else 0 end)+max(case t.type when '18' then t.account else 0 end)) NQaccount11,");
    	
    	sb.append(" max(case t.type when '1' then t.amount else 0 end) amount1,max(case t.type when '1' then t.account else 0 end) account1,");
    	sb.append(" max(case t.type when '10' then t.amount else 0 end) amount2,max(case t.type when '10' then t.account else 0 end) account2,");
    	sb.append(" max(case t.type when '2' then t.amount else 0 end) amount3,max(case t.type when '2' then t.account else 0 end) account3,");
    	sb.append(" max(case t.type when '11' then t.amount else 0 end) amount4,max(case t.type when '11' then t.account else 0 end) account4,");
    	
    	sb.append(" (max(case t.type when '3' then t.amount else 0 end)+max(case t.type when '12' then t.amount else 0 end)) amount5,(max(case t.type when '3' then t.account else 0 end)+max(case t.type when '12' then t.account else 0 end)) account5,");
    	sb.append(" (max(case t.type when '4' then t.amount else 0 end)+max(case t.type when '13' then t.amount else 0 end)) amount6,(max(case t.type when '4' then t.account else 0 end)+max(case t.type when '13' then t.account else 0 end)) account6,");
    	sb.append(" (max(case t.type when '5' then t.amount else 0 end)+max(case t.type when '14' then t.amount else 0 end)) amount7,(max(case t.type when '5' then t.account else 0 end)+max(case t.type when '14' then t.account else 0 end)) account7,");
    	sb.append(" (max(case t.type when '6' then t.amount else 0 end)+max(case t.type when '15' then t.amount else 0 end)) amount8,(max(case t.type when '6' then t.account else 0 end)+max(case t.type when '15' then t.account else 0 end)) account8,");
    	sb.append(" (max(case t.type when '7' then t.amount else 0 end)+max(case t.type when '16' then t.amount else 0 end)) amount9,(max(case t.type when '7' then t.account else 0 end)+max(case t.type when '16' then t.account else 0 end)) account9,");
    	sb.append(" (max(case t.type when '8' then t.amount else 0 end)+max(case t.type when '17' then t.amount else 0 end)) amount10,(max(case t.type when '8' then t.account else 0 end)+max(case t.type when '17' then t.account else 0 end)) account10,");
    	sb.append(" (max(case t.type when '9' then t.amount else 0 end)+max(case t.type when '18' then t.amount else 0 end)) amount11,(max(case t.type when '9' then t.account else 0 end)+max(case t.type when '18' then t.account else 0 end)) account11,");
    	
    	sb.append(" (max(case t.type when '4' then t.amount else 0 end)+max(case t.type when '5' then t.amount else 0 end)+max(case t.type when '6' then t.amount else 0 end)+max(case t.type when '7' then t.amount else 0 end)+max(case t.type when '8' then t.amount else 0 end)+max(case t.type when '9' then t.amount else 0 end)+max(case t.type when '13' then t.amount else 0 end)+max(case t.type when '14' then t.amount else 0 end)+max(case t.type when '15' then t.amount else 0 end)+max(case t.type when '16' then t.amount else 0 end)+max(case t.type when '17' then t.amount else 0 end)+max(case t.type when '18' then t.amount else 0 end)) amount12,");
    	sb.append(" (max(case t.type when '4' then t.account else 0 end)+max(case t.type when '5' then t.account else 0 end)+max(case t.type when '6' then t.account else 0 end)+max(case t.type when '7' then t.account else 0 end)+max(case t.type when '8' then t.account else 0 end)+max(case t.type when '9' then t.account else 0 end)+max(case t.type when '13' then t.account else 0 end)+max(case t.type when '14' then t.account else 0 end)+max(case t.type when '15' then t.account else 0 end)+max(case t.type when '16' then t.account else 0 end)+max(case t.type when '17' then t.account else 0 end)+max(case t.type when '18' then t.account else 0 end)) account12,");
    
    	sb.append(" sum(t.amount) amount13,sum(t.account) account13 from(");
    	
    	sb.append(getTypeSql(true,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql(false,sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" )t group by t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.orgUnitLongNumber,t.bpNumber,t.bpName");
    	sb.append(" order by t.orgUnitLongNumber,t.pspLongNumber,t.bpNumber");
    	return sb.toString();
    }
}