package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public class RoomSelectUIForNewSHE extends RoomSelectUI {
	

	public boolean isCanceled = false;

	public RoomSelectUIForNewSHE() throws Exception	{
		super();
	}
	
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception
	{
		if (isMultiSelect.booleanValue())
		{
			RoomCollection rooms = new RoomCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++)
			{
				KDTSelectBlock block = this.tblMain.getSelectManager().get(i);
				if (block == null)
				{
					return;
				}
				for (int row = block.getBeginRow(); row <= block.getEndRow(); row++)
				{
					for (int col = block.getBeginCol(); col <= block
							.getEndCol(); col++)
					{
						ICell cell = this.tblMain.getCell(row, col);
						if (cell == null || cell.getUserObject() == null)
						{
							continue;
						} else
						{
							RoomInfo room = (RoomInfo) cell.getUserObject();
							room = SHEHelper.queryRoomInfo(room.getId().toString());
							rooms.add(room);
						}
					}
				}
			}
			if (rooms.size() == 0)
			{
				MsgBox.showInfo("请正确选择房间！");
				return;
			} else
			{
				this.getUIContext().put("rooms", rooms);
				 this.isCanceled = false;
		         this.destroyWindow();
			}
		} else
		{
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			int activeColumnIndex = this.tblMain.getSelectManager().getActiveColumnIndex();
			ICell cell = this.tblMain.getCell(activeRowIndex, activeColumnIndex);
			if (cell == null || cell.getUserObject() == null)
			{
				MsgBox.showInfo("请正确选择房间！");
				return;
			} else
			{
				RoomInfo room = (RoomInfo) cell.getUserObject();
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				this.getUIContext().put("room", room);
				this.isCanceled = false;
				this.destroyWindow();
			}
		}
	}
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception
	{
		isCanceled = true;
		super.btnNo_actionPerformed(e);
	}
	/**
	 * 返回多个房间的值
	 * @return
	 * @author laiquan_luo
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public Object[] getReturnValueArray() throws EASBizException, BOSException, UuidException
	{
		Object obj[] = null;
		if (isMultiSelect.booleanValue())
		{
			RoomCollection rooms = new RoomCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) 
			{
				KDTSelectBlock block = this.tblMain.getSelectManager().get(i);
				if (block == null)
				{
					return null;
				}
				for (int row = block.getBeginRow(); row <= block.getEndRow(); row++)
				{
					for (int col = block.getBeginCol(); col <= block.getEndCol(); col++) {
						ICell cell = this.tblMain.getCell(row, col);
						if (cell == null || cell.getUserObject() == null)
						{
							continue;
						} else 
						{
							RoomInfo room = (RoomInfo) cell.getUserObject();
							room = SHEHelper.queryRoomInfo(room.getId().toString());
							rooms.add(room);
						}
					}
				}
			}
			if (rooms.size() == 0)
			{
				MsgBox.showInfo("请正确选择房间！");
				return null;
			} else
			{
				obj = rooms.toArray();
			}
		} 
		else 
		{
			
		}
		return obj;
	}
	/**
	 * 返回单个房间的值
	 * @return
	 * @author laiquan_luo
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public Object getReturnValue() throws EASBizException, BOSException, UuidException
	{
		Object obj = null;
		if(!this.isMultiSelect.booleanValue())
		{
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			int activeColumnIndex = this.tblMain.getSelectManager().getActiveColumnIndex();
			ICell cell = this.tblMain.getCell(activeRowIndex, activeColumnIndex);
			if (cell == null || cell.getUserObject() == null) 
			{
				MsgBox.showInfo("请正确选择房间！");
				return null;
			} else 
			{
				RoomInfo room = (RoomInfo) cell.getUserObject();
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				obj = room;
			}
		}
		else
		{
			
		}
		return obj;
	}
	
	public static RoomInfo showOneRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum) throws UIException {
		return showOneRoomSelectUI(ui,selectBuilding,selectUnit,sysTypeEnum,null,null);
	}
	/**
	 * 根据搂栋、单元选择一个房间
	 */
	public static RoomInfo showOneRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum,RoomCollection roomCollRestrict,SellProjectInfo sellProjectRestrict) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("buildingInfo", selectBuilding);
		uiContext.put("unit", selectUnit);
		uiContext.put("isMultiSelect", Boolean.FALSE);
		
		if(sysTypeEnum!=null)
			uiContext.put("moneySysTypeEnum", sysTypeEnum);		
		if(roomCollRestrict!=null)
			uiContext.put("roomColl", roomCollRestrict);
		if(sellProjectRestrict!=null)
			uiContext.put("sellProjectInfo", sellProjectRestrict);
			
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomSelectUIForNewSHE.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomInfo room = (RoomInfo) uiWindow.getUIObject().getUIContext().get(
				"room");
		return room;
	}
	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit) throws UIException {
		return showMultiRoomSelectUI(ui,selectBuilding,selectUnit,null,null,null);
	}	
	
	
	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum) throws UIException {
		return showMultiRoomSelectUI(ui,selectBuilding,selectUnit,sysTypeEnum,null,null);
	}
	
	
	/**
	 * 根据搂栋、单元选择多个房间
	 */
	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, BuildingUnitInfo selectUnit,MoneySysTypeEnum sysTypeEnum,RoomCollection roomCollRestrict,SellProjectInfo sellProjectRestrict) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("buildingInfo", selectBuilding);
		uiContext.put("unit", selectUnit);
		uiContext.put("isMultiSelect", Boolean.TRUE);
		
		if(sysTypeEnum!=null)
			uiContext.put("moneySysTypeEnum", sysTypeEnum);		
		if(roomCollRestrict!=null)
			uiContext.put("roomColl", roomCollRestrict);
		if(sellProjectRestrict!=null)
			uiContext.put("sellProjectInfo", sellProjectRestrict);
		
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomSelectUIForNewSHE.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomCollection rooms = (RoomCollection) uiWindow.getUIObject()
				.getUIContext().get("rooms");
		return rooms;
	}
	protected void initTree() throws Exception {
		this.moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProjectInfo");
		
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(sellproject,true));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		int activeColumnIndex = this.tblMain.getSelectManager()
				.getActiveColumnIndex();
		ICell cell = this.tblMain.getCell(activeRowIndex, activeColumnIndex);
		if (cell == null || cell.getUserObject() == null) {
			MsgBox.showInfo("请正确选择房间！");
			return;
		} else {
			RoomInfo room = (RoomInfo) cell.getUserObject();
			if (room != null) {
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", room.getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(RoomSourceEditUI.class.getName(),
						uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
	}
}
