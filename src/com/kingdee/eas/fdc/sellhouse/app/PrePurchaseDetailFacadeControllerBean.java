package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class PrePurchaseDetailFacadeControllerBean extends
		AbstractPrePurchaseDetailFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PrePurchaseDetailFacadeControllerBean");

	protected Map _getPrePurchaseData(Context ctx, Map map)
			throws BOSException, EASBizException {
		Map dataMap = new HashMap();
		try {
			IRowSet querySet = (IRowSet) getQueryData(ctx, map);
			IRowSet prePurchaseSet = (IRowSet) getPrePurchase(ctx, map);
			IRowSet amountSet = (IRowSet) getEarnestMoney(ctx, map);
			IRowSet amount2Set = (IRowSet) getPreconcertMoney(ctx, map);
			dataMap.put("querySet", querySet);
			dataMap.put("prePurchaseSet", prePurchaseSet);
			dataMap.put("amountSet", amountSet);
			dataMap.put("amount2Set", amount2Set);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	private IRowSet getQueryData(Context ctx, Map map) throws SQLException,
			BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);

		builder.appendSql("SELECT ROOM.FID AS roomId, ORGUNIT.FName_l2 AS orgName, SELLPROJECT.FName_l2 AS projectName, BUILDING.FName_l2 AS builderName, "
						+ " PRODUCTTYPE.FName_l2 AS typeName, BUILDINGPROPERTY.FName_l2 AS propertyName, PRODUCTDETAIL.FName_l2 AS productDetail, "
						+ " BUILDINGUNIT.FName_l2 AS unit, ROOM.FFloor AS floor, ROOM.FBuildingArea AS buildingArea, ROOM.FRoomArea AS roomArea, "
						+ " ROOM.FActualBuildingArea AS actualBuildingArea, ROOM.FActualRoomArea AS actualRoomArea, ROOM.FDisplayName AS number, "
						+ " ROOM.FStandardTotalAmount AS standardAmount, ROOMMODEL.FName_l2 AS modelName, ROOM.FBuildingID AS buildingId, SUBAREA.FName_l2 AS subArea "
						+ " FROM T_SHE_Room AS ROOM "
						+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDINGUNIT ON ROOM.FBuildUnitID = BUILDINGUNIT.FID "
						+ " LEFT OUTER JOIN T_SHE_ProductDetial AS PRODUCTDETAIL ON ROOM.FProductDetailID = PRODUCTDETAIL.FID "
						+ " LEFT OUTER JOIN T_SHE_BuildingProperty AS BUILDINGPROPERTY ON ROOM.FBuildingPropertyID = BUILDINGPROPERTY.FID "
						+ " LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID "
						+ " LEFT OUTER JOIN T_SHE_Building AS BUILDING ON ROOM.FBuildingID = BUILDING.FID "
						+ " LEFT OUTER JOIN T_SHE_Subarea AS SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID"
						+ " LEFT OUTER JOIN T_SHE_RoomForm AS ROOMFORM ON ROOM.FRoomFormID = ROOMFORM.FID "
						+ " LEFT OUTER JOIN T_SHE_HopedDirection AS DIRECTION ON ROOM.FDirectionID = DIRECTION.FID "
						+ " LEFT OUTER JOIN T_SHE_RoomModel AS ROOMMODEL ON ROOM.FRoomModelID = ROOMMODEL.FID "
						+ " LEFT OUTER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "
						+ " LEFT OUTER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		builder.appendSql(" where " + map.get("filter").toString()
				+ " and ROOM.FIsForSHE=1 order by SELLPROJECT.FName_l2,BUILDING.FName_l2,BUILDINGUNIT.FName_l2,ROOM.FDisplayName asc ");

		IRowSet querySet = builder.executeQuery(ctx);

		return querySet;
	}

	private IRowSet getPrePurchase(Context ctx, Map map) throws SQLException,
			BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select room.fid roomid,purchase.FCustomerNames customerNames," 
				+ " case when (purchase.FPrePurchaseDate is null) then purchase.FPurchaseDate else purchase.FPrePurchaseDate end as purchaseDate, "
						+ " purchase.FPrePurchaseValidDate valaidDate "
						+ " from t_she_room room "
						+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDINGUNIT ON ROOM.FBuildUnitID = BUILDINGUNIT.FID "
						+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid ");
		// + " where room.fsellstate = 'PrePurchase'");
		builder.appendSql(" where " + map.get("filter").toString()
				+ " and ROOM.FIsForSHE=1 ");
		IRowSet prePurchaseSet = builder.executeQuery(ctx);
		return prePurchaseSet;
	}

	private IRowSet getEarnestMoney(Context ctx, Map map) throws SQLException,
			BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select room.fid roomid, sum(case when (payList.FActRevAmount is null) then 0 else payList.FActRevAmount end "
						+ " - case when (payList.FHasTransferredAmount is null) then 0 else payList.FHasTransferredAmount end "
						+ " - case when (payList.FHasRefundmentAmount is null) then 0 else payList.FHasRefundmentAmount end) as amount "
						+ " from t_she_room room "
						+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
						+ " left join T_SHE_PurchasePayListEntry payList on payList.FHeadID = purchase.fid "
						+ " left join t_she_moneyDefine money on payList.FMoneyDefineId=money.fid "
						+ " where money.FMoneyType ='"+MoneyTypeEnum.EARNESTMONEY_VALUE+"' and room.fsellstate = '"+RoomSellStateEnum.PREPURCHASE_VALUE+"' ");
		builder.appendSql(" group by room.fid ");
		IRowSet amountSet = builder.executeQuery(ctx);

		return amountSet;
	}

	private IRowSet getPreconcertMoney(Context ctx, Map map)
			throws SQLException, BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select room.fid roomid, (sum(case when (payList.FActRevAmount is null) then 0 else payList.FActRevAmount end) "
						+ " - sum(case when (payList.FHasTransferredAmount is null) then 0 else payList.FHasTransferredAmount end) "
						+ " - sum(case when (payList.FHasRefundmentAmount is null) then 0 else payList.FHasRefundmentAmount end)) as amount "
						+ " from t_she_room room "
						+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
						+ " left join T_SHE_PurchasePayListEntry payList on payList.FHeadID = purchase.fid "
						+ " left join t_she_moneyDefine money on payList.FMoneyDefineId=money.fid "
						+ " where money.FMoneyType ='"+MoneyTypeEnum.PRECONCERTMONEY_VALUE+"' and room.fsellstate = '"+RoomSellStateEnum.PREPURCHASE_VALUE+"' ");
		builder.appendSql(" group by room.fid ");
		IRowSet amount2Set = builder.executeQuery(ctx);

		return amount2Set;
	}
}