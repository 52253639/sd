package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class BackAmountCycleReportFacadeControllerBean extends AbstractBackAmountCycleReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.BackAmountCycleReportFacadeControllerBean");
    
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
	    initColoum(header,col,"typeNumber",100,true);
	    initColoum(header,col,"orgUnit",100,false);
	    initColoum(header,col,"sellProject",150,false);
	    initColoum(header,col,"typeName",180,false);
	    initColoum(header,col,"amount1",80,false);
	    initColoum(header,col,"account1",100,false);
	    initColoum(header,col,"amount2",80,false);
	    initColoum(header,col,"account2",100,false);
	    initColoum(header,col,"amount3",80,false);
	    initColoum(header,col,"account3",100,false);
	    initColoum(header,col,"amount4",80,false);
	    initColoum(header,col,"account4",100,false);
	    initColoum(header,col,"amount5",80,false);
	    initColoum(header,col,"account5",100,false);
	    initColoum(header,col,"amount6",80,false);
	    initColoum(header,col,"account6",100,false);
	    initColoum(header,col,"amount7",80,false);
	    initColoum(header,col,"account7",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"orgUnitId","sellProjectId","typeNumber","公司","项目","类型"
	    			,"所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段","所属阶段"
	    		}
	    		,
	    		{
	    			"orgUnitId","sellProjectId","typeNumber","公司","项目","类型"
	    			,"首付未齐","首付未齐","首付齐，未签贷款合同","首付齐，未签贷款合同","已签贷款合同，资料未齐","已签贷款合同，资料未齐","资料齐，送审阶段","资料齐，送审阶段","审批通过，抵押中","审批通过，抵押中","等待放款","等待放款","合计","合计"
	    		}
	    		,
	    		{
	    			"orgUnitId","sellProjectId","typeNumber","公司","项目","类型"
	    			,"套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额","套数","金额"
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
    protected String getTypeSql(String type,String approach,String sellProjectId,Date fromSignDate,Date toSignDate,Date fromAppDate,Date toAppDate,Date fromActDate,Date toActDate){
    	StringBuffer where=new StringBuffer();
    	where.append(" and sign.fbizState in('SignApple','SignAudit')");
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
		if(approach.equals("'首付未齐'")){
			where.append(" and entry2.fid is not null and entry2.factRevAmount!=entry2.fappAmount");
		}else if(approach.equals("'首付齐，未签贷款合同'")){
			where.append(" and entry2.fid is not null and entry2.factRevAmount=entry2.fappAmount");
		}
		StringBuffer sb=new StringBuffer();
		if(approach.indexOf("首付")>=0){
			sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '住宅类' typeName,'1' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from t_she_signManage sign");
	    	sb.append(" left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState=0 and md.fname_l2='首付款'");
	    	sb.append(" and bp.fnumber in('001','005')");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '商业类(签约在竣工备案前30天)' typeName,'2' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from t_she_signManage sign");
	    	sb.append(" left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState=0 and md.fname_l2='首付款'");
	    	sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is not null and datediff(day,psp.fendDate,sign.FBusAdscriptionDate)<=30");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '商业类(签约晚于竣工备案30天)' typeName,'3' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from t_she_signManage sign");
	    	sb.append(" left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState=0 and md.fname_l2='首付款'");
	    	sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is not null and datediff(day,psp.fendDate,sign.FBusAdscriptionDate)>30");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '商业类(可分部分项证明取得后放款)' typeName,'4' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from t_she_signManage sign");
	    	sb.append(" left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState=0 and md.fname_l2='首付款'");
	    	sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is null");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '小计' typeName,'5' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from t_she_signManage sign");
	    	sb.append(" left join T_SHE_RoomLoan loan on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState=0 and md.fname_l2='首付款'");
	    	sb.append(" and bp.fnumber in('001','002','003','005','007')");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
		}else{
			sb.append(" select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '住宅类' typeName,'1' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from T_SHE_RoomLoan loan");
	    	sb.append(" left join t_she_signManage sign on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState!=4 and FApproach="+approach);
	    	sb.append(" and bp.fnumber in('001','005')");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '商业类(签约在竣工备案前30天)' typeName,'2' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from T_SHE_RoomLoan loan");
	    	sb.append(" left join t_she_signManage sign on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState!=4 and FApproach="+approach);
	    	sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is not null and datediff(day,psp.fendDate,sign.FBusAdscriptionDate)<=30");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '商业类(签约晚于竣工备案30天)' typeName,'3' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from T_SHE_RoomLoan loan");
	    	sb.append(" left join t_she_signManage sign on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState!=4 and FApproach="+approach);
	    	sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is not null and datediff(day,psp.fendDate,sign.FBusAdscriptionDate)>30");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '商业类(可分部分项证明取得后放款)' typeName,'4' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from T_SHE_RoomLoan loan");
	    	sb.append(" left join t_she_signManage sign on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState!=4 and FApproach="+approach);
	    	sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is null");
	    	sb.append(" and entry.fid is not null");
	    	sb.append(where);
	    	sb.append(" group by orgUnit.fid,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end,case when psp.fid is not null then psp.fid else sp.fid end,orgUnit.fdisplayName_l2,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end,orgUnit.flongNumber");
	    	
	    	sb.append(" union all select orgUnit.flongNumber orgUnitLongNumber,orgUnit.fid orgUnitId,case when psp.flongNumber is not null then psp.flongNumber else sp.flongNumber end pspLongNumber,case when psp.fid is not null then psp.fid else sp.fid end sellProjectId,orgUnit.fdisplayName_l2 orgUnit,");
	    	sb.append(" '小计' typeName,'5' typeNumber,");
	    	sb.append(" case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProject,"+type+" type,count(*) amount,sum(factualLoanAmt) account from T_SHE_RoomLoan loan");
	    	sb.append(" left join t_she_signManage sign on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
	    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId ) sherevbill on sherevbill.FPayListEntryId=entry.fid");
	    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
	    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where FAFMortgagedState!=4 and FApproach="+approach);
	    	sb.append(" and bp.fnumber in('001','002','003','005','007')");
	    	sb.append(" and entry.fid is not null");
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
    	
    	sb.append(" select t.orgUnitId,t.sellProjectId,t.typeNumber,t.orgUnit,t.sellProject,t.typeName,max(case t.type when '1' then t.amount else 0 end) amount1,max(case t.type when '1' then t.account else 0 end) account1,");
    	sb.append(" max(case t.type when '2' then t.amount else 0 end) amount2,max(case t.type when '2' then t.account else 0 end) account2,");
    	sb.append(" max(case t.type when '3' then t.amount else 0 end) amount3,max(case t.type when '3' then t.account else 0 end) account3,");
    	sb.append(" max(case t.type when '4' then t.amount else 0 end) amount4,max(case t.type when '4' then t.account else 0 end) account4,");
    	sb.append(" max(case t.type when '5' then t.amount else 0 end) amount5,max(case t.type when '5' then t.account else 0 end) account5,");
    	sb.append(" max(case t.type when '6' then t.amount else 0 end) amount6,max(case t.type when '6' then t.account else 0 end) account6,");
    	sb.append(" sum(t.amount) amount6,sum(t.account) account6 from(");

    	sb.append(getTypeSql("'1'","'首付未齐'",sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql("'2'","'首付齐，未签贷款合同'",sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql("'3'","'已签贷款合同，资料未齐'",sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql("'4'","'资料齐，送审阶段'",sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql("'5'","'审批通过，抵押中'",sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" union all");
    	sb.append(getTypeSql("'6'","'办理完成'",sellProjectId,fromSignDate,toSignDate,fromAppDate,toAppDate,fromActDate,toActDate));
    	sb.append(" )t group by t.orgUnitId,t.pspLongNumber,t.sellProjectId,t.orgUnit,t.sellProject,t.typeName,t.typeNumber,t.orgUnitLongNumber");
    	sb.append(" order by t.orgUnitLongNumber,t.pspLongNumber,t.typeNumber");
    	return sb.toString();
    }
}