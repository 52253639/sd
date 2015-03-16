/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.EventListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SeqAdjustmentUI extends AbstractSeqAdjustmentUI {
	private static final Logger logger = CoreUIObject.getLogger(SeqAdjustmentUI.class);

	/**
	 * output class constructor
	 */
	public SeqAdjustmentUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		EventListener[] unitListeners = this.comboUnit.getListeners(DataChangeListener.class);
		for (int i = 0; i < unitListeners.length; i++) {
			this.comboUnit.removeItemListener((ItemListener) unitListeners[i]);
		}
		EventListener[] floorListeners = this.comboFloor.getListeners(DataChangeListener.class);
		for (int i = 0; i < floorListeners.length; i++) {
			this.comboFloor.removeItemListener((ItemListener) floorListeners[i]);
		}
		super.loadFields();
		for (int i = 0; i < unitListeners.length; i++) {
			this.comboUnit.addItemListener((ItemListener) unitListeners[i]);
		}
		for (int i = 0; i < floorListeners.length; i++) {
			this.comboFloor.addItemListener((ItemListener) floorListeners[i]);
		}
	}

	/**
	 * output comboFloor_itemStateChanged method
	 */
	protected void comboFloor_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
		BuildingInfo building = (BuildingInfo) this.getUIContext().get("building");
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		cmbFloorValueChange(buildUnit, building, sellProject);
	}

	/**
	 * output tblMain_editStopped method
	 */
	protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
	}

	/**
	 * output comboUnit_itemStateChanged method
	 */
	protected void comboUnit_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
		BuildingInfo building = (BuildingInfo) this.getUIContext().get("building");
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		comboFoorSetDate(buildUnit, building, sellProject);
		cmbFloorValueChange(buildUnit, building, sellProject);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			if (this.tblMain.getRow(i).getCell("newSeq").getValue() == null) {
				MsgBox.showInfo("请输入第" + (i + 1) + "行的新序号！");
				return;
			}
			if (this.tblMain.getRow(i).getCell("seqCount").getValue() == null) {
				MsgBox.showInfo("请输入第" + (i + 1) + "行的占号个数！");
				return;
			}
			BigDecimal newSeq = FDCHelper.toBigDecimal(this.tblMain.getRow(i).getCell("newSeq").getValue());
			BigDecimal seqCount = FDCHelper.toBigDecimal(this.tblMain.getRow(i).getCell("seqCount").getValue());
			if (newSeq.intValue() < 1) {
				MsgBox.showInfo("第" + (i + 1) + "行的新序号不能小于1 ！");
				return;
			}
			if (seqCount.intValue() < 1) {
				MsgBox.showInfo("第" + (i + 1) + "行的占号个数不能小于1 ！");
				return;
			}
			for (int j = 0; j < this.tblMain.getRowCount(); j++) {
				if (j != i) {
					if (this.tblMain.getRow(j).getCell("newSeq").getValue() == null) {
						MsgBox.showInfo("请输入第" + (j + 1) + "行的新序号！");
						return;
					}
					BigDecimal newSeqNext = FDCHelper.toBigDecimal(this.tblMain.getRow(j).getCell("newSeq").getValue());
					if (newSeqNext.equals(newSeq)) {
						MsgBox.showInfo("第" + (i + 1) + "行与第" + (j + 1) + "行的序号相同，请检查");
						return;
					}
					if (newSeqNext.compareTo(newSeq) > 0) {
						if ((newSeq.intValue() + seqCount.intValue() - 1) >= (newSeqNext.intValue())) {
							MsgBox.showInfo("第" + (i + 1) + "行的序号和占号个数与第" + (j + 1) + "行的序号相冲突，请检查");
							return;
						}
					}
				}
			}
		}

		CoreBaseCollection roomColl = new CoreBaseCollection();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			RoomInfo info = (RoomInfo) this.tblMain.getRow(i).getUserObject();
			if (info != null && this.tblMain.getRow(i).getCell("newSeq").getValue() != null) {
				BigDecimal newSeq = FDCHelper.toBigDecimal(this.tblMain.getRow(i).getCell("newSeq").getValue());
				if (newSeq != null && this.tblMain.getRow(i).getCell("seqCount").getValue() != null) {
					info.setSerialNumber(newSeq.intValue());
					BigDecimal seqCount = FDCHelper.toBigDecimal(this.tblMain.getRow(i).getCell("seqCount").getValue());
					if (seqCount != null) {
						info.setEndSerialNumber(newSeq.intValue() + seqCount.intValue() - 1);
						roomColl.add(info);
					}
				}
			}
		}
		if (roomColl.size() > 0) {
			RoomFactory.getRemoteInstance().update(roomColl);
			MsgBox.showInfo("调整成功！");
		} else {
			MsgBox.showInfo("未有序号调整，请检查调整数据！");
		}
	}

	public void onLoad() throws Exception {
		BuildingFloorEntryInfo buildFloorEntry = (BuildingFloorEntryInfo) this.getUIContext().get("buildFloorEntry");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
		BuildingInfo building = (BuildingInfo) this.getUIContext().get("building");
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		RoomInfo roomInfo = (RoomInfo) this.getUIContext().get("selectedRoom");
		if (roomInfo != null) {
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("buildingFloor.*");
			sel.add("buildUnit.*");
			roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomInfo.getId().toString())), sel);
			if (roomInfo.getBuildingFloor() != null) {
				buildFloorEntry = roomInfo.getBuildingFloor();
			}
			if (roomInfo.getBuildUnit() != null) {
				buildUnit = roomInfo.getBuildUnit();
			}
		}
		comboUnitSetDate(buildUnit, building, sellProject);
		comboFoorSetDate(buildFloorEntry, buildUnit, building, sellProject);
		cmbFloorValueChange(buildFloorEntry, buildUnit, building, sellProject);
		initControl();
	}

	private void initControl() {
		this.tblMain.getColumn("number").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("roomNo").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("serialNumber").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("serialNumber").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("newSeq").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("seqCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getStyleAttributes().setLocked(false);
		this.tblMain.getColumn("newSeq").getStyleAttributes().setLocked(false);
		this.tblMain.getColumn("seqCount").getStyleAttributes().setLocked(false);

		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setEnabled(true);
		this.btnSave.setText("保存");
		this.btnSave.setToolTipText("保存");
		this.btnCopy.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnRemove.setVisible(false);

		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionSave.setEnabled(true);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRemove.setVisible(false);

	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		this.tblMain.getStyleAttributes().setLocked(false);
		this.tblMain.getColumn("newSeq").getStyleAttributes().setLocked(false);
		this.tblMain.getColumn("seqCount").getStyleAttributes().setLocked(false);

	}

	protected IObjectValue createNewData() {
		RoomInfo info = new RoomInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return SeqAdjustmentUI.class.getName();
	}

	private void cmbFloorValueChange(BuildingFloorEntryInfo buildFloorEntry, BuildingUnitInfo buildUnit, BuildingInfo building, SellProjectInfo sellProject) {
		RoomCollection roomCol = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");

		FilterInfo filter = new FilterInfo();
		if (buildUnit != null) {
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
		} else {
			if (this.comboUnit.getSelectedIndex() >= 0) {
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.name", this.comboUnit.getSelectedItem()));
			}
		}
		if (building != null && building.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId()));
		}
		if (sellProject != null && sellProject.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId()));
		}
		if (buildFloorEntry != null) {
			filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id", buildFloorEntry.getId().toString()));
		} else {
			if (this.comboFloor.getSelectedIndex() >= 0) {
				filter.getFilterItems().add(new FilterItemInfo("buildingFloor.floorAlias", this.comboFloor.getSelectedItem()));
			}
		}

		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("unit"));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSorter().add(new SorterItemInfo("serialNumber"));
		try {
			roomCol = RoomFactory.getRemoteInstance().getRoomCollection(view);
		} catch (BOSException ex) {
			handleException(ex);
		}
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		if (roomCol.size() > 0) {

			for (int i = 0; i < roomCol.size(); i++) {
				IRow row = this.tblMain.addRow();
				RoomInfo info = roomCol.get(i);
				row.setUserObject(info);
				row.getCell("number").setValue(info.getNumber());
				row.getCell("serialNumber").setValue(info.getSerialNumber() + "");
				row.getCell("newSeq").setValue(info.getSerialNumber() + "");
				if (info.getEndSerialNumber() - info.getSerialNumber() <= 0) {
					row.getCell("seqCount").setValue("1");
				} else {
					row.getCell("seqCount").setValue("" + (info.getEndSerialNumber() - info.getSerialNumber() + 1));
				}

			}

		}

	}

	private void cmbFloorValueChange(BuildingUnitInfo buildUnit, BuildingInfo building, SellProjectInfo sellProject) {
		RoomCollection roomCol = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");

		FilterInfo filter = new FilterInfo();
		if (buildUnit != null) {
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
		} else {
			if (this.comboUnit.getSelectedIndex() >= 0) {
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.name", this.comboUnit.getSelectedItem()));
			}
		}
		if (building != null && building.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId()));
		}
		if (sellProject != null && sellProject.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId()));
		}

		if (this.comboFloor.getSelectedIndex() >= 0) {
			filter.getFilterItems().add(new FilterItemInfo("buildingFloor.floorAlias", this.comboFloor.getSelectedItem()));
		}

		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("unit"));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSorter().add(new SorterItemInfo("serialNumber"));
		try {
			roomCol = RoomFactory.getRemoteInstance().getRoomCollection(view);
		} catch (BOSException ex) {
			handleException(ex);
		}
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		if (roomCol.size() > 0) {
			for (int i = 0; i < roomCol.size(); i++) {
				IRow row = this.tblMain.addRow();
				RoomInfo info = roomCol.get(i);
				row.setUserObject(info);
				row.getCell("number").setValue(info.getNumber());
				row.getCell("roomNo").setValue(info.getRoomNo());
				row.getCell("serialNumber").setValue(info.getSerialNumber() + "");
				row.getCell("newSeq").setValue(info.getSerialNumber() + "");
				if (info.getEndSerialNumber() - info.getSerialNumber() <= 0) {
					row.getCell("seqCount").setValue("1");
				} else {
					row.getCell("seqCount").setValue("" + (info.getEndSerialNumber() - info.getSerialNumber() + 1));
				}

			}

		}

	}

	private void comboFoorSetDate(BuildingFloorEntryInfo buildFloorEntry, BuildingUnitInfo buildUnit, BuildingInfo building, SellProjectInfo sellProject) {
		try {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
			view.setSelector(sel);
			view.setFilter(filter);
			view.getSorter().add(new SorterItemInfo("floor"));
			BuildingFloorEntryCollection col = BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
			this.comboFloor.removeAllItems();
			for (int i = 0; i < col.size(); i++) {
				BuildingFloorEntryInfo info = col.get(i);
				if (info != null) {
					this.comboFloor.addItem(info.getFloorAlias());
				}
			}

		} catch (Exception e) {
			handleException(e);
		}
		if (buildFloorEntry != null) {
			this.comboFloor.setSelectedItem((Object) (buildFloorEntry.getFloorAlias()));
		} else {
			if (this.comboFloor.getItemCount() > 0) {
				this.comboFloor.setSelectedIndex(0);
			}

		}

	}

	private void comboFoorSetDate(BuildingUnitInfo buildUnit, BuildingInfo building, SellProjectInfo sellProject) {
		try {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
			view.setSelector(sel);
			view.setFilter(filter);
			view.getSorter().add(new SorterItemInfo("floor"));
			BuildingFloorEntryCollection col = BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
			this.comboFloor.removeAllItems();
			for (int i = 0; i < col.size(); i++) {
				BuildingFloorEntryInfo info = col.get(i);
				if (info != null) {
					this.comboFloor.addItem(info.getFloorAlias());
				}
			}

		} catch (Exception e) {
			handleException(e);
		}
		if (this.comboFloor.getItemCount() > 0) {
			this.comboFloor.setSelectedIndex(0);
		}
	}

	private void comboUnitSetDate(BuildingUnitInfo buldUnit, BuildingInfo building, SellProjectInfo sellProject) throws Exception {

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
		view.setSelector(sel);
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("name"));
		BuildingUnitCollection col = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
		this.comboUnit.removeAllItems();
		for (int i = 0; i < col.size(); i++) {
			BuildingUnitInfo info = col.get(i);
			if (info != null) {
				this.comboUnit.addItem(info.getName());
			}
		}
		if (buldUnit != null) {
			this.comboUnit.setSelectedItem((Object) (buldUnit.getName().toString()));
		} else {
			if (this.comboUnit.getItemCount() > 0) {
				this.comboUnit.setSelectedIndex(0);
			}

		}

	}
}