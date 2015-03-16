/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;

/**
 * output class name
 */
public class PushManageSetViewUI extends AbstractPushManageSetViewUI {
	private static final Logger logger = CoreUIObject.getLogger(PushManageSetViewUI.class);

	/**
	 * output class constructor
	 */
	public PushManageSetViewUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		RoomDisplaySetting setting = new RoomDisplaySetting();
		
		this.kDLabel1.setBackground(setting.getBaseRoomSetting().getInitColor());
		this.kDLabel1.setOpaque(true);
		this.kDLabel1.setText(" н╢мфел(" + PushManageListUI.initCount + ")");
		
		this.kDLabel2.setBackground(setting.getBaseRoomSetting().getOnShowColor());
		this.kDLabel2.setOpaque(true);
		this.kDLabel2.setText(" рямфел(" + PushManageListUI.pushCount + ")");
		
	}

	public static void insertUIToScrollPanel(KDScrollPane panel) throws UIException {
		UIContext uiContext = new UIContext();
		CoreUIObject detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(PushManageSetViewUI.class.getName(), uiContext, null, "VIEW");
		panel.setViewportView(detailUI);
		panel.setKeyBoardControl(true);
	}
}