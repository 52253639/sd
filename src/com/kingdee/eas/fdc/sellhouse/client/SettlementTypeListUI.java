/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fm.nt.NTTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SettlementTypeListUI extends AbstractSettlementTypeListUI {
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getType() != 1)
			return;
		int activeRowIndex = e.getRowIndex();
		int activeColumnIndex = e.getColIndex();
		if (activeColumnIndex == this.tblMain.getColumnIndex("isSelected")) {
			ICell iCell = this.tblMain.getRow(activeRowIndex).getCell("isSelected");
			if (Boolean.TRUE.equals(iCell.getValue())) {
				iCell.setValue(Boolean.FALSE);
			} else {
				iCell.setValue(Boolean.TRUE);
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		getSettlementTypeData();
	}

	private void getSettlementTypeData() {
		SettlementTypeCollection stCol = new SettlementTypeCollection();
		EntityViewInfo evi = new EntityViewInfo();

		evi.getSelector().add("*");
		evi.getSelector().add("ntType.*");

		evi.getSorter().add(new SorterItemInfo("id"));
		evi.getSorter().add(new SorterItemInfo("name"));

		try {
			stCol = SettlementTypeFactory.getRemoteInstance().getSettlementTypeCollection(evi);
		} catch (BOSException ex) {
			handleException(ex);
		}
		if (stCol.size() > 0) {
			this.tblMain.removeRows();
			this.tblMain.checkParsed();
			for (int i = 0; i < stCol.size(); i++) {
				SettlementTypeInfo info = stCol.get(i);
				if (info != null) {
					IRow row = this.tblMain.addRow();
					row.setUserObject(info);
					row.getCell("isSelected").setValue(Boolean.FALSE);
					row.getCell("number").setValue(info.getNumber());
					row.getCell("name").setValue(info.getName());
					row.getCell("description").setValue(info.getDescription());
					row.getCell("simpleName").setValue(info.getSimpleName());
					if (Boolean.TRUE.booleanValue() == info.isIsDefault()) {
						row.getCell("isDefault").setValue(Boolean.TRUE);
					} else {
						row.getCell("isDefault").setValue(Boolean.FALSE);
					}
					row.getCell("deletedStatus").setValue(info.getDeletedStatus());

					NTTypeInfo ntInfo = (NTTypeInfo) info.getNtType();
					if (ntInfo != null) {
						row.getCell("ntTypeID").setValue(ntInfo.getId());
						row.getCell("ntTypeName").setValue(ntInfo.getName());
						row.getCell("ntTypeNumber").setValue(ntInfo.getNumber());
						row.getCell("ntTypeGroup").setValue(ntInfo.getGroup());
					}
				}
			}
		}

	}

	private static final Logger logger = CoreUIObject.getLogger(SettlementTypeListUI.class);

	/**
	 * output class constructor
	 */
	public SettlementTypeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionConfirm_actionPerformed
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		super.actionConfirm_actionPerformed(e);
		SettlementTypeCollection setTypeCol = new SettlementTypeCollection();
		setTypeCol.clear();
		for (int j = 0; j < this.tblMain.getRowCount(); j++) {
			ICell iCell = this.tblMain.getRow(j).getCell("isSelected");
			if (Boolean.TRUE.equals(iCell.getValue())) {
				IRow row = this.tblMain.getRow(j);
				SettlementTypeInfo setTypeInfo = (SettlementTypeInfo) row.getUserObject();
				setTypeCol.add(setTypeInfo);
			}
		}
		this.getUIContext().put("setTypeCol", setTypeCol);
		this.destroyWindow();
	}

	/**
	 * output actionQuit_actionPerformed
	 */
	public void actionQuit_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuit_actionPerformed(e);
		SettlementTypeCollection setTypeCol = new SettlementTypeCollection();
		setTypeCol.clear();
		this.getUIContext().put("setTypeCol", setTypeCol);
		this.destroyWindow();
	}

	protected IObjectValue createNewData() {
		SettlementTypeInfo info = new SettlementTypeInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MoneyDefineFactory.getRemoteInstance();
	}

	private void initControl() {
		this.tblMain.checkParsed();
		this.btnComfirm.setVisible(true);
		this.btnComfirm.setEnabled(true);
		this.btnQuit.setVisible(true);
		this.btnQuit.setEnabled(true);

		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
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
		this.actionSave.setVisible(false);
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

}