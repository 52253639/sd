/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Rectangle;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import sun.print.SunAlternateMedia;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomPriceListUI extends AbstractRoomPriceListUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomPriceListUI.class);

	/**
	 * output class constructor
	 */
	public RoomPriceListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	/*
	 * protected void tblMain_tableClicked(
	 * com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
	 * if (this.tblMain.getStyleAttributes().isLocked()) { return; } if
	 * (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) { int
	 * rowIndex = e.getRowIndex(); int colIndex = e.getColIndex(); if
	 * (this.tblMain.getColumnKey(colIndex).equals("isCalByRoom")) { IRow row =
	 * this.tblMain.getRow(rowIndex); boolean isCalByRoomArea = ((Boolean)
	 * row.getCell("isCalByRoom") .getValue()).booleanValue(); isCalByRoomArea =
	 * !isCalByRoomArea; row.getCell("isCalByRoom").setValue( new
	 * Boolean(isCalByRoomArea)); if (isCalByRoomArea) {
	 * row.getCell("roomArea").getStyleAttributes().setLocked( false);
	 * row.getCell("roomPrice").getStyleAttributes().setLocked( false);
	 * row.getCell("buildArea").getStyleAttributes().setLocked( true);
	 * row.getCell("buildPrice").getStyleAttributes().setLocked( true); } else {
	 * row.getCell("roomArea").getStyleAttributes() .setLocked(true);
	 * row.getCell("roomPrice").getStyleAttributes().setLocked( true);
	 * row.getCell("buildArea").getStyleAttributes().setLocked( false);
	 * row.getCell("buildPrice").getStyleAttributes().setLocked( false); }
	 * 
	 * BigDecimal amount = FDCHelper.ZERO; if (isCalByRoomArea) { if
	 * (row.getCell("roomArea").getValue() != null &&
	 * row.getCell("roomPrice").getValue() != null) { amount = (BigDecimal)
	 * row.getCell("roomArea") .getValue(); amount =
	 * amount.multiply((BigDecimal) row.getCell( "roomPrice").getValue()); } }
	 * else { if (row.getCell("buildArea").getValue() != null &&
	 * row.getCell("buildPrice").getValue() != null) { amount = (BigDecimal)
	 * row.getCell("buildArea") .getValue(); amount =
	 * amount.multiply((BigDecimal) row.getCell( "buildPrice").getValue()); } }
	 * row.getCell("sumAmount").setValue(amount); } } }
	 */
	protected void setActionState() {
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		// this.verifySaved(e.getOldLeadSelectionPath());
		fillData();
	}

	private void fillData() throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("building.name");
		view.getSelector().add("building.sellProject.name");
		view.getSelector().add("building.subarea.name");
		view.getSelector().add("buildUnit.name");
		view.getSelector().add("buildUnit.seq");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		setColumnNotHidden();
		if (node.getUserObject() instanceof Integer) { // 已作废
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("unit", unit));
			setColumnHidden("sellProject");
			setColumnHidden("subArea");
			setColumnHidden("building");
			setColumnHidden("unit");
		} else if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
			setColumnHidden("sellProject");
			setColumnHidden("subArea");
			setColumnHidden("building");
			setColumnHidden("unit");
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			view.getSorter().add(new SorterItemInfo("unit"));
			if (building.getUnitCount() == 0) {
				setColumnHidden("unit");
			}
			setColumnHidden("sellProject");
			setColumnHidden("subArea");
			setColumnHidden("building");
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			String subareaId = subarea.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.subarea.id", subareaId));
			view.getSorter().add(new SorterItemInfo("building.id"));
			view.getSorter().add(new SorterItemInfo("unit"));

			setColumnHidden("sellProject");
			setColumnHidden("subArea");
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			String sellProjectId = sellProject.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProjectId));
			view.getSorter().add(new SorterItemInfo("building.subarea.id"));
			view.getSorter().add(new SorterItemInfo("building.id"));
			view.getSorter().add(new SorterItemInfo("unit"));
			if (sellProject.getSubarea() == null || sellProject.getSubarea().isEmpty()) {
				setColumnHidden("subArea");
			}
			setColumnHidden("sellProject");
		} else {
			this.tblMain.removeRows();
			this.actionSubmit.setEnabled(false);
			return;
		}
		view.getSorter().add(new SorterItemInfo("displayName"));
		this.tblMain.removeRows();
		this.actionSubmit.setEnabled(true);
		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			IRow row = this.tblMain.addRow();
			row.setUserObject(room);
			row.getCell("id").setValue(room.getId().toString());
			row.getCell("sellProject").setValue(room.getBuilding().getSellProject().getName());
			if (room.getBuilding().getSubarea() != null) {
				row.getCell("subArea").setValue(room.getBuilding().getSubarea().getName());
			}
			row.getCell("building").setValue(room.getBuilding().getName());
			row.getCell("unit").setValue(room.getBuildUnit());
			row.getCell("floor").setValue(new Integer(room.getFloor()));
			row.getCell("number").setValue(room.getDisplayName());
			row.getCell("roomNo").setValue(room.getRoomNo());
			boolean isCalByRoomArea = room.isIsCalByRoomArea();
			if (isCalByRoomArea) {
				row.getCell("isCalByRoom").setValue(Boolean.TRUE);
				//row.getCell("roomArea").getStyleAttributes().setLocked(false);
				//row.getCell("roomPrice").getStyleAttributes().setLocked(false)
				// ;
				//row.getCell("buildArea").getStyleAttributes().setLocked(true);
				//row.getCell("buildPrice").getStyleAttributes().setLocked(true)
				// ;
			} else {
				row.getCell("isCalByRoom").setValue(Boolean.FALSE);
				// row.getCell("roomArea").getStyleAttributes().setLocked(true);
				//row.getCell("roomPrice").getStyleAttributes().setLocked(true);
				//row.getCell("buildArea").getStyleAttributes().setLocked(false)
				// ;
				//row.getCell("buildPrice").getStyleAttributes().setLocked(false
				// );
			}
			row.getCell("buildArea").setValue(room.getBuildingArea());
			row.getCell("buildPrice").setValue(room.getBuildPrice());
			row.getCell("roomArea").setValue(room.getRoomArea());
			row.getCell("roomPrice").setValue(room.getRoomPrice());

			row.getCell("sumAmount").setValue(room.getStandardTotalAmount());

			RoomSellStateEnum sellState = room.getSellState();
			if (RoomSellStateEnum.Init.equals(sellState) || RoomSellStateEnum.OnShow.equals(sellState) || RoomSellStateEnum.KeepDown.equals(sellState)) {
				row.getCell("sellState").setValue("未售");
			} else if (RoomSellStateEnum.PrePurchase.equals(sellState) || RoomSellStateEnum.Purchase.equals(sellState) || RoomSellStateEnum.Sign.equals(sellState)) {
				row.getCell("sellState").setValue("已售");
			} else {
				row.getCell("sellState").setValue("");
			}

			/*
			 * BigDecimal buildAmount = FDCHelper.ZERO; if
			 * (room.getBuildingArea() != null) { buildAmount =
			 * room.getBuildingArea(); } if (room.getBuildPrice() != null) {
			 * buildAmount = buildAmount.multiply(room.getBuildPrice()); } else
			 * { buildAmount = FDCHelper.ZERO; } BigDecimal roomAmount =
			 * FDCHelper.ZERO; if (room.getRoomArea() != null) { roomAmount =
			 * room.getRoomArea(); } if (room.getRoomPrice() != null) {
			 * roomAmount = roomAmount.multiply(room.getRoomPrice()); } else {
			 * roomAmount = FDCHelper.ZERO; } if (isCalByRoomArea) {
			 * row.getCell("sumAmount").setValue(roomAmount); } else {
			 * row.getCell("sumAmount").setValue(buildAmount); }
			 */
		}
		// 设置合计
		fillTotal();
	}

	private void setColumnNotHidden() {
		tblMain.getColumn("sellProject").getStyleAttributes().setHided(false);
		tblMain.getColumn("subArea").getStyleAttributes().setHided(false);
		tblMain.getColumn("building").getStyleAttributes().setHided(false);
		tblMain.getColumn("unit").getStyleAttributes().setHided(false);
	}

	private void setColumnHidden(String key) {
		tblMain.getColumn(key).getStyleAttributes().setHided(true);
	}

	public boolean isEditedTable() {
		KDTable table = this.tblMain;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() != null) {
				if (this.isEditedRow(row))
					return true;
			}
		}
		return false;
	}

	private void verifySaved(TreePath treePath) {
		this.tblMain.getSelectManager().select(0, 0);
		if (this.isEditedTable()) {
			if (MsgBox.showConfirm2New(this, "是否保存?") == MsgBox.YES) {
				if (!this.verify()) {
					if (treePath != null) {
						this.treeMain.setSelectionPath(treePath);
					}
					this.abort();
				}
				// if (this.roo.aimCost.getAuditor() != null) {
				// if (treePath != null) {
				// this.treeMain.setSelectionPath(treePath);
				// }
				// MsgBox.showError(AimCostHandler.getResource("HasAudit"));
				// this.abort();
				// }
				this.btnSubmit.doClick();
			}
		}
	}

	public boolean destroyWindow() {
		// verifySaved(null);
		return super.destroyWindow();
	}

	public boolean isEditedRow(IRow row) {
		if (row.getUserObject() == null) {
			return false;
		}
		RoomInfo room = (RoomInfo) row.getUserObject();
		String[] compareyKeys = new String[] { "buildingArea", "buildPrice", "roomArea", "roomPrice" };
		String[] columnKeys = new String[] { "buildArea", "buildPrice", "roomArea", "roomPrice" };
		for (int i = 0; i < compareyKeys.length; i++) {
			if (!FDCHelper.isEqual(room.get(compareyKeys[i]), row.getCell(columnKeys[i]).getValue())) {
				return true;
			}
		}
		if (room.isIsCalByRoomArea() != ((Boolean) row.getCell("isCalByRoom").getValue()).booleanValue()) {
			return true;
		}
		return false;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		// return SHEPriceHistoryListUI.class.getName();
		return null;
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		this.tblMain.getColumn("buildArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("sumAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("sumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblMain.getColumn("buildArea").setEditor(numberEditor);
		this.tblMain.getColumn("buildPrice").setEditor(numberEditor);
		this.tblMain.getColumn("roomArea").setEditor(numberEditor);
		this.tblMain.getColumn("roomPrice").setEditor(numberEditor);
		this.actionSubmit.setVisible(false);
		this.actionSchemePrice.setVisible(false);
		this.tblMain.getStyleAttributes().setLocked(true);

		SimpleKDTSortManager.setTableSortable(tblMain);

		this.tHelper = new TablePreferencesHelper(this);
		tntSumAmount.setVisible(true);
		tntBuildArea.setVisible(true);
		tntAvgBuild.setVisible(true);
		tntAvgRoom.setVisible(true);
	}

	private void initControl() {
		this.actionImportData.setVisible(true);
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
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.actionImportData.setVisible(false);
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	public boolean verify() {
		return true;
	}

	private void refresh() {
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		fillData();
	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblMain.getRow(rowIndex);
		boolean isCalByRoomArea = ((Boolean) row.getCell("isCalByRoom").getValue()).booleanValue();
		BigDecimal amount = FDCHelper.ZERO;

		if (isCalByRoomArea) {
			if (row.getCell("roomArea").getValue() != null && row.getCell("roomPrice").getValue() != null) {
				amount = (BigDecimal) row.getCell("roomArea").getValue();
				amount = amount.multiply((BigDecimal) row.getCell("roomPrice").getValue());
			}
		} else {
			if (row.getCell("buildArea").getValue() != null && row.getCell("buildPrice").getValue() != null) {
				amount = (BigDecimal) row.getCell("buildArea").getValue();
				amount = amount.multiply((BigDecimal) row.getCell("buildPrice").getValue());
			}
		}
		row.getCell("sumAmount").setValue(amount);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (!this.verify()) {
			return;
		}
		CoreBaseCollection colls = new CoreBaseCollection();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (this.isEditedRow(row)) {
				RoomInfo room = (RoomInfo) row.getUserObject();
				room.setBuildingArea((BigDecimal) row.getCell("buildArea").getValue());
				room.setBuildPrice((BigDecimal) row.getCell("buildPrice").getValue());
				room.setRoomArea((BigDecimal) row.getCell("roomArea").getValue());
				room.setRoomPrice((BigDecimal) row.getCell("roomPrice").getValue());
				room.setIsCalByRoomArea(((Boolean) row.getCell("isCalByRoom").getValue()).booleanValue());
				colls.add(room);
			}
		}
		RoomFactory.getRemoteInstance().submit(colls);
		MsgBox.showInfo("保存成功!");
		super.actionSubmit_actionPerformed(e);
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
			// prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		int temp = e.getClickCount();
		if (temp != 2)
			return;
		int i = e.getRowIndex();
		IRow row = this.tblMain.getRow(i);
		if (row == null)
			return;

		String id = row.getCell("id").getValue().toString();

		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SHEPriceHistoryListUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}

	/*
	 * setContainerLayout一定要在子类调用的语义不明确(不调用设计会出现界面显示时的死循环,导致outofMem 错误而使程序中断),
	 * 将其移入基类方法,子类就不用实现了
	 * 
	 * @see com.kingdee.bos.ui.face.CoreUIObject#initLayout()
	 */
	public void initLayout() {
		super.initLayout();
		setContainerLayout();
	}

	/**
	 * 描述：设置采用KDLayout布局的容器的"OriginalBounds"客户属性。KDLayout设计思想采用了绝对布局方式，
	 * 没有考虑Java中存在相对布局的情况。 必须在UI的非抽象类中重载方法public void initUIContentLayout()
	 * 
	 * @return
	 */
	private void setContainerLayout() {
		treeView.putClientProperty("OriginalBounds", new Rectangle(10, 10, 240, 580));
		kDPanel1.putClientProperty("OriginalBounds", new Rectangle(260, 10, 683, 580));
	}

	private void fillTotal() {
		if (tblMain.getRowCount() > 0) {
			BigDecimal sumAmount = new BigDecimal("0.0000");
			BigDecimal buildArea = new BigDecimal("0.0000");
			BigDecimal roomArea = new BigDecimal("0.0000");
			BigDecimal avgBuild = new BigDecimal("0.0000");
			BigDecimal avgRoom = new BigDecimal("0.0000");
//			BigDecimal size = new BigDecimal(tblMain.getRowCount());

			BigDecimal sumAmountCell;
			BigDecimal buildAreaCell;
//			BigDecimal avgBuildCell;
//			BigDecimal avgRoomCell;
			BigDecimal roomAreaCell;
			
			IRow row;
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				row = tblMain.getRow(i);
				//标准总价
				sumAmountCell = row.getCell("sumAmount").getValue() == null ? new BigDecimal("0.0000") : (BigDecimal) row.getCell("sumAmount").getValue();
				//建筑面积
				buildAreaCell = row.getCell("buildArea").getValue() == null ? new BigDecimal("0.0000") : (BigDecimal) row.getCell("buildArea").getValue();
				//建筑单价和
//				avgBuildCell = row.getCell("buildPrice").getValue() == null ? new BigDecimal("0.0000") : (BigDecimal) row.getCell("buildPrice").getValue();
				//套内单价和
//				avgRoomCell = row.getCell("roomPrice").getValue() == null ? new BigDecimal("0.0000") : (BigDecimal) row.getCell("roomPrice").getValue();
				//套内面积
				roomAreaCell = row.getCell("roomArea").getValue() == null ? new BigDecimal("0.0000") : (BigDecimal) row.getCell("roomArea").getValue();
				
				sumAmount = sumAmount.add(sumAmountCell);
				buildArea = buildArea.add(buildAreaCell);
				roomArea = roomArea.add(roomAreaCell);
//				avgBuild = avgBuild.add(avgBuildCell);
//				avgRoom = avgRoom.add(avgRoomCell);
			}
			//建筑均价=总标准价/建筑面积
			avgBuild = sumAmount.divide(buildArea, 4, BigDecimal.ROUND_HALF_UP);
			//套内均价=总标准价/套内面积
			avgRoom = sumAmount.divide(roomArea, 4, BigDecimal.ROUND_HALF_UP);

			tntSumAmount.setValue(sumAmount);
			tntBuildArea.setValue(buildArea);
			tntAvgBuild.setValue(avgBuild);
			tntAvgRoom.setValue(avgRoom);
		} else {
			tntSumAmount.setValue(null);
			tntBuildArea.setValue(null);
			tntAvgBuild.setValue(null);
			tntAvgRoom.setValue(null);
		}
	}
}