/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomPriceSchemeBatchSetUI extends
		AbstractRoomPriceSchemeBatchSetUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(RoomPriceSchemeBatchSetUI.class);

	private BuildingInfo[] buildingInfo = null;

	/**
	 * output class constructor
	 */
	public RoomPriceSchemeBatchSetUI() throws Exception
	{
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	public void onLoad() throws Exception
	{
		super.onLoad();
		Object[] o = (Object[]) this.getUIContext().get("buildings");
		String oprt = this.getUIContext().get("oprt").toString();
		BuildingPriceSetCollection priceSetCollection = (BuildingPriceSetCollection) this.getUIContext().get("priceSetCollection");
		priceSetCollection.get(0).getBuilding();
		// 动态构造页签
		for (int i = 0; i < priceSetCollection.size(); i++)
		{
			UIContext c = new UIContext();
			c.put("priceSet",priceSetCollection.get(i));
			this.pnlSettings.add((CoreUIObject) UIFactoryHelper.initUIObject(
					RoomPriceSchemeSetUI.class.getName(), c, null,
					oprt),priceSetCollection.get(i).getBuilding().getName());
		}
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception
	{
		super.btnYes_actionPerformed(e);
		BuildingPriceSetCollection settings = new BuildingPriceSetCollection();
		for (int i = 0; i < this.pnlSettings.getTabCount(); i++)
		{
			RoomPriceSchemeSetUI setUI = (RoomPriceSchemeSetUI) this.pnlSettings
					.getComponentAt(i);
			
			 BuildingPriceSetInfo setting = setUI.getSetting();
			 
			 //判断是否选择了付款方案
			 if(setting.getPriceScheme()==null)
			 {
				 MsgBox.showInfo("楼栋 "+setting.getBuilding()+" 未选择定价方案！");
				 return;
			 }
			 
			 settings.add(setting);
		}
		this.getUIContext().put("settings", settings);
		this.getUIContext().put("YES", Boolean.TRUE);
		this.destroyWindow();
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception
	{
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}

	public static BuildingPriceSetCollection showUI(CoreUI ui,
			BuildingPriceSetCollection priceSetCollection, Object buildings, String oprt)
			throws UIException
	{
		
		UIContext uiContext = new UIContext(ui);
		uiContext.put("priceSetCollection", priceSetCollection);
		uiContext.put("buildings", buildings);
		uiContext.put("oprt",oprt);
		
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomPriceSchemeBatchSetUI.class.getName(), uiContext,
						null, oprt);
		uiWindow.show();
		priceSetCollection = (BuildingPriceSetCollection) uiWindow.getUIObject()
				.getUIContext().get("settings");
		if (uiWindow.getUIObject().getUIContext().get("YES") == null)
		{
			return null;
		}
		return priceSetCollection;
	}
}