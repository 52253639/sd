/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class F7SelectRentMoneyDefineListUI extends AbstractF7SelectRentMoneyDefineListUI {
	public static final Color KEY_LOCKED_ROW = new Color(0xE8E8E3);
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
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

	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		TenancyRoomPayListEntryCollection payCol = new TenancyRoomPayListEntryCollection();
		TenancyRoomEntryCollection roomCol = new TenancyRoomEntryCollection();
		for (int j = 0; j < this.tblMain.getRowCount(); j++) {
			ICell iCell = this.tblMain.getRow(j).getCell("isSelected");
			if (Boolean.TRUE.equals(iCell.getValue())) {
				IRow seleRow = this.tblMain.getRow(j);
				TenancyRoomPayListEntryInfo payInfo = (TenancyRoomPayListEntryInfo) seleRow.getCell("strartDate").getUserObject();
				TenancyRoomEntryInfo roomEntryInfo = (TenancyRoomEntryInfo) seleRow.getCell("endDate").getUserObject();
				payCol.add(payInfo);
				roomCol.add(roomEntryInfo);
			}
		}
		this.getUIContext().put("payCol", payCol);
		this.getUIContext().put("roomCol", roomCol);
		this.destroyWindow();
	}

	public void actionUnselect_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnselect_actionPerformed(e);
		TenancyRoomPayListEntryCollection payCol = new TenancyRoomPayListEntryCollection();
		TenancyRoomEntryCollection roomCol = new TenancyRoomEntryCollection();
		payCol.clear();
		roomCol.clear();
		this.getUIContext().put("payCol", payCol);
		this.getUIContext().put("roomCol", roomCol);
		this.destroyWindow();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		execQuery();
	}

	private void initControl() {
		this.tblMain.checkParsed();
		
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
		this.actionAddNew.setVisible(false);
		
		this.btnSelect.setVisible(true);
		this.btnSelect.setEnabled(true);
		this.btnUnSelect.setVisible(true);
		this.btnUnSelect.setEnabled(true);
		
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("isSelected").getStyleAttributes().setLocked(false);
	}

	private void execQuery() throws BOSException {
		String tenId = (String) this.getUIContext().get("tenId");
		if(tenId == null){
			logger.error("error impossible...");
			this.abort();
		}
		
		this.tblMain.removeRows(false);
		EntityViewInfo evi = new EntityViewInfo();
		
		evi.getSelector().add("*");
		evi.getSelector().add("roomPayList.*");
		evi.getSelector().add("roomPayList.moneyDefine.name");
		evi.getSelector().add("roomPayList.moneyDefine.number");
		evi.getSelector().add("roomPayList.moneyDefine.moneyType");
		evi.getSelector().add("roomPayList.moneyDefine.sysType");
		evi.getSelector().add("roomPayList.moneyDefine.isEnabled");
		
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy", tenId));
		evi.setFilter(filterInfo);

		evi.getSorter().add(new SorterItemInfo("roomPayList.leaseSeq"));
		evi.getSorter().add(new SorterItemInfo("roomLongNum"));
		
		TenancyRoomEntryCollection csCol = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(evi);
	
		for (int i = 0; i < csCol.size(); i++) {
			TenancyRoomEntryInfo roomEntryInfo = csCol.get(i);
			for (int m = 0; m < roomEntryInfo.getRoomPayList().size(); m++) {
				TenancyRoomPayListEntryInfo payInfo = roomEntryInfo.getRoomPayList().get(m);
				if (payInfo == null) {
					return;
				}
				IRow row = this.tblMain.addRow();
				row.getCell("isSelected").setValue(Boolean.FALSE);
				row.getCell("leaseSeq").setValue("" + payInfo.getLeaseSeq());
				row.getCell("moneyDefine").setValue(payInfo.getMoneyDefine().getName());
				row.getCell("room").setValue(roomEntryInfo.getRoomLongNum());
				row.getCell("strartDate").setValue(payInfo.getStartDate());
				row.getCell("endDate").setValue(payInfo.getEndDate());
				row.getCell("appAmount").setValue(payInfo.getAppAmount());
				row.getCell("paidAmount").setValue(payInfo.getActAmount());
				row.getCell("strartDate").setUserObject(payInfo);
				row.getCell("endDate").setUserObject(roomEntryInfo);
				setLockActRevAmount(payInfo,row);
			}
			this.tblMain.getMergeManager().mergeBlock(0, 0, this.tblMain.getRowCount() - 1, 1, KDTMergeManager.FREE_MERGE);
		}
	}

	
	private void setLockActRevAmount(IRevListInfo revListInfo,IRow row)
	{
		if(!revListInfo.isIsCanRevBeyond()){
			BigDecimal remainAppAmount = CRMHelper.getBigDecimal(revListInfo.getFinalAppAmount());
			BigDecimal allRemainAMount = CRMHelper.getBigDecimal(revListInfo.getAllRemainAmount());
			if(allRemainAMount.compareTo(remainAppAmount) >= 0)	{
				row.getStyleAttributes().setBackground(KEY_LOCKED_ROW);
//				row.getStyleAttributes().setLocked(true);
				row.getCell("isSelected").getStyleAttributes().setLocked(true);
			}
		}
	}
	private static final Logger logger = CoreUIObject.getLogger(F7SelectRentMoneyDefineListUI.class);

	public F7SelectRentMoneyDefineListUI() throws Exception {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected void checkIsOUSealUp() throws Exception {
	}
	
	protected IObjectValue createNewData() {
		return null;
	}

}