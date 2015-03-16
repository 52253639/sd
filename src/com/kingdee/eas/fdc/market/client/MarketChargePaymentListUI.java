/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.scm.common.ExpenseItemInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.market.MarketChargePaymentEntryCollection;
import com.kingdee.eas.fdc.market.MarketChargePaymentEntryFactory;
import com.kingdee.eas.fdc.market.MarketChargePaymentEntryInfo;
import com.kingdee.eas.fdc.market.MarketManageEntryCollection;
import com.kingdee.eas.fdc.market.MarketManageEntryInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketChargePaymentListUI extends AbstractMarketChargePaymentListUI {

	private static final Logger logger = CoreUIObject.getLogger(MarketChargePaymentListUI.class);

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() != 1)
			return;
		int activeColumnIndex = e.getColIndex();
		if (activeColumnIndex != this.tblMain.getColumnIndex("paymentAmount")) {
			return;
		}

	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		if (e.getType() != 1)
			return;
		int activeRowIndex = e.getRowIndex();
		int activeColumnIndex = e.getColIndex();
		if (activeColumnIndex == this.tblMain.getColumnIndex("paymentAmount")) {
			IRow row = this.tblMain.getRow(activeRowIndex);
			BigDecimal factAmount = FDCHelper.ZERO;
			factAmount = FDCHelper.toBigDecimal(row.getCell("factAmount").getValue());
			BigDecimal paidAmount = FDCHelper.ZERO;
			paidAmount = FDCHelper.toBigDecimal(row.getCell("paidAmount").getValue());
			BigDecimal paymentAmount = FDCHelper.ZERO;
			paymentAmount = FDCHelper.toBigDecimal(row.getCell("paymentAmount").getValue());
			if (paymentAmount.compareTo(factAmount.subtract(paidAmount)) > 0) {
				MsgBox.showInfo("本次付款金额超出剩余未付金额");
				row.getCell("paymentAmount").setValue(factAmount.subtract(paidAmount));
				return;
			}
		} else {
			return;
		}
	}

	protected String getKeyFieldName() {
		return "id";
	}

	public void onLoad() throws Exception {
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.btnRemove.setText("");
		this.btnRemove.setToolTipText("");
		this.btnRemove.setSize(0, 0);
		this.btnConfirm.setEnabled(true);
		this.btnExit.setEnabled(true);
		MarketManageEntryCollection chargeCol = (MarketManageEntryCollection) this.getUIContext().get("chargeCol");
		loadEntrys(chargeCol);
	}

	private void loadEntrys(MarketManageEntryCollection entrys) {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		for (int i = 0; i < entrys.size(); i++) {
			MarketManageEntryInfo entryInfo = entrys.get(i);
			if (entryInfo != null) {
				ExpenseItemInfo expenseItemInfo =entryInfo.getCostNumber();
				if (expenseItemInfo != null) {
					IRow row = this.tblMain.addRow();
					row.setUserObject(entryInfo);
					row.getCell("chargeCode").setValue(expenseItemInfo.getName());
					row.getCell("chargeCode").setUserObject(expenseItemInfo);
					row.getCell("chargeName").setValue(entryInfo.getCostName());
					row.getCell("factAmount").setValue(FDCHelper.toBigDecimal(entryInfo.getAmount()));
					row.getCell("paidAmount").setValue(FDCHelper.toBigDecimal(entryInfo.getPayment()));
					row.getCell("paidDate").setValue(FDCDateHelper.formatDate2(new Date()));
					row.getCell("id").setValue(entryInfo.getId().toString());
				}
			}
		}
		if (entrys.size() > 0) {
			this.tblMain.setRowCount(entrys.size());
		}
	}

	/**
	 * output class constructor
	 */
	public MarketChargePaymentListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionRemove_actionPerformed(e);
	}

	private void checkSelected() {
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * output actionConfirm_actionPerformed
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		MarketManageEntryCollection payCol = new MarketManageEntryCollection();
		CoreBaseCollection corChargePayColl = new CoreBaseCollection();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row != null) {
				MarketManageEntryInfo info = null;
				MarketChargePaymentEntryInfo entryInfo = null;
				if (row.getUserObject() instanceof MarketManageEntryInfo) {
					info = (MarketManageEntryInfo) row.getUserObject();
				}
				if (info != null) {
					BigDecimal paidAmount = FDCHelper.toBigDecimal(row.getCell("paidAmount").getValue());
					BigDecimal paymentAmount = FDCHelper.toBigDecimal(row.getCell("paymentAmount").getValue());
					entryInfo = new MarketChargePaymentEntryInfo();
					entryInfo.setMarketManageEntry(info);
					entryInfo.setPaidAmount(info.getPayment());
					entryInfo.setPayment(paymentAmount);
					entryInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
					info.setPayment(paymentAmount.add(paidAmount));
					payCol.add(info);
					corChargePayColl.add(entryInfo);
				}
			}
		}
		this.getUIContext().put("payCol", payCol);
		this.getUIContext().put("corChargePayColl", corChargePayColl);
		this.destroyWindow();
	}

	/**
	 * output actionExit_actionPerformed
	 */
	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		MarketManageEntryCollection payCol = new MarketManageEntryCollection();
		CoreBaseCollection corChargePayColl = new CoreBaseCollection();
		payCol.clear();
		corChargePayColl.clear();
		this.getUIContext().put("payCol", payCol);
		this.getUIContext().put("corChargePayColl", corChargePayColl);
		this.destroyWindow();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	protected IObjectValue createNewData() {
		return null;
	}

}