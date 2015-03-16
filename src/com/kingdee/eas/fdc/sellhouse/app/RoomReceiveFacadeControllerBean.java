package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class RoomReceiveFacadeControllerBean extends AbstractRoomReceiveFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomReceiveFacadeControllerBean");
    protected Map _getRoomReceiveData(Context ctx, Map map)throws BOSException, EASBizException {
    	Map roomMap = new HashMap();
    	try {
			IRowSet rowSet = (IRowSet)getRoomQueryData(ctx, map);   //获取可以用query取出的数据
			IRowSet sellAmountSet = (IRowSet)getSellAmount(ctx, map);   //获取 房间状态为签约的  补差款
			IRowSet phoneSet = (IRowSet)getNamesPhones(ctx, map);   //获取客户证件号码和电话号码
			IRowSet loanAmountSet = (IRowSet)getLoanAmount(ctx, map);   //获取‘按揭类房款’ 类数据
			IRowSet noLoanAmountSet = (IRowSet)getNoLoanAmount(ctx, map);   //获取‘非按揭类房款’ 类数据
			IRowSet accFundSet = (IRowSet)getAccFundAmount(ctx, map);   //获取‘公积金’ 类数据
			IRowSet lateFeeSet = (IRowSet)getLateFee(ctx, map);   //获取‘滞纳金’ 类数据
			IRowSet comCharSet = (IRowSet)getCommissionCharge(ctx, map);   //获取 ‘手续费’ 类数据
			IRowSet elseAmountSet = (IRowSet)getElseAmount(ctx, map);   //获取‘其他’ 类数据
			IRowSet fitmentAmountSet = (IRowSet)getFitmentAmount(ctx, map);   //获取‘装修款’ 类数据
			IRowSet replaceFeeSet = (IRowSet)getReplaceFee(ctx, map);   //获取‘代收费用’ 类数据
			IRowSet conArgSet = (IRowSet)getConArrearage(ctx, map);   //获取 ‘合同欠款’ 类数据
			IRowSet conBackSet = (IRowSet)getConComeBack(ctx, map);   //获取 ‘合同回款’ 类数据
			IRowSet overFlowSet = (IRowSet)getOverFlow(ctx, map);   //获取 ‘溢交金额’ 类数据
			IRowSet comsateSet = (IRowSet)getCompensate(ctx, map);   //获取 ‘应收补差金额’， ‘已收面积补差款’ 类数据
			 
			roomMap.put("rowSet", rowSet);   //获取可以用query取出的数据
			roomMap.put("sellAmountSet", sellAmountSet);   //获取 房间状态为签约的 补差款
			roomMap.put("phoneSet", phoneSet);   //获取客户证件号码和电话号码
			roomMap.put("loanAmountSet", loanAmountSet);   //获取‘按揭类房款’ 类数据
			roomMap.put("noLoanAmountSet", noLoanAmountSet);   //获取‘非按揭类房款’ 类数据
			roomMap.put("accFundSet", accFundSet);   //获取‘公积金’ 类数据
			roomMap.put("lateFeeSet", lateFeeSet);   //获取‘滞纳金’ 类数据
			roomMap.put("comCharSet", comCharSet);   //获取‘手续费’ 类数据
			roomMap.put("elseAmountSet", elseAmountSet);   //获取‘其他’ 类数据
			roomMap.put("fitmentAmountSet", fitmentAmountSet);   //获取‘装修款’ 类数据
			roomMap.put("replaceFeeSet", replaceFeeSet);   //获取‘代收费用’ 类数据
			roomMap.put("conArgSet", conArgSet);   //获取‘合同欠款’ 类数据
			roomMap.put("conBackSet", conBackSet);   //获取‘合同回款’ 类数据
			roomMap.put("overFlowSet", overFlowSet);  //获取 ‘溢交金额’ 类数据
			roomMap.put("comsateSet", comsateSet);  //获取 ‘应收补差金额’， ‘已收面积补差款’ 类数据
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return roomMap;
    }
    
    //获取可以用query取出的数据
    private IRowSet getRoomQueryData(Context ctx, Map map)throws BOSException, SQLException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("SELECT ROOM.FID AS ID, SELLPROJECT.FName_l2 AS projectName, BUILDING.FName_l2 AS buildingName, baseUnit.FName_l2 as orgName, subarea.FName_l2 as subareaName, BUILDUNIT.Fname_l2 AS unit,  " 
    			+ " ROOM.FDisplayName AS roomNumber,PURCHASE.FCustomerNames AS customerNames,PRODUCTTYPE.FName_l2 AS productType,ROOMMODEL.FName_l2 AS roomModel, " 
    			+ " ROOM.FBuildingArea AS buildingArea,ROOM.FActualBuildingArea AS actualBuildingArea,PURCHASE.FPurchaseDate AS purchaseDate, " 
    			+ " PURCHASE.FContractTotalAmount AS contractAmount,PAYTYPE.FName_l2 AS payType, " 
    			+ " SALESMAN.FName_l2 AS salesman,ROOM.FRoomArea AS roomArea,ROOM.FActualRoomArea AS actualRoomArea,SIGNCONTRACT.FContractNumber AS contractNumber, " 
    			+ " SIGNCONTRACT.FSignDate AS signDate,SIGNCONTRACT.FIsOnRecord AS isOnRecord,ROOM.FRoomPrice AS dealRoomPrice " 
    			+ " FROM T_SHE_Room AS ROOM " 
    			+ " LEFT OUTER JOIN T_SHE_RoomModel AS ROOMMODEL ON ROOM.FRoomModelID = ROOMMODEL.FID " 
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
    			+ " LEFT OUTER JOIN T_SHE_BuildingProperty AS BUILDINGPROPERTY ON ROOM.FBuildingPropertyID = BUILDINGPROPERTY.FID " 
    			+ " INNER JOIN T_SHE_Building AS BUILDING ON ROOM.FBuildingID = BUILDING.FID " 
    			+ " LEFT OUTER JOIN T_SHE_HopedDirection AS DIRECTION ON ROOM.FDirectionID = DIRECTION.FID " 
    			+ " LEFT OUTER JOIN T_SHE_SightRequirement AS SIGHT ON ROOM.FSightID = SIGHT.FID " 
    			//+ " LEFT OUTER JOIN T_SHE_RoomForm AS ROOMFORM ON ROOM.FRoomFormID = ROOMFORM.FID "
    			+ " LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID " 
    			//+ " LEFT OUTER JOIN T_SHE_ProductDetial AS PRODUCTDETIAL ON ROOM.FProductDetailID = PRODUCTDETIAL.FID " 
    			+ " LEFT OUTER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FLastPurchaseID = PURCHASE.FID " 
    			+ " LEFT OUTER JOIN T_SHE_RoomSignContract AS SIGNCONTRACT ON ROOM.FLastSignContractID = SIGNCONTRACT.FID "
    			+ " INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "
    			//+ " LEFT OUTER JOIN T_SHE_Subarea AS SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID "
    			+ " LEFT OUTER JOIN T_SHE_SHEPayType AS PAYTYPE ON PURCHASE.FPayTypeID = PAYTYPE.FID "
    			+ " LEFT OUTER JOIN T_PM_User AS SALESMAN ON PURCHASE.FSalesmanID = SALESMAN.FID "
    			+ " left outer join T_ORG_BaseUnit as baseUnit on baseUnit.FID=sellProject.FOrgUnitID"
    			+ " left outer join T_SHE_Subarea as subarea on BUILDING.FSubareaID=subarea.fid"
				+ " WHERE room.FIsForSHE=1 ");
    	builder.appendSql(" and (ROOM.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or ROOM.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  ROOM.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");
    	
    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and ROOM.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}

		IRowSet roomData = builder.executeQuery(ctx);
    	
    	return roomData;
    }
    
    //获取 房间状态为签约的  补差款
    private IRowSet getSellAmount(Context ctx, Map map)throws BOSException, SQLException{
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,areaCompensate.FActRevAmount sellAmount " 
				+ " from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
    			+ " LEFT OUTER JOIN T_SHE_RoomAreaCompensate AS roomArea ON room.FLastAreaCompensateID = roomArea.FID "
    			+ " LEFT OUTER JOIN T_SHE_AreaCompensateRevList AS areaCompensate ON areaCompensate.FheadId = roomArea.FID "
				+ " WHERE room.FIsForSHE=1 ");
		builder.appendSql(" and room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "' and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
		IRowSet sellAmountSet = builder.executeQuery(ctx);
    	
    	return sellAmountSet;
    }
    
    //获取 客户证件号码和电话号码
    private IRowSet getNamesPhones(Context ctx, Map map)throws BOSException, SQLException{
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,customer.FCertificateNumber CertificateNumber,customer.FPhone fphone,customer.FTel ftel " 
				+ " from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchaseCustomerInfo  purCustomer on purchase.fid = purCustomer.fheadid "
				+ " left join t_she_fdccustomer customer on purCustomer.FCustomerID = customer.fid "
//				+ " AND customer.FCertificateName = '1IDCard'"
				+ " WHERE room.FIsForSHE=1 ");
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
		IRowSet phoneSet = builder.executeQuery(ctx);
    	
    	return phoneSet;
    }
    
    //获取 ‘按揭类房款’ 类数据
    private IRowSet getLoanAmount(Context ctx, Map map)throws BOSException, SQLException{
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType= 'LoanAmount' ");

		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
		IRowSet accumulationFundSet = builder.executeQuery(ctx);
    	
    	return accumulationFundSet;
    }
    
    //获取 ‘非按揭类房款’ 类数据
    private IRowSet getNoLoanAmount(Context ctx, Map map)throws BOSException, SQLException{
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder
    	.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount," 
//    			+ " revList.FActRevAmount bYSamount,revList.FHasTransferredAmount bYZamount,revList.FHasRefundmentAmount bYQamount," 
    			+ " purchase.FisEarnestInHouseAmount isIn,moneyDefine.FMoneyType moneyType from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
//				+ " left join T_SHE_RoomAreaCompensate roomArea on room.FLastAreaCompensateID=roomArea.fid"
//				+ " left join T_SHE_AreaCompensateRevList revList on revList.FheadId=roomArea.fid"
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType in('"+MoneyTypeEnum.EARNESTMONEY_VALUE+"','"+MoneyTypeEnum.FISRTAMOUNT_VALUE+"','"+MoneyTypeEnum.HOUSEAMOUNT_VALUE+"') ");

		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
		IRowSet purToHouseSet = builder.executeQuery(ctx);
    	
    	return purToHouseSet;
    }
    
    //获取 ‘公积金’ 类数据
    private IRowSet getAccFundAmount(Context ctx, Map map)throws BOSException, SQLException{
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType= '"+MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"' ");

		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
		IRowSet accumulationFundSet = builder.executeQuery(ctx);
    	
    	return accumulationFundSet;
    }
    
    //获取 ‘滞纳金’ 类数据
    private IRowSet getLateFee(Context ctx, Map map)throws BOSException, SQLException{
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchaseElsePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType= '"+MoneyTypeEnum.LATEFEE_VALUE+"' ");

		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
		IRowSet lateFeeSet = builder.executeQuery(ctx);
    	
    	return lateFeeSet;
    }
    
    //获取 ‘手续费’ 类数据
    private IRowSet getCommissionCharge(Context ctx, Map map)throws BOSException, SQLException{
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchaseElsePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType= '"+MoneyTypeEnum.COMMISSIONCHARGE_VALUE+"' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet commissionChargeSet = builder.executeQuery(ctx);
    	
    	return commissionChargeSet;
    }
    
    //获取 ‘其他’ 类数据
    private IRowSet getElseAmount(Context ctx, Map map)throws BOSException, SQLException{
    	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchaseElsePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType= '"+MoneyTypeEnum.ELSEAMOUNT_VALUE+"' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet elseAmountSet = builder.executeQuery(ctx);
    	
    	return elseAmountSet;
    }
    
    //获取 ‘装修款’ 类数据
    private IRowSet getFitmentAmount(Context ctx, Map map)throws BOSException, SQLException{
    	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchaseElsePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType= '"+MoneyTypeEnum.FITMENTAMOUNT_VALUE+"' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet fitmentAmountSet = builder.executeQuery(ctx);
    	
    	return fitmentAmountSet;
    }
    
    //获取 ‘代收费用’ 类数据
    private IRowSet getReplaceFee(Context ctx, Map map)throws BOSException, SQLException{
    	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount,payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchaseElsePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 AND moneyDefine.FMoneyType= '"+MoneyTypeEnum.REPLACEFEE_VALUE+"' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet replaceFeeSet = builder.executeQuery(ctx);
    	
    	return replaceFeeSet;
    }
    
    //获取 ‘合同欠款’ 类数据
    private IRowSet getConArrearage(Context ctx, Map map)throws BOSException, SQLException{
    	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql("select room.fid roomid,payList.fappAmount sumRevAmount,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount," 
				+ "payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join T_SHE_Purchase purchase on room.FLastPurchaseID= purchase.fid "
				+ " left join T_SHE_PurchasePayListEntry payList on payList.FHeadID=purchase.fid "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 and moneyDefine.FMoneyType != 'PreconcertMoney' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "')");
		builder.appendSql(" and room.FHouseProperty != '"
				+ HousePropertyEnum.ATTACHMENT_VALUE + "' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet conArrearageSet = builder.executeQuery(ctx);
    	
    	return conArrearageSet;
    }
    
    //获取 ‘合同回款’ 类数据
    private IRowSet getConComeBack(Context ctx, Map map)throws BOSException, SQLException{
    	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount," 
				+ " payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount, " 
				+ " purchase.FisEarnestInHouseAmount isIn,moneyDefine.FMoneyType moneyType from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 and moneyDefine.FMoneyType != 'PreconcertMoney' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet conComeBackSet = builder.executeQuery(ctx);
    	
    	return conComeBackSet;
    }
    
    //获取 ‘溢交金额’ 类数据
    private IRowSet getOverFlow(Context ctx, Map map)throws BOSException, SQLException{
    	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql("select room.fid roomid,payList.fappAmount sumRevAmount,payList.FActRevAmount YSamount,payList.FHasTransferredAmount YZamount," 
				+ "payList.FHasRefundmentAmount YQamount,payList.FHasAdjustedAmount YTamount from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join T_SHE_Purchase purchase on room.FLastPurchaseID= purchase.fid "
				+ " left join T_SHE_PurchasePayListEntry payList on payList.FHeadID=purchase.fid "
				+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
				+ " where room.FIsForSHE=1 and moneyDefine.FMoneyType != 'PreconcertMoney' ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet overFlowSet = builder.executeQuery(ctx);
    	
    	return overFlowSet;
    }
    
    //获取 ‘应收补差金额’,‘实收面积补差款’  类数据
    private IRowSet getCompensate(Context ctx, Map map)throws BOSException, SQLException{
    	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
		.appendSql(" select room.fid roomid,roomArea.FCompensateAmount FAppAmount,areaList.FActRevAmount FActRevAmount," 
				+ " areaList.FHasTransferredAmount YZAmount,areaList.FHasRefundmentAmount YQAmount " 
				+ " from t_she_areacompensaterevList areaList "
    			+ " LEFT OUTER JOIN T_SHE_RoomAreaCompensate AS roomArea on areaList.FheadId=roomArea.fid "
				+ " left join t_she_room room on room.FID=roomArea.froomid "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON room.FBuildUnitID = BUILDUNIT.FID"
				+ " where room.FIsForSHE=1 ");
		
		builder.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");

    	//根据树过滤
    	Set buildingIds = (Set)map.get("buildingIds");
    	Iterator iter = (Iterator)buildingIds.iterator();
    	String buildid = "";
    	while(iter.hasNext()){
    		if("".equals(buildid) || buildid == null){
    			buildid = "'"+(String)iter.next()+"'";
    		}else{
    			buildid = buildid+",'"+(String)iter.next()+"'";
    		}
    	}
    	
    	builder.appendSql(" and room.FBuildingID in ( "+buildid+" ) ");
    	int  unitNumber = ((Integer)map.get("unitNumber")).intValue();
    	if(unitNumber != 0){
        	builder.appendSql(" and BUILDUNIT.Fseq = "+unitNumber+" ");
    	}
    	
    	IRowSet elseAmountSet = builder.executeQuery(ctx);
    	
    	return elseAmountSet;
    }
}