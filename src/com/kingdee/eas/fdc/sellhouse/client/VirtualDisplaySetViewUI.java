/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class VirtualDisplaySetViewUI extends AbstractVirtualDisplaySetViewUI {
	private static final Logger logger = CoreUIObject.getLogger(VirtualDisplaySetViewUI.class);

	/**
	 * output class constructor
	 */
	public VirtualDisplaySetViewUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		RoomDisplaySetting setting = new RoomDisplaySetting();

		 this.kDLabel7.setVisible(false);
		 this.kDLabel1.setVisible(false);
		 this.kDLabel3.setVisible(false);
		 this.kDLabel5.setVisible(false);
		 this.kDLabel6.setVisible(false);

	

		this.kDLabel2.setBackground(setting.getBaseRoomSetting().getOnShowColor());
		this.kDLabel2.setOpaque(true);
		this.kDLabel2.setText("      ¥˝ €");

		

		this.kDLabel4.setBackground(setting.getBaseRoomSetting().getSignColor());
		this.kDLabel4.setOpaque(true);
		this.kDLabel4.setText("      “— €");

		
	}

	public static void insertUIToScrollPanel(KDScrollPane panel) throws UIException {
		UIContext uiContext = new UIContext();
		CoreUIObject detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(VirtualDisplaySetViewUI.class.getName(), uiContext, null, "VIEW");
		panel.setViewportView(detailUI);
		panel.setKeyBoardControl(true);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
}