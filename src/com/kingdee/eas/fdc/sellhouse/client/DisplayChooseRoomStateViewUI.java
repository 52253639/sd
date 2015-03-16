/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class DisplayChooseRoomStateViewUI extends AbstractDisplayChooseRoomStateViewUI
{
    private static final Logger logger = CoreUIObject.getLogger(DisplayChooseRoomStateViewUI.class);
    
    /**
     * output class constructor
     */
    public DisplayChooseRoomStateViewUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		RoomDisplaySetting setting = new RoomDisplaySetting();
		
		
//		this.kDLabel4.setBackground(Color.blue);
//		this.kDLabel4.setOpaque(true);
//		this.kDLabel4.setText(" 非售楼(" + SellControlUI.noSellhouseCount + ")");
		this.kDLabel4.setVisible(false);
		this.kDLabel4.setEnabled(false);
		
		this.kDLabel1.setBackground(ChooseRoomDisplaySetting.chooseRoomSelled);
		this.kDLabel1.setOpaque(true);
		this.kDLabel1.setText(" 已售(" + ChooseRoomListUI.selledCount + ")");
		
		this.kDLabel2.setBackground(new Color(89,172,255));
		this.kDLabel2.setOpaque(true);
		this.kDLabel2.setText(" 选房确认(" + ChooseRoomListUI.affirmCount + ")");
		 
		this.kDLabel3.setBackground(ChooseRoomDisplaySetting.chooseRoomUnconfirmed);
		this.kDLabel3.setOpaque(true);
		this.kDLabel3.setText(" 未确认(" + ChooseRoomListUI.unconfirmedCount + ")");	
	}
    
    
	public static void insertUIToScrollPanel(KDScrollPane panel) throws UIException {
		UIContext uiContext = new UIContext();
		CoreUIObject detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(DisplayChooseRoomStateViewUI.class.getName(), uiContext, null, "VIEW");
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