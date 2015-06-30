package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;
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
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class RevDetailVoucherReportFacadeControllerBean extends AbstractRevDetailVoucherReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.RevDetailVoucherReportFacadeControllerBean");
    
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
	    initColoum(header,col,"spId",100,true);
	    initColoum(header,col,"roomId",100,true);
	    initColoum(header,col,"customerId",100,true);
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
	    			"mdNumber","mdId","conId","spId","roomId","customerId","房间信息","房间信息","房间信息","房间信息","房间信息","合同信息","合同信息","合同信息","合同信息","合同信息","合同信息","租金信息","租金信息","租金信息","款项类别"
	    		}
	    		,
	    		{
	    			"mdNumber","mdId","conId","spId","roomId","customerId","项目分期","楼栋","房间","建筑面积","租赁面积","合同编码","合同名称","客户名称","合同起始日","合同终止日","免租期（按日）","租金总额","租金（按月）","租金单价（每月元/平米）","款项类别"
		    	}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String sellProject = (String) params.getObject("sellProject");
    	StringBuffer room =null;
	    if(params.getObject("room")!=null){
	    	room=new StringBuffer();
	    	Object[] roomObject = (Object[])params.getObject("room");
        	for(int i=0;i<roomObject.length;i++){
        		if(roomObject[i]==null) continue;
            	if(i==0){
            		room.append("'"+((RoomInfo)roomObject[i]).getId().toString()+"'");
            	}else{
            		room.append(",'"+((RoomInfo)roomObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer tenancyBill =null;
	    if(params.getObject("tenancyBill")!=null){
	    	tenancyBill=new StringBuffer();
	    	Object[] tenancyBillObject = (Object[])params.getObject("tenancyBill");
        	for(int i=0;i<tenancyBillObject.length;i++){
        		if(tenancyBillObject[i]==null) continue;
            	if(i==0){
            		tenancyBill.append("'"+((TenancyBillInfo)tenancyBillObject[i]).getId().toString()+"'");
            	}else{
            		tenancyBill.append(",'"+((TenancyBillInfo)tenancyBillObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer customer =null;
	    if(params.getObject("customer")!=null){
	    	customer=new StringBuffer();
	    	Object[] customerObject = (Object[])params.getObject("customer");
        	for(int i=0;i<customerObject.length;i++){
        		if(customerObject[i]==null) continue;
            	if(i==0){
            		customer.append("'"+((FDCCustomerInfo)customerObject[i]).getId().toString()+"'");
            	}else{
            		customer.append(",'"+((FDCCustomerInfo)customerObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer moneyDefine =null;
	    if(params.getObject("moneyDefine")!=null){
	    	moneyDefine=new StringBuffer();
	    	Object[] moneyDefineObject = (Object[])params.getObject("moneyDefine");
        	for(int i=0;i<moneyDefineObject.length;i++){
        		if(moneyDefineObject[i]==null) continue;
            	if(i==0){
            		moneyDefine.append("'"+((MoneyDefineInfo)moneyDefineObject[i]).getId().toString()+"'");
            	}else{
            		moneyDefine.append(",'"+((MoneyDefineInfo)moneyDefineObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Integer year = (Integer)params.getObject("year");
	    Integer month =   (Integer)params.getObject("month");
	    
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);

		Date startDate=FDCDateHelper.getFirstDayOfMonth(cal.getTime());
		Date endDate=FDCDateHelper.getLastDayOfMonth(cal.getTime());
		
	    Boolean isAll=params.getBoolean("isAll");
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select * from (select distinct t.mdNumber,t.mdId,t.conId,t.spId,t.roomId,t.customerId,t.sellProject,t.build,t.room,t.buildArea,t.tenancyArea,");
    	sb.append(" t.conNumber,t.conName,t.customer,t.startDate,t.endDate,t.freeDays,t.dealTotal,");
    	sb.append(" t.dealPrice,t.roomPrice,t.moneyDefine from ("); 
    	sb.append(" select md.fnumber mdNumber,md.fid mdId,con.fid conId,sp.fid spId,room.fid roomId,customerEntry.ffdcCustomerId customerId,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate) freeDays,con.fdealTotalRent dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenancyRoomPayListEntry pay on pay.ftenRoomId=roomEntry.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" where md.fid is not null and md.fnumber not in('03','04','08','10','12','13','14','15','16')");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(startDate!=null){
    		sb.append(" and con.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
    	}
    	if(endDate!=null){
    		sb.append(" and con.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
    	}
    	
    	sb.append(" union all select md.fnumber mdNumber,md.fid mdId,con.fid conId,sp.fid spId,room.fid roomId,customerEntry.ffdcCustomerId customerId,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate) freeDays,con.fdealTotalRent dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenBillOtherPay pay on pay.fheadId=con.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" where md.fid is not null and md.fnumber not in('03','04','08','10','12','13','14','15','16')");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(startDate!=null){
    		sb.append(" and con.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
    	}
    	if(endDate!=null){
    		sb.append(" and con.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
    	}
		
    	sb.append(" )t )t order by t.conNumber,mdNumber");
    	
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rs", rs);
		
		sb=new StringBuffer();
		sb.append(" select t.mdId,t.conId,sum(t.appAmount) appAmount,t.feeAmount from (");
    	sb.append(" select md.fid mdId,con.fid conId,isnull(pay.fappAmount,0) appAmount,isnull(feeEntry.feeAmount,0) feeAmount");
    	sb.append(" from T_TEN_TenancyRoomPayListEntry pay left join T_TEN_TenancyRoomEntry roomEntry on pay.ftenRoomId=roomEntry.fid left join T_TEN_TenancyBill con on con.fid=roomEntry.ftenancyId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join (select feeEntry.ftenancyBillId conId,feeEntry.fmoneyDefineId mdId,sum(feeEntry.fappAmount) feeAmount from T_TEN_FeesWarrantEntrys feeEntry group by ftenancyBillId,fmoneyDefineId) feeEntry on feeEntry.conId=con.fid and feeEntry.mdId=md.fid");
    	sb.append(" where md.fid is not null and md.fnumber not in('03','04','08','10','12','13','14','15','16')");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(startDate!=null){
    		sb.append(" and con.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
    	}
    	if(endDate!=null){
    		sb.append(" and con.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
    	}
    	
    	sb.append(" union all select md.fid mdId,con.fid conId,isnull(pay.fappAmount,0) appAmount,isnull(feeEntry.feeAmount,0) feeAmount");
    	sb.append(" from T_TEN_TenBillOtherPay pay left join T_TEN_TenancyBill con on con.fid=pay.fheadId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join (select feeEntry.ftenancyBillId conId,feeEntry.fmoneyDefineId mdId,sum(feeEntry.fappAmount) feeAmount from T_TEN_FeesWarrantEntrys feeEntry group by ftenancyBillId,fmoneyDefineId) feeEntry on feeEntry.conId=con.fid and feeEntry.mdId=md.fid");
    	sb.append(" where md.fid is not null and md.fnumber not in('03','04','08','10','12','13','14','15','16')");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(startDate!=null){
    		sb.append(" and con.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
    	}
    	if(endDate!=null){
    		sb.append(" and con.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
    	}
    	sb.append(" )t group by t.mdId,t.conId,t.feeAmount");
    	RptRowSet detailrs = executeQuery(sb.toString(), null, ctx);
		params.setObject("detailrs", detailrs);
		return params;
    }
}