package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

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
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownReasonEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomSourceReportFacadeControllerBean extends AbstractRoomSourceReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.RoomSourceReportFacadeControllerBean");
    
    protected Map _getTableList(Context ctx, Map map)throws BOSException
    {
    	Map map1 = new HashMap();
    	map1 = getData(ctx,map);
    	
        return map1;
    }
    /**
     * 取房源清单数据
     */
    protected Map getData(Context ctx,Map params) throws BOSException{
		Map map = new HashMap();
		List list = new ArrayList();
		
		String sql = getRoomSourceSql(params);//来电或来访
		try {
			IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
			while(rowSet.next()){
				Map mapList = new HashMap();
				mapList.put("productType", rowSet.getString("productType"));//产品类型
				mapList.put("sellState", rowSet.getString("FSellState"));//销售状态
				mapList.put("name", rowSet.getString("name"));//房间
				mapList.put("roomModel", rowSet.getString("roomModel"));//房型
				mapList.put("buildingArea", rowSet.getBigDecimal("FBuildingArea"));//预售建筑面积
				mapList.put("roomArea", rowSet.getBigDecimal("FRoomArea"));//预售套内面积
				mapList.put("ibasement", rowSet.getBigDecimal("FIbaseMent"));//含地下室预售建筑面积
				mapList.put("ibaInnside", rowSet.getBigDecimal("FIBaInnside"));//含地下室预售套内面积
				mapList.put("standardBuildPrice", rowSet.getBigDecimal("FBuildPrice"));//标准建筑单价
				mapList.put("standardRoomPrice", rowSet.getBigDecimal("FRoomPrice"));//标准套内单价
				mapList.put("standardTotalAmount", rowSet.getBigDecimal("FStandardTotalAmount"));//标准总价
				mapList.put("baseStandardPrice", rowSet.getBigDecimal("baseStandardPrice"));//标准总价
				mapList.put("baseBuildPrice", rowSet.getBigDecimal("baseBuildPrice"));//标准总价
				mapList.put("baseRoomPrice", rowSet.getBigDecimal("baseRoomPrice"));//标准总价
//				if(rowSet.getString("FID")!=null){
//					Map dealMap = getDealAmount(ctx,(String)rowSet.getString("FSellState"), (String)rowSet.getString("FID"));
//					if(dealMap.get("dealBuildPrice")!=null){
//						mapList.put("dealBuildPrice", dealMap.get("dealBuildPrice"));//成交建筑单价
//					}
//					if(dealMap.get("dealRoomPrice")!=null){
//						mapList.put("dealRoomPrice", dealMap.get("dealRoomPrice"));//成交套内单价
//					}
//					if(dealMap.get("dealTotalAmount")!=null){
//						mapList.put("dealTotalAmount", dealMap.get("dealTotalAmount"));//成交总价
//					}
//					if(dealMap.get("sellAmount")!=null){
//						mapList.put("sellAmount", dealMap.get("sellAmount"));//补差后合同价
//					}
//				}
				
				mapList.put("dealBuildPrice", rowSet.getBigDecimal("fdealBuildPrice"));//成交建筑单价
				mapList.put("dealRoomPrice", rowSet.getBigDecimal("fdealRoomPrice"));//成交套内单价
				mapList.put("dealTotalAmount", rowSet.getBigDecimal("fdealTotalAmount"));//成交总价
				mapList.put("sellAmount", rowSet.getBigDecimal("fsellAmount"));//补差后合同
				
				mapList.put("actualBuildingArea", rowSet.getBigDecimal("FActualBuildingArea"));//实测建筑面积
				mapList.put("actualRoomArea", rowSet.getBigDecimal("FActualRoomArea"));//实测套内面积
				
				mapList.put("build", rowSet.getString("build"));
				mapList.put("roomModelType", rowSet.getString("roomModelType"));
				mapList.put("busAdscriptionDate", rowSet.getDate("FBusAdscriptionDate"));
				list.add(mapList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("list", list);
		
		return map;
    	
    }
    /**
     * 取房源清单sql
     */
    protected String  getRoomSourceSql(Map params){
    	RptParams para = (RptParams)params.get("param");
    	StringBuilder sql = new StringBuilder();
    	//小订、大订、签约、待售
    	sql.append(" select b.fname_l2 build,rmt.fname_l2 roomModelType,r.FID,pt.FName_l2 productType,case when keep.fid is null then r.FSellState else '销控' end FSellState,t.FBusAdscriptionDate,r.FName_l2 name,rm.Fname_l2 roomModel,r.FBuildingArea,r.FRoomArea, " +
    			" r.FIbaseMent,r.FIBaInnside,r.FBuildPrice,r.FRoomPrice,r.FStandardTotalAmount,r.FActualBuildingArea,r.FActualRoomArea, " +
    			" r.fbaseStandardPrice baseStandardPrice,r.fbaseRoomPrice baseRoomPrice,r.fbaseBuildingPrice baseBuildPrice,t.FDealBuildPrice,t.FDealRoomPrice,t.FDealTotalAmount,t.FSellAmount from t_she_Room r " +
    			" left join T_SHE_RoomModel rm on rm.FID = r.FRoomModelID " +
    			" left join T_SHE_RoomModelType rmt on rmt.FID = rm.froomModelTypeId " +
    			" left join T_FDC_ProductType pt on pt.FID = r.FProductTypeID " +
    			" left join T_SHE_Building b on b.FID = r.FBuildingID " +
    			" left join (select fid,froomid from T_SHE_RoomKeepDownBill where freason='CONTROL' and fbizstate!='2CANCELKEEPDOWN')keep on keep.FRoomID = r.fid " +
    	
				" left join (select 'PrePurchase' sellState,m.FBusAdscriptionDate,m.FDealBuildPrice,m.FDealRoomPrice,m.FDealTotalAmount,0 FSellAmount,m.froomId from  T_SHE_PrePurchaseManage m where m.fbizState in('PreApple','PreAudit') " +
				" union all select 'Purchase' sellState,m.FBusAdscriptionDate,m.FDealBuildPrice,m.FDealRoomPrice,m.FDealTotalAmount,0 FSellAmount,m.froomId from  T_SHE_PurchaseManage m where m.fbizState in('PurApple','PurAudit')" +
				" union all select 'Sign' sellState,m.FBusAdscriptionDate,m.FDealBuildPrice,m.FDealRoomPrice,m.FDealTotalAmount,m.FSellAmount,m.froomId from  T_SHE_SignManage m where m.fbizState in('SignApple','SignAudit'))t on t.froomId=r.fid and r.FSellState=t.sellState" +
    	" where 1=1 ");
		if(para.getObject("buildUnit")!=null){
			BuildingUnitInfo buildUnit = (BuildingUnitInfo)para.getObject("buildUnit");
			sql.append(" and r.FBuildUnitID = '"+buildUnit.getId()+"' ");
		}
		if(para.getObject("building")!=null){
			BuildingInfo building = (BuildingInfo)para.getObject("building");
			sql.append(" and r.FBuildingID = '"+building.getId()+"' ");
		}
		if(para.getObject("sellProject")!=null){
			String sellProject = (String)para.getObject("sellProject");
			sql.append(" and b.FSellProjectID in ("+sellProject+") ");
		}else{
			sql.append(" and b.FSellProjectID = null ");
		}
		if(para.getObject("sellState")!=null){
			if(para.getObject("sellState").toString().indexOf("'Control'")>0){
				sql.append(" and (keep.fid is not null or r.FSellState in "+para.getObject("sellState")+")");
			}else{
				sql.append(" and r.FSellState in "+para.getObject("sellState"));
			}
		}
		StringBuffer productType =null;
	    if(para.getObject("productType")!=null){
	    	productType=new StringBuffer();
	    	Object[] userObject = (Object[])para.getObject("productType");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		productType.append("'"+((ProductTypeInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		productType.append(",'"+((ProductTypeInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    if(productType!=null){
	    	sql.append(" and r.FProductTypeID in ("+productType+")");
	    }
	    
		StringBuffer roomMode =null;
	    if(para.getObject("roomMode")!=null){
	    	roomMode=new StringBuffer();
	    	Object[] userObject = (Object[])para.getObject("roomMode");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		roomMode.append("'"+((RoomModelInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		roomMode.append(",'"+((RoomModelInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    if(roomMode!=null){
	    	sql.append(" and rm.fid in ("+roomMode+")");
	    }
	    StringBuffer building =null;
	    if(para.getObject("buildingObj")!=null){
	    	building=new StringBuffer();
	    	Object[] userObject = (Object[])para.getObject("buildingObj");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		building.append("'"+((BuildingInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		building.append(",'"+((BuildingInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    if(building!=null){
	    	sql.append(" and r.FBuildingID in ("+building+")");
	    }
	    if(para.getObject("floorFrom")!=null){
			int floorFrom = para.getInt("floorFrom");
			sql.append(" and r.ffloor >="+floorFrom);
		}
	    if(para.getObject("floorTo")!=null){
			int floorTo = para.getInt("floorTo");
			sql.append(" and r.ffloor <="+floorTo);
		}
	    if(para.getObject("ydj")!=null){
	    	if(para.getBoolean("ydj")){
	    		sql.append(" and r.FBuildPrice is not null and r.fsellstate<>'Sign'");
	    	}else{
	    		sql.append(" and r.FBuildPrice is null");
	    	}
	    }
		sql.append(" order by b.fnumber,r.funit,r.ffloor,r.fnumber"); 
    	
    	return sql.toString();
    }
    
    /**
     * 查询房间成交建筑单价、成交套内单价、成交总价
     */
    protected Map getDealAmount(Context ctx,String sign,String fid) throws BOSException{
    	Map map = new HashMap();
    	StringBuilder sql = new StringBuilder();
		if(sign.equals("PrePurchase")){
			sql.append(" select m.FDealBuildPrice,m.FDealRoomPrice,m.FDealTotalAmount from  T_SHE_PrePurchaseManage m ");
		}else if(sign.equals("Purchase")){
			sql.append(" select m.FDealBuildPrice,m.FDealRoomPrice,m.FDealTotalAmount from  T_SHE_PurchaseManage m ");
		}else if(sign.equals("Sign")){
			sql.append(" select m.FDealBuildPrice,m.FDealRoomPrice,m.FDealTotalAmount,m.FSellAmount from  T_SHE_SignManage m ");
		}
		if(sql.toString().equals("")){
			
		}else{
	    	sql.append(" where m.FRoomId = '"+fid+"'  ");
	    	sql.append(" order by m.FBizDate desc ");
	    	
	    	try {
				IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
				if(rowSet.next()){
					map.put("dealBuildPrice", rowSet.getBigDecimal("FDealBuildPrice"));//成交建筑单价
					map.put("dealRoomPrice", rowSet.getBigDecimal("FDealRoomPrice"));//成交套内单价
					map.put("dealTotalAmount", rowSet.getBigDecimal("FDealTotalAmount"));//成交总价
					if(rowSet.getBigDecimal("FSellAmount")!=null){
						map.put("sellAmount", rowSet.getBigDecimal("FSellAmount"));//补差后合同价(签约单销售总价)
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return map;
    }
}