package com.kingdee.eas.fdc.sellhouse.report;

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

import com.kingdee.jdbc.rowset.IRowSet;
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

import java.util.Date;
import java.util.Set;

public class RoomAccountReportFacadeControllerBean extends AbstractRoomAccountReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.RoomAccountReportFacadeControllerBean");
    protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
    {
        return null;
    }
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
	    initColoum(header,col,"sellProjectName",100,false);
	    initColoum(header,col,"productTypeId",100,true);
	    initColoum(header,col,"productTypeName",100,false);
	    
	    initColoum(header,col,"canSellAmount",100,false);
	    initColoum(header,col,"canSellBuildArea",100,false);
	    initColoum(header,col,"canSellRoomArea",100,false);
	    initColoum(header,col,"canSellAccount",100,false);
	    initColoum(header,col,"canSellPrice",100,false);
	    
	    initColoum(header,col,"onSellAmount",100,false);
	    initColoum(header,col,"onSellBuildArea",100,false);
	    initColoum(header,col,"onSellRoomArea",100,false);
	    initColoum(header,col,"onSellAccount",100,false);
	    initColoum(header,col,"onSellPrice",100,false);
	    
	    initColoum(header,col,"preAmount",100,false);
	    initColoum(header,col,"preBuildArea",100,false);
	    initColoum(header,col,"preRoomArea",100,false);
	    initColoum(header,col,"preAccount",100,false);
	    initColoum(header,col,"prePrice",100,false);
	    
	    initColoum(header,col,"purAmount",100,false);
	    initColoum(header,col,"purBuildArea",100,false);
	    initColoum(header,col,"purRoomArea",100,false);
	    initColoum(header,col,"purAccount",100,false);
	    initColoum(header,col,"purPrice",100,false);
	    
	    initColoum(header,col,"signAmount",100,false);
	    initColoum(header,col,"signBuildArea",100,false);
	    initColoum(header,col,"signRoomArea",100,false);
	    initColoum(header,col,"signAccount",100,false);
	    initColoum(header,col,"signPrice",100,false);
	   
	    header.setLabels(new Object[][]{ 
	    		{
	    		    "sellProjectId","项目","productTypeId","业态","可售房源","可售房源","可售房源","可售房源","可售房源","待售房源",
	    			"待售房源","待售房源","待售房源","待售房源","预定","预定","预定","预定","预定","认购","认购","认购","认购","认购","签约","签约","签约","签约","签约"
	    		}
	    		,
	    		{
	    			"sellProjectId","项目","productTypeId","业态","套数","建筑面积","	套内面积","总金额","均价","套数","建筑面积","	套内面积","总金额","均价","套数","建筑面积","	套内面积","总金额","均价",
	    			"套数","建筑面积","	套内面积","总金额","均价","套数","建筑面积","	套内面积","总金额","均价"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		params.setInt("count", rowSet.getRowCount());
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	SellProjectInfo sellProject = (SellProjectInfo) params.getObject("sellProject");
    	String sellProjectStr=null;
    	if(sellProject!=null){
    		try {
    			sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(ctx,sellProject));
			} catch (BOSException e) {
				e.printStackTrace();
			} 
    	}
    	ProductTypeInfo productType=(ProductTypeInfo)params.getObject("productType");
    	String productTypeId=null;
    	if(productType!=null){
    		productTypeId=productType.getId().toString();
    	}
    	String org=(String) params.getObject("org");
    	
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select room.sellProjectId,room.sellProjectName,room.productTypeId,room.productTypeName,room.amount canSellAmount,room.buildArea canSellBuildArea,room.roomArea canSellRoomArea,room.account canSellAccount,room.price canSellPrice,");
    	sb.append(" onSellRoom.amount onSellAmount,onSellRoom.buildArea onSellBuildArea,onSellRoom.roomArea onSellRoomArea,onSellRoom.account onSellAccount,onSellRoom.price onSellPrice,");
    	sb.append(" pre.amount preAmount,pre.buildArea preBuildArea,pre.roomArea preRoomArea,pre.account preAccount,pre.price prePrice,");
    	sb.append(" pur.amount purAmount,pur.buildArea purBuildArea,pur.roomArea purRoomArea,pur.account purAccount,pur.price purPrice,");
    	sb.append(" sign.amount signAmount,sign.buildArea signBuildArea,sign.roomArea signRoomArea,sign.account signAccount,sign.price signPrice");
    	sb.append(" from (");
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,count(*) amount,sum(t.buildArea) buildArea,sum(t.roomArea) roomArea,sum(t.account) account,(case when sum(t.buildArea)=0 then 0 else sum(t.account)/sum(t.buildArea) end )price from (");
    	getBaseTransaction(sb,"t_she_signManage","'SignApple','SignAudit'",fromDate,toDate);
    	sb.append(" union all");
    	getRoom(sb);
    	sb.append(" )t group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName");
    	sb.append(") room");
    	
    	sb.append(" left join (");
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,count(*) amount,sum(t.buildArea) buildArea,sum(t.roomArea) roomArea,sum(t.account) account,(case when sum(t.buildArea)=0 then 0 else sum(t.account)/sum(t.buildArea) end ) price from (");
    	getOnShowRoom(sb);
    	sb.append(" )t group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName");
    	sb.append(") onSellRoom on room.sellProjectid=onSellRoom.sellProjectid and room.productTypeId=onSellRoom.productTypeId ");
    	
    	sb.append(" left join (");
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,count(*) amount,sum(t.buildArea) buildArea,sum(t.roomArea) roomArea,sum(t.account) account,(case when sum(t.buildArea)=0 then 0 else sum(t.account)/sum(t.buildArea) end ) price from (");
    	getBaseTransaction(sb,"t_she_prepurchaseManage","'PreApple','PreAudit'",fromDate,toDate);
    	sb.append(" )t group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName");
    	sb.append(") pre on room.sellProjectid=pre.sellProjectid and room.productTypeId=pre.productTypeId ");
    	
    	sb.append(" left join (");
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,count(*) amount,sum(t.buildArea) buildArea,sum(t.roomArea) roomArea,sum(t.account) account,(case when sum(t.buildArea)=0 then 0 else sum(t.account)/sum(t.buildArea) end )price from (");
    	getBaseTransaction(sb,"t_she_purchaseManage","'PurApple','PurAudit'",fromDate,toDate);
    	sb.append(" )t group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName");
    	sb.append(") pur on room.sellProjectid=pur.sellProjectid and room.productTypeId=pur.productTypeId ");
    	
    	sb.append(" left join (");
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,count(*) amount,sum(t.buildArea) buildArea,sum(t.roomArea) roomArea,sum(t.account) account,(case when sum(t.buildArea)=0 then 0 else sum(t.account)/sum(t.buildArea) end ) price from (");
    	getBaseTransaction(sb,"t_she_signManage","'SignApple','SignAudit'",fromDate,toDate);
    	sb.append(" )t group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName");
    	sb.append(") sign on room.sellProjectid=sign.sellProjectid and room.productTypeId=sign.productTypeId where 1=1");
    	
    	if(sellProjectStr!=null){
			sb.append(" and room.sellProjectId in ("+sellProjectStr+")");
		}else if(org!=null){
			sb.append(" and room.sellProjectId in ("+org+")");
		}else{
			sb.append(" and room.sellProjectId in ('null')");
		}
		if(productTypeId!=null){
			sb.append(" and room.productTypeId ='"+productTypeId+"'");
		}
		return sb.toString();
    }
	private StringBuffer getRoom(StringBuffer sb){
		sb.append(" select sellProject.fid sellProjectId,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )buildArea,");
		sb.append(" sum(case when FIsActualAreaAudited='1' then room.factualRoomArea else case when fispre='1' then room.fRoomArea else case when FIsPlan='1' then room.fplanRoomArea else room.fplanRoomArea end end end )RoomArea,");
		sb.append(" sum(case when room.cfmanagerAgio=null then room.fstandardTotalAmount else case when room.cfmanagerAgio=0 then room.fstandardTotalAmount else room.fstandardTotalAmount*room.cfmanagerAgio end end )account,");
		sb.append(" case when sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )=0 then 0 else sum(case when room.cfmanagerAgio=null then room.fstandardTotalAmount else case when room.cfmanagerAgio=0 then room.fstandardTotalAmount else room.fstandardTotalAmount*room.cfmanagerAgio end end )/sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end ) end price");
		sb.append(" from T_SHE_Room room");
		sb.append(" left join t_she_building build on build.fid=room.fbuildingid left join t_she_sellProject sellproject on sellProject.fid=build.fsellProjectid  inner join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" where build.FIsGetCertificated='1' and room.fsellstate<>'Sign'");
		sb.append(" group by sellProject.fid,sellProject.fname_l2,productType.fid,productType.fname_l2,room.fid");
		return sb;
	}
	private StringBuffer getOnShowRoom(StringBuffer sb){
		sb.append(" select sellProject.fid sellProjectId,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )buildArea,");
		sb.append(" sum(case when FIsActualAreaAudited='1' then room.factualRoomArea else case when fispre='1' then room.fRoomArea else case when FIsPlan='1' then room.fplanRoomArea else room.fplanRoomArea end end end )RoomArea,");
		sb.append(" sum(case when room.cfmanagerAgio=null then room.fstandardTotalAmount else case when room.cfmanagerAgio=0 then room.fstandardTotalAmount else room.fstandardTotalAmount*room.cfmanagerAgio end end )account,");
		sb.append(" case when sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )=0 then 0 else sum(case when room.cfmanagerAgio=null then room.fstandardTotalAmount else case when room.cfmanagerAgio=0 then room.fstandardTotalAmount else room.fstandardTotalAmount*room.cfmanagerAgio end end )/sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end ) end price");
		sb.append(" from T_SHE_Room room");
		sb.append(" left join t_she_building build on build.fid=room.fbuildingid left join t_she_sellProject sellproject on sellProject.fid=build.fsellProjectid  inner join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" where room.fsellstate='Onshow'");
		sb.append(" group by sellProject.fid,sellProject.fname_l2,productType.fid,productType.fname_l2,room.fid");
		return sb;
	}
	private StringBuffer getBaseTransaction(StringBuffer sb,String table,String state,Date fromDate,Date toDate){
		sb.append(" select deal.fsellProjectid sellProjectId ,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanBuildingArea when 'PreSell' then deal.fbulidingArea else deal.fstrdActualBuildingArea end )buildArea,");
		sb.append(" sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanRoomArea when 'PreSell' then deal.froomArea else deal.fstrdActualRoomArea end )roomArea,sum(deal.fdealTotalAmount) account,case when sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanBuildingArea when 'PreSell' then deal.fbulidingArea else deal.fstrdActualBuildingArea end )=0 then 0 else sum(deal.fdealTotalAmount)/sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanBuildingArea when 'PreSell' then deal.fbulidingArea else deal.fstrdActualBuildingArea end ) end price");
    	sb.append(" from "+table+" deal");
    	sb.append(" left join t_she_sellProject sellproject on sellProject.fid=deal.fsellProjectid left join t_she_room room on room.fid=deal.froomid  left join t_she_building build on build.fid=room.fbuildingid inner join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
    	sb.append(" where deal.fbizState in("+state+") and productType.fid is not null");
    	if(fromDate!=null){
			sb.append(" and deal.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and deal.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" group by productType.fid,deal.fsellProjectid,sellProject.fname_l2,deal.froomid,productType.fname_l2");
		return sb;
    }
}