package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.IPurchase;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.jdbc.rowset.IRowSet;

public class AgingScheduleFacadeControllerBean extends AbstractAgingScheduleFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.AgingScheduleFacadeControllerBean");
    protected Map _getAllRptData(Context ctx, Map params)throws BOSException,EASBizException
    {
    	Map returnMap= new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	StringBuffer sb= getTblMainSql();
    	appeadWhere(sb,params);
    	sb.append(" order by  \"ORGUNIT\".FID , \"SELLPROJECT\".FID");
    	builder.appendSql(sb.toString());
    	logger.info(builder.getTestSql());
    	StringBuffer attach = updateSetByAttach(params);
    	
    	try {
    	IRowSet rowSet = builder.executeQuery(ctx);
    	IRowSet outSet =null;
    	if(attach!=null && attach.length()>0){
    		builder = new FDCSQLBuilder(ctx);
    		builder.appendSql(attach.toString());
    		outSet = builder.executeQuery(ctx);
    	}
    	Set set = getIdSet(rowSet,outSet);
		
    	PurchaseCollection puc = getPuc(set,ctx);
    	
    	RoomSignContractCollection roomcoll=null;
    	EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter2=new FilterInfo();
		if(set!=null &&  set.size()>0){
			filter2.getFilterItems().add(new FilterItemInfo("purchase",set,CompareType.INCLUDE));
			filter2.getFilterItems().add(new FilterItemInfo("isBlankOut",Boolean.valueOf(true),CompareType.NOTEQUALS));
			filter2.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			view.setFilter(filter2);
			roomcoll = RoomSignContractFactory.getLocalInstance(ctx).getRoomSignContractCollection(view) ;
		}
		
    	
    	FDCReceivingBillCollection  frc = getFDCRBC(puc,ctx);
    	
    	returnMap.put("tblMain", set);
    	returnMap.put("pur", puc);
    	returnMap.put("frc", frc);
    	
    	returnMap.put("roomcoll", roomcoll);
    	
    	
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        return returnMap;
    }
    
    private Set getIdSet(IRowSet rowSet,IRowSet outSet)throws BOSException, SQLException{
    	Set set = new HashSet();
    	Map outMap = null;
    	if(outSet!=null &&outSet.size()>0){
    		outMap = new HashMap();
    		while(outSet.next())
        	{
    			outMap.put(outSet.getString("ID"),outSet.getString("ID"));
        	}
    	}
    	if(outMap!=null){
	    	while(rowSet.next())
	    	{
	    		if(outMap.get(rowSet.getString("ID"))==null){
	    			set.add(rowSet.getString("ID"));
	    		}
	    	}
    	}else{
    		while(rowSet.next())
	    	{
	    			set.add(rowSet.getString("ID"));
	    	}
    	}
    	
    	
    	return set;
    }
    
    
    //附属房产 认购单ID
    private StringBuffer updateSetByAttach(Map params){
    	Boolean isattach = (Boolean)params.get("IncludeAttachment");
    
    	if(isattach!=null && isattach.booleanValue())
		{
    		return null;
		}
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT \n"); 

    	sb.append("\"PURCHASE\".FID AS ID \n");

    	sb.append("FROM T_SHE_PurchaseRoomAttachEntry AS \"attach\" \n");

    	sb.append("LEFT OUTER JOIN t_she_purchase AS \"PURCHASE\" \n");
    	sb.append("ON \"PURCHASE\".FID  =\"attach\".fheadid \n");
    	
    	sb.append("LEFT OUTER JOIN T_SHE_Room AS \"ROOM\" \n");
    	sb.append("ON \"PURCHASE\".FRoomID  =\"ROOM\".FID \n");

    	sb.append("LEFT OUTER JOIN T_ORG_BaseUnit AS \"ORGUNIT\" \n");
    	sb.append("ON \"PURCHASE\".FOrgUnitID = \"ORGUNIT\".FID \n");

    	sb.append("LEFT OUTER JOIN T_SHE_SellProject AS \"SELLPROJECT\" \n");
		sb.append("ON \"PURCHASE\".FSellProjectID = \"SELLPROJECT\".FID \n");

		sb.append("LEFT OUTER JOIN T_SHE_Building AS \"BUILDING\" \n");
		sb.append("ON \"ROOM\".FBuildingID = \"BUILDING\".FID \n");

		sb.append("LEFT OUTER JOIN T_SHE_BuildingUnit AS \"BUILDUNIT\" \n");
		sb.append("ON \"ROOM\".FBuildUnitID = \"BUILDUNIT\".FID \n");
		
		sb.append("LEFT OUTER JOIN T_SHE_Subarea AS \"SUBAREA\" \n");
		sb.append("ON \"BUILDING\".FSubareaID = \"SUBAREA\".FID \n");
		
		sb.append(" where 1=1 and \"ROOM\".FisForSHE=1 \n");
		sb.append(" and (\"PURCHASE\".FPurchaseState='PurchaseAudit' or \"PURCHASE\".FPurchaseState='PurchaseChange') \n");
		
		Date from = (Date)params.get("purchaseDateFrom");
    	Date to = (Date)params.get("purchaseDateTo");
    	
    	String buildingName = (String)params.get("building.name");
    	String buildUnitName = (String)params.get("buildUnit.name");
    
    	String buildingSubarea = (String)params.get("building.subarea.id");
    	String sellProject = (String)params.get("sellProject.id");
    	String orgUnit = (String)params.get("orgUnit.id");
    	
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	sb.append(" and \"PURCHASE\".FpurchaseDate>={TS'"+sdf.format(from)+"'}");
    	sb.append(" and \"PURCHASE\".FpurchaseDate<{TS'"+sdf.format(to)+"'}");
    	
    	Set stateset = new HashSet();
    	stateset.add(PurchaseStateEnum.PURCHASEAPPLY_VALUE);
    	stateset.add(PurchaseStateEnum.PURCHASEAUDITING_VALUE);
    	stateset.add(PurchaseStateEnum.PURCHASEAUDIT_VALUE);
    	stateset.add(PurchaseStateEnum.PURCHASECHANGE_VALUE);
    	sb.append(" and \"PURCHASE\".FpurchaseState in ('"
				+ PurchaseStateEnum.PURCHASEAPPLY_VALUE + "','"
				+ PurchaseStateEnum.PURCHASEAUDITING_VALUE + "','"
				+ PurchaseStateEnum.PURCHASEAUDIT_VALUE + "','"
				+ PurchaseStateEnum.PURCHASECHANGE_VALUE + "') ");
    	
    	
    	if(buildingName!=null){
    		sb.append(" and \"BUILDING\".FName_l2='"+buildingName+"'");
    		
    		if(buildUnitName!=null){
        		sb.append(" and \"BUILDUNIT\".FName_l2='"+buildUnitName+"'");
        	}
    		
    	}

    	
		if(buildingSubarea!=null){
    		sb.append(" and \"SUBAREA\".FID='"+buildingSubarea+"'");
    	}
		
		if(sellProject!=null  &&  sellProject.length()>0){
				sb.append(" and \"SELLPROJECT\".FID in ("+sellProject+")");
		}
		
		if(orgUnit!=null && orgUnit.length()>0){
				sb.append(" and \"ORGUNIT\".FID in ("+orgUnit+")");
		}
    	
		sb.append(" group by \"PURCHASE\".FID \n");
    
		
		return sb;
    }
    
    private FDCReceivingBillCollection  getFDCRBC(PurchaseCollection puc,Context ctx){
    	
    	if(puc==null ||puc.size()<1){
    		return null;
    	}
    	int count=puc.size();
		Set set=new HashSet();
		for(int i = 0 ; i<count;i++){
			PurchaseInfo puInfo=puc.get(i);
			set.add(puInfo.getId());
		}
		FDCReceivingBillCollection frc=null;
		
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("purchaseObj.id",set,CompareType.INCLUDE));
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("purchaseObj.id");
		sic.add("purchaseObj.name");
		sic.add("purchaseObj.number");
		sic.add("purchaseObj.payListEntry.id");
		sic.add("purchaseObj.payListEntry.number");
		sic.add("fdcReceiveBill.revBillType");
		sic.add("purchaseObj.payListEntry.name");
		sic.add("purchaseObj.payListEntry.moneyDefine.id");
		sic.add("purchaseObj.payListEntry.moneyDefine.number");
		sic.add("purchaseObj.payListEntry.moneyDefine.name");
		sic.add("purchaseObj.payListEntry.moneyDefine.moneyType");
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("bizDate");
		sic.add("entries.id");
		sic.add("entries.name");
		sic.add("entries.number");
		sic.add("entries.amount");
		sic.add("entries.moneyDefine.id");
		sic.add("entries.moneyDefine.number");
		sic.add("entries.moneyDefine.name");
		sic.add("entries.moneyDefine.moneyType");
		/*sic.add("fdcReceiveBill.moneyDefine.id");
		sic.add("fdcReceiveBill.moneyDefine.number");
		sic.add("fdcReceiveBill.moneyDefine.name");
		sic.add("fdcReceiveBill.moneyDefine.moneyType");*/
		
		view.setSelector(sic);
		view.setFilter(filter);
		IFDCReceivingBill rb =null;
		
		try
		{
			rb=FDCReceivingBillFactory.getLocalInstance(ctx);
			frc=rb.getFDCReceivingBillCollection(view);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		
		return frc;
		
		
    }
    
    private  PurchaseCollection getPuc(Set set,Context ctx){
    	
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter2=new FilterInfo();
		if(set==null ||  set.size()<1){
			return null;
		}
		
		filter2.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		view.setSelector(getSelectors());
		view.setFilter(filter2);
		SorterItemCollection sort = new SorterItemCollection();
		sort.add(new SorterItemInfo("orgUnit.name"));
		sort.add(new SorterItemInfo("sellProject.name"));
		sort.add(new SorterItemInfo("room.building.subarea.name"));
		sort.add(new SorterItemInfo("room.building.name"));
		sort.add(new SorterItemInfo("room.buildUnit.id"));
		sort.add(new SorterItemInfo("room.displayName"));
		sort.add(new SorterItemInfo("number"));
		view.setSorter(sort);
		IPurchase ip=null;
		PurchaseCollection puc=null;
		try
		{
			ip=PurchaseFactory.getLocalInstance(ctx);
			puc=ip.getPurchaseCollection(view);
		} catch (BOSException e2)
		{
			e2.printStackTrace();
		}
		return puc;
    }
    
    
    private StringBuffer appeadWhere(StringBuffer sql,Map params){
    	Boolean isattach = (Boolean)params.get("IncludeAttachment");
    	Date from = (Date)params.get("purchaseDateFrom");
    	Date to = (Date)params.get("purchaseDateTo");
    	
    	String buildingName = (String)params.get("building.name");
    	String buildUnitName = (String)params.get("buildUnit.name");
    
    	String buildingSubarea = (String)params.get("building.subarea.id");
    	String sellProject = (String)params.get("sellProject.id");
    	String orgUnit = (String)params.get("orgUnit.id");
    	
    	
    	/*if(isattach==null || !isattach.booleanValue())
		{
			//sql.append(" and \"ROOM\".FHouseProperty <> '"+HousePropertyEnum.ATTACHMENT_VALUE+"'");	
		}*/
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	sql.append(" and \"PURCHASE\".FpurchaseDate>={TS'"+sdf.format(from)+"'}");
    	sql.append(" and \"PURCHASE\".FpurchaseDate<{TS'"+sdf.format(to)+"'}");
    	
    	Set stateset = new HashSet();
    	stateset.add(PurchaseStateEnum.PURCHASEAPPLY_VALUE);
    	stateset.add(PurchaseStateEnum.PURCHASEAUDITING_VALUE);
    	stateset.add(PurchaseStateEnum.PURCHASEAUDIT_VALUE);
    	stateset.add(PurchaseStateEnum.PURCHASECHANGE_VALUE);
    	sql.append(" and \"PURCHASE\".FpurchaseState in ('"
				+ PurchaseStateEnum.PURCHASEAPPLY_VALUE + "','"
				+ PurchaseStateEnum.PURCHASEAUDITING_VALUE + "','"
				+ PurchaseStateEnum.PURCHASEAUDIT_VALUE + "','"
				+ PurchaseStateEnum.PURCHASECHANGE_VALUE + "') ");
    	
    	
    	if(buildingName!=null){
    		sql.append(" and \"BUILDING\".FName_l2='"+buildingName+"'");
    		
    		if(buildUnitName!=null){
        		sql.append(" and \"BUILDUNIT\".FName_l2='"+buildUnitName+"'");
        	}
    		
    	}

    	
		if(buildingSubarea!=null){
    		sql.append(" and \"SUBAREA\".FID='"+buildingSubarea+"'");
    	}
		
		if(sellProject!=null  &&  sellProject.length()>0){
			//String temp = getSetToStr(sellProject);
			//if(temp!=null){
				sql.append(" and \"SELLPROJECT\".FID in ("+sellProject+")");
			//}
			//sql.append("  and \"SELLPROJECT\".FID='"+sellProject+"'");
		}
		
		if(orgUnit!=null && orgUnit.length()>0){
			//String temp = getSetToStr(orgUnit);
			//if(temp!=null){
				sql.append(" and \"ORGUNIT\".FID in ("+orgUnit+")");
			//}
			//sql.append(" and \"ORGUNIT\".FID = '"+orgUnit+"'");
		}
    	
    	
			
		return sql;
    	
    }
    
    private StringBuffer getTblMainSql(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT \n"); 

    	sb.append("\"PURCHASE\".FID AS ID \n");
//    	sb.append("\"ORGUNIT\".FName_l2 AS ORGUNIT.NAME, \n");
//    	sb.append("\"SELLPROJECT\".FName_l2 AS SELLPROJECT.NAME, \n");
//    	sb.append("\"BUILDING\".FName_l2 AS BUILDING.NAME, \n");
//    	sb.append("\"BUILDUNIT\".FName_l2 AS BUILDUNIT.NAME, \n");
//    	sb.append("\"ROOM\".FName_l2 AS ROOM.NAME, \n");
//    	sb.append("\"PURCHASE\".FNumber AS PURCHASE.NUMBER, \n");
//    	sb.append("\"PRODUCTTYPE\".FName_l2 AS RODUCTTYPE.NAME, \n");
//    	sb.append("\"ROOMMODEL\".FName_l2 AS OOMMODEL.NAME, \n");
//    	sb.append("\"PURCHASE\".FContractTotalAmount AS CONTRACTTOTALAMOUNT, \n");
//    	sb.append("\"PURCHASE\".FToSignDate AS TOSIGNDATE, \n");
//    	sb.append("\"SALESMAN\".FName_l2 AS SALESMAN.NAME, \n");
//    	sb.append("\"PURCHASE\".FState AS PURCHASE.STATE \n");

    	sb.append("FROM T_SHE_Purchase AS \"PURCHASE\" \n");

    	sb.append("LEFT OUTER JOIN T_SHE_Room AS \"ROOM\" \n");
    	sb.append("ON \"PURCHASE\".FRoomID  =\"ROOM\".FID \n");

    	sb.append("LEFT OUTER JOIN T_ORG_BaseUnit AS \"ORGUNIT\" \n");
    	sb.append("ON \"PURCHASE\".FOrgUnitID = \"ORGUNIT\".FID \n");

    	sb.append("LEFT OUTER JOIN T_SHE_SellProject AS \"SELLPROJECT\" \n");
		sb.append("ON \"PURCHASE\".FSellProjectID = \"SELLPROJECT\".FID \n");

		sb.append("LEFT OUTER JOIN T_PM_User AS \"SALESMAN\" \n");
		sb.append("ON \"PURCHASE\".FSalesmanID = \"SALESMAN\".FID \n");

		sb.append("LEFT OUTER JOIN T_SHE_Building AS \"BUILDING\" \n");
		sb.append("ON \"ROOM\".FBuildingID = \"BUILDING\".FID \n");

		sb.append("LEFT OUTER JOIN T_SHE_BuildingUnit AS \"BUILDUNIT\" \n");
		sb.append("ON \"ROOM\".FBuildUnitID = \"BUILDUNIT\".FID \n");

		sb.append("LEFT OUTER JOIN T_FDC_ProductType AS \"PRODUCTTYPE\" \n");
		sb.append("ON \"ROOM\".FProductTypeID = \"PRODUCTTYPE\".FID \n");

		sb.append("LEFT OUTER JOIN T_SHE_RoomModel AS \"ROOMMODEL\" \n");
		sb.append("ON \"ROOM\".FRoomModelID = \"ROOMMODEL\".FID \n");
		
		sb.append("LEFT OUTER JOIN T_SHE_Subarea AS \"SUBAREA\" \n");
		sb.append("ON \"BUILDING\".FSubareaID = \"SUBAREA\".FID \n");
		
		sb.append(" where 1=1 and \"ROOM\".FisForSHE=1 \n");
		//sb.append(" and (\"ROOM\".FsellState='Purchase' or  \"ROOM\".FsellState='Sign') \n");
		sb.append(" and (\"PURCHASE\".FPurchaseState='PurchaseAudit' or \"PURCHASE\".FPurchaseState='PurchaseChange') \n");
		
		
		return sb;
    }
    
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("room.building.name"));
        sic.add(new SelectorItemInfo("room.building.subarea.name"));
        sic.add(new SelectorItemInfo("room.buildUnit.name"));
        sic.add(new SelectorItemInfo("room.buildUnit.id"));
        sic.add(new SelectorItemInfo("room.buildUnit.number"));
        sic.add(new SelectorItemInfo("room.displayName"));
        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("prePurchaseDate"));  //预定日期
        sic.add(new SelectorItemInfo("isEarnestInHouseAmount"));  //定金是否隶属于房款
        
        
        sic.add(new SelectorItemInfo("room.productType.name"));
        sic.add(new SelectorItemInfo("room.roomModel.name"));
        sic.add(new SelectorItemInfo("room.sellState"));
        sic.add(new SelectorItemInfo("contractTotalAmount"));
        sic.add(new SelectorItemInfo("toSignDate"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("contractTotalAmount"));
        sic.add(new SelectorItemInfo("payListEntry.seq"));
        sic.add(new SelectorItemInfo("payListEntry.id"));
        sic.add(new SelectorItemInfo("payListEntry.name"));
        sic.add(new SelectorItemInfo("payListEntry.number"));
        sic.add(new SelectorItemInfo("payListEntry.actPayAmount"));
        sic.add(new SelectorItemInfo("payListEntry.appDate"));
        
        sic.add(new SelectorItemInfo("payListEntry.actrevAmount"));
        sic.add(new SelectorItemInfo("payListEntry.appAmount"));
        
        sic.add(new SelectorItemInfo("payListEntry.actrevDate"));
        sic.add(new SelectorItemInfo("payListEntry.HasTransferredAmount"));
        sic.add(new SelectorItemInfo("payListEntry.HasRefundmentAmount"));
        sic.add(new SelectorItemInfo("payListEntry.HasAdjustedAmount"));
        
        ///
        sic.add(new SelectorItemInfo("payListEntry.actPayDate"));
        sic.add(new SelectorItemInfo("payListEntry.apAmount"));
        ///
        
        
        sic.add(new SelectorItemInfo("payListEntry.moneyDefine.id"));
		sic.add(new SelectorItemInfo("payListEntry.moneyDefine.number"));
		sic.add(new SelectorItemInfo("payListEntry.moneyDefine.name"));
		sic.add(new SelectorItemInfo("payListEntry.moneyDefine.moneyType"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("elsePayListEntry.id"));
        sic.add(new SelectorItemInfo("elsePayListEntry.number"));
        sic.add(new SelectorItemInfo("elsePayListEntry.name"));
       //
        sic.add(new SelectorItemInfo("elsePayListEntry.apAmount"));
       /// 
        sic.add(new SelectorItemInfo("elsePayListEntry.appAmount"));
        sic.add(new SelectorItemInfo("elsePayListEntry.appDate"));
        sic.add(new SelectorItemInfo("elsePayListEntry.actrevDate"));
        //
        sic.add(new SelectorItemInfo("elsePayListEntry.actrevAmount"));
        
        sic.add(new SelectorItemInfo("elsePayListEntry.HasTransferredAmount"));
        sic.add(new SelectorItemInfo("elsePayListEntry.HasRefundmentAmount"));
        sic.add(new SelectorItemInfo("elsePayListEntry.HasAdjustedAmount"));
        
        
        sic.add(new SelectorItemInfo("elsePayListEntry.actPayAmount"));
        sic.add(new SelectorItemInfo("elsePayListEntry.actPayDate"));
        sic.add(new SelectorItemInfo("elsePayListEntry.moneyDefine.id"));
        sic.add(new SelectorItemInfo("elsePayListEntry.moneyDefine.number"));
        sic.add(new SelectorItemInfo("elsePayListEntry.moneyDefine.name"));
        sic.add(new SelectorItemInfo("elsePayListEntry.moneyDefine.moneyType"));
        return sic;
    }
    
    private String getSetToStr(Set set){
    	StringBuffer sb = new StringBuffer();
    	if(set!=null && set.size()>0){
    		Iterator it = set.iterator();
    		while(it.hasNext()){
    			String str = (String)it.next();
    			sb.append(",'"+str+"'");
    		}
    		sb.delete(0, 1);
    	}
    	
    	return sb.toString();
    }
    
    
}