/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomMortagageCollection;
import com.kingdee.eas.fdc.sellhouse.RoomMortagageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomMortagageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MortagageControlUI extends AbstractMortagageControlUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MortagageControlUI.class);

	CoreUIObject detailUI = null;

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	RoomDisplaySetting setting = new RoomDisplaySetting();

	/**
	 * output class constructor
	 */
	public MortagageControlUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		if (!saleOrg.isIsBizUnit()) {
			this.actionMortagage.setEnabled(false);
			this.actionAntiMortagage.setEnabled(false);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain
				.getModel().getRoot();
		DefaultKingdeeTreeNode node = this.findFirstNode(root);
		this.treeMain.setSelectionNode(node);
	}

	private DefaultKingdeeTreeNode findFirstNode(DefaultKingdeeTreeNode node) {
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo buildingInfo = (BuildingInfo) node.getUserObject();
			if (!buildingInfo.getCodingType().equals(
					CodingTypeEnum.UnitFloorNum)) {
				return node;
			}
		} else if (node.getUserObject() instanceof Integer) {  //已作废
			return node;
		}else if (node.getUserObject() instanceof BuildingUnitInfo) {
			return node;
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findFirstNode((DefaultKingdeeTreeNode) node
					.getChildAt(i));
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	public void initControl() {
		this.menuEdit.setVisible(false);
		this.actionImportData.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			ICell cell = this.tblMain.getCell(e.getRowIndex(), e.getColIndex());
			if (cell != null) {
				RoomInfo room = (RoomInfo) cell.getUserObject();
				if (room == null) {
					return;
				}
				if (!room.isIsMortagaged()) {
					return;
				}
				UIContext uiContext = new UIContext(this);
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));
				filter.getFilterItems().add(
						new FilterItemInfo("room.id", room.getId().toString()));
				RoomMortagageCollection mors = RoomMortagageFactory
						.getRemoteInstance().getRoomMortagageCollection(view);
				if (mors.size() == 0) {
					MsgBox.showInfo("抵押单丢失!");
					return;
				} else if (mors.size() > 1) {
					MsgBox.showInfo("多次抵押");
					return;
				}
				RoomMortagageInfo mor = mors.get(0);
				uiContext.put("ID", mor.getId().toString());
				String oprt = "EDIT";
				if (!saleOrg.isIsBizUnit()) {
					oprt = "VIEW";
				}
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(
						MortagageEditUI.class.getName(), uiContext, null, oprt);
				uiWindow.show();
			}
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		changeButtonState();
		ICell cell = this.tblMain.getCell(e.getSelectBlock().getTop(), e
				.getSelectBlock().getLeft());
		if (cell != null) {
			RoomInfo room = (RoomInfo) cell.getUserObject();
			if (room != null) {
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				if (detailUI == null) {
					UIContext uiContext = new UIContext(ui);
					uiContext.put(UIContext.ID, room.getId().toString());
					detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(
							RoomFullInfoUI.class.getName(), uiContext, null,
							"VIEW");
					sclPanel.setViewportView(detailUI);
					sclPanel.setKeyBoardControl(true);
				}
				detailUI.getUIContext().put(UIContext.ID,
						room.getId().toString());
				detailUI.setDataObject(room);
				detailUI.loadFields();
			}
		}
	}

	private void changeButtonState() {
		RoomCollection rooms = this.getSelectRooms();
		if (!saleOrg.isIsBizUnit()) {
			this.actionMortagage.setEnabled(false);
			this.actionAntiMortagage.setEnabled(false);
			return;
		}
		if (rooms == null || rooms.size() == 0) {
			this.actionMortagage.setEnabled(false);
			this.actionAntiMortagage.setEnabled(false);
			return;
		}
		if (rooms.size() == 1) {
			RoomInfo room = rooms.get(0);
			if (room.isIsMortagaged()) {
				this.actionMortagage.setEnabled(false);
				this.actionAntiMortagage.setEnabled(true);
			} else {
				this.actionMortagage.setEnabled(true);
				this.actionAntiMortagage.setEnabled(false);
			}
		} else {
			this.actionMortagage.setEnabled(true);
			this.actionAntiMortagage.setEnabled(true);
		}
	}

	public RoomCollection getSelectRooms() {
		RoomCollection rooms = new RoomCollection();
		for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) {
			KDTSelectBlock block = this.tblMain.getSelectManager().get(i);
			if (block == null) {
				return null;
			}
			for (int row = block.getTop(); row <= block.getBottom(); row++) {
				for (int col = block.getLeft(); col <= block.getRight(); col++) {
					ICell cell = this.tblMain.getCell(row, col);
					if (cell == null) {
						return null;
					}
					RoomInfo room = (RoomInfo) cell.getUserObject();
					if (room != null) {
						try {
							room = SHEHelper.queryRoomInfo(room.getId().toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						rooms.add(room);
					}
				}
			}
		}
		return rooms;
	}

	protected void setActionState() {
		// super.setActionState();
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_CELL_SELECT);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		refresh(null);
	}

	protected void refresh(ActionEvent e) throws BOSException {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		int left = 1;
		int top = 0;
		if (block != null) {
			if (block.getLeft() != -1) {
				left = block.getLeft();
			}
			if (block.getTop() != -1) {
				top = block.getTop();
			}
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
//		FilterInfo filter = new FilterInfo();
//		if (node.getUserObject() instanceof Integer) {
//			Integer unit = (Integer) node.getUserObject();
//			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
//					.getParent()).getUserObject();
//			String buildingId = building.getId().toString();
//			SHEHelper.fillRoomTableByUnit(this.tblMain, buildingId, unit
//					.intValue());
//		} else if (node.getUserObject() instanceof BuildingInfo) {
//			BuildingInfo building = (BuildingInfo) node.getUserObject();
//			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
//				String buildingId = building.getId().toString();
//				SHEHelper.fillRoomTableByUnit(this.tblMain, buildingId, 0);
//			} else {
//				filter.getFilterItems().add(new FilterItemInfo("id", null));
//				tblMain.removeColumns();
//				tblMain.removeHeadRows();
//				tblMain.removeRows();
//			}
//		} else {
//			filter.getFilterItems().add(new FilterItemInfo("id", null));
//			tblMain.removeColumns();
//			tblMain.removeHeadRows();
//			tblMain.removeRows();
//		}
		SHEHelper.fillRoomTableByNode(this.tblMain,node, MoneySysTypeEnum.SalehouseSys, setting);
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
				if (!(row.getCell(j).getUserObject() instanceof RoomInfo)) {
					continue;
				}
				RoomInfo room = (RoomInfo) row.getCell(j).getUserObject();
//				if (room.isIsMortagaged()) {
//					if (room.getSellState().equals(
//							RoomSellStateEnum.PrePurchase)
//							|| room.getSellState().equals(
//									RoomSellStateEnum.Purchase)
//							|| room.getSellState().equals(
//									RoomSellStateEnum.Sign)) {
//						row.getCell(j).getStyleAttributes().setBackground(
//								Color.PINK);
//					} else {
//						row.getCell(j).getStyleAttributes().setBackground(
//								Color.CYAN);
//					}
//				} else {
//					if (room.getSellState().equals(
//							RoomSellStateEnum.PrePurchase)
//							|| room.getSellState().equals(
//									RoomSellStateEnum.Purchase)
//							|| room.getSellState().equals(
//									RoomSellStateEnum.Sign)) {
//						row.getCell(j).getStyleAttributes().setBackground(
//								Color.YELLOW);
//					} else {
//						row.getCell(j).getStyleAttributes().setBackground(
//								Color.WHITE);
//					}
//				}
				if (room.getSellState().equals(
						RoomSellStateEnum.PrePurchase)
						|| room.getSellState().equals(
								RoomSellStateEnum.Purchase)
						|| room.getSellState().equals(
								RoomSellStateEnum.Sign)) {
					row.getCell(j).getStyleAttributes().setBackground(
							Color.YELLOW);
				}else if (room.isIsMortagaged()) {
					row.getCell(j).getStyleAttributes().setBackground(Color.RED);
				} else {
					row.getCell(j).getStyleAttributes().setBackground(Color.WHITE);
				}

			}
		}
		//this.tblMain.getSelectManager().select(top, left);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	public void actionAntiMortagage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAntiMortagage_actionPerformed(e);
		RoomCollection rooms = this.getSelectRooms();
		if (rooms.size() == 0) {
			return;
		}
		//是否有进行解押操作，防止选择的房间均没有抵押，导致后面提示抵押成功错误 zhicheng_jin 081114
		boolean isHasAntiMortagage = false;
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (!room.isIsMortagaged()) {
				MsgBox.showInfo("房间:" + room.getNumber() + "没有抵押,无需解押!");
				continue;
			}
			isHasAntiMortagage = true;
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(
					new FilterItemInfo("room.id", room.getId().toString()));
			RoomMortagageCollection mors = RoomMortagageFactory
					.getRemoteInstance().getRoomMortagageCollection(view);
			if (mors.size() == 0) {
				MsgBox.showInfo("抵押单丢失!");
				return;
			} else if (mors.size() > 1) {
				MsgBox.showInfo("多次抵押");
				return;
			}
			RoomMortagageInfo mor = mors.get(0);
			mor.setIsEnabled(false);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isEnabled");
			RoomMortagageFactory.getRemoteInstance().updatePartial(mor, sels);
			sels = new SelectorItemCollection();
			sels.add("isMortagaged");
			room.setIsMortagaged(false);
			RoomFactory.getRemoteInstance().updatePartial(room, sels);
		}
		if(isHasAntiMortagage){
			MsgBox.showInfo("解押成功!");
			this.refresh(null);
		}
	}

	public void actionMortagage_actionPerformed(ActionEvent e) throws Exception {
		super.actionMortagage_actionPerformed(e);
		RoomCollection rooms = this.getSelectRooms();
		if (rooms.size() == 0) {
			return;
		}
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (room.isIsMortagaged()) {
				MsgBox.showInfo("选择的房间存在已经被抵押!");
				return;
			}
			if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
					|| room.getSellState().equals(RoomSellStateEnum.Purchase)
					|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
				MsgBox.showInfo("选择的房间存在已经被认购!");
				return;
			}
		}
		UIContext uiContext = new UIContext(this);
		SellProjectInfo sellProject = rooms.get(0).getBuilding()
				.getSellProject();
		uiContext.put("sellProject", sellProject);
		if (rooms.size() == 1) {
			uiContext.put("room", rooms.get(0));

		} else {
			uiContext.put("rooms", rooms);
		}
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(MortagageEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		this.refresh(null);
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