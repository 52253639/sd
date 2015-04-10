/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.SelectRevListUI;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.tenancy.InvoiceBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBillInfo;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class InvoiceBillEditUI extends AbstractInvoiceBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvoiceBillEditUI.class);
    public InvoiceBillEditUI() throws Exception
    {
        super();
    }
    public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			InvoiceBillEntryInfo entry=(InvoiceBillEntryInfo) row.getUserObject();
			try {
				IRevListInfo revListInfo = TENRevHelper.getRevListInfo(entry.getRevListType(),entry.getRevListId());
				
				CRMClientHelper.setColValue(row, "moneyDefine", revListInfo.getMoneyDefine());
				CRMClientHelper.setColValue(row, "appDate", revListInfo.getAppDate());
				CRMClientHelper.setColValue(row, "appAmount", revListInfo.getAppAmount());
				CRMClientHelper.setColValue(row, "actRevAmount", revListInfo.getActRevAmount());
				CRMClientHelper.setColValue(row, "invoiceAmount", revListInfo.getInvoiceAmount());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		setOprtState(this.oprtState);
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	public void storeFields()
    {
        super.storeFields();
    }
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InvoiceBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		InvoiceBillInfo info=new InvoiceBillInfo();
		TenancyBillInfo ten = (TenancyBillInfo) this.getUIContext().get("tenancy");
		info.setTenancyBill(ten);
		info.setOrgUnit(ten.getOrgUnit());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		sels.add("orgUnit.*");
		sels.add("fiVouchered");
		
		sels.add("entry.revListType");
		return sels;
	}
	public void onLoad() throws Exception {
		this.menuTable1.setVisible(false);
		this.actionAddNew.setVisible(false);
		super.onLoad();
		this.actionRemoveLine.setVisible(true);
		
		this.btnSubmitAudit.setIcon(EASResource.getIcon("imgTbtn_suitbest"));
		 
		KDWorkButton btnSelect = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		
		this.actionSelect.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnSelect = (KDWorkButton)contEntry.add(this.actionSelect);
		btnSelect.setText("新增行");
		btnSelect.setSize(new Dimension(140, 19));

		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntry.getColumn("amount").setEditor(numberEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("amount").setRequired(true);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.kdtEntry.getColumn("description").setEditor(txtEditor);
		
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			
			this.actionSelect.setEnabled(false);
			
			this.actionSubmitAudit.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
			
			this.actionSelect.setEnabled(true);
			
			this.actionSubmitAudit.setEnabled(true);
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		setAuditButtonStatus(STATUS_VIEW);
		this.actionSave.setEnabled(false);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		setAuditButtonStatus(this.getOprtState());
	}
	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		
		Map map = new HashMap();
		List paylist = new ArrayList();
		List otherPayList = new ArrayList();
		TenancyBillInfo tenancy =this.editData.getTenancyBill();
		inPutListByTenancyBillandList(tenancy, paylist, otherPayList);
		
		map.put("1tenPayList", paylist);
		map.put("2tenOtherPayList", otherPayList);
		
		uiContext.put(SelectRevListUI.KEY_APP_REV_LIST, map);
		uiContext.put(SelectRevListUI.KEY_MONEYSYSTYPE, MoneySysTypeEnum.TenancySys);
		uiContext.put(SelectRevListUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
		uiContext.put("isInvoice", "true");
		uiContext.put("isGathering", "true");
		uiContext.put("ISBYCUSTOMER", "false");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectRevListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		Map resContext = uiWindow.getUIObject().getUIContext();
		
		if (resContext.get(SelectRevListUI.KEY_RES_OPT) == null ||
				!((Boolean)resContext.get(SelectRevListUI.KEY_RES_OPT)).booleanValue()){
			return;
		}
		this.kdtEntry.removeRows(false);
		List list = (List) resContext.get(SelectRevListUI.KEY_RES_APP_REV_LIST);
		
		BigDecimal total=FDCHelper.ZERO;
		for(int i=0; i<list.size(); i++){
			IRevListInfo revListInfo = (IRevListInfo) list.get(i);
			InvoiceBillEntryInfo revEntry = new InvoiceBillEntryInfo();
			revEntry.setRevListId(revListInfo.getId() == null ? null : revListInfo.getId().toString());
			revEntry.setRevListType(revListInfo.getRevListTypeEnum());
			IRow row = this.kdtEntry.addRow();
			row.setUserObject(revEntry);

			CRMClientHelper.setColValue(row, "moneyDefine", revListInfo.getMoneyDefine());
			CRMClientHelper.setColValue(row, "appDate", revListInfo.getAppDate());
			CRMClientHelper.setColValue(row, "appAmount", revListInfo.getAppAmount());
			CRMClientHelper.setColValue(row, "actRevAmount", revListInfo.getActRevAmount());
			CRMClientHelper.setColValue(row, "invoiceAmount", revListInfo.getInvoiceAmount());
			CRMClientHelper.setColValue(row, "amount", FDCHelper.subtract(revListInfo.getAppAmount(), revListInfo.getInvoiceAmount()));
			
			total=FDCHelper.add(total, FDCHelper.subtract(revListInfo.getAppAmount(), revListInfo.getInvoiceAmount()));
		}
		this.txtAmount.setValue(total);
	}
	protected void inPutListByTenancyBillandList(TenancyBillInfo tenancy,List paylist,List otherPayList) throws EASBizException, BOSException{
		tenancy = TenancyHelper.getTenancyBillInfo(tenancy.getId().toString());
		TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			TenancyRoomPayListEntryCollection tenRoomPays = tenRoom
					.getRoomPayList();
			for (int j = 0; j < tenRoomPays.size(); j++) {
				TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPays.get(j);
				tenRoomPay.setTenRoom(tenRoom);
				// by tim_gao 加入合同
				tenRoomPay.setTenBill(tenancy);
				paylist.add(tenRoomPay);
			}
		}
		TenBillOtherPayCollection tenOtherColl = tenancy.getOtherPayList();
		CRMHelper.sortCollection(tenOtherColl, "leaseSeq", true);
		for (int i = 0; i < tenOtherColl.size(); i++) {
			TenBillOtherPayInfo tenOtherInfo = tenOtherColl.get(i);
			// by tim_gao 加入合同
			tenOtherInfo.setHead(tenancy);
			otherPayList.add(tenOtherInfo);
		}
	}
	public void actionSubmitAudit_actionPerformed(ActionEvent e) throws Exception {
		actionSubmit_actionPerformed(e);
		actionAudit_actionPerformed(e);
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if("amount".equals(this.kdtEntry.getColumnKey(e.getColIndex()))){
			BigDecimal total=FDCHelper.ZERO;
			for(int i=0; i<this.kdtEntry.getRowCount(); i++){
				IRow row = this.kdtEntry.getRow(i);
				total=FDCHelper.add(total, row.getCell("amount").getValue());
			}
			this.txtAmount.setValue(total);
		}
	}
	public void actionRemoveLine_actionPerformed(ActionEvent arg0)throws Exception {
		super.actionRemoveLine_actionPerformed(arg0);
		BigDecimal total=FDCHelper.ZERO;
		for(int i=0; i<this.kdtEntry.getRowCount(); i++){
			IRow row = this.kdtEntry.getRow(i);
			total=FDCHelper.add(total, row.getCell("amount").getValue());
		}
		this.txtAmount.setValue(total);
	}
	protected void verifyInputForSubmint() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbType);
		
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"分录不能为空！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row = this.kdtEntry.getRow(i);
			if(row.getCell("amount").getValue()==null||((BigDecimal)row.getCell("amount").getValue()).compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"票据金额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
			if(FDCHelper.add(row.getCell("amount").getValue(), row.getCell("invoiceAmount").getValue()).compareTo((BigDecimal) row.getCell("appAmount").getValue())>0){
				FDCMsgBox.showWarning(this,"已开票据金额+票据金额不能大于应收金额！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
		}
	}

}