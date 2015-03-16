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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysCollection;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysFactory;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustEntrysInfo;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustFactory;
import com.kingdee.eas.fdc.tenancy.AttachRentAdjustInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceEnum;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.AttachSourceTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AttachRentAdjustEditUI extends AbstractAttachRentAdjustEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(AttachRentAdjustEditUI.class);
	
	public AttachRentAdjustEditUI() throws Exception
	{
		super();
	}
	
	public void onLoad() throws Exception {
		
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.txtSellProjectNumber.setEnabled(false);
		this.txtSellProjectName.setEnabled(false);
		this.pkCreateDate.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		this.btnImportPrice.setVisible(false);
		super.onLoad();
		hideButton();
		initAttachInfo();
		if(STATUS_VIEW.equals(this.getOprtState()))
    	{
    		this.btnImportAttach.setEnabled(false);
    		this.btnDelAttach.setEnabled(false);
    		this.btnImportPrice.setEnabled(false);
    		this.btnAddNew.setEnabled(false);
    	}
		
	}
	
	public void loadFields() {
		super.loadFields();
		AttachRentAdjustInfo attachInfo = this.editData;
		this.txtSellProjectNumber.setText(attachInfo.getSellProject().getNumber());
		this.txtSellProjectName.setText(attachInfo.getSellProject().getName());
		this.txtName.setText(attachInfo.getName());
		this.txtNumber.setText(attachInfo.getNumber());
		this.prmtCreator.setValue(attachInfo.getCreator());
		this.tblAttachRentAdjust.removeRows();
		AttachRentAdjustEntrysCollection attEntrysColl = attachInfo.getEntrys();
		IRow row = null;
		this.tblAttachRentAdjust.checkParsed();
		for(int i=0;i<attEntrysColl.size();i++)
		{
			AttachRentAdjustEntrysInfo attEntrysInfo = (AttachRentAdjustEntrysInfo)attEntrysColl.get(i);
			AttachResourceInfo attInfo = attEntrysInfo.getAttach();
			row= this.tblAttachRentAdjust.addRow();
			this.showTblAttachRentAdjust(row, attInfo, attEntrysInfo);
			row.getCell("newRentType").setValue(attEntrysInfo.getNewRentType());
			row.getCell("newStrandRent").setValue(attEntrysInfo.getNewStandardRent());
		}	
	}
	
	public void storeFields() {
		super.storeFields();
		AttachRentAdjustInfo attachInfo = this.editData;
		attachInfo.getEntrys().clear();
		attachInfo.setName(this.txtName.getText());
    	attachInfo.setNumber(this.txtNumber.getText());
    	SellProjectInfo sellProjectInfo = (SellProjectInfo)this.getUIContext().get("sellProject");
    	attachInfo.setSellProject(sellProjectInfo);
    	for(int i=0;i<tblAttachRentAdjust.getRowCount();i++)
    	{
    		IRow row = tblAttachRentAdjust.getRow(i);
    		AttachRentAdjustEntrysInfo attInfo = (AttachRentAdjustEntrysInfo)row.getUserObject();
    		attInfo.setNewRentType((RentTypeEnum)row.getCell("newRentType").getValue());
    		attInfo.setNewStandardRent((BigDecimal)row.getCell("newStrandRent").getValue());
    		attachInfo.getEntrys().add(attInfo);
    	}
	}
	
	/**
	 * 隐藏按钮
	 *
	 */
	private void hideButton()
	{
		this.menuBiz.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		//this.actionCopyFrom.setVisible(false);
		this.actionSave.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuSubmitOption.setVisible(false);
		this.actionAuditResult.setVisible(false);
	}
	
	protected IObjectValue createNewData() {
		AttachRentAdjustInfo attachInfo = new AttachRentAdjustInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		if(sellProject != null)
		{
			attachInfo.setSellProject(sellProject);
		}
		attachInfo.setIsExecuted(false);
		try {
			attachInfo.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		attachInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		attachInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		attachInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		attachInfo.setBookedDate(new Date());
		return attachInfo;
	}
	
	protected void btnImportAttach_actionPerformed(ActionEvent e) throws Exception {
		
		super.btnImportAttach_actionPerformed(e);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.AttachResourceQuery")));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",((SellProjectInfo)this.getUIContext()
				.get("sellProject")).getId().toString()));
		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		//设置多选
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[])dlg.getData();
		if(object!=null && object.length>0)
		{
			IRow row = null;
			List list = new ArrayList();
			for(int i=0;i<tblAttachRentAdjust.getRowCount();i++)
			{
				IRow row2 = tblAttachRentAdjust.getRow(i);
				AttachResourceInfo attachInfo = (AttachResourceInfo)row2.getCell("attachName").getUserObject();
				if(attachInfo!=null)
				{
					list.add(attachInfo.getId().toString());
				}
			}
			for(int j=0;j<object.length;j++)
			{
				AttachResourceInfo info = (AttachResourceInfo)object[j];
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add(new SelectorItemInfo("*"));
				sel.add(new SelectorItemInfo("subarea.*"));
				sel.add(new SelectorItemInfo("building.*"));
				sel.add(new SelectorItemInfo("sellProject.*"));
				
				AttachResourceInfo attachInfo = AttachResourceFactory.getRemoteInstance().getAttachResourceInfo(
						new ObjectUuidPK(info.getId()),sel);
				if(TenancyClientHelper.isInclude(attachInfo.getId().toString(),list))
				{
					MsgBox.showInfo("该配套资源已经存在不要重复添加！");
					return;
				}else
				{
					this.tblAttachRentAdjust.checkParsed();
					row = this.tblAttachRentAdjust.addRow(j);
					AttachRentAdjustEntrysInfo attEntrysInfo = new AttachRentAdjustEntrysInfo();
					attEntrysInfo.setAttach(attachInfo);
					attEntrysInfo.setOldRentType(attachInfo.getRentType());
					attEntrysInfo.setOldStandardRent(attachInfo.getStandardRent());
					//显示项目信息
					showTblAttachRentAdjust(row,attachInfo,attEntrysInfo);
				}
			}
		}
	}
	
	private void showTblAttachRentAdjust(IRow row,AttachResourceInfo attachInfo,AttachRentAdjustEntrysInfo attEntrysInfo)
	{
		row.setUserObject(attEntrysInfo);
		if(attachInfo.getSubarea()==null)
		{
			row.getCell("subareaName").setValue(null);
			row.getCell("subareaName").getStyleAttributes().setLocked(true);
		}else
		{
			row.getCell("subareaName").setValue(attachInfo.getSubarea().getName());
			row.getCell("subareaName").getStyleAttributes().setLocked(true);
		}
		if(attachInfo.getBuilding()==null)
		{
			row.getCell("buildingName").setValue(null);
			row.getCell("buildingName").getStyleAttributes().setLocked(true);
		}else
		{
			row.getCell("buildingName").setValue(attachInfo.getBuilding().getName());
			row.getCell("buildingName").getStyleAttributes().setLocked(true);
		}
		row.getCell("attachType").setValue(attachInfo.getAttachType());
		row.getCell("attachType").getStyleAttributes().setLocked(true);
		row.getCell("attachName").setValue(attachInfo.getName());
		row.getCell("attachName").getStyleAttributes().setLocked(true);
		row.getCell("attachName").setUserObject(attachInfo);
		row.getCell("attachState").setValue(attachInfo.getAttachState());
		row.getCell("attachState").getStyleAttributes().setLocked(true);
		if(attachInfo.getRentType()!=null)
		{
			row.getCell("oldRentType").setValue(attachInfo.getRentType());
			row.getCell("newRentType").setValue(attachInfo.getRentType());
		}else
		{
			row.getCell("oldRentType").setValue(null);
			row.getCell("newRentType").setValue(RentTypeEnum.RentByDay);
		}
		row.getCell("oldRentType").getStyleAttributes().setLocked(true);
		if(attachInfo.getStandardRent()!=null)
		{
			row.getCell("oldStrandRent").setValue(attachInfo.getStandardRent());
		}else
		{
			row.getCell("oldStrandRent").setValue(FDCHelper.ZERO);
		}
		row.getCell("oldStrandRent").getStyleAttributes().setLocked(true);
		
	}
	
	private void initAttachInfo()
	{
		this.tblAttachRentAdjust.checkParsed();
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblAttachRentAdjust.getColumn("subareaName").setEditor(txtEditor);
		this.tblAttachRentAdjust.getColumn("buildingName").setEditor(txtEditor);
		this.tblAttachRentAdjust.getColumn("attachName").setEditor(txtEditor);
		
//		设置租金类型为枚举类型
		KDComboBox RentTypeComboBox =  new KDComboBox();
		List list2 = RentTypeEnum.getEnumList();
		for(int i=0;i<list2.size();i++)
		{
			RentTypeComboBox.addItem(list2.get(i));
		}
		KDTDefaultCellEditor RentTypeComboBoxEditer = new KDTDefaultCellEditor(RentTypeComboBox);
		this.tblAttachRentAdjust.getColumn("oldRentType").setEditor(RentTypeComboBoxEditer);
		this.tblAttachRentAdjust.getColumn("newRentType").setEditor(RentTypeComboBoxEditer);
		
		KDComboBox attachTypeComboBox =  new KDComboBox();
		List list = AttachSourceTypeEnum.getEnumList();
		for(int i=0;i<list.size();i++)
		{
			attachTypeComboBox.addItem(list.get(i));
		}
		KDTDefaultCellEditor attachTypeComboBoxEditer = new KDTDefaultCellEditor(attachTypeComboBox);
		this.tblAttachRentAdjust.getColumn("attachType").setEditor(attachTypeComboBoxEditer);
		
		KDComboBox attachStateComboBox =  new KDComboBox();
		List list3 = AttachResourceEnum.getEnumList();
		for(int i=0;i<list3.size();i++)
		{
			attachStateComboBox.addItem(list3.get(i));
		}
		KDTDefaultCellEditor attachStateComboBoxEditer = new KDTDefaultCellEditor(attachStateComboBox);
		this.tblAttachRentAdjust.getColumn("attachState").setEditor(attachStateComboBoxEditer);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblAttachRentAdjust.getColumn("oldStrandRent").setEditor(numberEditor);
		this.tblAttachRentAdjust.getColumn("newStrandRent").setEditor(numberEditor);

	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("creator.*"));
		return sic;
	}
	
	protected IObjectValue getValue(IObjectPK pk) throws Exception 
	{
		AttachRentAdjustInfo attachInfo = (AttachRentAdjustInfo)super.getValue(pk);
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(new FilterItemInfo("head", pk.toString()));
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("*"));
		sel.add(new SelectorItemInfo("attach.*"));
		sel.add(new SelectorItemInfo("attach.subarea.*"));
		sel.add(new SelectorItemInfo("attach.building.*"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(entryFilter);
		
		AttachRentAdjustEntrysCollection attEntrysColl = AttachRentAdjustEntrysFactory.getRemoteInstance().getAttachRentAdjustEntrysCollection(view);
		attachInfo.getEntrys().addCollection(attEntrysColl);
		
		return attachInfo;
	}
	
	protected void btnDelAttach_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelAttach_actionPerformed(e);
		int activeRowIndex = this.tblAttachRentAdjust.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblAttachRentAdjust.getRowCount();
		}
		this.tblAttachRentAdjust.removeRow(activeRowIndex);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.btnImportAttach.setEnabled(true);
		this.btnDelAttach.setEnabled(true);
		this.btnImportPrice.setEnabled(true);
		this.btnAddNew.setEnabled(true);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return AttachRentAdjustFactory.getRemoteInstance();
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