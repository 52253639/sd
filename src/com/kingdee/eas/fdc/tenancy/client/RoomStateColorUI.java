/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;

/**
 * output class name
 */
public class RoomStateColorUI extends AbstractRoomStateColorUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(RoomStateColorUI.class);

	/**
	 * output class constructor
	 */
	public RoomStateColorUI() throws Exception
	{
		super();
	}

	public void onLoad() throws Exception
	{
		super.onLoad();
		TenancyDisPlaySetting setting= new TenancyDisPlaySetting();
		this.kDLabel7.setBackground(setting.getNoTenancyColor());
		this.kDLabel7.setOpaque(true);
		this.kDLabel7.setText("      ·Ç×âÁÞ");
		
		this.kDLabel1.setBackground(setting.getUnTenancyColor());
		this.kDLabel1.setOpaque(true);
		this.kDLabel1.setText("      Î´·Å×â");
		
		this.kDLabel2.setBackground(setting.getWaitTenancyColor());
		this.kDLabel2.setOpaque(true);
		this.kDLabel2.setText("      ´ý×â");

		this.kDLabel3.setBackground(setting.getKeepTenancyColor());
		this.kDLabel3.setOpaque(true);
		this.kDLabel3.setText("      ±£Áô");
		
		this.kDLabel4.setBackground(setting.getNewTenancyColor());
		this.kDLabel4.setOpaque(true);
		this.kDLabel4.setText("      ÐÂ×â");
		
		this.kDLabel5.setBackground(setting.getContinueTenancyColor());
		this.kDLabel5.setOpaque(true);
		this.kDLabel5.setText("      Ðø×â");
		
		this.kDLabel6.setBackground(setting.getEnlargeTenancyColor());
		this.kDLabel6.setOpaque(true);
		this.kDLabel6.setText("      À©×â");
		
		this.kDLabel8.setBackground(setting.getSincerObliColor());
		this.kDLabel8.setOpaque(true);
		this.kDLabel8.setText("      Ô¤Áô");

	}

	public static void insertUIToScrollPanel(KDScrollPane panel)
			throws UIException
	{
		UIContext uiContext = new UIContext();
		CoreUIObject detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(
				RoomStateColorUI.class.getName(), uiContext, null, "VIEW");
		panel.setViewportView(detailUI);
		panel.setKeyBoardControl(true);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}
}