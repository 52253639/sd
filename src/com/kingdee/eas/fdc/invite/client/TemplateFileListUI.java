/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.InviteFileItemFactory;
import com.kingdee.eas.fdc.invite.TemplateFileFactory;
import com.kingdee.eas.fdc.invite.TemplateFileInfo;
import com.kingdee.eas.fdc.invite.TemplateTypeCollection;
import com.kingdee.eas.fdc.invite.TemplateTypeFactory;
import com.kingdee.eas.fdc.invite.TemplateTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TemplateFileListUI extends AbstractTemplateFileListUI
{
	private static final Logger logger = CoreUIObject.getLogger(TemplateFileListUI.class);

	private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
	
	private final String COL_ID = "id";
	private final String COL_STATE = "state";
	private final String COL_CREATE_TIME = "creatorDate";
	private final String COL_AUDIT_DATE = "auditTime";
	
	private final String COL_TEMPLATETYPE_ID = "templateType.id";
	
	private final static String GroupOperate="GroupOperate";
	/**
	 * output class constructor
	 */
	public TemplateFileListUI() throws Exception
	{
		super();
	}
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
	{
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
	{
		super.tblMain_tableSelectChanged(e);
		
		reSetAuditBtnState();
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
	{
		DefaultKingdeeTreeNode typeNode = getSelectedTreeNode();
		Object templateType = null ;
		if(typeNode != null)
		{
			templateType = typeNode.getUserObject();
		}
		
		//评标模板文件类型
		FilterInfo typeFilter = new FilterInfo();
		if(templateType != null && (templateType instanceof com.kingdee.eas.fdc.invite.TemplateTypeInfo))
		{
			TemplateTypeInfo typeInfo = (TemplateTypeInfo)templateType;
			BOSUuid id = typeInfo.getId();

			Set idSet = getTemplateTypeIdSet(id);
			typeFilter.getFilterItems().add(new FilterItemInfo("templateType.id", idSet, CompareType.INCLUDE));
		}

		mainQuery.setFilter(typeFilter);
		execQuery();

		if(getMainTable().getRowCount() > 0)
		{
			getMainTable().getSelectManager().select(0, 0);
		}

		reSetAuditBtnState();
	}
	
	 protected void refresh(ActionEvent e) throws Exception
	    {
		 super.refresh(e);
		 this.btnAttachment.setVisible(true);
			this.btnAttachment.setEnabled(true);
			this.actionAttachment.setVisible(true);
			this.actionAttachment.setEnabled(true);
	    }
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(getTypeSelectedTreeNode().getUserObject() instanceof TemplateTypeInfo&&((TemplateTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf())
		{
			super.actionAddNew_actionPerformed(e);
		}else{
			FDCMsgBox.showWarning(this,"请选择明细节点新增数据");
		}
		
	}

	public void actionGroupAddNew_actionPerformed(ActionEvent e)
	throws Exception {
		if(getTypeSelectedTreeNode().getUserObject() instanceof TemplateTypeInfo&&((TemplateTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("templateType.id",((TemplateTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
			if(this.getBizInterface().exists(filter)){
				FDCMsgBox.showWarning(this,"明细类型已经有数据，不能进行此操作");
				abort();
			}
		}
//		super.actionGroupAddNew_actionPerformed(e);
		
		checkTreeNodeSelected(e);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put(GroupOperate,"false");

		if(getTypeSelectedTreeNode()!=null){
			uiContext.put("FILE_TYPE", getTypeSelectedTreeNode().getUserObject());
		}

		// 供子类定义要传递到EditUI中扩展的属性
		prepareGroupUIContext(uiContext, e);

		IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext,
				null, OprtState.ADDNEW);
		uiWindow.show();

		// 如果编辑界面以模式窗口方式打开，则刷新界面
		setActionEvent(e);
		// if (getGroupEditUIModal().equals(UIFactoryName.MODEL))
		if (isDoRefresh(uiWindow,getGroupEditUIModal()))
		{
			refresh(e);
		}
	}
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
		.getLastSelectedPathComponent();
	}

	public void onLoad() throws Exception {

		if (currentOrg == null || !currentOrg.isIsCompanyOrgUnit()) {
			MsgBox.showInfo("非财务组织不能进入!");
			this.abort();
		}
		
		super.onLoad();
		this.btnQuery.setVisible(false);

		this.tblMain.getColumn(this.COL_ID).getStyleAttributes().setHided(true);
		tblMain.getColumn(COL_TEMPLATETYPE_ID).getStyleAttributes().setHided(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		treeMain.setRootVisible(true);
		treeMain.expandRow(0);

		boolean canAdd = false;
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		this.actionAddNew.setEnabled(canAdd);
		this.actionRemove.setEnabled(canAdd);
		this.actionEdit.setEnabled(canAdd);
		this.actionCancel.setEnabled(canAdd);
		this.actionCancelCancel.setEnabled(canAdd);
		this.actionCancel.setVisible(canAdd);
		this.actionCancelCancel.setVisible(canAdd);

		this.actionGroupAddNew.setEnabled(canAdd);
		this.actionGroupEdit.setEnabled(canAdd);
		this.actionGroupMoveTree.setEnabled(canAdd);
		this.actionGroupRemove.setEnabled(canAdd);
		
		this.actionAudit.setEnabled(canAdd);
		this.actionUnAudit.setEnabled(canAdd);
		
		FDCHelper.formatTableDate(tblMain, this.COL_CREATE_TIME);
		FDCHelper.formatTableDate(tblMain, this.COL_AUDIT_DATE);
		
		this.chkIncludeChild.setVisible(false);
		this.chkIncludeChild.setEnabled(false);
		this.actionMoveTree.setEnabled(false);
		this.actionMoveTree.setVisible(false);
		this.actionGroupMoveTree.setVisible(false);
		
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.menuItemGroupView.setIcon(EASResource.getIcon("imgTree_view"));
		this.menuItemGroupEdit.setIcon(EASResource.getIcon("imgTree_edit"));
		this.menuItemGroupRemove.setIcon(EASResource.getIcon("imgTree_delete"));
		
		reSetAuditBtnState();
		
		this.menuItemSendMessage.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.pnlMain.setDividerLocation(300);
		
		this.btnAttachment.setVisible(true);
		this.btnAttachment.setEnabled(true);
		this.actionAttachment.setVisible(true);
		this.actionAttachment.setEnabled(true);
	}
	  
	protected String getEditUIName() {
		return TemplateFileEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return TemplateFileFactory.getRemoteInstance();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		if(getTypeSelectedTreeNode()!=null){
			uiContext.put("TEMPLATE_TYPE", getTypeSelectedTreeNode().getUserObject());
		}
		
		boolean canAdd = false;
		
		OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		
        uiContext.put("CAN_ADD", Boolean.valueOf(canAdd));
	}	
	protected String getGroupEditUIName() {
		return TemplateTypeEditUI.class.getName();
	}
	protected String getQueryFieldName() {
		String fieldName = "templateType.id";
		return fieldName;
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		TemplateFileInfo templateFileInfo = null ;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());

		if(detail == null)
		{
			return null ;
		}
		try
		{
			templateFileInfo = (TemplateFileInfo)getBizInterface().getValue(detail);
		}catch(Exception e)	{
			handUIException(e);
		}
		if (templateFileInfo.getTemplateType() == null
				|| templateFileInfo.getTemplateType().getId() == null) {
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
			abort();
		}

		return new ObjectUuidPK(templateFileInfo.getTemplateType().getId()); 

	}

	protected String getRootName() {
		String rootName = "文件模板类型";
		return rootName;
	}
	private Set getTemplateTypeIdSet(BOSUuid id) throws EASBizException, BOSException
	{
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		TemplateTypeInfo parentTypeInfo = TemplateTypeFactory.getRemoteInstance().getTemplateTypeInfo(pk);

		String longNumber = parentTypeInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber+"!%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));

		filter.setMaskString("#0 or #1");
		view.setFilter(filter);

		TemplateTypeCollection typeCols = TemplateTypeFactory.getRemoteInstance().getTemplateTypeCollection(view);

		Iterator iter = typeCols.iterator();
		while(iter.hasNext())
		{
			TemplateTypeInfo tmp = (TemplateTypeInfo)iter.next();
			idSet.add(tmp.getId());
		}

		return idSet ;
	}
	
	protected ITreeBase getTreeInterface() throws Exception {
		return TemplateTypeFactory.getRemoteInstance();
	}
	private void reSetAuditBtnState()
	{
		boolean canAdd = false;

		OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		
		if(!canAdd)
		{
			return ;
		}
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();

		if(rowIndex < 0)
		{
			return ;
		}
			
		IRow row = tblMain.getRow(rowIndex);

		//判断当前状态，更新按钮状态
		if(FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(true);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAddNew.setEnabled(true);

			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		else if(FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(true);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAddNew.setEnabled(true);

			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(false);
		}
		else if(FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(false);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(false);
			this.actionAddNew.setEnabled(true);

			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(true);
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		if(!checkTemplateState(getStateForAudit()))
		{
			FDCMsgBox.showWarning(this,"非保存状态不能审批");
			abort();
		}
		List idList =ContractClientUtils.getSelectedIdValues(this.getMainTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			if(idList.size() > 0 && idList.get(0) != null)
			{
				BOSUuid billId = BOSUuid.read(idList.get(0).toString());
				TemplateFileFactory.getRemoteInstance().audit(billId);
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		showOprtOKMsgAndRefresh();
		
		if (isDoRefresh(uiWindow,getGroupEditUIModal()))
		{
			refresh(e);
		}
	}
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}

	private boolean checkTemplateState(String state)
	{
		boolean flag = false ;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex() ;
		IRow row = tblMain.getRow(rowIndex);

		if(row.getCell(COL_STATE).getValue() != null)
		{
			if(state.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
			{
				flag = true ;
			}
			else
			{
				flag = false ;
			}
		}

		return flag ;
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception 
	{
		checkSelected();

		if(!checkTemplateState(getStateForUnAudit()))
		{
			FDCMsgBox.showWarning(this,"非审批状态不能反审批");
			abort();
		}
		
		//引用检测,待需求确认
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex() ;
		IRow row = tblMain.getRow(rowIndex);

		if(row.getCell(COL_ID).getValue() != null)
		{
			if(checkTemplateReference(row.getCell(COL_ID).getValue().toString()))
			{
				FDCMsgBox.showWarning(this,"该文件模板已经被引用，不能反审批");
				abort();
			}
		}
		
		List idList =ContractClientUtils.getSelectedIdValues(this.getMainTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "UnAudit");
			if(idList.size() > 0 && idList.get(0) != null)
			{
				BOSUuid billId = BOSUuid.read(idList.get(0).toString());
				TemplateFileFactory.getRemoteInstance().unAudit(billId);
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		showOprtOKMsgAndRefresh();
		
		if (isDoRefresh(uiWindow,getGroupEditUIModal()))
		{
			refresh(e);
		}
	}
	
	public void actionGroupRemove_actionPerformed(ActionEvent e)
			throws Exception {
		if(getTypeSelectedTreeNode().getUserObject() instanceof TemplateTypeInfo){
    		if(((TemplateTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
        		filter.getFilterItems().add(new FilterItemInfo("templateType.id",((TemplateTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		if(getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,"明细类型已经有数据，不能进行此操作");
        			abort();
        		}
    		}
    		else{
    			FDCMsgBox.showWarning(this,"非明细节点，不能进行此操作");
    			abort();
    		}
    	}
		super.actionGroupRemove_actionPerformed(e);
		
		reSetAuditBtnState();
	}
	
	public void actionGroupView_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);

		boolean canAdd = false;
		
		OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		
        UIContext uiContext = new UIContext(this);
        uiContext.put(UIContext.ID, getSelectedNodeKeyValue());
        uiContext.put(GroupOperate,"false");

        uiContext.put("CAN_ADD", Boolean.valueOf(canAdd));
        // 供子类定义要传递到EditUI中扩展的属性
        prepareGroupUIContext(uiContext, e);

        // 创建UI对象并显示
        IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext,
                null, OprtState.VIEW);
        uiWindow.show();
        // 查看后无需刷新
        setActionEvent(e);
        if (isDoRefresh(uiWindow,getGroupEditUIModal()))
        {
            refresh(e);
        }
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		
		reSetAuditBtnState();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionView_actionPerformed(e);
	}
	
	protected boolean checkTemplateReference(String paramTemplateId) throws EASBizException, BOSException
	{
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("fileTemplate.id", paramTemplateId);
		
		return InviteFileItemFactory.getRemoteInstance().exists(filter);
		
	}
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionLocate_actionPerformed(e);
	}
	
	 public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
	    {
	        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        String boID = this.getSelectedKeyValue();
	        checkSelected();
	        
	        if (boID == null)
	        {
	            return;
	        }
	        boolean isEdit = false;
	        
	        IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
	        BizEnumValueInfo enumv = (BizEnumValueInfo) row.getCell("state").getValue();
	        if(null!=enumv){
	        	if( FDCBillStateEnum.SAVED_VALUE.equals(enumv.getValue())||
	 	    		   FDCBillStateEnum.SUBMITTED_VALUE.equals(enumv.getValue())){
	 	    	   isEdit = true;
	 	       }
	        }
	       
//	        if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
//	        {
//	            isEdit = true;
//	        }
//	        acm.showAttachmentListUIByBoID(boID, this); // boID 是 与附件关联的 业务对象的 ID
	        acm.showAttachmentListUIByBoID(boID, "", this, isEdit, null);
	    }

}