/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.client.CSClientUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgF7PromptDialog;
import com.kingdee.eas.basedata.org.client.OrgViewF7PromptDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.invite.InviteClarifyEntryFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.InviteProjectEditUI;
import com.kingdee.eas.fdc.invite.supplier.CurrencyEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class NewSupplierStockEditUI extends AbstractNewSupplierStockEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(NewSupplierStockEditUI.class);
    protected Map listenersMap = new HashMap();
    /**
     * output class constructor
     */
    public NewSupplierStockEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	initTable();
		super.onLoad();
		initF7();
		initControl();
	}
    public void storeFields()
    {
    	this.storeSupplierAttachList();
    	this.storeSupplierLinkPerson();
    	this.storeSupplierPerson();
    	this.storeSupplierServiceType();
    	this.storeSupplierSplArea();
        super.storeFields();
    }
    public void loadFields() {
    	detachListeners();
    	super.loadFields();
    	this.loadSupplierLinkPerson();
    	this.loadSupplierPerson();
    	this.loadSupplierServiceType();
    	this.loadSupplierSplArea();
    	this.loadSupplierAttachList();
    	attachListeners();
    	
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }
    protected void initControl(){
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		
    	this.actionSubmit.putValue("SmallIcon", EASResource.getIcon("imgTbtn_submit"));
    	this.actionSave.putValue("SmallIcon", EASResource.getIcon("imgTbtn_save"));
    	
    	this.actionCopy.setVisible(true);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	
    	this.menuView.setVisible(false);
    	
    	this.menuItemSave.setVisible(true);
    	this.menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
    	this.menuItemSubmit.setVisible(true);
    	this.menuItemSubmit.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
    	this.menuItemAddNew.setAccelerator(null);
    	
		if(!SupplierStateEnum.SAVE.equals(this.editData.getState())&&!SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		this.actionAddNew.setVisible(false);
		
		try {
			FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		} catch (CodingRuleException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.txtName.setRequired(true);
		this.prmtProvince.setRequired(true);
		this.prmtCity.setRequired(true);
		this.txtAddress.setRequired(true);
		this.txtLinkPhone.setRequired(true);
		this.txtLinkFax.setRequired(true);
		this.txtLinkMail.setRequired(true);
		this.txtPunish.setRequired(true);
		this.comboEnterpriseKind.setRequired(true);
		this.txtRegisterMoney.setRequired(true);
		this.txtTaxRegisterNo.setRequired(true);
		this.txtBizRegisterNo.setRequired(true);
		this.txtEnterpriseMaster.setRequired(true);
		this.txtContractor.setRequired(true);
		this.txtContractorPhone.setRequired(true);
		
		this.prmtInviteType.setRequired(true);
		this.txtEnterpriseMaster.setRequired(true);
		this.prmtSupplierFileType.setRequired(true);
		this.prmtSupplierSplAreaEntry.setRequired(true);
		this.prmtSupplierBusinessMode.setRequired(true);
		this.prmtQuaLevel.setRequired(true);
//		this.txtAbility.setRequired(true);
//		this.txtBusinessNum.setRequired(true);
//		this.txtRecommended.setRequired(true);
		
//		if(this.txtRecommended.getText()!=null&&!this.txtRecommended.getText().trim().equals("")){
//			this.txtRecommended.setEnabled(false);
//		}
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCopy.setEnabled(false);
		}
		loadReviewGatherUI();
    }
    protected void loadReviewGatherUI(){
    	try {
			Map map=new HashMap();
			if(editData.getId()!=null){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id" , editData.getId()));
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("id"));
				view.getSelector().add(new SelectorItemInfo("evaluationType.id"));
				
				SupplierReviewGatherCollection col= SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				for(int i=0;i<col.size();i++){
					if(col.get(i).getEvaluationType()==null) continue;
					if(map.get(col.get(i).getEvaluationType().getId().toString())!=null){
						((Set)map.get(col.get(i).getEvaluationType().getId().toString())).add(col.get(i).getId().toString());
					}else{
						Set id=new HashSet();
						id.add(col.get(i).getId().toString());
						map.put(col.get(i).getEvaluationType().getId().toString(), id);
					}
				}
			}
			Iterator it=map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry) it.next();
				String typeId=entry.getKey().toString();
				Set idSet=(HashSet)entry.getValue();
				
				KDScrollPane panel=new KDScrollPane();
				panel.setName(typeId);
				panel.setMinimumSize(new Dimension(1013,600));		
				panel.setPreferredSize(new Dimension(1013,600));
		        this.kDTabbedPane2.add(panel,SupplierEvaluationTypeFactory.getRemoteInstance().getSupplierEvaluationTypeInfo(new ObjectUuidPK(typeId)).getName());
		        
		        UIContext uiContext = new UIContext(this);
		        uiContext.put("IDSET", idSet);
				SupplierReviewGatherListUI ui = (SupplierReviewGatherListUI) UIFactoryHelper.initUIObject(SupplierReviewGatherListUI.class.getName(), uiContext, null,OprtState.VIEW);
				panel.setViewportView(ui);
				panel.setKeyBoardControl(true);
				panel.setEnabled(false);
			}
		}catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if(oprtType.equals(OprtState.VIEW)){
			this.actionPersonAddLine.setEnabled(false);
			this.actionPersonInsertLine.setEnabled(false);
			this.actionPersonRemoveLine.setEnabled(false);
			this.actionLinkPersonAddLine.setEnabled(false);
			this.actionLinkPersonInsertLine.setEnabled(false);
			this.actionLinkPersonRemoveLine.setEnabled(false);
			this.actionAttachListAddLine.setEnabled(false);
			this.actionAttachListInsertLine.setEnabled(false);
			this.actionAttachListRemoveLine.setEnabled(false);
		}else{
			this.actionPersonAddLine.setEnabled(true);
			this.actionPersonInsertLine.setEnabled(true);
			this.actionPersonRemoveLine.setEnabled(true);
			this.actionLinkPersonAddLine.setEnabled(true);
			this.actionLinkPersonInsertLine.setEnabled(true);
			this.actionLinkPersonRemoveLine.setEnabled(true);
			this.actionAttachListAddLine.setEnabled(true);
			this.actionAttachListInsertLine.setEnabled(true);
			this.actionAttachListRemoveLine.setEnabled(true);
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run() {
				storeFields();
				initOldData(editData);
			}
		});
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run() {
				storeFields();
				initOldData(editData);
			}
		});
		
		FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("supplierServiceType.*");
    	sel.add("supplierServiceType.serviceType.*");
    	sel.add("supplierSplAreaEntry.*");
    	sel.add("supplierSplAreaEntry.fdcSplArea.*");
    	sel.add("supplierPersonEntry.*");
    	sel.add("supplierAttachListEntry.*");
    	sel.add("linkPerson.*");
    	sel.add("state");
    	sel.add("adminCU.*");
    	sel.add("sysSupplier.*");
    	sel.add("*");
		return sel;
	}
    protected void storeSupplierSplArea(){
    	editData.getSupplierSplAreaEntry().clear();
    	Object[] value=(Object[]) this.prmtSupplierSplAreaEntry.getValue();
    	String splArea="";
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			FDCSplAreaInfo info = (FDCSplAreaInfo) value[i];
				SupplierSplAreaEntryInfo entry = new SupplierSplAreaEntryInfo();
				entry.setFdcSplArea(info);
				splArea=splArea+info.getName()+";";
				editData.getSupplierSplAreaEntry().add(entry);
    		}
    	}
    	editData.setSplArea(splArea);
    }
    protected void storeSupplierServiceType(){
    	editData.getSupplierServiceType().clear();
    	Object[] value=(Object[]) this.prmtServiceType.getValue();
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo) value[i];
    			FDCSupplierServiceTypeInfo entry = new FDCSupplierServiceTypeInfo();
				entry.setServiceType(info);
				editData.getSupplierServiceType().add(entry);
    		}
    	}
    }
    protected void storeSupplierLinkPerson(){
    	editData.getLinkPerson().clear();
    	for(int i=0;i<this.kdtLinkPerson.getRowCount();i++){
    		IRow row = this.kdtLinkPerson.getRow(i);
    		SupplierLinkPersonInfo entry=new SupplierLinkPersonInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierLinkPersonInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setPersonName((String)row.getCell("personName").getValue());
    		entry.setPosition((String)row.getCell("position").getValue());
    		entry.setPhone((String)row.getCell("phone").getValue());
    		entry.setWorkPhone((String)row.getCell("workPhone").getValue());
    		entry.setPersonFax((String)row.getCell("personFax").getValue());
    		entry.setEmail((String)row.getCell("email").getValue());
			entry.setIsDefault(((Boolean)row.getCell("isDefault").getValue()).booleanValue());
    		editData.getLinkPerson().add(entry);
    	}
    }
    protected void storeSupplierPerson(){
    	editData.getSupplierPersonEntry().clear();
    	for(int i=0;i<this.kdtSupplierPerson.getRowCount();i++){
    		IRow row = this.kdtSupplierPerson.getRow(i);
    		SupplierPersonEntryInfo entry=new SupplierPersonEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierPersonEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setNumber((String)row.getCell("number").getValue());
    		entry.setName((String)row.getCell("name").getValue());
    		if(row.getCell("amount").getValue()==null){
    			entry.setAmount(0);
    		}else{
    			entry.setAmount(Integer.parseInt(row.getCell("amount").getValue().toString()));
    		}
    		editData.getSupplierPersonEntry().add(entry);
    	}
    		
    }
    protected void storeSupplierAttachList(){
    	editData.getSupplierAttachListEntry().clear();
    	for(int i=0;i<this.kdtSupplierAttachList.getRowCount();i++){
    		IRow row = this.kdtSupplierAttachList.getRow(i);
    		SupplierAttachListEntryInfo entry=new SupplierAttachListEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierAttachListEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setNumber((String)row.getCell("number").getValue());
    		entry.setName((String)row.getCell("name").getValue());
    		entry.setDescription((String)row.getCell("description").getValue());
    		editData.getSupplierAttachListEntry().add(entry);
    	}
    }
	protected void loadSupplierSplArea(){
    	SupplierSplAreaEntryCollection col=editData.getSupplierSplAreaEntry();
    	if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			SupplierSplAreaEntryInfo entry = col.get(i);
    			value[i]=entry.getFdcSplArea();
    		}
    		this.prmtSupplierSplAreaEntry.setValue(value);
    	}else{
    		this.prmtSupplierSplAreaEntry.setValue(null);
    	}
    }
	protected void loadSupplierServiceType(){
		FDCSupplierServiceTypeCollection col=editData.getSupplierServiceType();
		if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			FDCSupplierServiceTypeInfo entry = col.get(i);
    			value[i]=entry.getServiceType();
    		}
    		this.prmtServiceType.setValue(value);
    	}else{
    		this.prmtServiceType.setValue(null);
    	}
	}
	protected void loadSupplierLinkPerson(){
		SupplierLinkPersonCollection col=editData.getLinkPerson();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtLinkPerson.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierLinkPersonInfo entry=col.get(i);
			IRow row=this.kdtLinkPerson.addRow();
			row.setUserObject(entry);
			row.getCell("personName").setValue(entry.getPersonName());
			row.getCell("position").setValue(entry.getPosition());
			row.getCell("phone").setValue(entry.getPhone());
			row.getCell("workPhone").setValue(entry.getWorkPhone());
			row.getCell("personFax").setValue(entry.getPersonFax());
			row.getCell("email").setValue(entry.getEmail());
			row.getCell("isDefault").setValue(Boolean.valueOf(entry.isIsDefault()));
			
//			if(row.getCell("position").getValue()!=null
//        			&&(row.getCell("position").getValue().toString().equals("法人代表/董事长")
//        					||row.getCell("position").getValue().toString().equals("总经理"))){
//				row.getCell("position").getStyleAttributes().setLocked(true);
//        	}
		}
	}
	protected void loadSupplierPerson(){
		SupplierPersonEntryCollection col=editData.getSupplierPersonEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtSupplierPerson.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierPersonEntryInfo entry=col.get(i);
			IRow row=this.kdtSupplierPerson.addRow();
			row.setUserObject(entry);
			row.getCell("number").setValue(entry.getNumber());
			row.getCell("name").setValue(entry.getName());
			row.getCell("amount").setValue(Integer.valueOf(entry.getAmount()));
		}
	}
	protected void loadSupplierAttachList(){
		SupplierAttachListEntryCollection col=editData.getSupplierAttachListEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtSupplierAttachList.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierAttachListEntryInfo entry=col.get(i);
			IRow row=this.kdtSupplierAttachList.addRow();
			row.setUserObject(entry);
			row.getCell("number").setValue(entry.getNumber());
			row.getCell("name").setValue(entry.getName());
			row.getCell("description").setValue(entry.getDescription());
			try {
				if(entry.getId()!=null){
					row.getCell("attachment").setValue(loadAttachment(entry.getId().toString()));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}	
    protected void prmtProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		ProvinceInfo pinfo = (ProvinceInfo) prmtProvince.getValue();
		if(pinfo!=null){
			filter.getFilterItems().add(new FilterItemInfo("province.id" , pinfo.getId().toString()));
		}
		view.setFilter(filter);
		this.prmtCity.setEntityViewInfo(view);
    }

    protected void prmtSupplierFileType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         SupplierFileTypeInfo fileType=(SupplierFileTypeInfo)this.prmtSupplierFileType.getValue();
         boolean isShowWarn=false;
         boolean isUpdate=false;
         if(this.kdtSupplierPerson.getRowCount()>0||this.kdtSupplierAttachList.getRowCount()>0){
        	 isShowWarn=true;
         }
         if(isShowWarn){
        	 if(FDCMsgBox.showConfirm2(this, "档案分类改变会覆盖职员构成和附件清单数据，是否继续？")== FDCMsgBox.YES){
        		 isUpdate=true;
             }
         }else{
        	 isUpdate=true;
         }
         if(isUpdate){
        	 this.kdtSupplierPerson.removeRows();
        	 this.kdtSupplierAttachList.removeRows();
        	 
        	 if(fileType!=null){
        		 SorterItemCollection sort=new SorterItemCollection();
        		 sort.add(new SorterItemInfo("number"));
        		 EntityViewInfo view=new EntityViewInfo();
            	 FilterInfo filter = new FilterInfo();
                 filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id" , fileType.getId().toString()));
                 filter.getFilterItems().add(new FilterItemInfo("isEnabled" , Boolean.TRUE));
                 view.setFilter(filter);
                 view.setSorter(sort);
                 SupplierPersonCollection col=SupplierPersonFactory.getRemoteInstance().getSupplierPersonCollection(view);
                 for(int i=0;i<col.size();i++){
                	 SupplierPersonInfo sp=col.get(i);
                	 IRow row=this.kdtSupplierPerson.addRow();
                	 row.getCell("number").setValue(sp.getNumber());
                	 row.getCell("name").setValue(sp.getName());
                 }
                 SupplierAttachListCollection alCol=SupplierAttachListFactory.getRemoteInstance().getSupplierAttachListCollection(view);
                 for(int i=0;i<alCol.size();i++){
                	 SupplierAttachListInfo at=alCol.get(i);
                	 IRow row=this.kdtSupplierAttachList.addRow();
                	 SupplierAttachListEntryInfo info=new SupplierAttachListEntryInfo();
     				 info.setId(BOSUuid.create(info.getBOSType()));
     				 row.setUserObject(info);
                	 row.getCell("number").setValue(at.getNumber());
                	 row.getCell("name").setValue(at.getName());
                	 row.getCell("description").setValue(at.getDescription());
                 }
        	 }
         }
    }
    protected void initF7(){
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtServiceType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtSupplierFileType.setEntityViewInfo(view);
		this.prmtSupplierBusinessMode.setEntityViewInfo(view);
		this.prmtSupplierSplAreaEntry.setEntityViewInfo(view);
    }
    protected void initWorkButton(ItemAction add,ItemAction insert,ItemAction remove,KDContainer container){
    	KDWorkButton btnAddRowinfo=new KDWorkButton();
    	KDWorkButton btnInsertRowinfo=new KDWorkButton();
    	KDWorkButton btnDeleteRowinfo=new KDWorkButton();
    	
    	add.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)container.add(add);
		btnAddRowinfo.setText(getResource("addRow"));
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		insert.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)container.add(insert);
		btnInsertRowinfo.setText(getResource("insertRow"));
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		remove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)container.add(remove);
		btnDeleteRowinfo.setText(getResource("deleteRow"));
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
    }
    protected void initTable(){
    	initWorkButton(this.actionPersonAddLine,this.actionPersonInsertLine,this.actionPersonRemoveLine,this.contSupplierPerson);
    	initWorkButton(this.actionLinkPersonAddLine,this.actionLinkPersonInsertLine,this.actionLinkPersonRemoveLine,this.contLinkPerson);
    	initWorkButton(this.actionAttachListAddLine,this.actionAttachListInsertLine,this.actionAttachListRemoveLine,this.contSupplierAttachList);
    	
    	KDTextField testField = new KDTextField();
    	testField.setMaxLength(80);
		KDTDefaultCellEditor editorSize = new KDTDefaultCellEditor(testField);
		
		KDTextField testField1 = new KDTextField();
    	testField.setMaxLength(255);
		KDTDefaultCellEditor editorSize1 = new KDTDefaultCellEditor(testField1);
		
		KDFormattedTextField peopleC = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		peopleC.setPrecision(0);
		peopleC.setDataVerifierType(12);
		KDTDefaultCellEditor peopleCount = new KDTDefaultCellEditor(peopleC);
		
    	this.kdtLinkPerson.checkParsed();
 	    this.kdtLinkPerson.getColumn("personName").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("position").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("phone").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("workPhone").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("personFax").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("email").setEditor(editorSize);
 		KDCheckBox isAutidor = new KDCheckBox();
 		isAutidor.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(isAutidor);
 		this.kdtLinkPerson.getColumn("isDefault").setEditor(editor);
 		
 		this.kdtLinkPerson.getColumn("workPhone").setRequired(true);
 		
 		this.kdtSupplierPerson.checkParsed();
 		this.kdtSupplierPerson.getColumn("number").setEditor(editorSize);
  		this.kdtSupplierPerson.getColumn("name").setEditor(editorSize1);
  		this.kdtSupplierPerson.getColumn("amount").setEditor(peopleCount);
  		this.kdtSupplierPerson.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
 		
 		this.kdtSupplierAttachList.checkParsed();
 		this.kdtSupplierAttachList.getColumn("number").setEditor(editorSize);
  		this.kdtSupplierAttachList.getColumn("name").setEditor(editorSize1);
  		this.kdtSupplierAttachList.getColumn("attachment").getStyleAttributes().setLocked(true);
  		this.kdtSupplierAttachList.getColumn("attachment").getStyleAttributes().setFontColor(Color.BLUE);
  		this.kdtSupplierAttachList.getColumn("attachment").setWidth(400);
    }
    
	public void actionAttachListAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtSupplierAttachList);
	}
	public void actionAttachListInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtSupplierAttachList);
	}
	public void actionAttachListRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtSupplierAttachList);
	}
	public void actionLinkPersonAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtLinkPerson);
	}
	public void actionLinkPersonInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtLinkPerson);
	}
	public void actionLinkPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtLinkPerson);
	}
	public void actionPersonAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtSupplierPerson);
	}
	public void actionPersonInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtSupplierPerson);
	}
	public void actionPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtSupplierPerson);
	}
	protected void kdtLinkPerson_editStopped(KDTEditEvent e) throws Exception {
		int index = e.getRowIndex();
		int size = this.kdtLinkPerson.getRowCount();
	    IRow row = this.kdtLinkPerson.getRow(index);
	    Boolean b=(Boolean) row.getCell("isDefault").getValue();
	    /*
	     * 这里是合并手机与办公电话到一列中去,序时薄上要把手机和办公电话显示到一起.暂用这种方式做
	     */
	    for(int j = 0 ; j<size ; j++){
	    	if(null != this.kdtLinkPerson.getRow(j).getCell("contact").getValue()){
	    		this.kdtLinkPerson.getRow(j).getCell("contact").setValue(null);
	    	}
	    	if(null != this.kdtLinkPerson.getRow(j).getCell("phone").getValue()){
	    		if(null != this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    			this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("phone").getValue()+";"+this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue());
	    		}else{
	    			this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("phone").getValue().toString());
	    		}
	    	}else if(null != this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    		this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue().toString());
	    	}else{
	    		continue;
	    	}
	    }
	    /*
	     * 处理只能勾选一个人为默认联系.新增第一行时为空 则返回
	     */
	    if(null == b ){
	    	return ;
	    }
	    if(b.booleanValue()){
			for( int i = 0 ; i< size ;i++){
				if(i ==index ){
					continue;
				}
				this.kdtLinkPerson.getRow(i).getCell("isDefault").setValue(Boolean.FALSE);
			}
	    }
	}
	protected void kdtSupplierAttachList_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2&&
				this.kdtSupplierAttachList.getColumnKey(e.getColIndex()).equals("attachment")) {
			String id=((SupplierAttachListEntryInfo)this.kdtSupplierAttachList.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true")){
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else{
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.kdtSupplierAttachList.getRow(e.getRowIndex()).getCell("attachment").setValue(loadAttachment(id));
		}
	}
	protected String loadAttachment(String id) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		for(int i=0;i<col.size();i++){
			name=name+col.get(i).getAttachment().getName()+" ";
		}
		return name;
	}
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		SupplierStockInfo info = new SupplierStockInfo();
		try {
			FDCSplAreaCollection col=FDCSplAreaFactory.getRemoteInstance().getFDCSplAreaCollection("select * from where number='01'");
			if(col.size()>0){
				SupplierSplAreaEntryInfo entry=new SupplierSplAreaEntryInfo();
				entry.setFdcSplArea(col.get(0));
				info.getSupplierSplAreaEntry().add(entry);
			}
			SupplierBusinessModeCollection modeCol=SupplierBusinessModeFactory.getRemoteInstance().getSupplierBusinessModeCollection("select * from where number='01'");
			if(modeCol.size()>0){
				info.setSupplierBusinessMode(modeCol.get(0));
			}
			info.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setCurrency(CurrencyEnum.RMB);
		if(getUIContext().get("type")!=null){
			info.setInviteType((InviteTypeInfo)getUIContext().get("type"));
		}
		if(getUIContext().get("org")!=null){
			try {
				PurchaseOrgUnitInfo orgUnitInfo = PurchaseOrgUnitFactory.getRemoteInstance().getPurchaseOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo) getUIContext().get("org")).getUnit().getId()));
				info.setPurchaseOrgUnit(orgUnitInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		info.setHasCreatedSupplierBill(false);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setAdminCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
//		SupplierLinkPersonInfo one=new SupplierLinkPersonInfo();
//		one.setPosition("法人代表/董事长");
//		
//		SupplierLinkPersonInfo two=new SupplierLinkPersonInfo();
//		two.setPosition("总经理");
//		
//		info.getLinkPerson().add(one);
//		info.getLinkPerson().add(two);
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SupplierStockFactory.getRemoteInstance();
	}
	protected void attachListeners() {
		addDataChangeListener(this.prmtSupplierFileType);
		addDataChangeListener(this.prmtProvince);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtSupplierFileType);
		removeDataChangeListener(this.prmtProvince);
	}
    protected void addDataChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
    }
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    private void kdTableAddRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW)){
			IRow row=table.addRow();
			if(table.equals(this.kdtSupplierAttachList)){
				SupplierAttachListEntryInfo info=new SupplierAttachListEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}else if(table.equals(this.kdtLinkPerson)){
				SupplierLinkPersonInfo info=new SupplierLinkPersonInfo();
				row.setUserObject(info);
				if(this.kdtLinkPerson.getRowCount()==1){
					row.getCell("isDefault").setValue(Boolean.TRUE);
				}else{
					row.getCell("isDefault").setValue(Boolean.FALSE);
				}
			}
		}
			
	}
	private void kdTableInsertLine(KDTable table){
		if(!getOprtState().equals(OprtState.VIEW)){
			if(table == null)
	            return;
	        IRow row = null;
	        if(table.getSelectManager().size() > 0)
	        {
	            int top = table.getSelectManager().get().getTop();
	            if(isTableColumnSelected(table))
	                row = table.addRow();
	            else
	                row = table.addRow(top);
	        } else
	        {
	            row = table.addRow();
	        }
	        if(table.equals(this.kdtSupplierAttachList)&&row!=null){
				SupplierAttachListEntryInfo info=new SupplierAttachListEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}else if(table.equals(this.kdtLinkPerson)){
				SupplierLinkPersonInfo info=new SupplierLinkPersonInfo();
				row.setUserObject(info);
				if(this.kdtLinkPerson.getRowCount()==1){
					row.getCell("isDefault").setValue(Boolean.TRUE);
				}else{
					row.getCell("isDefault").setValue(Boolean.FALSE);
				}
			}
		}
	}
	protected final boolean isTableColumnSelected(KDTable table)
    {
        if(table.getSelectManager().size() > 0)
        {
            KDTSelectBlock block = table.getSelectManager().get();
            if(block.getMode() == 4 || block.getMode() == 8)
                return true;
        }
        return false;
    }
	private boolean confirmRemove(Component comp){
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	private void kdTableDeleteRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW)){
	        if(table.getSelectManager().size() == 0 || isTableColumnSelected(table)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(confirmRemove(this)){
	            int top = table.getSelectManager().get().getBeginRow();
	            int bottom = table.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(table.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                if(table.equals(this.kdtSupplierAttachList)){
	                	try {
	                		deleteAttachment(((SupplierAttachListEntryInfo)table.getRow(top).getUserObject()).getId().toString());
	                	} catch (BOSException e) {
							e.printStackTrace();
						} catch (EASBizException e) {
							e.printStackTrace();
						}
	                }
//	                if(table.equals(this.kdtLinkPerson)){
//	                	if(this.kdtLinkPerson.getRow(top).getCell("position").getValue()!=null
//	                			&&(this.kdtLinkPerson.getRow(top).getCell("position").getValue().toString().equals("法人代表/董事长")
//	                					||this.kdtLinkPerson.getRow(top).getCell("position").getValue().toString().equals("总经理"))){
//	                		FDCMsgBox.showWarning(this,"此行不能删除！");
//	                		return;
//	                	}
//	                }
	                table.removeRow(top);
	            }
	        }
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}
	private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
	protected void showSaveSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
	    setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
	    setShowMessagePolicy(0);
	    setIsShowTextOnly(false);
	    showMessage();
	}
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Submit_OK"));
	    if(chkMenuItemSubmitAndAddNew.isSelected())
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
	    else
	    if(!chkMenuItemSubmitAndPrint.isSelected() && chkMenuItemSubmitAndAddNew.isSelected())
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
	    else
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
	    setIsShowTextOnly(false);
	    setShowMessagePolicy(0);
	    showMessage();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		FDCClientVerifyHelper.verifyEmpty(this, getNameCtrl());
		
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProvince);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCity);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtAddress);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkPhone);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkFax);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkMail);
		if(this.txtPunish.getText().trim().equals("")){
			FDCMsgBox.showWarning(this,"三年内是否有法律纠纷，是否被政府部门处罚不能为空！");
			SysUtil.abort();
		}
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtPunish);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboEnterpriseKind);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRegisterMoney);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTaxRegisterNo);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBizRegisterNo);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtEnterpriseMaster);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtContractor);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtContractorPhone);
		
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPurchaseOrgUnit);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtEnterpriseMaster);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierFileType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierSplAreaEntry);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierBusinessMode);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtQuaLevel);
		
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtBusinessNum);
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtAbility);
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtRecommended);
		boolean isDefault=true;
		for(int i=0;i<this.kdtLinkPerson.getRowCount();i++){
			IRow row=this.kdtLinkPerson.getRow(i);
			if(((Boolean)row.getCell("isDefault").getValue()).booleanValue()){
				isDefault=false;
			}
			if(row.getCell("personName").getValue()==null||row.getCell("personName").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"授权联系人姓名不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("phone").getValue()==null||row.getCell("phone").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"授权联系人手机不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("workPhone").getValue()==null||row.getCell("workPhone").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"授权联系人办公电话不能为空！");
				SysUtil.abort();
			}
		}
		if(isDefault){
			FDCMsgBox.showWarning(this,"授权联系人不能为空！");
			SysUtil.abort();
		}
	}
	public boolean checkBeforeWindowClosing(){
		if (hasWorkThreadRunning()) {
			return false;
		}
		if(getTableForPrintSetting()!=null){
			this.savePrintSetting(this.getTableForPrintSetting());
		}
		boolean b = true;
		if (!b) {
			return b;
		} else {
			if (isModify()) {
				int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Confirm_Save_Exit"));
				if (result == KDOptionPane.YES_OPTION) {
					try {
						if (editData.getState() == null|| editData.getState() == SupplierStateEnum.SAVE) {
							actionSave.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSave,ActionEvent.ACTION_PERFORMED, btnSave.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSubmit,ActionEvent.ACTION_PERFORMED, btnSubmit.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
						}
					} catch (Exception exc) {
						return false;
					}
				} else if (result == KDOptionPane.NO_OPTION) {
					if(editData!=null){
						for(int i=0;i<this.editData.getSupplierAttachListEntry().size();i++){
							if(this.editData.getSupplierAttachListEntry().get(i).getId()==null) continue;
							try {
								if(!SupplierAttachListEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getSupplierAttachListEntry().get(i).getId().toString()))){
									this.deleteAttachment(this.editData.getSupplierAttachListEntry().get(i).getId().toString());
								}
							} catch (EASBizException e) {
								e.printStackTrace();
							} catch (BOSException e) {
								e.printStackTrace();
							}
						}
					}
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCopy_actionPerformed(e);
    	FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
    	this.txtStorageNumber.setText(null);
    	this.pkStorageDate.setValue(null);
    	
    	this.cbIsPass.setSelectedItem(null);
    	this.prmtCreator.setValue(null);
    	this.prmtAuditor.setValue(null);
    	this.pkCreateTime.setValue(null);
    	this.pkAuditDate.setValue(null);
    	this.kDTabbedPane2.removeAll();
    	
    	this.editData.setLevel(null);
    	this.editData.setGrade(null);
    	this.editData.put("isPass", null);
    }
}