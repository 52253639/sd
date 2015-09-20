/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class PayRequestFullInfoUI extends AbstractPayRequestFullInfoUI {
	/**
	 * output class constructor
	 */
	public PayRequestFullInfoUI() throws Exception {
		super();
	}
	private Map attachMap = new HashMap();
	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getColumn("amount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("createTime").getStyleAttributes()
				.setNumberFormat(formatString);
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("creator.name");
		view.getSelector().add("auditor.name");
		view.getSelector().add("supplier.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", contractId));
		PayRequestBillCollection payRequestBillCollection = PayRequestBillFactory
				.getRemoteInstance().getPayRequestBillCollection(view);
		
		Set boIds = new HashSet();
		
		Map rowMap=new HashMap();
		for (int i = 0; i < payRequestBillCollection.size(); i++) {
			PayRequestBillInfo info = payRequestBillCollection.get(i);
			IRow row = this.tblMain.addRow();
			row.setUserObject(info.getId().toString());
			
			boIds.add(info.getId().toString());
			
			rowMap.put(info.getId().toString(), row);
			
			row.getCell("state").setValue(info.getState());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("amount").setValue(info.getAmount());
			row.getCell("payDate").setValue(info.getPayDate());
			if (info.getSupplier() != null) {
				row.getCell("receiveUnit").setValue(
						info.getSupplier().getName());
			}
			row.getCell("creator").setValue(info.getCreator().getName());
			row.getCell("createTime").setValue(info.getCreateTime());
			row.getCell("moneyDes").setValue(info.getMoneyDesc());
			row.getCell("receiveBank").setValue(info.getRecBank());
			row.getCell("receiveBankNum").setValue(info.getRecAccount());
			if (info.getAuditor() != null) {
				row.getCell("auditor").setValue(info.getAuditor().getName());
			}
			row.getCell("auditTime").setValue(info.getAuditTime());
			row.getCell("description").setValue(info.getDescription());
			
//			row.getCell("attachment").setValue(
//					new Integer(info.getAttachment()));
		}
		
		attachMap = PayReqTableHelper.handleAttachment(boIds);
		if (attachMap == null) {
			attachMap = new HashMap();
		}
		for (int i = 0; i < payRequestBillCollection.size(); i++) {
			PayRequestBillInfo info = payRequestBillCollection.get(i);
			IRow row=(IRow) rowMap.get(info.getId().toString());
			String idkey = info.getId().toString();
			if (attachMap.containsKey(idkey)) {
				row.getCell("attachment").setValue(Boolean.TRUE);
			} else {
				row.getCell("attachment").setValue(Boolean.FALSE);
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getClickCount() == 2) {
			IRow row = tblMain.getRow(e.getRowIndex());
			if(row==null) return ;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, row.getUserObject());
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(PayRequestBillEditUI.class.getName(), uiContext,
							null, "FINDVIEW");
			uiWindow.show();
		}
	}
}