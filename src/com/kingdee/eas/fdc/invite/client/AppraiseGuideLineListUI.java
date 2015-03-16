/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class AppraiseGuideLineListUI extends AbstractAppraiseGuideLineListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppraiseGuideLineListUI.class);
    private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
    
    /**
     * output class constructor
     */
    public AppraiseGuideLineListUI() throws Exception
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
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
    }

    /**
     * output chkIncludeChild_itemStateChanged method
     */
    protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.chkIncludeChild_itemStateChanged(e);
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getTypeSelectedTreeNode()!=null&&getTypeSelectedTreeNode().getUserObject() instanceof AppraiseGuideLineTypeInfo
				&& ((AppraiseGuideLineTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
			this.getUIContext().put("type", getTypeSelectedTreeNode().getUserObject());
		}else{
			FDCMsgBox.showInfo(this,"请先选择明细类别，再进行操作");
			abort();
		}
    	super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
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
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
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
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);   
        this.refresh(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
        cancelCancel();
        this.refresh(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionGroupAddNew_actionPerformed
     */
    public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getTypeSelectedTreeNode().getUserObject() instanceof AppraiseGuideLineTypeInfo&&((AppraiseGuideLineTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("guideLineType.id",((AppraiseGuideLineTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
    		if(this.getBizInterface().exists(filter)){
    			FDCMsgBox.showWarning(this,"明细类别已经有数据，不能进行此操作");
    			abort();
    		}
    	}
        super.actionGroupAddNew_actionPerformed(e);
    }

    /**
     * output actionGroupView_actionPerformed
     */
    public void actionGroupView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupView_actionPerformed(e);
    }

    /**
     * output actionGroupEdit_actionPerformed
     */
    public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupEdit_actionPerformed(e);
    }

    /**
     * output actionGroupRemove_actionPerformed
     */
    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getTypeSelectedTreeNode().getUserObject() instanceof AppraiseGuideLineTypeInfo){
    		if(((AppraiseGuideLineTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
        		filter.getFilterItems().add(new FilterItemInfo("guideLineType.id",((AppraiseGuideLineTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		if(this.getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,"明细类别已经有数据，不能进行此操作");
        			abort();
        		}
    		}
    		else{
    			FDCMsgBox.showWarning(this,"非明细节点，不能执行此操作");
    			abort();
    		}
    	}
        super.actionGroupRemove_actionPerformed(e);
    }

    /**
     * output actionGroupMoveTree_actionPerformed
     */
    public void actionGroupMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupMoveTree_actionPerformed(e);
    }

    /**
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }

	protected IObjectPK getSelectedTreeKeyValue() {
		AppraiseGuideLineInfo apprGLInfo = null;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());
		if (detail == null) {
			return null;
		}
		try {
			apprGLInfo = (AppraiseGuideLineInfo) getBizInterface().getValue(detail);
		} catch (Exception e) {
			handUIException(e);
		}
		if (apprGLInfo.getGuideLineType() == null
				|| apprGLInfo.getGuideLineType().getId() == null) {
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
			abort();
		}
		return new ObjectUuidPK(apprGLInfo.getGuideLineType().getId());
	}

	protected String getGroupEditUIName() {
		// TODO 自动生成方法存根
		return AppraiseGuideLineTypeEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		// TODO 自动生成方法存根
		return "guideLineType.id";
	}

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO 自动生成方法存根
		return AppraiseGuideLineTypeFactory.getRemoteInstance();
	}
	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return AppraiseGuideLineFactory.getRemoteInstance();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
	}
	protected String getEditUIModal(){
		return UIFactoryName.MODEL;
	}
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
				.getLastSelectedPathComponent();
	}
	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return AppraiseGuideLineEditUI.class.getName();
	}
	protected String getRootName()
    {
        return "评标指标类型";
    }
	public void onLoad() throws Exception{
		if(!currentOrg.isIsCompanyOrgUnit()){
			FDCMsgBox.showInfo("非财务组织不能进入!");
			abort();
		}
		super.onLoad();
		boolean canAdd = false;
		
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.actionAddNew.setEnabled(canAdd);
		this.actionRemove.setEnabled(canAdd);
		this.actionEdit.setEnabled(canAdd);
		this.actionCancel.setEnabled(canAdd);
		this.actionCancelCancel.setEnabled(canAdd);
		this.actionCancel.setVisible(canAdd);
		this.actionCancelCancel.setVisible(canAdd);
		
		this.actionGroupAddNew.setEnabled(canAdd);
		this.actionGroupEdit.setEnabled(canAdd);
		this.actionGroupMoveTree.setEnabled(false);
		this.actionGroupMoveTree.setVisible(false);
		this.actionGroupRemove.setEnabled(canAdd);
		
		this.actionMoveTree.setEnabled(false);
		this.actionMoveTree.setVisible(false);
		
//		FDCHelper.formatTableDate(tblMain, "createTime");
//		FDCHelper.formatTableDate(tblMain, "lastUpdateTime");
		
	}

}