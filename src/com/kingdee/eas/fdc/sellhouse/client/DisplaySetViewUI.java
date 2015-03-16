/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;

/**
 * output class name
 */
public class DisplaySetViewUI extends AbstractDisplaySetViewUI {
	private static final Logger logger = CoreUIObject.getLogger(DisplaySetViewUI.class);
	/**
	 * output class constructor
	 */
	public DisplaySetViewUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		RoomDisplaySetting setting = new RoomDisplaySetting();
		this.kDLabel7.setVisible(false);
//		this.kDLabel7.setBackground(setting.getBaseRoomSetting().getNoSellhouseColor());
//		this.kDLabel7.setOpaque(true);
//		this.kDLabel7.setText(" 非售楼(" + SellControlUI.noSellhouseCount + ")");
		this.kDLabel1.setBackground(setting.getBaseRoomSetting().getInitColor());
		this.kDLabel1.setOpaque(true);
		this.kDLabel1.setText(" 未推盘(" + SellControlUI.initCount + ")");
		this.kDLabel2.setBackground(setting.getBaseRoomSetting().getOnShowColor());
		this.kDLabel2.setOpaque(true);
		this.kDLabel2.setText(" 待售(" + SellControlUI.onShowCount + ")");
		this.kDLabel5.setBackground(setting.getBaseRoomSetting().getPrePurColor());
		this.kDLabel5.setOpaque(true);
		this.kDLabel5.setText(" 预定(" + SellControlUI.prePur + ")");
		this.kDLabel6.setBackground(setting.getBaseRoomSetting().getPurColor());
		this.kDLabel6.setOpaque(true);
		this.kDLabel6.setText(" 认购(" + SellControlUI.purchaseCount + ")");
		this.kDLabel8.setBackground(setting.getBaseRoomSetting().getSignColor());
		this.kDLabel8.setOpaque(true);
		this.kDLabel8.setText(" 签约(" + SellControlUI.signContractCount + ")");
		this.kDLabel3.setBackground(setting.getBaseRoomSetting().getKeepDownColor());
		this.kDLabel3.setOpaque(true);
		this.kDLabel3.setText(" 预留(" + SellControlUI.keepDownCount + ")");
		this.kDLabel4.setBackground(setting.getBaseRoomSetting().getSincerPurColor());
		this.kDLabel4.setOpaque(true);
		this.kDLabel4.setText(" 排号(" + SellControlUI.sincerPurCount + ")");
		//by zgy 增加房间显示状态 飞鹰计划   需求：刘威
		this.kDLabel9.setVisible(false);
//		this.kDLabel9.setBackground(setting.getBaseRoomSetting().getSinReColor());
//		this.kDLabel9.setOpaque(true);
//		this.kDLabel9.setText(" 认购收款(" + SellControlUI.sinReCount + ")");	
		
		this.kDLabel10.setBackground(setting.getBaseRoomSetting().getControlColor());
		this.kDLabel10.setOpaque(true);
		this.kDLabel10.setText(" 销控(" + SellControlUI.controlCount + ")");
	}

	public static void insertUIToScrollPanel(KDScrollPane panel) throws UIException {
		UIContext uiContext = new UIContext();
		CoreUIObject detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(DisplaySetViewUI.class.getName(), uiContext, null, "VIEW");
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