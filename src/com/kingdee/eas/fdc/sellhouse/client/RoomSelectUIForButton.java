package com.kingdee.eas.fdc.sellhouse.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

public class RoomSelectUIForButton extends RoomSelectUI
{

	public RoomSelectUIForButton() throws Exception
	{
		super();
	}
	/**
	 * 填充房间
	 */
	protected void fillData() throws BOSException
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) 
		{
			return;
		}
		moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
		SHEHelper.fillRoomTableByNode(this.tblMain,node, moneySysTypeEnum, new RoomDisplaySetting());
	}
	/**
	 * 显示窗体，房间是单选的
	 * @param ui
	 * @param selectBuilding
	 * @param selectUnit
	 * @param moneySysTypeEnum 所属系统
	 * @return
	 * @throws UIException
	 */
	public static RoomInfo showOneRoomSelectUI(IUIObject ui,BuildingInfo selectBuilding, int selectUnit,MoneySysTypeEnum moneySysTypeEnum) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("building", selectBuilding);
		uiContext.put("unit", new Integer(selectUnit));
		uiContext.put("isMultiSelect", Boolean.FALSE);
		uiContext.put("moneySysTypeEnum",moneySysTypeEnum);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomSelectUIForButton.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomInfo room = (RoomInfo) uiWindow.getUIObject().getUIContext().get(
				"room");
		return room;
	}
	/**
	 * 显示窗体房间是多选的
	 * @param ui
	 * @param selectBuilding
	 * @param selectUnit
	 * @param moneySysTypeEnum 所属系统
	 * @return
	 * @throws UIException
	 */
	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum moneySysTypeEnum) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("building", selectBuilding);
		uiContext.put("unit", selectUnit);
		uiContext.put("moneySysTypeEnum",moneySysTypeEnum);
		uiContext.put("isMultiSelect", Boolean.TRUE);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomSelectUIForButton.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomCollection rooms = (RoomCollection) uiWindow.getUIObject()
				.getUIContext().get("rooms");
		return rooms;
	}
}
