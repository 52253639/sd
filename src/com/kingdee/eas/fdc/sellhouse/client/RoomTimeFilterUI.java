/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.common.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomTimeFilterUI extends AbstractRoomTimeFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomTimeFilterUI.class);

	public static final String ROOM_SELL_TYPE_PREPURCHASE = "roomSellTypePrepurchase";

	public static final String ROOM_SELL_TYPE_PURCHASE = "roomSellTypePurchase";

	public static final String ROOM_SELL_TYPE_SIGN = "roomSellTypeSign";

	/**
	 * output class constructor
	 */
	public RoomTimeFilterUI() throws Exception {
		super();
	}

	public void clear() {
		this.kDTable1.removeRows();
		this.checkPrePurchase.setSelected(false);
		this.checkPurchase.setSelected(false);
		this.checkSign.setSelected(false);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.kDTable1.checkParsed();

		KDComboBox comboBox = new KDComboBox();
		comboBox.addItems(BizFlowEnum.getEnumList().toArray());
		KDTDefaultCellEditor cmbItem = new KDTDefaultCellEditor(comboBox);

		IColumn startColumn = this.kDTable1.getColumn("start");
		startColumn.setEditor(cmbItem);

		IColumn endColumn = this.kDTable1.getColumn("end");
		endColumn.setEditor(cmbItem);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		this.kDTable1.addRow();
	}

	/**
	 * output actionDeleteLine_actionPerformed
	 */
	public void actionDeleteLine_actionPerformed(ActionEvent e)
			throws Exception {
		int[] rows = KDTableUtil.getSelectedRows(this.kDTable1);
		for (int i = 0; i < rows.length; i++) {
			this.kDTable1.removeRow(rows[i] - i);
		}
	}

	public CustomerParams getCustomerParams() {
		CustomerParams params = new CustomerParams();
		String param = "";
		for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
			IRow row = this.kDTable1.getRow(i);
			if (i > 0) {
				param += "#";
			}
			param += row.getCell("start").getValue();
			param += "~";
			param += row.getCell("end").getValue();
			param += "~";
			param += row.getCell("columnName").getValue();
		}
		params.addCustomerParam("params", param);
		params.addCustomerParam(ROOM_SELL_TYPE_PREPURCHASE,
				this.checkPrePurchase.isSelected() + "");
		params.addCustomerParam(ROOM_SELL_TYPE_PURCHASE, this.checkPurchase
				.isSelected()
				+ "");
		params.addCustomerParam(ROOM_SELL_TYPE_SIGN, this.checkSign
				.isSelected()
				+ "");
		return params;
	}

	public void setCustomerParams(CustomerParams cp) {
		String params = cp.getCustomerParam("params");
		String[] rows = params.split("#");
		this.kDTable1.removeRows();
		for (int i = 0; i < rows.length; i++) {
			String[] cells = rows[i].split("~");
			if (cells.length != 3) {
				continue;
			}
			IRow row = this.kDTable1.addRow();
			for (int m = 0; m < cells.length; m++) {
				ICell cell = row.getCell(m);
				cell.setValue(cells[m]);
			}
		}

		this.checkPrePurchase.setSelected(cp
				.getBoolean(ROOM_SELL_TYPE_PREPURCHASE));
		this.checkPurchase.setSelected(cp.getBoolean(ROOM_SELL_TYPE_PURCHASE));
		this.checkSign.setSelected(cp.getBoolean(ROOM_SELL_TYPE_SIGN));
	}

	public FilterInfo getFilterInfo() {
		FilterInfo filterInfo = super.getFilterInfo();
		// filterInfo.put("kuangbiao","kuangbiao");
		return filterInfo;
	}

	public boolean verify() {
		for (int i = 0; i < this.kDTable1.getRowCount(); i++) {
			IRow row = this.kDTable1.getRow(i);
			if (row.getCell("start").getValue() == null) {
				MsgBox.showWarning("第" + (i + 1) + "行的开始时间点没有录入！");
				return false;
			}
			if (row.getCell("end").getValue() == null) {
				MsgBox.showWarning("第" + (i + 1) + "行的结束时间点没有录入！");
				return false;
			}
			if (row.getCell("columnName").getValue() == null
					|| row.getCell("columnName").getValue().toString().trim()
							.equals("")) {
				MsgBox.showWarning("第" + (i + 1) + "行的列名没有录入！");
				return false;
			}

		}
		
		if(this.kDTable1.getRowCount() == 0){
			MsgBox.showWarning("表格中至少有一行记录！");
			return false;
		}

		return super.verify();
	}

	protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
		if (this.kDTable1.getColumn(e.getColIndex()).getKey().equals("start")
				|| this.kDTable1.getColumn(e.getColIndex()).getKey().equals(
						"end")) {
			int index = e.getRowIndex();
			String s = (this.kDTable1.getRow(index).getCell("start").getValue() == null ? ""
					: this.kDTable1.getRow(index).getCell("start").getValue())
					+ " - "
					+ (this.kDTable1.getRow(index).getCell("end").getValue() == null ? ""
							: this.kDTable1.getRow(index).getCell("end")
									.getValue());
			this.kDTable1.getRow(index).getCell("columnName").setValue(s);
		}

	}
	
	public String getRoomSellType(CustomerParams para) {
		String sellType = null;
		if (para.getBoolean(ROOM_SELL_TYPE_PREPURCHASE)) {
			if (sellType != null) {
				sellType += " or ";
			} else {
				sellType = "";
			}
			sellType += "sellState = 'PrePurchase'";
		}
		if (para.getBoolean(ROOM_SELL_TYPE_PURCHASE)) {
			if (sellType != null) {
				sellType += " or ";
			} else {
				sellType = "";
			}
			sellType += "sellState = 'Purchase'";
		}
		if (para.getBoolean(ROOM_SELL_TYPE_SIGN)) {
			if (sellType != null) {
				sellType += " or ";
			} else {
				sellType = "";
			}
			sellType += "sellState = 'Sign'";
		}
		return sellType;
	}
}