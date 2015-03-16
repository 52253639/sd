/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SalemanStatTrackRptUI extends AbstractSalemanStatTrackRptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SellStatRoomRptUI.class);

	/**
	 * output class constructor
	 */
	public SalemanStatTrackRptUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionQuery.setVisible(false);

		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("dealPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealTotalAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("dealTotalAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

	}

	protected void beforeExcutQuery(EntityViewInfo arg0) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sellState", RoomSellStateEnum.INIT_VALUE,
						CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("sellState", RoomSellStateEnum.ONSHOW_VALUE,
						CompareType.NOTEQUALS));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("sellState",
								RoomSellStateEnum.KEEPDOWN_VALUE,
								CompareType.NOTEQUALS));
		String buildingId = (String) this.getUIContext().get("buildingId");
		if (buildingId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
		}
		arg0.setFilter(filter);
		arg0.getSorter().add(new SorterItemInfo("number"));
		super.beforeExcutQuery(arg0);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	/**
	 * 根据id显示窗体
	 */
	public static void showUI(IUIObject ui, String buildingId)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("buildingId", buildingId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SellStatRoomRptUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
	}

}