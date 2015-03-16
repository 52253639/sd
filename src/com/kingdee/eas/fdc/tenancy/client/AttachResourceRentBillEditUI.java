/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AttachResTypeInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceEnum;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysInfo;
import com.kingdee.eas.fdc.tenancy.AttachSourceTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AttachResourceRentBillEditUI extends AbstractAttachResourceRentBillEditUI {
	private static final Logger logger = CoreUIObject.getLogger(AttachResourceRentBillEditUI.class);

	public AttachResourceRentBillEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
		AttachResourceRentBillInfo attachInfo = this.editData;
		attachInfo.setName(this.txtName.getText());
		attachInfo.setNumber(this.txtNumber.getText());
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext().get("sellProject");
		attachInfo.setSellProject(sellProjectInfo);
		AttachResourceRentEntrysCollection attentrysColl = new AttachResourceRentEntrysCollection();
		for (int i = 0; i < tblAttachResource.getRowCount(); i++) {
			IRow row = tblAttachResource.getRow(i);
			AttachResourceRentEntrysInfo attentrysInfo = new AttachResourceRentEntrysInfo();
			AttachResourceInfo attInfo = (AttachResourceInfo) row.getUserObject();
			attentrysInfo.setAttach(attInfo);
			attentrysInfo.setRentType((RentTypeEnum) row.getCell("rentType").getValue());
			attentrysInfo.setStandardRent((BigDecimal) row.getCell("standardRent").getValue());
			attentrysColl.add(attentrysInfo);
		}
		attachInfo.getEntrys().clear();
		attachInfo.getEntrys().addCollection(attentrysColl);
	}

	public void onLoad() throws Exception {

		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.txtSellProjectNumber.setEnabled(false);
		this.txtProjectName.setEnabled(false);
		this.pkCreateDate.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		super.onLoad();
		this.actionAuditResult.setVisible(false);
		hideButton();
		initAttachInfo();
		if (STATUS_VIEW.equals(this.getOprtState())) {
			this.btnImportAttach.setEnabled(false);
			this.btnDelAttach.setEnabled(false);
			this.btnImportPrice.setEnabled(false);
			this.btnAddNew.setEnabled(false);
		}
	}

	public void loadFields() {
		super.loadFields();
		AttachResourceRentBillInfo attachInfo = this.editData;
		this.txtSellProjectNumber.setText(attachInfo.getSellProject().getNumber());
		this.txtProjectName.setText(attachInfo.getSellProject().getName());
		this.txtName.setText(attachInfo.getName());
		this.txtNumber.setText(attachInfo.getNumber());
		this.prmtCreator.setValue(attachInfo.getCreator());
		this.tblAttachResource.removeRows();
		AttachResourceRentEntrysCollection attEntrysColl = attachInfo.getEntrys();
		IRow row = null;
		this.tblAttachResource.checkParsed();
		for (int i = 0; i < attEntrysColl.size(); i++) {
			AttachResourceRentEntrysInfo attEntrysInfo = (AttachResourceRentEntrysInfo) attEntrysColl.get(i);
			AttachResourceInfo attInfo = attEntrysInfo.getAttach();
			row = this.tblAttachResource.addRow();
			showTblAttachResource(row, attInfo);
			row.getCell("standardRent").setValue(attEntrysInfo.getStandardRent());

		}
	}

	/**
	 * 隐藏按钮
	 * 
	 */
	private void hideButton() {
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionCopyFrom.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuSubmitOption.setVisible(false);
	}

	protected IObjectValue createNewData() {
		AttachResourceRentBillInfo attachInfo = new AttachResourceRentBillInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sellProject != null) {
			attachInfo.setSellProject(sellProject);
		}
		attachInfo.setIsExecuted(false);
		attachInfo.setIsInvalid(false);
		try {
			attachInfo.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		attachInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		attachInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		attachInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		attachInfo.setBookedDate(new Date());
		return attachInfo;
	}

	private void initAttachInfo() {
		this.tblAttachResource.checkParsed();
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblAttachResource.getColumn("subAreaName").setEditor(txtEditor);
		this.tblAttachResource.getColumn("buildingName").setEditor(txtEditor);
		this.tblAttachResource.getColumn("attachName").setEditor(txtEditor);

		// 设置租金类型为枚举类型
		KDComboBox RentTypeComboBox = new KDComboBox();
		List list2 = RentTypeEnum.getEnumList();
		for (int i = 0; i < list2.size(); i++) {
			RentTypeComboBox.addItem(list2.get(i));
		}
		KDTDefaultCellEditor RentTypeComboBoxEditer = new KDTDefaultCellEditor(RentTypeComboBox);
		this.tblAttachResource.getColumn("rentType").setEditor(RentTypeComboBoxEditer);

		KDComboBox attachTypeComboBox = new KDComboBox();
		List list = AttachSourceTypeEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			attachTypeComboBox.addItem(list.get(i));
		}
		KDTDefaultCellEditor attachTypeComboBoxEditer = new KDTDefaultCellEditor(attachTypeComboBox);
		this.tblAttachResource.getColumn("attachType").setEditor(attachTypeComboBoxEditer);

		KDComboBox attachStateComboBox = new KDComboBox();
		List list3 = AttachResourceEnum.getEnumList();
		for (int i = 0; i < list3.size(); i++) {
			attachStateComboBox.addItem(list3.get(i));
		}
		KDTDefaultCellEditor attachStateComboBoxEditer = new KDTDefaultCellEditor(attachStateComboBox);
		this.tblAttachResource.getColumn("attachState").setEditor(attachStateComboBoxEditer);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblAttachResource.getColumn("standardRent").setEditor(numberEditor);

	}

	protected void btnDelAttach_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelAttach_actionPerformed(e);
		int activeRowIndex = this.tblAttachResource.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblAttachResource.getRowCount();
		}
		this.tblAttachResource.removeRow(activeRowIndex);
	}

	protected void btnImportPrice_actionPerformed(ActionEvent e) throws Exception {
		super.btnImportPrice_actionPerformed(e);
		String fileName = SHEHelper.showExcelSelectDlg(this);
		int count = SHEHelper.importExcelToTable(fileName, this.tblAttachResource, 1, 3);
		for (int i = 0; i < this.tblAttachResource.getRowCount(); i++) {
			IRow row = this.tblAttachResource.getRow(i);
			Object rentTypeObject = (Object) row.getCell("rentType").getValue();
			if (rentTypeObject instanceof RentTypeEnum) {
				RentTypeEnum rentType = (RentTypeEnum) rentTypeObject;
				row.getCell("rentType").setValue(rentType);
			} else {
				List list = RentTypeEnum.getEnumList();
				if (TenancyClientHelper.isInclude(rentTypeObject.toString(), list)) {
					for (int j = 0; j < list.size(); j++) {
						if (rentTypeObject.equals(list.get(j).toString())) {
							row.getCell("rentType").setValue(list.get(j));
						}
					}
				} else {
					MsgBox.showInfo("导入了不正确的租金类型,设为默认值!");
					row.getCell("rentType").setValue(RentTypeEnum.RentByMonth);
				}
			}

			Object standardRentObject = (Object) row.getCell("standardRent").getValue();
			if (standardRentObject instanceof BigDecimal) {
				row.getCell("standardRent").setValue(standardRentObject);
			} else if (standardRentObject instanceof Integer) {
				row.getCell("standardRent").setValue((BigDecimal) standardRentObject);
			} else if (standardRentObject instanceof String) {
				MsgBox.showInfo("错误的标准租金类型");
				row.getCell("standardRent").setValue(FDCHelper.ZERO);
			}
		}
		MsgBox.showInfo("已经成功导入" + count + "条数据!");
	}

	protected void btnImportAttach_actionPerformed(ActionEvent e) throws Exception {

		super.btnImportAttach_actionPerformed(e);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.AttachResourceQuery")));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", ((SellProjectInfo) this.getUIContext().get("sellProject")).getId().toString()));
		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		// 设置多选
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		if (object != null && object.length > 0) {
			IRow row = null;
			List list = new ArrayList();
			for (int i = 0; i < tblAttachResource.getRowCount(); i++) {
				IRow row2 = tblAttachResource.getRow(i);
				AttachResourceInfo attachInfo = (AttachResourceInfo) row2.getCell("attachName").getUserObject();
				if (attachInfo != null) {
					list.add(attachInfo.getId().toString());
				}
			}
			for (int j = 0; j < object.length; j++) {
				AttachResourceInfo info = (AttachResourceInfo) object[j];
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add(new SelectorItemInfo("*"));
				sel.add(new SelectorItemInfo("subarea.*"));
				sel.add(new SelectorItemInfo("building.*"));
				sel.add(new SelectorItemInfo("sellProject.*"));
				// attachResType
				sel.add(new SelectorItemInfo("attachResType.*"));
				AttachResourceInfo attachInfo = AttachResourceFactory.getRemoteInstance().getAttachResourceInfo(new ObjectUuidPK(info.getId()), sel);
				if (TenancyClientHelper.isInclude(attachInfo.getId().toString(), list)) {
					MsgBox.showInfo("该配套资源已经存在不要重复添加！");
					return;
				} else {
					this.tblAttachResource.checkParsed();
					row = this.tblAttachResource.addRow(j);
					// 显示项目信息
					showTblAttachResource(row, attachInfo);
				}
			}
		}
	}

	private void showTblAttachResource(IRow row, AttachResourceInfo attachInfo) {	
		//AttachSourceTypeEnum typeInfo = attachInfo.getAttachType();
		
		row.setUserObject(attachInfo);
		if(attachInfo!=null){
			if (attachInfo.getSubarea() == null) {
				row.getCell("subAreaName").setValue(null);
				row.getCell("subAreaName").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("subAreaName").setValue(attachInfo.getSubarea().getName());
				row.getCell("subAreaName").getStyleAttributes().setLocked(true);
			}
			if (attachInfo.getBuilding() == null) {
				row.getCell("buildingName").setValue(null);
				row.getCell("buildingName").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("buildingName").setValue(attachInfo.getBuilding().getName());
				row.getCell("buildingName").getStyleAttributes().setLocked(true);
			}
			if(attachInfo.getAttachResType()!=null){
				row.getCell("attachType").setValue(attachInfo.getAttachResType().getName());
				//row.getCell("attachType").setValue(attachInfo.getAttachType());
				row.getCell("attachType").getStyleAttributes().setLocked(true);
			}
			
		    //row.getCell("attachResTypeName").setValue(attachInfo.getAttachResType().getName());
			// row.getCell("attachResTypeName").getStyleAttributes().setLocked(true);
			
			row.getCell("attachName").setValue(attachInfo.getName());
			row.getCell("attachName").getStyleAttributes().setLocked(true);
			row.getCell("attachName").setUserObject(attachInfo);
			row.getCell("attachState").setValue(attachInfo.getAttachState());
			row.getCell("attachState").getStyleAttributes().setLocked(true);
			row.getCell("rentType").setValue(RentTypeEnum.RentByDay);
		}
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AttachResourceRentBillFactory.getRemoteInstance();
	}
	 
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.attach.building.*"));
		sic.add(new SelectorItemInfo("entrys.attach.subarea.*"));
		sic.add(new SelectorItemInfo("entrys.attach.attachType.*"));
		sic.add(new SelectorItemInfo("creator.*"));
		return sic;
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		AttachResourceRentBillInfo attachInfo = (AttachResourceRentBillInfo) super.getValue(pk);
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(new FilterItemInfo("head", pk.toString()));

		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("*"));
		sel.add(new SelectorItemInfo("attach.*"));
		sel.add(new SelectorItemInfo("attach.attachResType.*"));
		sel.add(new SelectorItemInfo("attach.subarea.*"));
		sel.add(new SelectorItemInfo("attach.building.*"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(entryFilter);

		AttachResourceRentEntrysCollection attEntrysColl = AttachResourceRentEntrysFactory.getRemoteInstance().getAttachResourceRentEntrysCollection(view);
		attachInfo.getEntrys().addCollection(attEntrysColl);

		return attachInfo;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.btnImportAttach.setEnabled(true);
		this.btnDelAttach.setEnabled(true);
		this.btnImportPrice.setEnabled(true);
		this.btnAddNew.setEnabled(true);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

}