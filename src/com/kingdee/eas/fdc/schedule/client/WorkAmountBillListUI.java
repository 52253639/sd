/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskSynchronize;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.WorkAmountBillFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class WorkAmountBillListUI extends AbstractWorkAmountBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(WorkAmountBillListUI.class);
    
    /**
     * output class constructor
     */
    
   
    public WorkAmountBillListUI() throws Exception
    {
        super();
    }
    
    public boolean isBaseOnTask(){
    	boolean isBaseOnTask = true;
    	try {
			Map paramMap = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			if(paramMap.get(FDCConstants.FDCSCH_PARAM_BASEONTASK) != null){
				isBaseOnTask = Boolean.valueOf(paramMap.get(FDCConstants.FDCSCH_PARAM_BASEONTASK).toString()).booleanValue();
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return isBaseOnTask;
    }
   
    private void registerSynchronizeTaskKey(final CoreUIObject uiObject) {
		String actionName = "TaskSynchronizeSQLExecute";
		this.getActionMap().put(actionName, new javax.swing.AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int ret = FDCMsgBox.showConfirm2(uiObject,
						"您将要同步任务中的完成百分比和完成时间信息, 这需要几分钟时间，确定现在开始？");
				if (ret == FDCMsgBox.OK) {
					try {

						FDCScheduleTaskSynchronize.modifyByWorkAmount(null, null);
						FDCMsgBox.showInfo(uiObject, "恭喜您，同步任务完成。 :)");
					} catch (BOSException e1) {
						uiObject.handUIException(e1);
					}
				}

			}
		});
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ctrl shift F12"), actionName);
	}
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
        if(!isBaseOnTask()){
        	FDCMsgBox.showError("未启用参数：\n“基于任务填报工程量”的严格控制模式！");
        	abort();
        }
       
    	super.onLoad();
    	SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
    	ProjectTreeBuilder builder = new ProjectTreeBuilder();
    	builder.build(this,this.projectTree, actionOnLoad);
    	this.btnAudit.setEnabled(true);
    	this.btnUnAudit.setEnabled(true);
    	initButtonStyle();
    	projectTree.setShowsRootHandles(true);
    	setUITitle("任务工程量填报");
    	mainSplitPanel.setDividerLocation(280);
    
    	 //增加KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(projectTree);
		treeView.setShowButton(true);
		treeView.setTitle("工程项目");
		mainSplitPanel.add(treeView,"left");
		treeView.setShowControlPanel(true);
		registerSynchronizeTaskKey(this);
    }
    
    public void initButtonStyle(){
        this.actionQuery.setVisible(false);
        this.actionTraceDown.setVisible(false);
        this.actionTraceUp.setVisible(false);
        this.actionLocate.setVisible(false);
        this.actionAttachment.setVisible(false);
        this.actionCopyTo.setVisible(false);
        this.actionCreateTo.setVisible(false);
        
        this.menuItemAudit.setEnabled(true);
        this.menuUtemUnAudit.setEnabled(true);
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

    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	// TODO Auto-generated method stub
    	super.prepareUIContext(uiContext, e);
    	if(e.getActionCommand().endsWith("AddNew")) 
    	{
        CurProjectInfo curProject = getSelectProject();
        uiContext.put("projectInfo", curProject);
        }
    }
    
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	CurProjectInfo projectInfo  = getSelectProject();
    	if(projectInfo != null && projectInfo.isIsLeaf()){
    		
    		super.actionAddNew_actionPerformed(e);
    		 
    	}else{
    		 FDCMsgBox.showError("请选择明细的工程项目！");
    		 abort();
    	}
    	
       
    }

    protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = null;
		CurProjectInfo project = null;
		node = (DefaultKingdeeTreeNode) projectTree.getLastSelectedPathComponent();
		if(node != null && node.getUserObject() instanceof CurProjectInfo ){
			project = (CurProjectInfo) node.getUserObject();
		}
		return project;
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
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex == -1){
    		FDCMsgBox.showError("请选择行!");
    		abort();
    	}else{
    		String state = null;
    		String id  = (String) tblMain.getCell(rowIndex,"id").getValue();
    		FDCSQLBuilder builder = new FDCSQLBuilder();
    		builder.appendSql("select fstate from t_sch_workamountbill where fid =?");
    		builder.addParam(id);
    		RowSet rs =builder.executeQuery();
    		while(rs.next()){
    			state = rs.getString(1);
    		}
    		if(state!=null && state.equals(FDCBillStateEnum.AUDITTED_VALUE)){
    			FDCMsgBox.showError("当前单据的状态为已审批状态，不能执行此操作！");
        		abort();
    		}
    	}
    	
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex == -1){
    		FDCMsgBox.showError("请选择行!");
    		abort();
    	}else{
    		String state = null;
    		String id  = (String) tblMain.getCell(rowIndex,"id").getValue();
    		FDCSQLBuilder builder = new FDCSQLBuilder();
    		builder.appendSql("select fstate from t_sch_workamountbill where fid =?");
    		builder.addParam(id);
    		RowSet rs =builder.executeQuery();
    		while(rs.next()){
    			state = rs.getString(1);
    		}
    		if(state!=null && state.equals(FDCBillStateEnum.AUDITTED_VALUE)){
    			FDCMsgBox.showError("当前单据的状态为已审批状态，不能执行此操作！");
        		abort();
    		}
    	}
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
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
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
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
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
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
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
    
    
  
    protected DefaultKingdeeTreeNode getSelectNode(KDTree tree){
		  DefaultKingdeeTreeNode node = null;
		 if(tree.getLastSelectedPathComponent() != null){
			node = (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent(); 
		 }
		  return node;
	  }  
    
    
    protected Set getSelectedProjectIds(){
		   Set idSet = new HashSet();
		   DefaultKingdeeTreeNode node = getSelectNode(projectTree);
		   if(node != null){
			   getLeafIds(node, idSet);
		   }
		   return idSet;
	   }
    
    
    protected FilterInfo getMainFilter(){
  	   FilterInfo filter = new FilterInfo();
  	   Set projectSet = new HashSet();
  	   projectSet = getSelectedProjectIds();
  	   if(projectSet.size()>0)
  	   filter.getFilterItems().add(new FilterItemInfo("project.id",projectSet,CompareType.INCLUDE));
  	   filter.getFilterItems().add(new FilterItemInfo("cu.longnumber",SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber()+"%",CompareType.LIKE));
  	   filter.getFilterItems().add(new FilterItemInfo("orgUnit.longnumber",SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
  	   return filter;
     } 
    
    
    private void getLeafIds(DefaultKingdeeTreeNode node,Set idSet){
		   if(node.isLeaf()){
			   if(node.getUserObject() instanceof CurProjectInfo){
				   idSet.add(((CurProjectInfo)node.getUserObject()).getId().toString());
			   }
			   if(node.getUserObject() instanceof OrgStructureInfo){
				   idSet.add(((OrgStructureInfo)node.getUserObject()).getId().toString());
			   }
			   if(node.getUserObject() instanceof FullOrgUnitInfo){
				   idSet.add(((FullOrgUnitInfo)node.getUserObject()).getId().toString());
			   }
		   }else{
			   for(int i = 0 ; i<node.getChildCount();i++){
				   DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
				   getLeafIds(childNode, idSet);
			   }
		   }
    }
    
    /**
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
    }
    //审批
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
//    	super.actionAudit_actionPerformed(e);
    	checkSelected();
    	IRow row = FDCTableHelper.getSelectedRow(tblMain);
    	try{
    	WorkAmountBillFactory.getRemoteInstance().audit(BOSUuid.read(row.getCell("id").getValue().toString()));
    	showOprtOKMsgAndRefresh();
    	}catch (ScheduleException es) {
			FDCMsgBox.showError("当前单据不能执行此操作！");
		}
    	
    }
    //反审批
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	checkSelected();
    	IRow row = FDCTableHelper.getSelectedRow(tblMain);
    	try{
    		WorkAmountBillFactory.getRemoteInstance().unAudit(BOSUuid.read(row.getCell("id").getValue().toString()));
    		showOprtOKMsgAndRefresh();
    	}catch (ScheduleException es) {
			// TODO: handle exception
    		FDCMsgBox.showError("当前单据不能执行些操作！");
		}
    	
//    	super.actionUnAudit_actionPerformed(e);
    }
    
    protected void projectTree_valueChanged(TreeSelectionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    
    	super.projectTree_valueChanged(e);
    	tblMain.removeRows();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	// TODO Auto-generated method stub
    	FilterInfo filter = getMainFilter();
    	if(viewInfo != null){
    		viewInfo.setFilter(filter);
    	}
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    
    
    
    protected ICoreBase getBizInterface() throws Exception {
    	// TODO Auto-generated method stub
    	return WorkAmountBillFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	// TODO Auto-generated method stub
    	return WorkAmountBillEditUI.class.getName();
    }
    
    protected String getEditUIModal() {
    	// TODO Auto-generated method stub
    	return UIFactoryName.NEWTAB;
    }

}