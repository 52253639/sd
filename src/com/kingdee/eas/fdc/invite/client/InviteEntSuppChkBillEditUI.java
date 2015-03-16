/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDContainer;
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
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillCollection;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillEntryInfo;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillFactory;
import com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 入围单位审批单 编辑
 */
public class InviteEntSuppChkBillEditUI extends AbstractInviteEntSuppChkBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteEntSuppChkBillEditUI.class);
    private Map supplierMap = null;
    private boolean isHasAttachment = false;
    private String inviteProjectId = null;

    public InviteEntSuppChkBillEditUI() throws Exception
    {
        super();
    }
    
    private void disableAction(){
      this.actionCancel.setVisible(false);
      this.actionCancelCancel.setVisible(false);
      this.actionAudit.setVisible(false);
      this.actionUnAudit.setVisible(false);
      this.actionCopy.setVisible(false);
      this.actionCopyFrom.setVisible(false);
      this.actionCopyLine.setVisible(false);
      this.actionCreateFrom.setVisible(false);
      this.actionCreateTo.setVisible(false);
      this.actionDelVoucher.setVisible(false);
      this.actionVoucher.setVisible(false);
      this.actionTraceDown.setVisible(false);
      this.actionTraceUp.setVisible(false);
      this.actionPre.setVisible(false);
      this.actionLast.setVisible(false);
      this.actionFirst.setVisible(false);
      this.actionNext.setVisible(false);
      this.actionAudit.setVisible(false);
      this.menuBiz.setVisible(false);
      this.menuView.setVisible(false);
    }
    
    private void addButton(KDContainer contrainer){
    	contrainer.addButton(this.btnAddLine);
    	contrainer.addButton(this.btnInsertLine);
    	contrainer.addButton(this.btnRemoveLine);
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	checkTableParsed();
    	refreshUniqueMap();
    	if(editData.getState() != null && editData.getState().equals(FDCBillStateEnum.AUDITTED) || !editData.getOrgUnit().getId().equals(SysContext.getSysContext().getCurrentOrgUnit().getId()) ){
    		this.actionEdit.setEnabled(false);
    		this.actionRemove.setEnabled(false);
    		this.actionAddLine.setEnabled(false);
    		this.actionRemoveLine.setEnabled(false);
    		this.actionInsertLine.setEnabled(false);
    	
    	}
    	if(editData.getState() != null && editData.getState().equals(FDCBillStateEnum.AUDITTED) || !editData.getOrgUnit().getId().equals(SysContext.getSysContext().getCurrentOrgUnit().getId()) ){
    		this.actionEdit.setEnabled(false);
    		this.actionRemove.setEnabled(false);
    		this.actionAddLine.setEnabled(false);
    		this.actionRemoveLine.setEnabled(false);
    		this.actionInsertLine.setEnabled(false);
    		
    	}
    	if(getOprtState().equals(OprtState.VIEW)){
    		this.actionCopyLine.setEnabled(false);
    		this.actionAddLine.setEnabled(false);
    		this.actionRemoveLine.setEnabled(false);
    		this.actionInsertLine.setEnabled(false);
    	}

    	addButton(this.kDContainer1);
    	
    	disableAction();
    	FDCClientUtils.setRespDeptF7(prmtHandlerDept, this, null);
    	FDCClientUtils.setPersonF7(prmtHandlerPerson, this,null);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("*"));
    	sic.add(new SelectorItemInfo("inviteType.*"));
    	sic.add(new SelectorItemInfo("respPerson.*"));
    	sic.add(new SelectorItemInfo("project.*"));
    	prmtInviteProject.setSelectorCollection(sic);
    	prmtInviteProject.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj.getNewValue() != null){
					InviteProjectInfo project = (InviteProjectInfo) eventObj.getNewValue();
					if(project.getNumber() != null){
						// 修改为名称带出编码 modify by msb 2010/04/27
						txtInviteProjectNumber.setText(project.getNumber());
					}
					if(project.getInviteType() != null && project.getInviteType().getName() != null){
						txtInviteType.setText(project.getInviteType().getName());
					}
					/*if(project.getProject()!=null && project.getProject().getName()!= null){
						txtCurProject.setText(project.getProject().getName());
					}*/
					if(project.getRespPerson() != null && project.getRespPerson().getName() != null ){
						txtInviteProjectRespPerson.setText(project.getRespPerson().getName());
					}
				}else{
					txtInviteProjectNumber.setText(null);
					txtInviteType.setText(null);
					txtCurProject.setText(null);
					txtInviteProjectRespPerson.setText(null);
				}
			}    		
    	});
    	
    	prmtInviteProject.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				EntityViewInfo evi  = null;
				FilterInfo filter = null;
				Set usedInviteProject = new HashSet();
				try {
					InviteEntSuppChkBillCollection cols = InviteEntSuppChkBillFactory.getRemoteInstance().getInviteEntSuppChkBillCollection();
				    for(Iterator it = cols.iterator();it.hasNext();){
				    	InviteEntSuppChkBillInfo info = (InviteEntSuppChkBillInfo) it.next();
				    	if(info.getInviteProject() != null){
				    		usedInviteProject.add(info.getInviteProject().getId().toString());
				    	}
				    }
				} catch (BOSException e1) {
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
    	fillAttachmentList();
    	this.btnViewAttachment.setEnabled(isHasAttachment);
    	this.actionInviteExecuteInfo.setEnabled(true);
    	
    	//add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		this.txtName.setMaxLength(255);
		this.txtNumber.setMaxLength(255);
    }
    /**
     * output storeFields method
     */
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
    
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception {
    	  String attachId = null;
      	if(this.cmbAttachmentList.getSelectedItem() != null && this.cmbAttachmentList.getSelectedItem() instanceof AttachmentInfo){
      		attachId = ((AttachmentInfo)this.cmbAttachmentList.getSelectedItem()).getId().toString();
      		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
      		acm.viewAttachment(attachId);
      	}
    }
  
   public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.cmbAttachmentList.removeAllItems();
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
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
		
		// modify liuguangyue 2010-4-27
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info, this, editData, String
				.valueOf("1"), isEdit);
        fillAttachmentList();
    }

    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	mustSelectInviteProjectFirst();
        super.actionAddLine_actionPerformed(e);
    }

    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    	mustSelectInviteProjectFirst();
        super.actionInsertLine_actionPerformed(e);
        refreshUniqueMap();
    }

    private void refreshUniqueMap(){
    	if(editData.getEntry() != null ){
    		for(int i=0;i<editData.getEntry().size();i++){
    			if(supplierMap == null ){
    				supplierMap = new HashMap();
    			}
    			if(editData.getEntry().get(i).getSupplier() != null){
    				supplierMap.put(editData.getEntry().get(i).getSupplier().getNumber(), editData.getEntry().get(i).getSupplier().getNumber());
    			}
    		}
    	}
    }

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}
	
    protected IObjectValue createNewData() {
    	InviteEntSuppChkBillInfo billInfo = new InviteEntSuppChkBillInfo();
    	billInfo.setState(FDCBillStateEnum.SAVED);
    	billInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
    	return billInfo;
    }
    
    protected IObjectValue createNewDetailData(KDTable table) {
    	return new InviteEntSuppChkBillEntryInfo();
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		return InviteEntSuppChkBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntry;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void verifyInputForSave() throws Exception {
		 ICodingRuleManager codingManager =getEncodingRule();
		    
		 if (this.editData != null) {
			boolean isUseCodingRule = codingManager.isExist(this.editData, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			if (!isUseCodingRule) {
				if (StringUtils.isEmpty(editData.getNumber()) || editData.getNumber().trim().length() > 100) {
					FDCMsgBox.showError("编号为空或者超出系统约定的长度！");
					abort();
				}
			}
		}
		
		if(StringUtils.isEmpty(editData.getName()) || editData.getName().trim().length() > 100){
			FDCMsgBox.showError("名称为空或者超出系统约定的长度！");
			abort();
		}
		
		if(editData.getHandlerPerson()== null){
			FDCMsgBox.showError("经办人不能为空！");
			abort();
		}
		
		if(editData.getInviteProject() == null ){
			FDCMsgBox.showError("招标立项不能为空！");
			abort();
		}
		if(editData.getRemark() != null && editData.getRemark().length() > 255){
			FDCMsgBox.showError("备注超出系统约定的长度(255)！");
			abort();
		}
		
		if(getDetailTable().getRowCount() == 0){
			FDCMsgBox.showError("分录为空！");
			abort();
		}
		
		verifyEntryEmpty();
		verifyEntrySupplier();
	}
	
	
	protected void verifyInputForSubmint() throws Exception {
		ICodingRuleManager codingManager = getEncodingRule();
		if (this.editData != null) {
			boolean isUseCodingRule = codingManager.isExist(this.editData, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			if (!isUseCodingRule) {
				if (StringUtils.isEmpty(editData.getNumber()) || editData.getNumber().trim().length() > 100) {
					FDCMsgBox.showError("编号为空或者超出系统约定的长度！");
					abort();
				}
			}
		}
			
		if(StringUtils.isEmpty(editData.getName()) || editData.getName().trim().length() > 100){
			FDCMsgBox.showError("名称为空或者超出系统约定的长度！");
			abort();
		}
		
		if(editData.getHandlerPerson() == null){
			FDCMsgBox.showError("经办人不能为空！");
			abort();
		}
		if(editData.getInviteProject() == null ){
			FDCMsgBox.showError("招标立项不能为空！");
			abort();
		}
		
		if(editData.getRemark() != null && editData.getRemark().length() > 255){
			FDCMsgBox.showError("备注超出系统约定的长度(255)！");
			abort();
		}
		
		if(getDetailTable().getRowCount() == 0){
			FDCMsgBox.showError("分录为空！");
			abort();
		}		
		
		verifyEntryEmpty();
		verifyEntrySupplier();		
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("entry.supplier.*"));
		sic.add(new SelectorItemInfo("entry.*"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		sic.add(new SelectorItemInfo("inviteProject.respPerson.*"));
		return sic;
	}
	
	public ICodingRuleManager getEncodingRule() {
	   	ICodingRuleManager codingRuleManager = null;
		try {
			if (this.getUIContext().get("codingRuleManager") == null) {
				codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
				this.getUIContext().put("codingRuleManager", codingRuleManager);
			} else {
				codingRuleManager = (ICodingRuleManager) this.getUIContext().get("codingRuleManager");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return codingRuleManager;
	}
	
	private void verifyEntryEmpty(){
        int count = 0;	
        StringBuffer str = new StringBuffer("分录中存在以下错误：\n");
        int errCount = 0 ;
		for(int i=0;i<editData.getEntry().size();i++){
			if(editData.getEntry().get(i).getSupplier() == null){
					count++;
			}
			if(editData.getEntry().get(i).getLinkman()!= null && editData.getEntry().get(i).getLinkman().length() > 100){
				errCount++;
				str.append("第");
				str.append(i+1);
				str.append("行联系人超过系统约定长度!\n");
			}
			if(editData.getEntry().get(i).getPhone() != null && editData.getEntry().get(i).getPhone().length() > 100){
				errCount++;
				str.append("第");
				str.append(i+1);
				str.append("行联系电话超过系统约定长度!\n");
			}
			if(editData.getEntry().get(i).getProvider() != null && editData.getEntry().get(i).getProvider().length() > 100){
				errCount++;
				str.append("第");
				str.append(i+1);
				str.append("行供方来源超过系统约定长度!\n");
			}
			if(editData.getEntry().get(i).getSiteInspection() != null && editData.getEntry().get(i).getSiteInspection().length() > 100){
				errCount++;
				str.append("第");
				str.append(i+1);
				str.append("行是否经过现场考察超过系统约定长度!\n");
			}
			if(editData.getEntry().get(i).getSameProjct()!= null && editData.getEntry().get(i).getSameProjct().length() > 100){
				errCount++;
				str.append("第");
				str.append(i+1);
				str.append("行类似项目业绩超过系统约定长度!\n");
			}
		}
		
		if(errCount > 0 ){
			FDCMsgBox.showError(str.toString());
			abort();
		}
		
		if(count>0){
			FDCMsgBox.showError("分录中供应商不能为空！");
			abort();
		}
	}
	
	
	public void verifyEntrySupplier() {
		if (supplierMap != null && !supplierMap.isEmpty()) {
			supplierMap.clear();
		}
		refreshUniqueMap();
		if (editData.getEntry() != null) {
			if (editData.getEntry().size() > supplierMap.size()) {

				FDCMsgBox.showError("分录中的供应商有重复记录，请检查！");
				abort();
			}
		}
	}
	
	protected void lockContainer(Container container) {
		if("conAttachmentList".equalsIgnoreCase(container.getName())){
			return;	
		}
		super.lockContainer(container);
	}
	
	public void actionInviteExecuteInfo_actionPerformed(ActionEvent e)
			throws Exception {
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
		uiContext.put("LIST_UI", "com.kingdee.eas.fdc.invite.client.InviteEntSuppChkBillEditUI");
		uiContext.put("INVITEPROJECT_NAME", null);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(InviteAllInformationUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * 
	 * 描述：初始化供应商F7，保持与供应商投标记录一致，过滤结果都是通过供应商资格预审的供应商。
	 * @throws BOSException 
	 * @Author：owen_wen
	 * @CreateTime：2011-10-26
	 */
	private void initSupplierF7() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();

		KDBizPromptBox supplierF7 = new KDBizPromptBox();
		supplierF7.setSelectorCollection(getSupplierSelector());

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", InviteClientHelper.getQualifiedSupplierIDs(inviteProjectId), CompareType.INCLUDE));
		view.setFilter(filter);
		supplierF7.setEntityViewInfo(view);
		supplierF7.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(supplierF7);
		kdtEntry.getColumn("supplier").setEditor(editor);
	}

	private SelectorItemCollection getSupplierSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("simpleName"));

		sic.add(new SelectorItemInfo("industry.name"));
		sic.add(new SelectorItemInfo("address"));
		sic.add(new SelectorItemInfo("isInternalCompany"));

		sic.add(new SelectorItemInfo("taxRegisterNo"));
		sic.add(new SelectorItemInfo("bizRegisterNo"));
		sic.add(new SelectorItemInfo("artificialPerson"));

		sic.add(new SelectorItemInfo("usedStatus"));
		sic.add(new SelectorItemInfo("effectedStatus"));
		sic.add(new SelectorItemInfo("description"));

		sic.add(new SelectorItemInfo("internalCompany.number"));
		sic.add(new SelectorItemInfo("internalCompany.name"));

		return sic;
	}
	
	protected void prmtInviteProject_dataChanged(DataChangeEvent e) throws Exception {
		if (prmtInviteProject.getValue() != null) {
			inviteProjectId = ((InviteProjectInfo) prmtInviteProject.getValue()).getId().toString();
			initSupplierF7();
		}
		kdtEntry.removeRows(false);
	}
	
	
	private void mustSelectInviteProjectFirst() {
		if (prmtInviteProject.getValue() == null) {
			FDCMsgBox.showInfo(this, "请先选择招标立项。");
			prmtInviteProject.requestFocus(true);
			this.abort();
		}
	}
}