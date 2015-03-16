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
import com.kingdee.eas.framework.report.util.SqlParams;

import java.util.Date;
import java.util.Set;

public class DealRoomReportFacadeControllerBean extends AbstractDealRoomReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.DealRoomReportFacadeControllerBean");
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
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"area",100,false);
	    initColoum(header,col,"account",100,false);
	    initColoum(header,col,"dealAmount",100,false);
	    initColoum(header,col,"amountAverage",100,false);
	    initColoum(header,col,"dealArea",100,false);
	    initColoum(header,col,"areaAverage",100,false);
	    initColoum(header,col,"dealAccount",100,false);
	    initColoum(header,col,"accountAverage",100,false);
	   
	    header.setLabels(new Object[][]{ 
	    		{
	    		    "sellProjectId","项目","productTypeId","业态","总可售套数","总可售面积","总可售金额","成交套数","套数去化率","成交面积",
	    			"面积去化率","成交金额","金额去化率"
	    		}
	    		,
	    		{
	    			"sellProjectId","项目","productTypeId","业态","总可售套数","总可售面积","总可售金额","成交套数","套数去化率","成交面积",
	    			"面积去化率","成交金额","金额去化率"
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
    	sb.append(" select t1.sellProjectId,t1.sellProjectName,t1.productTypeId,t1.productTypeName,t2.amount,t2.area,t2.account,");
    	sb.append(" t1.amount dealAmount,t1.amount/t2.amount amountAverage,t1.area dealArea,t1.area/t2.area areaAverage,t1.account dealAccount,t1.account/t2.account accountAverage from (");
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,count(*) amount,sum(t.area) area,sum(t.account) account from (");
    	getBaseTransaction(sb,"t_she_purchaseManage","'PurApple','PurAudit'",fromDate,toDate);
    	sb.append(" union all");
    	getBaseTransaction(sb,"t_she_signManage","'SignApple','SignAudit'",fromDate,toDate);
    	sb.append(" )t group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName ) t1");
    	sb.append(" left join (");
    	sb.append(" select t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName,count(*) amount,sum(t.area) area,sum(t.account) account from (");
    	getBaseTransaction(sb,"t_she_signManage","'SignApple','SignAudit'",fromDate,toDate);
    	sb.append(" union all");
    	getRoom(sb);
    	sb.append(" )t group by t.sellProjectId,t.sellProjectName,t.productTypeId,t.productTypeName");
    	sb.append(" )t2  on t2.sellProjectid=t1.sellProjectid and t2.productTypeId=t1.productTypeId where 1=1");
    	
    	if(sellProjectStr!=null){
			sb.append(" and t1.sellProjectId in ("+sellProjectStr+")");
		}else if(org!=null){
			sb.append(" and t1.sellProjectId in ("+org+")");
		}else{
			sb.append(" and t1.sellProjectId in ('null')");
		}
		if(productTypeId!=null){
			sb.append(" and t1.productTypeId ='"+productTypeId+"'");
		}
		return sb.toString();
    }
	private StringBuffer getRoom(StringBuffer sb){
		sb.append(" select sellProject.fid sellProjectId,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case when FIsActualAreaAudited='1' then room.factualBuildingArea else case when fispre='1' then room.fBuildingArea else case when FIsPlan='1' then room.fplanBuildingArea else room.fplanBuildingArea end end end )area,");
		sb.append(" sum(case when room.cfmanagerAgio=null then room.fstandardTotalAmount else case when room.cfmanagerAgio=0 then room.fstandardTotalAmount else room.fstandardTotalAmount*room.cfmanagerAgio end end )account");
		sb.append(" from T_SHE_Room room");
		sb.append(" left join t_she_building build on build.fid=room.fbuildingid left join t_she_sellProject sellproject on sellProject.fid=build.fsellProjectid  inner join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" where build.FIsGetCertificated='1' and room.fsellstate<>'Sign'");
		sb.append(" group by sellProject.fid,sellProject.fname_l2,productType.fid,productType.fname_l2,room.fid");
		return sb;
	}
	private StringBuffer getBaseTransaction(StringBuffer sb,String table,String state,Date fromDate,Date toDate){
		sb.append(" select deal.fsellProjectid sellProjectId ,sellProject.fname_l2 sellProjectName,productType.fid productTypeId,productType.fname_l2 productTypeName,sum(case deal.fsellType when 'PlanningSell' then deal.fstrdPlanBuildingArea when 'PreSell' then deal.fbulidingArea else deal.fstrdActualBuildingArea end )area,sum(deal.fdealTotalAmount) account");
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