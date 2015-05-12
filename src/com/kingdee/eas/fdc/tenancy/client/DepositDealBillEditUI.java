/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.DepositDealBillInfo;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.IDealAmountInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
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
public class DepositDealBillEditUI extends AbstractDepositDealBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DepositDealBillEditUI.class);
    public DepositDealBillEditUI() throws Exception
    {
        super();
    }
    public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.kdtEntry.setEnabled(false);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntry.getColumn("appAmount").setEditor(numberEditor);
		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("actRevAmount").setEditor(numberEditor);
		this.kdtEntry.getColumn("actRevAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("actRevAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		try {
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("otherPayList.*");
			sic.add("otherPayList.moneyDefine.*");
			sic.add("tenancyRoomList.*");
			sic.add("tenancyRoomList.moneyDefine*");
			TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(this.editData.getTenancyBill().getId()), sic);
			TenBillOtherPayCollection otherPayList = new TenBillOtherPayCollection();
			for (int i = 0; i < tenBill.getOtherPayList().size(); i++) {
				TenBillOtherPayInfo entry=tenBill.getOtherPayList().get(i);
				otherPayList.add(entry);
			}
			if(tenBill.getTenancyRoomList().size()>0){
				for (int i = 0; i < tenBill.getTenancyRoomList().get(0).getPayList().size(); i++) {
					TenancyRoomPayListEntryInfo tenOtherInfo = tenBill.getTenancyRoomList().get(0).getRoomPayList().get(i);
					TenBillOtherPayInfo entry=new TenBillOtherPayInfo();
					entry.setMoneyDefine(tenOtherInfo.getMoneyDefine());
					entry.setAppAmount(tenOtherInfo.getAppAmount());
					entry.setActRevAmount(tenOtherInfo.getActRevAmount());
					otherPayList.add(entry);
				}
			}
			CRMHelper.sortCollection(otherPayList, "moneyDefine.number", true);
			
			this.kdtEntry.removeRows();
			Map addRow=new HashMap();
			for (int i = 0; i < otherPayList.size(); i++) {
				TenBillOtherPayInfo entry=otherPayList.get(i);
				if(entry.getMoneyDefine()!=null){
					if(addRow.containsKey(entry.getMoneyDefine().getNumber())){
						IRow row=(IRow) addRow.get(entry.getMoneyDefine().getNumber());
						row.getCell("appAmount").setValue(FDCHelper.add(row.getCell("appAmount").getValue(), entry.getAppAmount()));
						row.getCell("actRevAmount").setValue(FDCHelper.add(row.getCell("actRevAmount").getValue(), entry.getActRevAmount()));
					}else{
						IRow row=this.kdtEntry.addRow();
						row.getCell("moneyDefine").setValue(entry.getMoneyDefine().getName());
						row.getCell("appAmount").setValue(entry.getAppAmount());
						row.getCell("actRevAmount").setValue(entry.getActRevAmount());
						addRow.put(entry.getMoneyDefine().getNumber(), row);
					}
				}
			}
			CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"appAmount","actRevAmount"});
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
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
		return DepositDealBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		DepositDealBillInfo info=new DepositDealBillInfo();
		TenancyBillInfo ten = (TenancyBillInfo) this.getUIContext().get("tenancy");
		info.setTenancyBill(ten);
		info.setOrgUnit(ten.getOrgUnit());
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		sels.add("orgUnit.*");
		return sels;
	}
	public void onLoad() throws Exception {
		this.menuTable1.setVisible(false);
		this.actionAddNew.setVisible(false);
		super.onLoad();
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
		}
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
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
	protected void verifyInputForSubmint() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbType);
	}

}