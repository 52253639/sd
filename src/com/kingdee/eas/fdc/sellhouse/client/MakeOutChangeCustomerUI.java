/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.framework.BizDataFormat;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MakeOutChangeCustomerUI extends AbstractMakeOutChangeCustomerUI {

	MarketDisplaySetting setting = new MarketDisplaySetting();

	private static final Logger logger = CoreUIObject
			.getLogger(MakeOutChangeCustomerUI.class);

	/**
	 * output class constructor
	 */
	public MakeOutChangeCustomerUI() throws Exception {
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
		if (getUIContext().get("purID") != null) {
			String purID = (String) getUIContext().get("purID");
			tblMain.checkParsed();
			setDefaultFeilds(purID);
		}
		actionConfirm.setEnabled(true);
		actionCancel.setEnabled(true);
		// 初始化文本编辑器
		ChequeCellRenderImpl avr = new ChequeCellRenderImpl();
		avr.getText(new BizDataFormat("$number$"));
		this.tblMain.getColumn("newNumber").setRenderer(avr);
//		KDTextField txtField1 = new KDTextField();
//		txtField1.setMaxLength(80);
//		KDTDefaultCellEditor txtEditor1 = new KDTDefaultCellEditor(txtField1);
//		tblMain.getColumn("newNumber").setEditor(txtEditor1);

		KDTextField txtField2 = new KDTextField();
		txtField2.setMaxLength(255);
		KDTDefaultCellEditor txtEditor2 = new KDTDefaultCellEditor(txtField2);
		tblMain.getColumn("description").setEditor(txtEditor2);
	}

	/**
	 * 确定
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		verifyDate();
		List param = new ArrayList();
		Map data;
		IRow row;
		String chgPK;
		String id;
		String type;
		String oldNum;
		Object newNum;
		String description;
		int receipt = 0;
		int invoice = 0;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			row = tblMain.getRow(i);
			newNum = row.getCell("newNumber").getValue();
			if (newNum != null) {
				data = new HashMap();
				chgPK = (String) row.getCell("id").getUserObject();
				id = (String) row.getCell("id").getValue();
				type = (String) row.getCell("type").getValue();
				oldNum = (String) row.getCell("number").getValue();
				description = (String) row.getCell("description").getValue();

				data.put("chgPK", chgPK);
				data.put("id", id);
				data.put("type", type);
				data.put("oldNum", oldNum);
				data.put("newNum", newNum);
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
			facade.updateChequeForChg(param);
		}
		if (receipt == 0 && invoice == 0) {
			if (MsgBox.showConfirm2(this, "没有任何改动，是否确定退出?") == 0) {
				getUIWindow().close();
			}
		} else {
			MsgBox.showInfo(this, "更名换票成功，共更改" + receipt + "张收据，" + invoice
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
	 * 从收款单ID读取页面默认值
	 * 
	 * @param chgID
	 * @param isPrmtSet
	 * @throws Exception
	 */
	private void setDefaultFeilds(String chgID) throws Exception {
		IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
				.getRemoteInstance();
		List lst = facade.getChequebyChgID(chgID);
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
					row.getCell("id").setUserObject(chgID);
					row.getCell("type").setValue(type);
					row.getCell("number").setValue(number);
					row.getCell("amount").setValue(amount);

					// 启用参数时用F7
					if (setting.getMarkInvoice() == 32) {
						row.getCell("newNumber").setEditor(creatF7Editor(type));
					}
				}
			}
		}
	}

	/**
	 * 更加票据类型返回F7编辑器
	 * 
	 * @param type
	 * @return
	 */
	private ICellEditor creatF7Editor(String type) {
		KDBizPromptBox prmtField = new KDBizPromptBox();
		prmtField.setDisplayFormat("$number$");
		prmtField.setEditFormat("$number$");
		prmtField.setCommitFormat("$number$");
		prmtField.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeQuery");
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("keeper.id", SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("status", new Integer(
						ChequeStatusEnum.BOOKED_VALUE)));
		if ("发票".equals(type)) {
			filter.getFilterItems().add(
					new FilterItemInfo("chequeType",
							ChequeTypeEnum.INVOICE_VALUE));
		} else if ("收据".equals(type)) {
			filter.getFilterItems().add(
					new FilterItemInfo("chequeType",
							ChequeTypeEnum.RECEIPT_VALUE));
		}
		viewInfo.setFilter(filter);
		viewInfo.getSorter().add(new SorterItemInfo("number"));
		prmtField.setEntityViewInfo(viewInfo);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("applyRec.*");
		prmtField.setSelectorCollection(sels);
		KDTDefaultCellEditor prmtEditor = new KDTDefaultCellEditor(prmtField);
		return prmtEditor;
	}
}