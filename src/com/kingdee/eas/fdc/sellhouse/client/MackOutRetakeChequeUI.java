/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MackOutRetakeChequeUI extends AbstractMackOutRetakeChequeUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MackOutRetakeChequeUI.class);

	/**
	 * output class constructor
	 */
	public MackOutRetakeChequeUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (getUIContext().get("sourceID") != null) {
			String sourceID = (String) getUIContext().get("sourceID");
			String type = (String) getUIContext().get("sourceType");
			tblMain.checkParsed();
			setDefaultFeilds(sourceID, type);
		}
		actionConfirm.setEnabled(true);
		actionCancel.setEnabled(true);
		// 初始化文本编辑器
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(255);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(txtField);
		tblMain.getColumn("description").setEditor(txtEditor);
	}

	/**
	 * 确认
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		verifyDate();
		List param = new ArrayList();
		Map data;
		IRow row;
		String sourceID;
		String id;
		String type;
		String number;
		String description;
		int receipt = 0;
		int invoice = 0;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			row = tblMain.getRow(i);
			boolean isSelect = ((Boolean) row.getCell("select").getValue())
					.booleanValue();
			if (isSelect) {
				data = new HashMap();
				sourceID = (String) row.getCell("id").getUserObject();
				id = (String) row.getCell("id").getValue();
				type = (String) row.getCell("type").getValue();
				number = (String) row.getCell("number").getValue();
				description = (String) row.getCell("description").getValue();

				data.put("sourceID", sourceID);
				data.put("id", id);
				data.put("type", type);
				data.put("number", number);
				data.put("description", description);
				param.add(data);

				if ("收据".equals(type)) {
					receipt++;
				} else if ("发票".equals(type)) {
					invoice++;
				}
			}
		}
		if (param != null && param.size() > 0) {
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
					.getRemoteInstance();
			facade.retakeChequeForRoom(param);
		}
		if (receipt == 0 && invoice == 0) {
			if (MsgBox.showConfirm2(this, "没有选择任何记录，是否确定退出?") == 0) {
				getUIWindow().close();
			}
		} else {
			MsgBox.showInfo(this, "退房回收票据成功，共回收" + receipt + "张收据，" + invoice
					+ "张发票!");
		}
		getUIWindow().close();
	}

	/**
	 * 取消
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		getUIWindow().close();
	}

	/**
	 * 校验
	 */
	private void verifyDate() {

	}

	/**
	 * 从退房或换房ID读取页面默认值
	 * 
	 * @param sourceID
	 * @param isPrmtSet
	 * @throws Exception
	 */
	private void setDefaultFeilds(String sourceID, String sourceType)
			throws Exception {
		IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
				.getRemoteInstance();
		List lst = facade.getChequeForRoom(sourceID, sourceType);
		if (lst != null && lst.size() > 0) {
			tblMain.removeRows();

			Map data = null;
			String id = null;
			String type = null;
			String number = null;
			BigDecimal amount = null;
			IRow row;
			for (int i = 0; i < lst.size(); i++) {
				if (lst.get(i) != null) {
					data = (Map) lst.get(i);
					id = (String) data.get("id");
					type = (String) data.get("type");
					number = (String) data.get("number");
					amount = (BigDecimal) data.get("amount");

					row = tblMain.addRow();
					row.getCell("id").setValue(id);
					row.getCell("id").setUserObject(sourceID);
					row.getCell("select").setValue(Boolean.FALSE);
					row.getCell("type").setValue(type);
					row.getCell("number").setValue(number);
					row.getCell("amount").setValue(amount);
				}
			}
		}
	}
}