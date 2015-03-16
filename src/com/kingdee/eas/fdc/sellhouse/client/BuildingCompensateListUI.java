/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.util.UIConfigUtility;

/**
 * output class name
 */
public class BuildingCompensateListUI extends AbstractBuildingCompensateListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BuildingCompensateListUI.class);

	/**
	 * output class constructor
	 */
	public BuildingCompensateListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	public ICoreBase getBillInterface() throws Exception {
		return BuildingCompensateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return BuildingCompensateEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BuildingCompensateFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionEdit, actionRemove, actionCreateTo, actionTraceUp,
				actionTraceDown, actionCopyTo }, false);
		menuBiz.setVisible(false);
//		menuWorkFlow.setVisible(false);
		menuEdit.setVisible(false);
	}

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		} else {
			return UIFactoryName.NEWTAB;
		}
	}
}