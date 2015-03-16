/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
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
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.client.RoomStateColorUI;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * RoomSelectUI的副本，在CVS中与 RoomSelectUI 的 1.4.10.4  09-2-4 4:59版本完全一致
 * 属于绘制房间稳定的版本，待RoomSelectUI 稳定了，可以考虑换过去，目前使用该类的地方只有 RoomSelectUIForF7
 * @author laiquan_luo
 */
public class Copy_RoomSelectUI extends AbstractRoomSelectUI {
	
	protected MoneySysTypeEnum moneySysTypeEnum = null;
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		Boolean isMultiSelect = (Boolean) this.getUIContext().get(
				"isMultiSelect");
		if (isMultiSelect.booleanValue()) {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.MULTIPLE_CELL_SELECT);
		} else {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.CELL_SELECT);
		}
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected void setActionState() {
		// super.setActionState();
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			btnYes_actionPerformed(null);
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
		this.actionView.setEnabled(true);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		fillData();
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
						UIFactoryName.MODEL).create(RoomEditUI.class.getName(),
						uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
	}

	protected void fillData() throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SHEHelper.fillRoomTableByNode(this.tblMain,node, null, null);
//		if (node.getUserObject() instanceof Integer) {
//			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
//					.getParent()).getUserObject();
//			String buildingId = building.getId().toString();
//			Integer unit = (Integer) node.getUserObject();
//			SHEHelper.fillRoomTableByUnit(this.tblMain, buildingId, unit
//					.intValue());
//		} else if (node.getUserObject() instanceof BuildingInfo) {
//			BuildingInfo building = (BuildingInfo) node.getUserObject();
//			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
//				String buildingId = building.getId().toString();
//				SHEHelper.fillRoomTableByUnit(this.tblMain, buildingId, 0);
//			} else {
//				tblMain.removeColumns();
//				tblMain.removeHeadRows();
//				tblMain.removeRows();
//			}
//		} else {
//			tblMain.removeColumns();
//			tblMain.removeHeadRows();
//			tblMain.removeRows();
//		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		fillData();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		BuildingInfo building = (BuildingInfo) this.getUIContext().get(
				"building");
		int unit = ((Integer) this.getUIContext().get("unit")).intValue();
		if (building != null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain
					.getModel().getRoot();
			DefaultKingdeeTreeNode node = this.findNode(root, building.getId()
					.toString(), unit);
			this.treeMain.setSelectionNode(node);
		}
		/*
		 * 因为还有其他未知的地方使用这个界面，所以这里得维护一种原有的情况
		 */
		if(moneySysTypeEnum == null)
		{
			DisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
		}
		else if(MoneySysTypeEnum.SalehouseSys.equals(moneySysTypeEnum))
		{
			DisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
		}
		else if(MoneySysTypeEnum.TenancySys.equals(moneySysTypeEnum))
		{
			RoomStateColorUI.insertUIToScrollPanel(this.kDScrollPane1);
		}
		
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String buildingId, int unit) {
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo buildingInfo = (BuildingInfo) node.getUserObject();
			if (buildingInfo.getId().toString().equals(buildingId) && unit == 0) {
				return node;
			}
		} else if (node.getUserObject() instanceof Integer) {   //已作废
			BuildingInfo buildingInfo = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			int aUnit = ((Integer) node.getUserObject()).intValue();
			if (buildingInfo.getId().toString().equals(buildingId)
					&& unit == aUnit) {
				return node;
			}
		} else if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingInfo buildingInfo = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			int aUnit = ((BuildingUnitInfo) node.getUserObject()).getSeq();
			if (buildingInfo.getId().toString().equals(buildingId)	&& unit == aUnit) {
				return node;
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), buildingId,
					unit);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	/**
	 * output class constructor
	 */
	public Copy_RoomSelectUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {

		Boolean isMultiSelect = (Boolean) this.getUIContext().get(
				"isMultiSelect");
		if (isMultiSelect.booleanValue()) {
			RoomCollection rooms = new RoomCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock block = this.tblMain.getSelectManager().get(i);
				if (block == null) {
					return;
				}
				for (int row = block.getBeginRow(); row <= block.getEndRow(); row++) {
					for (int col = block.getBeginCol(); col <= block
							.getEndCol(); col++) {
						ICell cell = this.tblMain.getCell(row, col);
						if (cell == null || cell.getUserObject() == null) {
							continue;
						} else {
							RoomInfo room = (RoomInfo) cell.getUserObject();
							rooms.add(room);
						}
					}
				}
			}
			if (rooms.size() == 0) {
				MsgBox.showInfo("请正确选择房间！");
				return;
			} else {
				this.getUIContext().put("rooms", rooms);
				this.destroyWindow();
			}
		} else {
			int activeRowIndex = this.tblMain.getSelectManager()
					.getActiveRowIndex();
			int activeColumnIndex = this.tblMain.getSelectManager()
					.getActiveColumnIndex();
			ICell cell = this.tblMain
					.getCell(activeRowIndex, activeColumnIndex);
			if (cell == null || cell.getUserObject() == null) {
				MsgBox.showInfo("请正确选择房间！");
				return;
			} else {
				RoomInfo room = (RoomInfo) cell.getUserObject();
				this.getUIContext().put("room", room);
				this.destroyWindow();
			}
		}
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
	}

	/**
	 * 根据id显示窗体
	 */
	public static RoomInfo showOneRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, int selectUnit) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("building", selectBuilding);
		uiContext.put("unit", new Integer(selectUnit));
		uiContext.put("isMultiSelect", Boolean.FALSE);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(Copy_RoomSelectUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomInfo room = (RoomInfo) uiWindow.getUIObject().getUIContext().get(
				"room");
		return room;
	}

	/**
	 * 根据id显示窗体
	 */
	public static RoomCollection showMultiRoomSelectUI(IUIObject ui,
			BuildingInfo selectBuilding, int selectUnit) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("building", selectBuilding);
		uiContext.put("unit", new Integer(selectUnit));
		uiContext.put("isMultiSelect", Boolean.TRUE);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(Copy_RoomSelectUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		RoomCollection rooms = (RoomCollection) uiWindow.getUIObject()
				.getUIContext().get("rooms");
		return rooms;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}
}