/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class HopedRoomsCommerceChanceListUI extends AbstractHopedRoomsCommerceChanceListUI {

	private static final Logger logger = CoreUIObject.getLogger(HopedRoomsCommerceChanceListUI.class);

	public void onLoad() throws Exception {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		String unit = (String) this.getUIContext().get("unit");
		String seq = (String) this.getUIContext().get("seq");
		String roomId = (String) this.getUIContext().get("roomId");
		String buildingId = (String) this.getUIContext().get("buildingId");
		String sellProjectId = (String) this.getUIContext().get("sellProjectId");
		String saleProjectId = (String) this.getUIContext().get("saleProjectId");
		String orgUnitId = (String) this.getUIContext().get("orgUnitId");

		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql(" SELECT ").
		appendSql(" ROOM.FID AS id, ").
		appendSql(" HOPEDROOMS.FSeq AS seq, ").
		appendSql(" ROOM.FNumber AS roomNumber, ").
		appendSql(" ROOMMODEL.FName_l2 AS model, ").
		appendSql(" BUILDING.FName_l2 AS building, ").
		appendSql(" ROOM.FUnit AS unit, ").
		appendSql(" ROOM.FSellState AS saleState, ").
		appendSql(" ROOM.FTenancyState AS tenancyState, ").
		appendSql(" HOPEDROOMS.FPlanToChange AS planToChange, ").
		appendSql(" COMMERCECHANCE.FNumber AS commerceNumber, ").
		appendSql(" COMMERCECHANCE.FName AS commerceName, ").
		appendSql(" COMMERCECHANCE.FPhoneNumber commercePhone, ").			
		appendSql(" FDCCUSTOMER.FName_l2 AS fdcCostomer, ").
		appendSql(" SALEMAN.FName_l2 AS saleMan, ").
		appendSql(" SALEPROJECT.FName_l2 AS saleProject, ").	
		appendSql(" SELLPROJECT.FName_l2 AS sellProject, ").	
		appendSql(" COMMERCECHANCE.FCommerceDate AS commerceDate, ").
		appendSql(" COMMERCELEVEL.FName_l2 AS commerceLevel "). 			
		appendSql(" FROM T_SHE_CommerceChance AS COMMERCECHANCE "). 		
		appendSql(" INNER JOIN T_SHE_CommerceRoomEntry AS HOPEDROOMS "). 
		appendSql(" ON COMMERCECHANCE.FID = HOPEDROOMS.FCommerceChanceID "). 
		appendSql(" LEFT JOIN T_SHE_SellProject AS SALEPROJECT "). 
		appendSql(" ON COMMERCECHANCE.FSellProjectID = SALEPROJECT.FID ").
		appendSql(" LEFT JOIN T_SHE_FDCCustomer AS FDCCUSTOMER "). 
		appendSql(" ON COMMERCECHANCE.FFdcCustomerID = FDCCUSTOMER.FID "). 
		appendSql(" LEFT JOIN T_SHE_CommerceLevel AS COMMERCELEVEL "). 
		appendSql(" ON COMMERCECHANCE.FCommerceLevelID = COMMERCELEVEL.FID "). 			
		appendSql(" LEFT JOIN T_PM_User AS SALEMAN ").
		appendSql(" ON COMMERCECHANCE.FSaleManId = SALEMAN.FID ").	
		appendSql(" LEFT JOIN T_SHE_Room AS ROOM "). 
		appendSql(" ON HOPEDROOMS.FRoomID = ROOM.FID "). 
		appendSql(" LEFT JOIN T_SHE_RoomModel AS ROOMMODEL "). 
		appendSql(" ON ROOM.FRoomModelID = ROOMMODEL.FID "). 
		appendSql(" LEFT JOIN T_SHE_Building AS BUILDING "). 
		appendSql(" ON ROOM.FBuildingID = BUILDING.FID ").
		appendSql(" LEFT JOIN T_SHE_SellProject AS SELLPROJECT "). 
		appendSql(" ON BUILDING.FSellProjectID = SELLPROJECT.FID "). 
		appendSql(" LEFT JOIN T_ORG_BaseUnit AS ORGUNIT "). 
		appendSql(" ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID "). 
		appendSql(" where COMMERCECHANCE.FCommerceStatus='Intent'");
		this.appendStrFilterSql(detailBuilder,"HOPEDROOMS.FSeq", seq);
		this.appendStrFilterSql(detailBuilder,"ROOM.FUnit", unit);
		this.appendStrFilterSql(detailBuilder,"ROOM.FID", roomId);
		this.appendStrFilterSql(detailBuilder,"BUILDING.FID", buildingId);
		this.appendStrFilterSql(detailBuilder,"SELLPROJECT.FID", sellProjectId);
		this.appendStrFilterSql(detailBuilder,"SALEPROJECT.FID", saleProjectId);
		this.appendStrFilterSql(detailBuilder,"ORGUNIT.FID", orgUnitId);
		IRowSet detailSet = detailBuilder.executeQuery();
		while (detailSet.next()) {
			IRow row = this.tblMain.addRow();
			row.getCell("id").setValue(detailSet.getString("id"));
			row.getCell("seq").setValue("第" + detailSet.getString("seq") + "意向商机");
			row.getCell("roomNumber").setValue(detailSet.getString("roomNumber"));
			row.getCell("model").setValue(detailSet.getString("model"));
			row.getCell("building").setValue(detailSet.getString("building"));
			row.getCell("unit").setValue(detailSet.getString("unit"));
			if (RoomSellStateEnum.INIT_VALUE.equals(detailSet.getString("saleState"))) {
				row.getCell("saleState").setValue(RoomSellStateEnum.Init);
			} else if (RoomSellStateEnum.KEEPDOWN_VALUE.equals(detailSet.getString("saleState"))) {
				row.getCell("saleState").setValue(RoomSellStateEnum.KeepDown);
			} else if (RoomSellStateEnum.ONSHOW_VALUE.equals(detailSet.getString("saleState"))) {
				row.getCell("saleState").setValue(RoomSellStateEnum.OnShow);
			} else if (RoomSellStateEnum.PREPURCHASE_VALUE.equals(detailSet.getString("saleState"))) {
				row.getCell("saleState").setValue(RoomSellStateEnum.PrePurchase);
			} else if (RoomSellStateEnum.PURCHASE_VALUE.equals(detailSet.getString("saleState"))) {
				row.getCell("saleState").setValue(RoomSellStateEnum.Purchase);
			} else if (RoomSellStateEnum.SIGN_VALUE.equals(detailSet.getString("saleState"))) {
				row.getCell("saleState").setValue(RoomSellStateEnum.Sign);
			} else {
				row.getCell("saleState").setValue(null);
			}

			if (TenancyStateEnum.CONTINUETENANCY_VALUE.equals(detailSet.getString("tenancyState"))) {
				row.getCell("tenancyState").setValue(TenancyStateEnum.continueTenancy);
			} else if (TenancyStateEnum.ENLARGETENANCY_VALUE.equals(detailSet.getString("tenancyState"))) {
				row.getCell("tenancyState").setValue(TenancyStateEnum.enlargeTenancy);
			} else if (TenancyStateEnum.KEEPTENANCY_VALUE.equals(detailSet.getString("tenancyState"))) {
				row.getCell("tenancyState").setValue(TenancyStateEnum.keepTenancy);
			} else if (TenancyStateEnum.NEWTENANCY_VALUE.equals(detailSet.getString("tenancyState"))) {
				row.getCell("tenancyState").setValue(TenancyStateEnum.newTenancy);
			} else if (TenancyStateEnum.SINCEROBLIGATE_VALUE.equals(detailSet.getString("tenancyState"))) {
				row.getCell("tenancyState").setValue(TenancyStateEnum.sincerObligate);
			} else if (TenancyStateEnum.UNTENANCY_VALUE.equals(detailSet.getString("tenancyState"))) {
				row.getCell("tenancyState").setValue(TenancyStateEnum.unTenancy);
			} else if (TenancyStateEnum.WAITTENANCY_VALUE.equals(detailSet.getString("tenancyState"))) {
				row.getCell("tenancyState").setValue(TenancyStateEnum.waitTenancy);
			} else {
				row.getCell("tenancyState").setValue(null);
			}

			row.getCell("planToChange").setValue(Boolean.FALSE);
			if ("1".equals(detailSet.getString("planToChange"))) {
				row.getCell("planToChange").setValue(Boolean.TRUE);
			}

			row.getCell("commerceNumber").setValue(detailSet.getString("commerceNumber"));
			row.getCell("commerceName").setValue(detailSet.getString("commerceName"));
			row.getCell("commercePhone").setValue(detailSet.getString("commercePhone"));
			row.getCell("fdcCostomer").setValue(detailSet.getString("fdcCostomer"));
			row.getCell("saleMan").setValue(detailSet.getString("saleMan"));			
			row.getCell("saleProject").setValue(detailSet.getString("saleProject"));
			row.getCell("sellProject").setValue(detailSet.getString("sellProject"));
			row.getCell("commerceDate").setValue(detailSet.getDate("commerceDate"));
			row.getCell("commerceLevel").setValue(detailSet.getString("commerceLevel"));
		}
		this.tblMain.getMergeManager().mergeBlock(9, 0, this.tblMain.getRowCount() - 1, 16, KDTMergeManager.FREE_MERGE);

	}

	/**
	 * 添加系统属性过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendStrFilterSql(FDCSQLBuilder builder, String proName, String proValue) throws Exception {
		if (proName != null && proValue != null) {
			builder.appendSql(" and " + proName + " = ? ");
			builder.addParam(proValue);
		}
	}

	/**
	 * output class constructor
	 */
	public HopedRoomsCommerceChanceListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

}