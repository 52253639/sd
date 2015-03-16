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

public class RoomLoanDetailReportFacadeControllerBean extends AbstractRoomLoanDetailReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.RoomLoanDetailReportFacadeControllerBean");
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
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"room",250,false);
	    initColoum(header,col,"customer",180,false);
	    initColoum(header,col,"number",180,false);
	    initColoum(header,col,"approach",180,false);
	    initColoum(header,col,"account",120,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"id","房间","客户","按揭编号","当前阶段","金额"
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
    protected String getSql(Context ctx,RptParams params){
    	StringBuffer sb=new StringBuffer();
    	String sellProjectId = (String) params.getObject("sellProject");
    	String approach=(String)params.getObject("approach");
    	Date fromSignDate=(Date)params.getObject("fromSignDate");
    	Date toSignDate=(Date)params.getObject("toSignDate");
    	Date fromAppDate=(Date)params.getObject("fromAppDate");
    	Date toAppDate=(Date)params.getObject("toAppDate");
    	Date fromActDate=(Date)params.getObject("fromActDate");
    	Date toActDate=(Date)params.getObject("toActDate");
    	String typeNumber=(String)params.getObject("typeNumber");
    	String type=(String)params.getObject("type");
    	
    	sb.append(" select fid id,room.fname_l2 room,sign.fcustomerNames customer,loan.fnumber number,loan.fapproach approach,loan.factualLoanAmt account from T_SHE_RoomLoan loan ");
    	sb.append(" left join t_she_signManage sign on sign.fid =loan.fsignId left join T_SHE_TranBusinessOverView entry on entry.fheadid=sign.FTransactionID and loan.FMmType=entry.fmoneyDefineid left join T_ORG_BaseUnit orgUnit on  orgUnit.fid =sign.forgUnitid");
    	sb.append(" left join (select revmap.FPayListEntryId,max(revbill.fbizDate) as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid group by revmap.FPayListEntryId) sherevbill on sherevbill.FPayListEntryId=entry.fid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join T_SHE_BuildingProperty bp on bp.fid=FBuildingPropertyID");
    	if(approach!=null&&approach.indexOf("首付")>=0){
    		sb.append(" left join T_SHE_TranBusinessOverView entry2 on entry2.fheadid=sign.FTransactionID left join T_SHE_MoneyDefine md on md.fid=entry2.fmoneyDefineId");
    	}
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId left join  t_she_sellproject psp on  psp.fid =sp.fparentid where 1=1");
    	sb.append(" and sign.fbizState in('SignApple','SignAudit') and entry.fid is not null");
    	if(sellProjectId!=null){
    		sb.append(" and (psp.fid = '"+sellProjectId+"' or sp.fid= '"+sellProjectId+"')");
		}
    	if(approach!=null){
    		if(approach.equals("首付未齐")){
    			sb.append(" and entry2.fid is not null and entry2.factRevAmount!=entry2.fappAmount and loan.FAFMortgagedState=0 and md.fname_l2='首付款'");
    		}else if(approach.equals("首付齐，未签贷款合同")){
    			sb.append(" and entry2.fid is not null and entry2.factRevAmount=entry2.fappAmount and loan.FAFMortgagedState=0 and md.fname_l2='首付款'");
    		}else if(approach.equals("等待放款")){
    			sb.append(" and loan.fapproach in('已签贷款合同，资料未齐','资料齐，送审阶段','审批通过，抵押中','办理完成') and loan.FApproach = '办理完成' and loan.FAFMortgagedState!=4");
    		}else{
    			sb.append(" and loan.fapproach in('已签贷款合同，资料未齐','资料齐，送审阶段','审批通过，抵押中','办理完成') and loan.FApproach = '"+approach+"' and loan.FAFMortgagedState!=4");
    		}
		}
    	if(fromSignDate!=null){
    		sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromSignDate))+ "'}");
		}
		if(toSignDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toSignDate))+ "'}");
		}
		if(fromAppDate!=null){
			sb.append(" and entry.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromAppDate))+ "'}");
		}
		if(toAppDate!=null){
			sb.append(" and entry.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toAppDate))+ "'}");
		}
		if(fromActDate!=null){
			sb.append(" and sherevbill.revDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromActDate))+ "'}");
		}
		if(toActDate!=null){
			sb.append(" and sherevbill.revDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toActDate))+ "'}");
		}
		if(typeNumber!=null){
			if(typeNumber.equals("1")){
				sb.append(" and bp.fnumber in('001','005')");
			}else if(typeNumber.equals("2")){
				sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is not null and datediff(day,psp.fendDate,sign.FBusAdscriptionDate)<=30");
			}else if(typeNumber.equals("3")){
				sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is not null and datediff(day,psp.fendDate,sign.FBusAdscriptionDate)>30");
			}else if(typeNumber.equals("4")){
				sb.append(" and bp.fnumber in('002','003','007') and psp.fendDate is null");
			}else{
				sb.append(" and bp.fnumber in('001','002','003','005','007')");
			}
		}
		if(type!=null){
			if(type.equals("逾期")){
				sb.append(" and datediff(day,entry.fappDate,now())>0 and (entry.fappAmount-entry.factRevAmount)>0");
			}else if(type.equals("未逾期")){
				sb.append(" and (datediff(day,entry.fappDate,now())<=0 or (datediff(day,entry.fappDate,now())>0 and (entry.fappAmount-entry.factRevAmount)<=0))");
			}
		}
    	sb.append(" order by loan.fnumber");
    	return sb.toString();
    }
    			
    			
}