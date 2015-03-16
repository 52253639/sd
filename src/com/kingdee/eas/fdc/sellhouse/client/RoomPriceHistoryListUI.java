/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustHisFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomPriceHistoryListUI extends AbstractRoomPriceHistoryListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomPriceHistoryListUI.class);

	public RoomPriceHistoryListUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();

    	this.fillBasalInfo();
    	HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString())));
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString())));
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString())));
		
		this.txtActualBuildingArea.setPrecision(2);
		this.txtActualRoomArea.setPrecision(2);
		this.txtBuildingArea.setPrecision(2);
		this.txtPlanBuildingArea.setPrecision(2);
		this.txtPlanRoomArea.setPrecision(2);
		this.txtRoomArea.setPrecision(2);
		
		txtActualBuildingArea.setRemoveingZeroInDispaly(false);
		txtActualRoomArea.setRemoveingZeroInDispaly(false);
		txtRoomArea.setRemoveingZeroInDispaly(false);
		txtBuildingArea.setRemoveingZeroInDispaly(false);
		txtPlanBuildingArea.setRemoveingZeroInDispaly(false);
		txtPlanRoomArea.setRemoveingZeroInDispaly(false);
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getClickCount() != 2)
			return;
		int rowIndex = e.getRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		if (row == null)
			return;

		String roomPriceBillId = null;
		if (row.getCell("billId").getValue() != null) {
			roomPriceBillId = row.getCell("billId").getValue().toString();
		}

		if (roomPriceBillId != null) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", roomPriceBillId);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(RoomPriceAdjustEditUI.class.getName(), uiContext,
							null, "VIEW");
			uiWindow.show();
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		String roomId = this.getUIContext().get("ID").toString();
		viewInfo = (EntityViewInfo) mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
		// 合并查询条件
		try {

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			this.handUIException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPriceAdjustHisFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * 填充房间的基本信息
	 * 
	 * @throws UuidException
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 */
	private void fillBasalInfo() throws EASBizException, BOSException,
			UuidException {
		String roomId = (String) this.getUIContext().get("ID");
		if (roomId == null)
			return;

		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("roomModel.*");
		sels.add("buildingProperty.*");
		sels.add("direction.*");
		sels.add("productType.*");

		RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(
				new ObjectUuidPK(BOSUuid.read(roomId)), sels);
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtPlanBuildingArea.setValue(room.getPlanBuildingArea());
		this.txtPlanRoomArea.setValue(room.getPlanRoomArea());
		this.f7Direction.setValue(room.getDirection());
		this.f7ProductType.setValue(room.getProductType());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.txtRoomNumber.setText(room.getNumber());
		this.comboRoomState.setSelectedItem(room.getSellState());
	}
}