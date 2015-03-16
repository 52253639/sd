/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo;
import com.kingdee.eas.fdc.sellhouse.CalculateMethodEnum;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomPriceSchemeSetUI extends AbstractRoomPriceSchemeSetUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomPriceSchemeSetUI.class);

	BuildingPriceSetInfo priceSet = null;

	/**
	 * output class constructor
	 */
	public RoomPriceSchemeSetUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		priceSet = (BuildingPriceSetInfo) this.getUIContext().get("priceSet");
		priceSet.getBuilding();
		this.tblFactors.checkParsed();
		this.tblFactors.getStyleAttributes().setLocked(true);
		this.tblFactors.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblFactorValue.checkParsed();
		tblFactorValue.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblFactorValue.getColumn(0).getStyleAttributes().setLocked(true);

		// KDFormattedTextField formattedTextField = new KDFormattedTextField(
		// KDFormattedTextField.DECIMAL);
		// formattedTextField.setPrecision(2);
		// formattedTextField.setSupportedEmpty(true);
		// formattedTextField.setNegatived(false);
		// ICellEditor numberEditor = new
		// KDTDefaultCellEditor(formattedTextField);
		// this.tblFactorValue.getColumn(1).setEditor(numberEditor);
		//this.tblFactorValue.getColumn(1).getStyleAttributes().setNumberFormat(
		// FDCHelper.getNumberFtm(2));
		this.tblFactorValue.getColumn(2).getStyleAttributes().setLocked(true);

		// SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("orgUnit.id", saleOrg.getId().toString()));
		// view.setFilter(filter);
		// SellProjectCollection sellProjects = SellProjectFactory
		// .getRemoteInstance().getSellProjectCollection(view);
		// Set idSet = new HashSet();
		// for (int i = 0; i < sellProjects.size(); i++) {
		// idSet.add(sellProjects.get(i).getId().toString());
		// }
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("project.id", priceSet.getBuilding().getSellProject().getId().toString()));
		this.f7PriceScheme.setEntityViewInfo(view);
		super.onLoad();
		this.f7PriceScheme.setValue(priceSet.getPriceScheme());
		this.txtBasePointAmount.setNegatived(false);
		this.txtBasePointAmount.setRemoveingZeroInDispaly(false);
		this.txtBasePointAmount.setRemoveingZeroInEdit(false);
		this.txtBasePointAmount.setPrecision(2);
		this.txtBasePointAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBasePointAmount.setSupportedEmpty(true);

		this.txtBasePointAmount.setValue(priceSet.getBasePointAmount());
		this.txtSumPriceAmount.setNegatived(false);
		this.txtSumPriceAmount.setRemoveingZeroInDispaly(false);
		this.txtSumPriceAmount.setRemoveingZeroInEdit(false);
		this.txtSumPriceAmount.setPrecision(2);
		this.txtSumPriceAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtSumPriceAmount.setSupportedEmpty(true);
		this.txtSumPriceAmount.setValue(priceSet.getSumPriceAmount());
		this.contSumPriceAmount.setVisible(false);
		if (this.getOprtState().equals("VIEW")) {
			this.f7PriceScheme.setEnabled(false);
			this.txtBasePointAmount.setEnabled(false);
			this.txtSumPriceAmount.setEnabled(false);
			this.tblFactorValue.getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblFactors_tableSelectChanged method
	 */
	protected void tblFactors_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblFactors_tableSelectChanged(e);
		int rowIndex = e.getSelectBlock().getBeginRow();
		if (rowIndex == -1) {
			return;
		}
		IRow row = this.tblFactors.getRow(rowIndex);
		PriceSetSchemeEntryInfo scheme = (PriceSetSchemeEntryInfo) row.getCell("factor").getUserObject();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("building.*");
		view.getSelector().add("buildingProperty.*");
		view.getSelector().add("buildUnit.*");
		view.getSelector().add("buildingFloor.*");
		view.getSelector().add("sight.*");
		view.getSelector().add("direction.*");
		view.getSelector().add("sellOrder.*");
		view.getSelector().add("newSight.*");
		view.getSelector().add("roomModel.*");
		view.getSelector().add("buildingProperty.*");
		view.getSelector().add("newNoise.*");
		view.getSelector().add("newEyeSignht.*");
		view.getSelector().add("newProduceRemark.*");
		view.getSelector().add("newDecorastd.*");
		view.getSelector().add("newPossStd.*");
		//add by yaoweiwen 2009-12-26 start
//		view.getSelector().add("decoraStd.*");		
//		view.getSelector().add("posseStd.*");	
		//end
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		BuildingInfo building = this.priceSet.getBuilding();
		filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
		Map contentMap = new TreeMap();
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			Object object = room.get(scheme.getFactorField());
			if (scheme.getFactorField().equals("houseProperty")) {
				object = room.getHouseProperty();
			}

			if (object == null)
				continue;

			BigDecimal initValue = null;
			if (scheme.getCalculateMethod().equals(CalculateMethodEnum.Difference)) {
				initValue = FDCHelper.ZERO;
			} else {
				initValue = FDCHelper.ONE;
			}

			if (object instanceof BigDecimal) {
				BigDecimal content = ((BigDecimal) object).setScale(2, BigDecimal.ROUND_HALF_UP);
				contentMap.put(content, initValue);
			} else if (object instanceof Integer) {
				int iObject = ((Integer) object).intValue();
				BigDecimal content = new BigDecimal(iObject);
				content = content.setScale(0, BigDecimal.ROUND_HALF_UP);
				contentMap.put(content, initValue);
			} else {
				if (object != null) {
					contentMap.put(object.toString(), initValue);
				}
			}
		}

		Object baseKey = null;
		BuildingPriceSetEntryCollection entrys = (BuildingPriceSetEntryCollection) row.getUserObject();
		for (int i = 0; i < entrys.size(); i++) {
			BuildingPriceSetEntryInfo entry = entrys.get(i);
			if (entry.getFactorContentA() != null) {
				if (contentMap.containsKey(entry.getFactorContentA())) {
					contentMap.put(entry.getFactorContentA(), entry.getValue());
				}
			}
			if (entry.getFactorContentS() != null) {
				if (contentMap.containsKey(entry.getFactorContentS())) {
					contentMap.put(entry.getFactorContentS(), entry.getValue());
				}
			}
			if (entry.isIsBasePoint()) {
				if (entry.getFactorContentA() != null) {
					if (contentMap.containsKey(entry.getFactorContentA())) {
						baseKey = entry.getFactorContentA();
					}
				}
				if (entry.getFactorContentS() != null) {
					if (contentMap.containsKey(entry.getFactorContentS())) {
						baseKey = entry.getFactorContentS();
					}
				}
			}
		}
		Set set = contentMap.keySet();
		// 排序
		Object[] temObjs = set.toArray();
		Arrays.sort(temObjs);

		tblFactorValue.removeRows();
		tblFactorValue.setUserObject(row);
		boolean isFirst = true;
		for (int i = 0; i < temObjs.length; i++) { // Iterator iter =
			// set.iterator();
			// iter.hasNext();
			// Object key = (Object) iter.next();
			Object key = temObjs[i];
			IRow valueRow = this.tblFactorValue.addRow();

			KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
			formattedTextField.setPrecision(2);
			formattedTextField.setSupportedEmpty(true);
			if (scheme.getCalculateMethod().equals(CalculateMethodEnum.Difference)) {
				formattedTextField.setNegatived(true);
			} else {
				formattedTextField.setNegatived(false);

			}
			ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
			valueRow.getCell(1).setEditor(numberEditor);
			valueRow.getCell(1).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			valueRow.getCell("factorContent").setValue(key);
			valueRow.getCell("factorValue").setValue(contentMap.get(key));
			if (baseKey == null) {
				if (isFirst) {
					valueRow.getCell("isBasePoint").setValue(Boolean.TRUE);
					valueRow.getCell("factorValue").getStyleAttributes().setLocked(true);
					isFirst = false;
				}
			} else {
				boolean isEquelKey = false;
				if (key instanceof BigDecimal) {
					if (((BigDecimal) key).compareTo((BigDecimal) baseKey) == 0) {
						isEquelKey = true;
					}
				} else {
					if (key.equals(baseKey)) {
						isEquelKey = true;
					}
				}
				if (isEquelKey) {
					valueRow.getCell("isBasePoint").setValue(Boolean.TRUE);
					valueRow.getCell("factorValue").getStyleAttributes().setLocked(true);
				}
			}
		}
		if (this.getOprtState().equals("VIEW")) {
			this.tblFactorValue.getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		// super.btnYes_actionPerformed(e);
		PriceSetSchemeInfo scheme = (PriceSetSchemeInfo) this.f7PriceScheme.getValue();
		if (scheme == null) {
			MsgBox.showInfo("定价方案必须录入!");
			return;
		}
		priceSet.setPriceScheme(scheme);
		priceSet.setBasePointAmount(this.txtBasePointAmount.getBigDecimalValue());
		priceSet.setSumPriceAmount(this.txtSumPriceAmount.getBigDecimalValue());
		this.priceSet.getPriceSetEntry().clear();
		for (int i = 0; i < this.tblFactors.getRowCount(); i++) {
			BuildingPriceSetEntryCollection entrys = (BuildingPriceSetEntryCollection) this.tblFactors.getRow(i).getUserObject();
			this.priceSet.getPriceSetEntry().addCollection(entrys);
		}
		this.getUIContext().put("priceSet", priceSet);
		this.getUIContext().put("YES", Boolean.TRUE);
		this.destroyWindow();
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		// super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	// /////////
	public BuildingPriceSetInfo getSetting() {
		PriceSetSchemeInfo scheme = (PriceSetSchemeInfo) this.f7PriceScheme.getValue();
		priceSet.setPriceScheme(scheme);
		priceSet.setBasePointAmount(this.txtBasePointAmount.getBigDecimalValue());
		priceSet.setSumPriceAmount(this.txtSumPriceAmount.getBigDecimalValue());
		this.priceSet.getPriceSetEntry().clear();
		for (int i = 0; i < this.tblFactors.getRowCount(); i++) {
			BuildingPriceSetEntryCollection entrys = (BuildingPriceSetEntryCollection) this.tblFactors.getRow(i).getUserObject();
			this.priceSet.getPriceSetEntry().addCollection(entrys);
		}
		return priceSet;
	}

	// ///////

	public static BuildingPriceSetInfo showUI(CoreUI ui, BuildingPriceSetInfo priceSet, String oprt) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("priceSet", priceSet);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomPriceSchemeSetUI.class.getName(), uiContext, null, oprt);
		uiWindow.show();
		priceSet = (BuildingPriceSetInfo) uiWindow.getUIObject().getUIContext().get("priceSet");
		if (uiWindow.getUIObject().getUIContext().get("YES") == null) {
			return null;
		}
		return priceSet;
	}

	protected void f7PriceScheme_dataChanged(DataChangeEvent e) throws Exception {

		super.f7PriceScheme_dataChanged(e);
		PriceSetSchemeInfo scheme = (PriceSetSchemeInfo) e.getNewValue();
		if (scheme == null) {
			this.tblFactors.removeRows();
			return;
		}
		this.tblFactors.removeRows();
		this.tblFactorValue.removeRows();
		Map buildingSetMap = getPriceSetValueMap();
		PriceSetSchemeEntryCollection entrys = scheme.getEntrys();
		for (int i = 0; i < entrys.size(); i++) {
			PriceSetSchemeEntryInfo schemeEntry = entrys.get(i);
			IRow row = this.tblFactors.addRow();
			BuildingPriceSetEntryCollection setValues = (BuildingPriceSetEntryCollection) buildingSetMap.get(schemeEntry.getId().toString());
			if (setValues == null) {
				setValues = new BuildingPriceSetEntryCollection();
			}
			row.setUserObject(setValues);
			row.getCell("factor").setValue(schemeEntry.getFactorName());
			row.getCell("factor").setUserObject(schemeEntry);
			row.getCell("calMethod").setValue(schemeEntry.getCalculateMethod());
		}
		if (this.tblFactors.getRowCount() > 0) {
			this.tblFactors.getSelectManager().select(0, 0);
		}
	}

	public void loadFields() {
		EventListener[] listeners = this.f7PriceScheme.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			this.f7PriceScheme.removeDataChangeListener((DataChangeListener) listeners[i]);
		}
		super.loadFields();
		for (int i = 0; i < listeners.length; i++) {
			this.f7PriceScheme.addDataChangeListener((DataChangeListener) listeners[i]);
		}
	}

	private Map getPriceSetValueMap() {
		BuildingPriceSetEntryCollection priceSetEntrys = this.priceSet.getPriceSetEntry();
		Map buildingSetMap = new TreeMap();
		for (int j = 0; j < priceSetEntrys.size(); j++) {
			BuildingPriceSetEntryInfo buildingSetEntry = priceSetEntrys.get(j);
			String entryId = buildingSetEntry.getSchemeEntry().getId().toString();
			if (buildingSetMap.containsKey(entryId)) {
				BuildingPriceSetEntryCollection buildingSetEntrys = (BuildingPriceSetEntryCollection) buildingSetMap.get(entryId);
				buildingSetEntrys.add(buildingSetEntry);
			} else {
				BuildingPriceSetEntryCollection buildingSetEntrys = new BuildingPriceSetEntryCollection();
				buildingSetEntrys.add(buildingSetEntry);
				buildingSetMap.put(entryId, buildingSetEntrys);
			}
		}
		return buildingSetMap;
	}

	protected void tblFactorValue_editStopped(KDTEditEvent e) throws Exception {
		super.tblFactorValue_editStopped(e);
		storeValue();
	}

	private void storeValue() {
		IRow row = (IRow) this.tblFactorValue.getUserObject();
		BuildingPriceSetEntryCollection entrys = (BuildingPriceSetEntryCollection) row.getUserObject();
		entrys.clear();
		PriceSetSchemeEntryInfo key = (PriceSetSchemeEntryInfo) row.getCell(0).getUserObject();
		for (int i = 0; i < this.tblFactorValue.getRowCount(); i++) {
			BuildingPriceSetEntryInfo entry = new BuildingPriceSetEntryInfo();
			entry.setSchemeEntry(key);
			Object content = this.tblFactorValue.getCell(i, "factorContent").getValue();
			if (content instanceof BigDecimal) {
				entry.setFactorContentA((BigDecimal) content);
			} else {
				entry.setFactorContentS((String) content);
			}
			BigDecimal value = (BigDecimal) this.tblFactorValue.getCell(i, "factorValue").getValue();
			if (value == null) {
				value = FDCHelper.ZERO;
			}
			entry.setValue(value);
			Object isBase = this.tblFactorValue.getCell(i, "isBasePoint").getValue();
			if (isBase != null) {
				entry.setIsBasePoint(true);
			} else {
				entry.setIsBasePoint(false);
			}
			entrys.add(entry);
		}
	}

	protected void tblFactorValue_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblFactorValue_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 1) {
			if (this.getOprtState().equals("VIEW")) {
				return;
			}
			int colIndex = e.getColIndex();
			if (colIndex == 2) {
				IRow row = (IRow) this.tblFactorValue.getUserObject();
				CalculateMethodEnum cal = (CalculateMethodEnum) row.getCell("calMethod").getValue();
				Object value = this.tblFactorValue.getCell(e.getRowIndex(), colIndex).getValue();
				if (cal.equals(CalculateMethodEnum.Coefficient)) {
					this.tblFactorValue.getCell(e.getRowIndex(), 1).setValue(FDCHelper.ONE);
				} else {
					this.tblFactorValue.getCell(e.getRowIndex(), 1).setValue(FDCHelper.ZERO);
				}
				if (value == null) {
					this.tblFactorValue.getCell(e.getRowIndex(), colIndex).setValue(Boolean.TRUE);
					this.tblFactorValue.getCell(e.getRowIndex(), 1).getStyleAttributes().setLocked(true);
					for (int i = 0; i < this.tblFactorValue.getRowCount(); i++) {
						if (i != e.getRowIndex()) {
							this.tblFactorValue.getCell(i, colIndex).setValue(null);
							this.tblFactorValue.getCell(i, 1).getStyleAttributes().setLocked(false);
						}
					}
				}
			}
			this.storeValue();
		}
	}
}