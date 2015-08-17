package com.kingdee.eas.fdc.tenancy.app;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class TenancyAllReportFacadeControllerBean extends AbstractTenancyAllReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyAllReportFacadeControllerBean");
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
	    initColoum(header,col,"mdNumber",100,true);
	    initColoum(header,col,"mdId",100,true);
	    initColoum(header,col,"conId",100,true);
	    initColoum(header,col,"sellProject",100,false);
	    initColoum(header,col,"build",50,false);
	    initColoum(header,col,"room",270,false);
	    initColoum(header,col,"buildArea",75,false);
	    initColoum(header,col,"tenancyArea",75,false);
	    initColoum(header,col,"conNumber",100,false);
	    initColoum(header,col,"conName",200,false);
	    initColoum(header,col,"customer",200,false);
	    initColoum(header,col,"startDate",75,false);
	    initColoum(header,col,"endDate",75,false);
	    initColoum(header,col,"freeDays",80,false);
	    initColoum(header,col,"dealTotal",80,false);
	    initColoum(header,col,"dealPrice",80,false);
	    initColoum(header,col,"roomPrice",80,false);
	    initColoum(header,col,"moneyDefine",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"mdNumber","mdId","conId","房间信息","房间信息","房间信息","房间信息","房间信息","合同信息","合同信息","合同信息","合同信息","合同信息","合同信息","租金信息","租金信息","租金信息","款项类别"
	    		}
	    		,
	    		{
	    			"mdNumber","mdId","conId","项目分期","楼栋","房间","建筑面积","租赁面积","合同编码","合同名称","客户名称","合同起始日","合同终止日","免租期（按日）","租金总额","租金（按月）","租金单价（每月元/平米）","款项类别"
		    	}
	    },true);
	    params.setObject("rentHeader", header);
	    
	    header = new RptTableHeader();
	    initColoum(header,col,"sellProject",100,false);
	    initColoum(header,col,"build",50,false);
	    initColoum(header,col,"room",270,false);
	    initColoum(header,col,"buildArea",75,false);
	    initColoum(header,col,"tenancyArea",75,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"房间信息","房间信息","房间信息","房间信息","房间信息"
	    		}
	    		,
	    		{
	    			"项目分期","楼栋","房间","建筑面积","租赁面积"
		    	}
	    },true);
	    params.setObject("unRentHeader", header);
	    
	    header = new RptTableHeader();
	    initColoum(header,col,"sellProject",100,false);
	    initColoum(header,col,"build",50,false);
	    initColoum(header,col,"room",270,false);
	    initColoum(header,col,"buildArea",75,false);
	    initColoum(header,col,"isDo",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"房间信息","房间信息","房间信息","房间信息","房间信息"
	    		}
	    		,
	    		{
	    			"项目分期","楼栋","房间","建筑面积","两证是否办理"
		    	}
	    },true);
	    params.setObject("sellHeader", header);
	    
	    header = new RptTableHeader();
	    initColoum(header,col,"sellProject",100,false);
	    initColoum(header,col,"build",50,false);
	    initColoum(header,col,"room",270,false);
	    initColoum(header,col,"buildArea",75,false);
	    initColoum(header,col,"reason",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"房间信息","房间信息","房间信息","房间信息","房间信息"
	    		}
	    		,
	    		{
	    			"项目分期","楼栋","房间","建筑面积","原因或现有用途"
		    	}
	    },true);
	    params.setObject("unToRentHeader", header);
	    
	    header = new RptTableHeader();
	    initColoum(header,col,"type",100,false);
	    initColoum(header,col,"value",250,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			null,null
	    		}
	    },true);
	    params.setObject("mainHeader", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String building = (String) params.getObject("building");
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select * from (select distinct t.mdNumber,t.mdId,t.conId,t.sellProject,t.build,t.room,t.buildArea,t.tenancyArea,");
    	sb.append(" t.conNumber,t.conName,t.customer,t.startDate,t.endDate,t.freeDays,t.dealTotal,");
    	sb.append(" t.dealPrice,t.roomPrice,t.moneyDefine from ("); 
    	sb.append(" select md.fnumber mdNumber,md.fid mdId,con.fid conId,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate) freeDays,con.fdealTotalRent dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenancyRoomPayListEntry pay on pay.ftenRoomId=roomEntry.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" where md.fid is not null");
//    	if(isAll){
//    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
//    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
//    	}
    	if(building!=null&&!"".equals(building)){
    		sb.append(" and room.fbuildingid in("+building+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	sb.append(" union all select md.fnumber mdNumber,md.fid mdId,con.fid conId,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate) freeDays,con.fdealTotalRent dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenBillOtherPay pay on pay.fheadId=con.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" where md.fid is not null");
//    	if(isAll){
//    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
//    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
//    	}
		if(building!=null&&!"".equals(building)){
    		sb.append(" and room.fbuildingid in("+building+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	sb.append(" )t )t order by t.conNumber,t.mdNumber");
    	
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rentRS", rs);
		
		sb=new StringBuffer();
    	sb.append(" select sp.fname_l2 sellProject,build.fname_l2 build,room.fname_l2 room,room.fbuildingArea buildArea,room.ftenancyArea tenancyArea");
    	sb.append(" from t_she_room room left join t_she_building build on room.fbuildingId=build.fid left join t_she_sellProject sp on sp.fid=build.fsellProjectId");
    	sb.append(" where room.fisForTen=1");
    	if(building!=null&&!"".equals(building)){
    		sb.append(" and room.fbuildingid in("+building+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	
    	rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("unRentRS", rs);
		
		sb=new StringBuffer();
    	sb.append(" select sp.fname_l2 sellProject,build.fname_l2 build,room.fname_l2 room,room.fbuildingArea buildArea,room.ftenancyArea tenancyArea");
    	sb.append(" from t_she_room room left join t_she_building build on room.fbuildingId=build.fid left join t_she_sellProject sp on sp.fid=build.fsellProjectId");
    	sb.append(" where room.fisForTen=1 and room.FTenancyState!='KeepTenancy'");
    	sb.append(" and NOT EXISTS(select roomEntry.froomId from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId where con.ftenancyState in('Audited','Executing','ContinueTenancying') and roomEntry.froomId=room.fid )");
    	if(building!=null&&!"".equals(building)){
    		sb.append(" and room.fbuildingid in("+building+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	sb.append(" order by sp.fnumber,build.fnumber,room.funit,room.ffloor,room.fnumber");
    	rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("unRentRS", rs);
		
		sb=new StringBuffer();
    	sb.append(" select sp.fname_l2 sellProject,build.fname_l2 build,room.fname_l2 room,room.fbuildingArea buildArea,room.ftenancyArea tenancyArea");
    	sb.append(" from t_she_room room left join t_she_building build on room.fbuildingId=build.fid left join t_she_sellProject sp on sp.fid=build.fsellProjectId");
    	sb.append(" where room.fisForSHE=1 and room.FSellState='Sign'");
    	if(building!=null&&!"".equals(building)){
    		sb.append(" and room.fbuildingid in("+building+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	sb.append(" order by sp.fnumber,build.fnumber,room.funit,room.ffloor,room.fnumber");
    	rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("sellRS", rs);
		
		sb=new StringBuffer();
    	sb.append(" select sum(area.FBuildArea) buildArea,sum(area.FArea)tenArea");
    	sb.append(" from t_she_building build left join t_she_sellProject sp on sp.fid=build.fsellProjectid left join T_SHE_BuildingAreaEntry area on area.fheadId=build.fid");
    	sb.append(" where sp.fisForSHE=1");
    	if(building!=null&&!"".equals(building)){
    		sb.append(" and build.fid in("+building+")");
    	}else{
    		sb.append(" and build.fid in('null')");
    	}
    	rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("buildAreaRS", rs);
		
		sb=new StringBuffer();
    	sb.append(" select sum(roomEntry.fbuildingArea) tenArea");
    	sb.append(" from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId");
    	sb.append(" where 1=1");
//    	if(isAll){
	//		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
	//	}else{
			sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
	//	}
		if(building!=null&&!"".equals(building)){
			sb.append(" and room.fbuildingid in("+building+")");
		}else{
			sb.append(" and room.fbuildingid in('null')");
		}
    	rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rentAreaRS", rs);
		return params;
    }
}