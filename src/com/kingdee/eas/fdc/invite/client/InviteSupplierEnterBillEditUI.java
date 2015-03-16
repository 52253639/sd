/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteSupplierEnterBillCollection;
import com.kingdee.eas.fdc.invite.InviteSupplierEnterBillFactory;
import com.kingdee.eas.fdc.invite.InviteSupplierEnterBillInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class InviteSupplierEnterBillEditUI extends AbstractInviteSupplierEnterBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteSupplierEnterBillEditUI.class);
    private boolean isHasAttachment = false;
    
    /**
     * output class constructor
     */
    public InviteSupplierEnterBillEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

//    private FullOrgUnitInfo getContextOrgUnit(){
//		Object orgUnit = this.getUIContext().get("orgUnit");
//		if(orgUnit instanceof FullOrgUnitInfo)
//			return (FullOrgUnitInfo)orgUnit;
//		else if(orgUnit instanceof OrgStructureInfo)
//			return ((OrgStructureInfo)orgUnit).getUnit();
//		else
//			return SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
//	}
    
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	
    	super.onLoad();
    	if(editData.getState() != null && editData.getState().equals(FDCBillStateEnum.AUDITTED) || !editData.getOrgUnit().getId().equals(SysContext.getSysContext().getCurrentOrgUnit().getId())){
    		this.actionEdit.setEnabled(false);
    		this.actionRemove.setEnabled(false);
    	}
        setUITitle("入围标准确认单编辑");
    	FDCClientUtils.setRespDeptF7(prmtResponsibleDept, this, null);
    	FDCClientUtils.setPersonF7(prmtResponsiblePerson, this,null);
    	fillAttachmentList();
    	this.btnViewAttachment.setEnabled(isHasAttachment);
    	this.actionAudit.setVisible(false);
    	this.actionUnAudit.setVisible(false);
    	this.actionCopy.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionSubmitOption.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionCopyLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);	
		this.actionCopyFrom.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuView.setVisible(false);
		this.menuTable1.setVisible(false);
		
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("name");
    	sic.add("id");
    	sic.add("number");
    	sic.add("respPerson.*");
    	sic.add("inviteType.*");
    	sic.add("project.*");
    	prmtInviteProject.setSelectorCollection(sic);
    	
    	prmtInviteProject.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				EntityViewInfo evi  = null;
				FilterInfo filter = null;
				Set usedInviteProject = new HashSet();
				try {
					InviteSupplierEnterBillCollection cols = InviteSupplierEnterBillFactory.getRemoteInstance().getInviteSupplierEnterBillCollection();
				    for(Iterator it = cols.iterator();it.hasNext();){
				    	InviteSupplierEnterBillInfo info = (InviteSupplierEnterBillInfo) it.next();
				    	if(info.getInviteProject() != null){
				    		usedInviteProject.add(info.getInviteProject().getId().toString());
				    	}
				    	
				    }
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				prmtInviteProject.getQueryAgent().resetRuntimeEntityView();
				if(prmtInviteProject.getEntityViewInfo()!=null ){
					evi = prmtInviteProject.getEntityViewInfo();
					if(evi.getFilter() != null){
						filter = evi.getFilter();
					}else{
						filter = new FilterInfo();
					}
					    if(usedInviteProject.size() > 0 ){
					    	filter.getFilterItems().add(new FilterItemInfo("id",usedInviteProject,CompareType.NOTINCLUDE));
					    }
						
					    filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
						filter.getFilterItems().add(new FilterItemInfo("parent.id",null,CompareType.EQUALS));
					if(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo() != null){
						filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString()));
					}
					evi.setFilter(filter);
				}else{
					
					evi = new EntityViewInfo();
					filter = new FilterInfo();
					 if(usedInviteProject.size() > 0 ){
					    	filter.getFilterItems().add(new FilterItemInfo("id",usedInviteProject,CompareType.NOTINCLUDE));
					    }
					    filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
						filter.getFilterItems().add(new FilterItemInfo("parent.id",null,CompareType.EQUALS));
					if(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo() != null){
						filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString()));
						}
					evi.setFilter(filter);
				}
				prmtInviteProject.setEntityViewInfo(evi);
			}});
    	prmtInviteProject.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				
				if(eventObj.getNewValue() != null){
					InviteProjectInfo projectInfo = (InviteProjectInfo) eventObj.getNewValue();
					InviteTypeInfo  typeInfo = projectInfo.getInviteType();
//					CurProjectInfo curProject  = projectInfo.getProject();
					PersonInfo person = projectInfo.getRespPerson();
					if(typeInfo != null && typeInfo.getName() != null){
					  txtInviteTypeName.setText(typeInfo.getName());
					}
					if(curProject != null && curProject.getName() != null){
				      txtCurProjectName.setText(curProject.getName());
					}
					if(person != null && person.getName() != null){
					  txtInviteProjectRespPerson.setText(person.getName());
					}
					// 修改为名称带出编码 modify by msb 2010/04/27
					txtInviteProjectNumber.setText(projectInfo.getNumber());
				}else{
					txtInviteTypeName.setText("");
					txtCurProjectName.setText("");
					txtInviteProjectNumber.setText("");
					txtInviteProjectRespPerson.setText("");
				}
				
			}});
    	this.actionInviteExecuteInfo.setEnabled(true);
    	
    	//add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		this.txtName.setMaxLength(255);
		this.txtNumber.setMaxLength(255);
    }
    
    
    private void fillAttachmentList(){
    	this.cmbAttachmentList.removeAllItems();
    	String boId = null;
    	if(this.editData.getId() == null){
    		return ;
    	}else{
    		boId = editData.getId().toString();	
    	}
    	if(boId != null){
    		SelectorItemCollection sic = new SelectorItemCollection();
    		sic.add("id");
    		sic.add("attachment.id");
    		sic.add("attachment.name");
    		
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("boID",boId));
    		
    		EntityViewInfo evi = new EntityViewInfo();
    		evi.setSelector(sic);
    		evi.setFilter(filter);
    		BoAttchAssoCollection col = null;
    		try {
				col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		if(col != null && col.size()>0 ){
    			for(Iterator it = col.iterator();it.hasNext();){
    				AttachmentInfo attachment  = ((BoAttchAssoInfo)it.next()).getAttachment();
    				this.cmbAttachmentList.addItem(attachment);
    			}
    			isHasAttachment = true;
    		}else{
    			isHasAttachment = false;
    		}
    		
    	}
    	
    	this.btnViewAttachment.setEnabled(isHasAttachment);
    }
    

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
        this.cmbAttachmentList.removeAllItems();
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	String boID = getSelectBOID();
		if (boID == null) {
			return;
		}
		boolean isEdit = false;
		if (STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			isEdit = true;
		}
		 
		 //add liuguangyue 2010-4-28
		isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
		
		////modify liuguangyue 2010-4-27
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info,this,editData,String.valueOf("1"),isEdit);
		
        //super.actionAttachment_actionPerformed(e);
        fillAttachmentList();
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

    /**
     * output actionViewAttachment_actionPerformed
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewAttachment_actionPerformed(e);
        String attachId = null;
    	if(this.cmbAttachmentList.getSelectedItem() != null && this.cmbAttachmentList.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttachmentList.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
    }
/**
 * FDCBill实现方法
 */
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return InviteSupplierEnterBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		InviteSupplierEnterBillInfo billInfo = new InviteSupplierEnterBillInfo();
		billInfo.setState(FDCBillStateEnum.SAVED);
		billInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		billInfo.setResponsibleDate(new Date());
		return billInfo;
	}
	
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
    
	protected void lockContainer(Container container) {
		// TODO Auto-generated method stub
		if("conAttachmentList".equalsIgnoreCase(container.getName())){
			return ;
		}
		super.lockContainer(container);
	}
	/**
	 * 编码规则
	 */
	protected void verifyInputForSave() throws Exception {
		// TODO Auto-generated method stub
		//super.verifyInputForSave();
		
		 ICodingRuleManager codingManager =getEncodingRule();
		    
			if(this.editData != null )
			{
				
				boolean isUseCodingRule = codingManager.isExist(this.editData,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
				if(!isUseCodingRule){
					
					if(StringUtils.isEmpty(editData.getNumber()) || editData.getNumber().length() > 100){
						FDCMsgBox.showError("编号为空或者超出系统约定的长度！");
						abort();
					}
					
			}}
			
		
		if(StringUtils.isEmpty(editData.getName())|| editData.getName().trim().length() > 100){
			FDCMsgBox.showError("名称为空或者超出系统约定的长度！");
			abort();
		}
		
		
		if(editData.getResponsiblePerson() == null){
			FDCMsgBox.showError("经办人不能为空！");
			abort();
		}
		
		if(editData.getInviteProject() == null ){
			FDCMsgBox.showError("立项号不能为空！");
			abort();
		}
		
		
		if(editData.getRemark() != null && editData.getRemark().length() > 255){
			FDCMsgBox.showError("备注超出系统约定的长度(255)！");
			abort();
		}
		if(StringUtils.isEmpty(editData.getEnterCriterion())||editData.getEnterCriterion().length() > 500){
			FDCMsgBox.showError("招标入围单位选择标准为空或者超出系统约定的长度(500)！");
			abort();
		}
		
	}
	
	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
     
//		super.verifyInputForSubmint();
		
		
		   ICodingRuleManager codingManager =getEncodingRule();
		    
			if(this.editData != null )
			{
				
				boolean isUseCodingRule = codingManager.isExist(this.editData,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
				if(!isUseCodingRule){
					
					if(StringUtils.isEmpty(editData.getNumber()) || editData.getNumber().trim().length() > 100){
						FDCMsgBox.showError("编号为空或者超出系统约定的长度！");
						abort();
					}
					
			}}
			
		
		if(StringUtils.isEmpty(editData.getName()) || editData.getName().trim().length() > 100){
			FDCMsgBox.showError("名称为空或者超出系统约定的长度！");
			abort();
		}
		
		if(editData.getInviteProject() == null ){
			FDCMsgBox.showError("立项号不能为空！");
			abort();
		}
		if(editData.getResponsiblePerson() == null){
			FDCMsgBox.showError("经办人不能为空！");
			abort();
		}
		
		
		if(editData.getRemark() != null && editData.getRemark().length() > 255){
			FDCMsgBox.showError("备注超出系统约定的长度(255)！");
			abort();
		}
		if(StringUtils.isEmpty(editData.getEnterCriterion())||editData.getEnterCriterion().length() > 500){
			FDCMsgBox.showError("招标入围单位选择标准为空或者超出系统约定的长度(500)！");
			abort();
		}
	}
	
   public SelectorItemCollection getSelectors() {
	// TODO Auto-generated method stub
	    SelectorItemCollection sic = new SelectorItemCollection();
	    sic.add(new SelectorItemInfo("*"));
	    sic.add(new SelectorItemInfo("orgUnit.*"));
	    sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("responsibleDate"));
        sic.add(new SelectorItemInfo("responsiblePerson.*"));
        sic.add(new SelectorItemInfo("responsibleDept.*"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("inviteProject.project.*"));
        sic.add(new SelectorItemInfo("inviteProject.respPerson.*"));
        sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("enterCriterion"));
	 
	return sic;
}
   public ICodingRuleManager getEncodingRule()
   {
   	
   	ICodingRuleManager codingRuleManager = null;
		try {
			if(this.getUIContext().get("codingRuleManager")== null){
			codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			this.getUIContext().put("codingRuleManager", codingRuleManager);
			}else{
				
				codingRuleManager = (ICodingRuleManager) this.getUIContext().get("codingRuleManager");
			}
			
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return codingRuleManager;
   }
   
   public void actionInviteExecuteInfo_actionPerformed(ActionEvent e)
		throws Exception {
	// TODO Auto-generated method stub
	super.actionInviteExecuteInfo_actionPerformed(e);
	InviteProjectInfo info = null;
	if(editData.getInviteProject() != null){
		info = editData.getInviteProject();
	}else{
		if(prmtInviteProject.getData() != null){
			info = (InviteProjectInfo) prmtInviteProject.getData();
		}
	}
	if(info == null){
		FDCMsgBox.showError("请先选择招标立项!");
		abort();
	}
	
	UIContext uiContext = new UIContext(this);

	uiContext.put(UIContext.ID, null);
	uiContext.put("INVITE_PROJECT", info.getId().toString());
	uiContext.put("LIST_UI",
			"com.kingdee.eas.fdc.invite.client.InviteSupplierEnterBillEditUI");
	uiContext.put("INVITEPROJECT_NAME", null);

	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
			.create(InviteAllInformationUI.class.getName(), uiContext,
					null, OprtState.ADDNEW);
	uiWindow.show();
}
}
