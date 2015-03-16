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
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class SellAmountReportFacadeControllerBean extends AbstractSellAmountReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SellAmountReportFacadeControllerBean");
    
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
	    initColoum(header,col,"orgId",100,true);
	    initColoum(header,col,"sellProjectId",100,true);
	    initColoum(header,col,"sellProjectName",180,false);
	    initColoum(header,col,"productTypeId",100,true);
	    initColoum(header,col,"productTypeName",120,false);
	    
	    initColoum(header,col,"contractAmount",120,false);
	    initColoum(header,col,"area",120,false);
	    initColoum(header,col,"price",120,false);
	    initColoum(header,col,"mtHouseAmount",120,false);
	    initColoum(header,col,"mtEarnestAmount",120,false);
	    initColoum(header,col,"mtTotalAmount",120,false);
	    initColoum(header,col,"unGatherAmount",120,false);
	    initColoum(header,col,"fiHouseAmount",120,false);
	    initColoum(header,col,"fiHandAmount",120,false);
	    initColoum(header,col,"fiTotalAmount",120,false);
	    
	    initColoum(header,col,"McontractAmount",120,false);
	    initColoum(header,col,"Marea",120,false);
	    initColoum(header,col,"Mprice",120,false);
	    initColoum(header,col,"MmtHouseAmount",120,false);
	    initColoum(header,col,"MmtEarnestAmount",120,false);
	    initColoum(header,col,"MmtTotalAmount",120,false);
	    initColoum(header,col,"MunGatherAmount",120,false);
	    initColoum(header,col,"MfiHouseAmount",120,false);
	    initColoum(header,col,"MfiHandAmount",120,false);
	    initColoum(header,col,"MfiTotalAmount",120,false);
	    
	    initColoum(header,col,"YcontractAmount",120,false);
	    initColoum(header,col,"Yarea",120,false);
	    initColoum(header,col,"Yprice",120,false);
	    initColoum(header,col,"YmtHouseAmount",120,false);
	    initColoum(header,col,"YmtEarnestAmount",120,false);
	    initColoum(header,col,"YmtTotalAmount",120,false);
	    initColoum(header,col,"YunGatherAmount",120,false);
	    initColoum(header,col,"YfiHouseAmount",120,false);
	    initColoum(header,col,"YfiHandAmount",120,false);
	    initColoum(header,col,"YfiTotalAmount",120,false);
	    
	    initColoum(header,col,"AcontractAmount",120,false);
	    initColoum(header,col,"Aarea",120,false);
	    initColoum(header,col,"Aprice",120,false);
	    initColoum(header,col,"AmtHouseAmount",120,false);
	    initColoum(header,col,"AmtEarnestAmount",120,false);
	    initColoum(header,col,"AmtTotalAmount",120,false);
	    initColoum(header,col,"AunGatherAmount",120,false);
	    initColoum(header,col,"AfiHouseAmount",120,false);
	    initColoum(header,col,"AfiHandAmount",120,false);
	    initColoum(header,col,"AfiTotalAmount",120,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"orgId","id","项目","productTypeId","业态"
	    			,"本期收款情况","本期收款情况","本期收款情况","本期收款情况","本期收款情况","本期收款情况","本期收款情况","本期收款情况","本期收款情况","本期收款情况"
	    			,"本月累计收款情况","本月累计收款情况","本月累计收款情况","本月累计收款情况","本月累计收款情况","本月累计收款情况","本月累计收款情况","本月累计收款情况","本月累计收款情况","本月累计收款情况"
	    			,"本年累计收款情况","本年累计收款情况","本年累计收款情况","本年累计收款情况","本年累计收款情况","本年累计收款情况","本年累计收款情况","本年累计收款情况","本年累计收款情况","本年累计收款情况"
	    			,"项目累计收款情况","项目累计收款情况","项目累计收款情况","项目累计收款情况","项目累计收款情况","项目累计收款情况","项目累计收款情况","项目累计收款情况","项目累计收款情况","项目累计收款情况"
	    		}
	    		,
	    		{
	    			"orgId","id","项目","productTypeId","业态"
	    			,"合同总价","销售面积","平均单价","营销收楼款","定金转楼款","营销收楼款合计","未生成汇总单","财务收楼款","财务收手工款","财务收款合计"
	    			,"合同总价","销售面积","平均单价","营销收楼款","定金转楼款","营销收楼款合计","未生成汇总单","财务收楼款","财务收手工款","财务收款合计"
	    			,"合同总价","销售面积","平均单价","营销收楼款","定金转楼款","营销收楼款合计","未生成汇总单","财务收楼款","财务收手工款","财务收款合计"
	    			,"合同总价","销售面积","平均单价","营销收楼款","定金转楼款","营销收楼款合计","未生成汇总单","财务收楼款","财务收手工款","财务收款合计"
	    		}
	    		,
	    		{
	    			"orgId","id","项目","productTypeId","业态"
	    			,"(元)","(㎡)","(元/㎡)","(A)","(B)","(A+B)","未生成汇总单","(C)","(D)","(B+C+D)"
	    			,"(元)","(㎡)","(元/㎡)","(A)","(B)","(A+B)","未生成汇总单","(C)","(D)","(B+C+D)"
	    			,"(元)","(㎡)","(元/㎡)","(A)","(B)","(A+B)","未生成汇总单","(C)","(D)","(B+C+D)"
	    			,"(元)","(㎡)","(元/㎡)","(A)","(B)","(A+B)","未生成汇总单","(C)","(D)","(B+C+D)"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    private StringBuffer getSelectSql(String type){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" ,(max(case t.name when '"+type+"sign' then isnull(t.amount,0) else null end)-max(case t.name when '"+type+"quitSign' then isnull(t.amount,0) else null end)+max(case t.name when '"+type+"signCB' then isnull(t.amount,0) else null end)) "+type+"contractAmount,");
    	sb.append(" (max(case t.name when '"+type+"sign' then isnull(t.area,0) else null end)-max(case t.name when '"+type+"quitSign' then isnull(t.area,0) else null end)+max(case t.name when '"+type+"signCB' then isnull(t.area,0) else null end)) "+type+"area,");
    	sb.append(" (case when max(case t.name when '"+type+"sign' then isnull(t.area,0) else null end)-max(case t.name when '"+type+"quitSign' then isnull(t.area,0) else null end)+max(case t.name when '"+type+"signCB' then isnull(t.area,0) else null end)=0 then 0 else (max(case t.name when '"+type+"sign' then isnull(t.amount,0) else null end)-max(case t.name when '"+type+"quitSign' then isnull(t.amount,0) else null end)+max(case t.name when '"+type+"signCB' then isnull(t.amount,0) else null end))/(max(case t.name when '"+type+"sign' then isnull(t.area,0) else null end)-max(case t.name when '"+type+"quitSign' then isnull(t.area,0) else null end)+max(case t.name when '"+type+"signCB' then isnull(t.area,0) else null end)) end ) "+type+"price,");
    	sb.append(" max(case t.name when '"+type+"house' then isnull(t.amount,0) else null end) "+type+"mtHouseAmount,");
    	sb.append(" max(case t.name when '"+type+"earnest' then isnull(t.amount,0) else null end) "+type+"mtEarnestAmount,");
    	sb.append(" (max(case t.name when '"+type+"house' then isnull(t.amount,0) else null end)+max(case t.name when '"+type+"earnest' then isnull(t.amount,0) else null end)) "+type+"mtTotalAmount,");
    	sb.append(" max(case t.name when '"+type+"unGahter' then isnull(t.amount,0) else null end) "+type+"unGatherAmount,");
    	sb.append(" max(case t.name when '"+type+"fiHouse' then isnull(t.amount,0) else null end) "+type+"fiHouseAmount,");
    	sb.append(" max(case t.name when '"+type+"fiHand' then isnull(t.amount,0) else null end) "+type+"fiHandAmount,");
    	sb.append(" 0 "+type+"fiTotalAmount");
    	return sb;
    }
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String sellProjectId = (String) params.getObject("sellProject");
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	ProductTypeInfo productType=(ProductTypeInfo)params.getObject("productType");
    	String productTypeId=null;
    	if(productType!=null){
    		productTypeId=productType.getId().toString();
    	}
    	
    	String orgId=(String) params.getObject("orgId");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select t.orgId,t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName");
    	sb.append(getSelectSql(""));
    	sb.append(getSelectSql("M"));
    	sb.append(getSelectSql("Y"));
    	sb.append(getSelectSql("A"));
    	sb.append(" from (");
    	sb.append(getBaseTransaction("sign",fromDate,toDate,0,sellProjectId));
    	sb.append(" union all");
    	sb.append(getChange("quitSign",fromDate,toDate,0,sellProjectId));
    	sb.append(" union all");
    	sb.append(getCompensateBill("signCB",fromDate,toDate,0,sellProjectId));
    	sb.append(" union all");
    	sb.append(getHouseBill("house",fromDate,toDate,0,sellProjectId));
    	sb.append(" union all");
    	sb.append(getEarnestBill("earnest",fromDate,toDate,0,sellProjectId));
    	sb.append(" union all");
    	sb.append(getUnGatherBill("unGahter",fromDate,toDate,0,sellProjectId));
    	sb.append(" union all");
    	sb.append(getFI("fiHouse",fromDate,toDate,0,orgId));
    	sb.append(" union all");
    	sb.append(getHandFI("fiHand",fromDate,toDate,0,orgId));
    	
    	sb.append(" union all");
    	sb.append(getBaseTransaction("Msign",fromDate,toDate,1,sellProjectId));
    	sb.append(" union all");
    	sb.append(getChange("MquitSign",fromDate,toDate,1,sellProjectId));
    	sb.append(" union all");
    	sb.append(getCompensateBill("MsignCB",fromDate,toDate,1,sellProjectId));
    	sb.append(" union all");
    	sb.append(getHouseBill("Mhouse",fromDate,toDate,1,sellProjectId));
    	sb.append(" union all");
    	sb.append(getEarnestBill("Mearnest",fromDate,toDate,1,sellProjectId));
    	sb.append(" union all");
    	sb.append(getUnGatherBill("MunGahter",fromDate,toDate,1,sellProjectId));
    	sb.append(" union all");
    	sb.append(getFI("MfiHouse",fromDate,toDate,1,orgId));
    	sb.append(" union all");
    	sb.append(getHandFI("MfiHand",fromDate,toDate,1,orgId));
    	
    	sb.append(" union all");
    	sb.append(getBaseTransaction("Ysign",fromDate,toDate,2,sellProjectId));
    	sb.append(" union all");
    	sb.append(getChange("YquitSign",fromDate,toDate,2,sellProjectId));
    	sb.append(" union all");
    	sb.append(getCompensateBill("YsignCB",fromDate,toDate,2,sellProjectId));
    	sb.append(" union all");
    	sb.append(getHouseBill("Yhouse",fromDate,toDate,2,sellProjectId));
    	sb.append(" union all");
    	sb.append(getEarnestBill("Yearnest",fromDate,toDate,2,sellProjectId));
    	sb.append(" union all");
    	sb.append(getUnGatherBill("YunGahter",fromDate,toDate,2,sellProjectId));
    	sb.append(" union all");
    	sb.append(getFI("YfiHouse",fromDate,toDate,2,orgId));
    	sb.append(" union all");
    	sb.append(getHandFI("YfiHand",fromDate,toDate,2,orgId));
    	
    	sb.append(" union all");
    	sb.append(getBaseTransaction("Asign",fromDate,toDate,3,sellProjectId));
    	sb.append(" union all");
    	sb.append(getChange("AquitSign",fromDate,toDate,3,sellProjectId));
    	sb.append(" union all");
    	sb.append(getCompensateBill("AsignCB",fromDate,toDate,3,sellProjectId));
    	sb.append(" union all");
    	sb.append(getHouseBill("Ahouse",fromDate,toDate,3,sellProjectId));
    	sb.append(" union all");
    	sb.append(getEarnestBill("Aearnest",fromDate,toDate,3,sellProjectId));
    	sb.append(" union all");
    	sb.append(getUnGatherBill("AunGahter",fromDate,toDate,3,sellProjectId));
    	sb.append(" union all");
    	sb.append(getFI("AfiHouse",fromDate,toDate,3,orgId));
    	sb.append(" union all");
    	sb.append(getHandFI("AfiHand",fromDate,toDate,3,orgId));
    	
    	sb.append(" ) t where 1=1");
    	if(productTypeId!=null){
    		sb.append(" and productType.fid='"+productTypeId+"'");
    	}
    	if(sellProjectId!=null&&!"".equals(sellProjectId)){
			sb.append(" and t.sellProjectId in ("+sellProjectId+")");
		}else{
			sb.append(" and t.sellProjectId in ('null')");
		}
    	sb.append(" group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,t.orgId");
    	RptRowSet rowSet = executeQuery(sb.toString(), null,ctx);
    	params.setObject("rowset", rowSet);
		return params;
    }
    
    private StringBuffer getBaseTransaction(String name,Date fromDate,Date toDate,int type,String sellProjectId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,'' orgId,sign.fsellProjectId sellProjectId,room.fproductTypeId productTypeId,sp.fname_l2 sellProjectName,productType.fname_l2 productTypeName,");
		sb.append(" sum((case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end)) area,");
		sb.append(" sum(sign.fcontractTotalAmount) amount from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId");
		sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectId left join t_fdc_productType productType on productType.fid=room.fproductTypeId");
    	sb.append(" where sign.fbizState in('SignApple','SignAudit','QRNullify')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null&&!"".equals(sellProjectId)){
			sb.append(" and sign.fsellProjectId in ("+sellProjectId+")");
		}else{
			sb.append(" and sign.fsellProjectId in ('null')");
		}
    	sb.append(" group by sign.fsellProjectId,room.fproductTypeId,sp.fname_l2,productType.fname_l2");
    	return sb;
    }
    private StringBuffer getChange(String name,Date fromDate,Date toDate,int type,String sellProjectId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,'' orgId,sign.fsellProjectId sellProjectId,room.fproductTypeId productTypeId,sp.fname_l2 sellProjectName,productType.fname_l2 productTypeName,");
    	sb.append(" sum((case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end)) area,");
		sb.append(" sum(sign.fcontractTotalAmount) amount from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId");
    	sb.append(" left join t_she_changeManage change on change.fsrcid=sign.fid");
    	sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectId left join t_fdc_productType productType on productType.fid=room.fproductTypeId");
    	sb.append(" where change.fstate in('2SUBMITTED','4AUDITTED') and change.fbizType='quitRoom'");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
		if(sellProjectId!=null&&!"".equals(sellProjectId)){
			sb.append(" and change.fsellProjectId in ("+sellProjectId+")");
		}else{
			sb.append(" and change.fsellProjectId in ('null')");
		}
		sb.append(" group by sign.fsellProjectId,room.fproductTypeId,sp.fname_l2,productType.fname_l2");
		return sb;
    }
    private StringBuffer getCompensateBill(String name,Date fromDate,Date toDate,int type,String sellProjectId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,'' orgId,revBill.fsellProjectId sellProjectId,room.fproductTypeId productTypeId,sp.fname_l2 sellProjectName,productType.fname_l2 productTypeName,sum(isnull(room.factualBuildingArea,0)-isnull(room.fbuildingArea,0)) area,sum(isnull(entry.factAmount,0)) amount");
    	sb.append(" from T_SHE_CompensateRoomList entry left join T_SHE_RoomAreaCompensate revBill on revBill.fid=entry.fheadid left join t_she_room room on room.fid=entry.froomid");
    	sb.append(" left join t_she_sellProject sp on sp.fid=revBill.fsellProjectId left join t_fdc_productType productType on productType.fid=room.fproductTypeId");
    	sb.append(" where revBill.fcompensateState in('COMSUBMIT','COMAUDITTED')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.FCompensateDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.FCompensateDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.FCompensateDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.FCompensateDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.FCompensateDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.FCompensateDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
    	if(sellProjectId!=null&&!"".equals(sellProjectId)){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}else{
			sb.append(" and revBill.fsellProjectId in ('null')");
		}
    	sb.append(" group by revBill.fsellProjectId,room.fproductTypeId,room.fproductTypeId,sp.fname_l2,productType.fname_l2");
    	return sb;
    }
    private StringBuffer getHouseBill(String name,Date fromDate,Date toDate,int type,String sellProjectId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,'' orgId,revBill.fsellProjectid sellProjectId,room.fproductTypeid productTypeId,sp.fname_l2 sellProjectName,productType.fname_l2 productTypeName,0 area,sum(isnull(entry.frevAmount,0)) amount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" left join t_she_sellProject sp on sp.fid=revBill.fsellProjectId left join t_fdc_productType productType on productType.fid=room.fproductTypeId");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null&&!"".equals(sellProjectId)){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}else{
			sb.append(" and revBill.fsellProjectId in ('null')");
		}
    	sb.append(" group by revBill.fsellProjectid,room.fproductTypeid,room.fproductTypeId,sp.fname_l2,productType.fname_l2");
    	return sb;
    }
    private StringBuffer getEarnestBill(String name,Date fromDate,Date toDate,int type,String sellProjectId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,'' orgId,revBill.fsellProjectid sellProjectId,room.fproductTypeid productTypeId,sp.fname_l2 sellProjectName,productType.fname_l2 productTypeName,0 area,sum(isnull(entry.famount,0)) amount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" left join t_she_sellProject sp on sp.fid=revBill.fsellProjectId left join t_fdc_productType productType on productType.fid=room.fproductTypeId");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null&&!"".equals(sellProjectId)){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}else{
			sb.append(" and revBill.fsellProjectId in ('null')");
		}
    	sb.append(" group by revBill.fsellProjectid,room.fproductTypeid,sp.fname_l2,productType.fname_l2");
    	return sb;
    }
    private StringBuffer getUnGatherBill(String name,Date fromDate,Date toDate,int type,String sellProjectId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,'' orgId,revBill.fsellProjectid sellProjectId,room.fproductTypeid productTypeId,sp.fname_l2 sellProjectName,productType.fname_l2 productTypeName,0 area,sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) amount from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" left join t_she_sellProject sp on sp.fid=revBill.fsellProjectId left join t_fdc_productType productType on productType.fid=room.fproductTypeId");
    	sb.append(" where revBill.fIsGathered=0 and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null&&!"".equals(sellProjectId)){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}else{
			sb.append(" and revBill.fsellProjectId in ('null')");
		}
    	sb.append(" group by revBill.fsellProjectid,room.fproductTypeid,sp.fname_l2,productType.fname_l2");
    	return sb;
    }
    private StringBuffer getFI(String name,Date fromDate,Date toDate,int type,String orgId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,rec.fcompanyId orgId,'' sellProjectId,'' productTypeId,null sellProjectName,null productTypeName,0 area,sum(rec.factRecLocAmt) amount from T_CAS_ReceivingBill rec");
    	sb.append(" left join (select distinct gather.FReceivingBillID recId from T_SHE_ReceiveGather gather left join T_SHE_ReceiveGatherEntry entry on entry.FReceiveGatherID=gather.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
		sb.append(" where md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')) t on t.recId=rec.fid");
		sb.append(" where rec.fbillStatus =14 and t.recId is not null");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
    	if(orgId!=null){
			sb.append(" and rec.fcompanyId in ("+orgId+")");
		}else{
			sb.append(" and rec.fcompanyId in ('null')");
		}
    	sb.append(" group by rec.fcompanyId");
    	return sb;
    }
    private StringBuffer getHandFI(String name,Date fromDate,Date toDate,int type,String orgId){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select '"+name+"' name,rec.fcompanyId orgId,'' sellProjectId,'' productTypeId,null sellProjectName,null productTypeName,0 area,sum(rec.factRecLocAmt) amount from T_CAS_ReceivingBill rec");
    	sb.append(" left join (select distinct gather.FReceivingBillID recId from T_SHE_ReceiveGather gather left join T_SHE_ReceiveGatherEntry entry on entry.FReceiveGatherID=gather.fid) t on t.recId=rec.fid");
		sb.append(" where rec.fbillStatus =14 and t.recId is null");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
    	if(orgId!=null){
			sb.append(" and rec.fcompanyId in ("+orgId+")");
		}else{
			sb.append(" and rec.fcompanyId in ('null')");
		}
    	sb.append(" group by rec.fcompanyId");
    	return sb;
    }
}