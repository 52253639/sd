/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomBasePriceListUI extends AbstractRoomBasePriceListUI {
	//add by warship at 2010/09/13
	private static final Logger logger = CoreUIObject.getLogger(RoomBasePriceListUI.class);
	
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	TreePath curPath = null;

	/**
	 * output class constructor
	 */
	public RoomBasePriceListUI() throws Exception {
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
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (isOrderForClickTableHead()
				&& e.getType() == KDTStyleConstants.HEAD_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 1) {
			// 重写，使用SimpleSortManager排序
			return;
		}
//		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		if (this.curPath != null) {
			if (e.getNewLeadSelectionPath().equals(this.curPath)) {
				return;
			}
		}
		this.verifySaved(e.getOldLeadSelectionPath());
		this.curPath = e.getNewLeadSelectionPath();
		fillTable();
	}

	private void fillTable() throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
			if (saleOrg.isIsBizUnit()) {
				this.actionSubmit.setEnabled(true);
				this.actionImportExcel.setEnabled(true);
			}
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (node.isLeaf()) {
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
				//filter.getFilterItems().add(new FilterItemInfo("unit", new Integer(0)));
				if (saleOrg.isIsBizUnit()) {
					this.actionSubmit.setEnabled(true);
					this.actionImportExcel.setEnabled(true);
				}
			} else {
				this.tblMain.removeRows();
				this.actionSubmit.setEnabled(false);
				this.actionImportExcel.setEnabled(false);
				return;
			}
		} else {
			this.tblMain.removeRows();
			this.actionSubmit.setEnabled(false);
			this.actionImportExcel.setEnabled(false);
			return;
		}
		this.tblMain.removeRows();
		RoomCollection rooms = RoomFactory.getRemoteInstance()
				.getRoomCollection(view);
		fillTableByRooms(rooms);
	}

	private void fillTableByRooms(RoomCollection rooms) {
		if (rooms == null) {
			return;
		}
		for (int i = 0; i < rooms.size(); i++) {
			IRow row = this.tblMain.addRow();
			RoomInfo room = rooms.get(i);
			
			row.setUserObject(room);
			row.getCell("id").setValue(room.getId().toString());
			row.getCell("number").setValue(room.getDisplayName());
			row.getCell("buildArea").setValue(room.getBuildingArea());
			row.getCell("roomArea").setValue(room.getRoomArea());
			row.getCell("baseBuildPrice").setValue(room.getBaseBuildingPrice());
			row.getCell("baseRoomPrice").setValue(room.getBaseRoomPrice());
			row.getCell("isBasePriceAudited").setValue(
					new Boolean(room.isIsBasePriceAudited()));
			
			if (room.getBuildingArea() == null
					|| room.getBuildingArea().equals(FDCHelper.ZERO)
					|| room.getRoomArea() == null
					|| room.getRoomArea().equals(FDCHelper.ZERO)) {
				row.getCell("baseBuildPrice").getStyleAttributes().setLocked(
						false);
				row.getCell("baseRoomPrice").getStyleAttributes().setLocked(
						false);
			}
			
			if(room.isIsCalByRoomArea()){
				row.getCell("baseBuildPrice").getStyleAttributes().setLocked(
						true);
			}else{
				row.getCell("baseRoomPrice").getStyleAttributes().setLocked(
						true);
			}
			
			if (!room.isIsBasePriceAudited()) {
//				row.getCell("baseBuildPrice").getStyleAttributes().setLocked(
//						false);
//				row.getCell("baseRoomPrice").getStyleAttributes().setLocked(
//						false);
//				row.getCell("baseBuildPrice").getStyleAttributes()
//						.setBackground(Color.WHITE);
//				row.getCell("baseRoomPrice").getStyleAttributes()
//						.setBackground(Color.WHITE);
			} else {
				row.getCell("baseBuildPrice").getStyleAttributes().setLocked(
						true);
				row.getCell("baseRoomPrice").getStyleAttributes().setLocked(
						true);
				row.getStyleAttributes().setBackground(
						FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getStyleAttributes().setBackground(
						FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}
			
			BigDecimal roomBasePrice = null;
			if(room.isIsCalByRoomArea()){
				if(room.getRoomArea()!=null && room.getBaseRoomPrice()!=null){
					roomBasePrice = room.getRoomArea().multiply(room.getBaseRoomPrice());
				}
			}else{
				if(room.getBuildingArea()!=null && room.getBaseBuildingPrice()!=null){
					roomBasePrice = room.getBuildingArea().multiply(room.getBaseBuildingPrice());
				}
			}
			row.getCell("roomBasePrice").setValue(roomBasePrice);
			
			
			
			
		}
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
		// this.tblMain.getSelectManager().select(0, 0);
		if (this.isEditedTable()) {
			if (MsgBox.showConfirm2New(this, "是否保存?") == MsgBox.YES) {
				if (!this.verify()) {
					if (treePath != null) {
						this.treeMain.setSelectionPath(treePath);
					}
					this.abort();
				}
				this.btnSubmit.doClick();
			}
		}
	}

	public boolean destroyWindow() {
		verifySaved(null);
		return super.destroyWindow();
	}

	public boolean isEditedRow(IRow row) {
		if (row.getUserObject() == null) {
			return false;
		}
		RoomInfo room = (RoomInfo) row.getUserObject();
		String[] compareyKeys = new String[] { "baseBuildingPrice",
				"baseRoomPrice" };
		String[] columnKeys = new String[] { "baseBuildPrice", "baseRoomPrice" };
		for (int i = 0; i < compareyKeys.length; i++) {
			if (!FDCHelper.isEqual(room.get(compareyKeys[i]), row.getCell(
					columnKeys[i]).getValue())) {
				return true;
			}
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
		return RoomEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(FDCTreeHelper.getUnitTree(actionOnLoad, null));  // SHEHelper.getUnitTree(this.actionOnLoad)
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		initControl();
		super.onLoad();
		this.tblMain.getColumn("buildArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("baseBuildPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("baseBuildPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("baseRoomPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("baseRoomPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblMain.getColumn("roomBasePrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblMain.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblMain.getColumn("baseBuildPrice").setEditor(numberEditor);
		this.tblMain.getColumn("baseRoomPrice").setEditor(numberEditor);
		KDCheckBox checkBox = new KDCheckBox();
		KDTDefaultCellEditor chkEditor = new KDTDefaultCellEditor(checkBox);
		this.tblMain.getColumn("isBasePriceAudited").setEditor(chkEditor);

		this.tblMain.getColumn("number").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("buildArea").getStyleAttributes()
				.setLocked(true);
		this.tblMain.getColumn("roomArea").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("baseBuildPrice").getStyleAttributes()
				.setLocked(false);
		this.tblMain.getColumn("baseRoomPrice").getStyleAttributes().setLocked(
				false);
		if (!saleOrg.isIsBizUnit()) {
			this.tblMain.getColumn("baseBuildPrice").getStyleAttributes()
					.setLocked(true);
			this.tblMain.getColumn("baseRoomPrice").getStyleAttributes()
					.setLocked(true);
			this.actionSubmit.setEnabled(false);
			this.actionImportExcel.setEnabled(false);
		}
		this.treeMain.setSelectionRow(0);

		// ListUI的排序对未绑定字段的Table忽略，这里重简单实现排序
		SimpleKDTSortManager.setTableSortable(tblMain);
	
		tblMain.setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					handleDelKeyPress(e);
				}
			}
		});
	}

	/**
	 * 处理Delete操作
	 * */
	private void handleDelKeyPress(BeforeActionEvent e) {
		int buildingIndex = tblMain.getColumnIndex("baseBuildPrice");
		int roomIndex = tblMain.getColumnIndex("baseRoomPrice");
		for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
			KDTSelectBlock block = tblMain.getSelectManager().get(i);
			for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++) {
				try {
					if (!tblMain.getCell(rowIndex, buildingIndex).getStyleAttributes().isLocked()) {
						tblMain.getCell(rowIndex, buildingIndex).setValue(null);
						tblMain_editStopped(new KDTEditEvent(e.getSource(),null, null, rowIndex, buildingIndex,false, 1));
					} else if(!tblMain.getCell(rowIndex, roomIndex).getStyleAttributes().isLocked()){
						tblMain.getCell(rowIndex, roomIndex).setValue(null);
						tblMain_editStopped(new KDTEditEvent(e.getSource(),null, null, rowIndex, roomIndex,false, 1));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void initControl() {
		this.actionImportData.setVisible(true);
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
		this.menuItemImportData.setVisible(false);
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	protected void refresh(ActionEvent arg0) throws Exception {
		this.verifySaved(null);
		fillTable();
	}

	public boolean verify() {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (this.isEditedRow(row)) {
				BigDecimal buildingBasePrice = (BigDecimal)row.getCell("baseBuildPrice").getValue();
				BigDecimal roomBasePrice = (BigDecimal)row.getCell("baseRoomPrice").getValue();
				
				if(buildingBasePrice != null  &&  buildingBasePrice.compareTo(FDCHelper.ZERO) < 0){
					MsgBox.showInfo("第" + (i + 1) + "行,建筑底价不能为负数");
					return false;
				}
				if(roomBasePrice != null  &&  roomBasePrice.compareTo(FDCHelper.ZERO) < 0){
					MsgBox.showInfo("第" + (i + 1) + "行,套内底价不能为负数");
					return false;
				}
				/*
				BigDecimal buildAreaCellValue = (BigDecimal) row.getCell(
						"buildArea").getValue();
				BigDecimal buildArea = buildAreaCellValue == null ? FDCHelper.ZERO
						: buildAreaCellValue;
				BigDecimal roomArea = (BigDecimal) row.getCell("roomArea")
						.getValue();
				// 录入建筑面积的情况下，套内面积不允许为空
				if (buildAreaCellValue != null && roomArea == null) {
					MsgBox.showInfo("第" + (i + 1) + "行,请录入套内面积");
					return false;
				}
				if (roomArea == null) {
					roomArea = FDCHelper.ZERO;
				}
				if (roomArea.compareTo(buildArea) > 0) {
					MsgBox.showInfo("第" + (i + 1) + "行,套内面积大于建筑面积");
					return false;
				}
				*/
			}
		}
		return true;
	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		IRow row = tblMain.getRow(rowIndex);
		BigDecimal buildingArea = (BigDecimal) row.getCell("buildArea")
				.getValue();
		BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
		if (colIndex == 5) {
			updateRoomPriceByBuildingPrice(row, buildingArea, roomArea);
		} else if (colIndex == 6) {
			updateBuildingPriceByRoomPrice(row, buildingArea, roomArea);
		}
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
				BigDecimal baseBuildPrice = (BigDecimal) row.getCell(
						"baseBuildPrice").getValue();
				room.setBaseBuildingPrice(baseBuildPrice);
				BigDecimal baseRoomPrice = (BigDecimal) row.getCell(
						"baseRoomPrice").getValue();
				room.setBaseRoomPrice(baseRoomPrice);
				colls.add(room);
			}
		}
		
		//--慎用submit,简单起见，这里循环调用远程更新接口 
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("baseBuildingPrice");
		sels.add("baseRoomPrice");
		for(int i=0; i<colls.size(); i++){
			RoomInfo room = (RoomInfo) colls.get(i);
			RoomFactory.getRemoteInstance().updatePartial(room, sels);
		}
		//---
		MsgBox.showInfo("保存成功!");
	}

	public void actionImportExcel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportExcel_actionPerformed(e);
		String fileName = SHEHelper.showExcelSelectDlg(this);
		SHEHelper.importExcelToTable(fileName, this.tblMain, 1, 1);
		
		//根据用户录入的底价计算另一个底价
		for(int i=0; i<tblMain.getRowCount(); i++){
			IRow row = tblMain.getRow(i);
			RoomInfo room = (RoomInfo) row.getUserObject();
			BigDecimal buildingArea = (BigDecimal) row.getCell("buildArea")
			.getValue();
			BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
			if (room.isIsCalByRoomArea()) {
				updateBuildingPriceByRoomPrice(row, buildingArea, roomArea);
			}else{
				updateRoomPriceByBuildingPrice(row, buildingArea, roomArea);
			}
		}
	}

	private void updateRoomPriceByBuildingPrice(IRow row, BigDecimal buildingArea, BigDecimal roomArea) {
		if (buildingArea != null) {
			BigDecimal baseBuildPrice = (BigDecimal) row.getCell("baseBuildPrice").getValue();
			RoomInfo room = (RoomInfo)row.getUserObject();
			if(room==null) return;
			if (baseBuildPrice != null) {
				BigDecimal amount = buildingArea.multiply(baseBuildPrice);
				BigDecimal baseRoomPrice = amount.divide(roomArea, 2,BigDecimal.ROUND_HALF_UP);
				row.getCell("baseRoomPrice").setValue(baseRoomPrice);
				if(room.isIsCalByRoomArea())
					row.getCell("roomBasePrice").setValue(baseRoomPrice.multiply(roomArea));
				else
					row.getCell("roomBasePrice").setValue(amount);
			}else{
				row.getCell("baseRoomPrice").setValue(null);
				row.getCell("roomBasePrice").setValue(null);
			}
		}
	}

	private void updateBuildingPriceByRoomPrice(IRow row, BigDecimal buildingArea, BigDecimal roomArea) {
		if (roomArea != null) {
			BigDecimal baseRoomPrice = (BigDecimal) row.getCell("baseRoomPrice").getValue();
			RoomInfo room = (RoomInfo)row.getUserObject();
			if(room==null) return;
			if (baseRoomPrice != null) {
				BigDecimal amount = roomArea.multiply(baseRoomPrice);
				BigDecimal baseBuildPrice = amount.divide(buildingArea, 2,	BigDecimal.ROUND_HALF_UP);
				row.getCell("baseBuildPrice").setValue(baseBuildPrice);
				if(room.isIsCalByRoomArea())
					row.getCell("roomBasePrice").setValue(amount);
				else
					row.getCell("roomBasePrice").setValue(baseBuildPrice.multiply(buildingArea));
			} else {
				row.getCell("baseBuildPrice").setValue(null);
				row.getCell("roomBasePrice").setValue(null);
			}
		}
	}

	/**
	 * 底价复核操作
	 */
	public void actionPriceAudit_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPriceAudit_actionPerformed(e);
		this.checkSelected();

		// 先检查保存
		if (this.isEditedTable()) {
			MsgBox.showInfo("请先保存!");
			return;
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			RoomInfo room = (RoomInfo) row.getUserObject();
			if (!room.isIsAreaAudited()) {
				MsgBox.showInfo("第" + (1 + selectRows[i]) + "行的面积没有复核!");
				return;
			}
			if (FDCHelper.isNullZero(room.getBaseBuildingPrice())) {
				MsgBox.showInfo("第" + (1 + selectRows[i]) + "行的建筑底价为0!");
				return;
			}
			if (FDCHelper.isNullZero(room.getBaseRoomPrice())) {
				MsgBox.showInfo("第" + (1 + selectRows[i]) + "行的套内底价为0!");
				return;
			}
		}

		RoomFactory.getRemoteInstance().doBasePriceAudit(getSelectedIdValues());
		refresh(e);
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
	//add by warship for server exception at 2010/09/13
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
}