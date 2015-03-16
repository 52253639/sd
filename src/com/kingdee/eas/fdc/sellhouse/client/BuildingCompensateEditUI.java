/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class BuildingCompensateEditUI extends AbstractBuildingCompensateEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BuildingCompensateEditUI.class);

	/**
	 * output class constructor
	 */
	public BuildingCompensateEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		BuildingCompensateInfo objectValue = new BuildingCompensateInfo();
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BuildingCompensateFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();
		String buildingId = null;
		if (getUIContext().get("buildingId") != null)
			buildingId = getUIContext().get("buildingId").toString();
		else if (editData != null && editData.getBuilding() != null)
			buildingId = editData.getBuilding().getId().toString();
		UIContext uiContextBuilding = new UIContext(ui);
		uiContextBuilding.put(UIContext.ID, buildingId);
		uiContextBuilding.put("buildingId", buildingId);
		CoreUIObject plUI = null;
		try {
			plUI = (CoreUIObject) UIFactoryHelper.initUIObject(
					BuildingCompensatePlusEditUI.class.getName(),
					uiContextBuilding, null, "VIEW");
		} catch (UIException e) {
			logger.error(e);
		}
		sclRoomInfo.setViewportView(plUI);
		sclRoomInfo.setKeyBoardControl(true);
		// sclRoomInfo.setAutoscrolls(true);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionEdit, actionSave, actionSubmit, actionSubmitOption,
				actionCopy, actionRemove, actionFirst, actionLast, actionPre,
				actionNext, actionCancel, actionCancelCancel, actionPrint,
				actionPrintPreview, actionCreateFrom, actionCreateTo,
				actionAddLine, actionInsertLine, actionRemoveLine,
				actionTraceDown, actionTraceUp }, false);
		menuBiz.setVisible(false);
		menuEdit.setVisible(false);
		menuView.setVisible(false);
		menuTable1.setVisible(false);
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return null;
	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return null;
	}
}