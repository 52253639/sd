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
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class RoomLoanReportFacadeControllerBean extends AbstractRoomLoanReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.RoomLoanReportFacadeControllerBean");
    
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
	    initColoum(header,col,"phone",100,false);
	    initColoum(header,col,"moneyDefine",100,false);
	    initColoum(header,col,"contractTotalAmount",100,false);
	    initColoum(header,col,"scheme",100,false);
	    initColoum(header,col,"loanAmount",100,false);
	    initColoum(header,col,"loanBank",100,false);
	    initColoum(header,col,"year",100,false);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"actDate",100,false);
	    initColoum(header,col,"state",100,false);
	    initColoum(header,col,"onLoanAmount",100,false);
	    initColoum(header,col,"approach1",100,false);
	    initColoum(header,col,"approach2",100,false);
	    initColoum(header,col,"approach3",100,false);
	    initColoum(header,col,"approach4",100,false);
	    initColoum(header,col,"approach5",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"id","房间","客户","联系电话","按揭/公积金款项","合同金额","按揭/公积金方案	","按揭/公积金金额","按揭银行","按揭年限","按揭/公积金编码","申请办理日期","承诺完成日期","办理状态","在途金额","当前进程","当前进程","当前进程","当前进程","当前进程"			

	    		}
	    		,
	    		{
	    			"id","房间","客户","联系电话","按揭/公积金款项","合同金额","按揭/公积金方案	","按揭/公积金金额","按揭银行","按揭年限","按揭/公积金编码","申请办理日期","承诺完成日期","办理状态","在途金额","签署购房合同","按揭资料准备完毕","银行审批完成","银行预抵（抵押）完成","收到银行按揭款"			

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
    	BuildingUnitInfo buildUnit = (BuildingUnitInfo)params.getObject("buildUnit");
    	BuildingInfo building = (BuildingInfo)params.getObject("building");
    	String sellProject = (String)params.getObject("sellProject");
    	
    	
    	sb.append(" select loan.fid id,room.fname_l2 room,sign.fcustomerNames customer,sign.fcustomerPhone phone,md.fname_l2 moneyDefine,sign.fcontractTotalAmount contractTotalAmount,scheme.fname_l2 scheme,loan.factualLoanAmt loanAmount,bank.fname_l2 loanBank,loan.fLoanFixedYear year,loan.fnumber number,loan.fapplicationDate bizDate,loan.fpromiseDate actDate,loan.FAFMortgagedState state,onload.famount onloadAmount,approach1.actdate approach1,approach2.actdate approach2,approach3.actdate approach3,approach4.actdate approach4,approach5.actdate approach5 from T_SHE_RoomLoan loan ");
    	sb.append(" left join t_she_signManage sign on sign.fid =loan.fsignId left join T_SHE_AFMortgaged scheme on scheme.fid=loan.forsoMortgagedid");
    	sb.append(" left join t_she_room room on room.fid =sign.froomId left join t_she_moneyDefine md on md.fid=loan.FMmType left join T_BD_Bank bank on bank.fid=loan.fBankId");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='签署购房合同')approach1 on approach1.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='按揭资料准备完成')approach2 on approach2.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='银行审批完成')approach3 on approach3.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='银行预抵（抵押）')approach4 on approach4.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='收到银行按揭款')approach5 on approach5.fparentid=loan.fid");
    	sb.append(" left join (select sum(bo.fappAmount-isnull(act.factRevAmount,0)) famount,bo.fheadId fid from T_SHE_SignPayListEntry bo ");
		sb.append(" left join t_she_moneyDefine moneyDefine on bo.fmoneyDefineid=moneyDefine.fid ");
    	sb.append(" left join t_she_TranBusinessOverView act on bo.ftanPayListEntryId=act.fid where moneyDefine.fmoneyType in('LoanAmount','AccFundAmount') group by bo.fheadId)onload on onload.fid=sign.fid");
    	sb.append(" left join t_she_sellproject sp on sp.fid =sign.fsellProjectId where sign.fid is not null and loan.FAFMortgagedState!=4");
    	if(buildUnit!=null){
    		sb.append(" and room.FBuildUnitID = '"+buildUnit.getId()+"' ");
    	}
    	if(building!=null){
    		sb.append(" and room.FBuildingID = '"+building.getId()+"' ");
    	}
    	if(sellProject!=null){
    		sb.append(" and sp.fid in ("+sellProject+") ");
    	}else{
    		sb.append(" and sp.fid in ('null') ");
    	}
    	sb.append(" order by loan.fnumber");
    	return sb.toString();
    }
}