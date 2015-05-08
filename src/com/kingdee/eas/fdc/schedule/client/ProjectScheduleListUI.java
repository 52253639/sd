/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ProjectScheduleListUI extends AbstractProjectScheduleListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectScheduleListUI.class);
    
    /**
     * output class constructor
     */
    public ProjectScheduleListUI() throws Exception
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
//        addStateCol();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionLocate.setVisible(false);
//    	addStateCol();
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionEdit.setVisible(false);
    	this.actionEdit.setEnabled(false);
    	this.actionRemove.setEnabled(false);
    	this.actionRemove.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	this.actionAddNew.setEnabled(false);
    	this.menuEdit.setVisible(false);
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	this.actionViewWorkFlow.setEnabled(true);
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	IRow row=FDCTableHelper.getSelectedRow(tblMain);
/*    	Object version = row.getCell("version").getValue();
    	uiContext.put("version", version);
    	uiContext.put("project.id", row.getCell("project.id").getValue());*/
    }

	protected ICoreBase getBizInterface() throws Exception {
		return ScheduleVerManagerFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ProjectScheduleUI.class.getName();
	}

	protected String getProjectFieldName() {
		return "project.id";
	}
	
    // [begin] 子类重载，如果希望怱略CU过滤。
    protected boolean isIgnoreCUFilter()
    {
        return true;
    }
    
    protected void execQuery() {
    	if(isTableTreeRow()){
    		getMainTable().getTreeColumn().setDepth(2);
    	}
    	super.execQuery();
    }
    
    protected void afterTableFillData(KDTDataRequestEvent e){
    	super.afterTableFillData(e);
    	if(!isTableTreeRow()){
    		return;
    	}
    	getMainTable().getTreeColumn().setDepth(2);
    	for(int i=e.getFirstRow();i<=e.getLastRow();i++){
    		IRow row = getMainTable().getRow(i);
    		if(Boolean.TRUE.equals(row.getCell("isLatestVer").getValue())){
    			row.setTreeLevel(0);
    			row.setCollapse(true);
    		}else{
    			row.setTreeLevel(1);
    			row.getStyleAttributes().setHided(true);
    		}
    	}
    }
    /**
     * 是否树形显示版本
     * @return
     */
    protected boolean isTableTreeRow(){
    	return true;
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWWIN;
    }
    //审批
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(!isSelected()) return;
    	String schVerManagerId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
//    	EntityViewInfo view = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("baseVer.state",ScheduleStateEnum.SUBMITTED,CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("baseVer.isLatestVer",Boolean.TRUE,CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("baseVer.id",schVerManagerId,CompareType.EQUALS));
//    	view.setFilter(filter);
//    	FDCScheduleCollection scheduleCol = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
//    	if(scheduleCol.size() > 0){
//    		FDCScheduleInfo scheduleInfo = scheduleCol.get(0);
//    		FDCScheduleFactory.getRemoteInstance().audit2(scheduleInfo.getId());
//    		FDCMsgBox.showInfo("操作成功！");
//    	}else{
//    		FDCMsgBox.showError("选择了不符合本操作条件的记录，请重新选择！");
//    		SysUtil.abort();
//    	}
//    	以上为走FDCScheduleXX2的方法
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",schVerManagerId,CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.SUBMITTED));
    	filter.getFilterItems().add(new FilterItemInfo("isLatestVer",Boolean.TRUE,CompareType.EQUALS));
    	view.setFilter(filter);
    	ScheduleVerManagerCollection schVerCol = ScheduleVerManagerFactory.getRemoteInstance().getScheduleVerManagerCollection(view);
    	if(schVerCol.size() > 0){
    		ScheduleVerManagerFactory.getRemoteInstance().audit(schVerCol.get(0).getId());
    		FDCMsgBox.showInfo("操作成功！");
    	}else{
    		FDCMsgBox.showError("选择了不符合本操作条件的记录，请重新选择！");
    		SysUtil.abort();
    	}
    	refresh(null);
    }
//    反审批
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(!isSelected()) return;
    	String schVerManagerId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
//    	EntityViewInfo view = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("baseVer.state",ScheduleStateEnum.AUDITTED,CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("baseVer.isLatestVer",Boolean.TRUE,CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("baseVer.id",schVerManagerId,CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("scheduleType",null,CompareType.EQUALS));
//    	view.setFilter(filter);
//    	FDCScheduleCollection scheduleCol = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
//    	if(scheduleCol.size() > 0){
//    		FDCScheduleInfo scheduleInfo = scheduleCol.get(0);
//    		FDCScheduleFactory.getRemoteInstance().unAudit2(scheduleInfo.getId());
//    		FDCMsgBox.showInfo("操作成功！");
////    		SysUtil.abort();
//    	}else{
//    		FDCMsgBox.showError("选择了不符合本操作条件的记录，请重新选择！");
//    		SysUtil.abort();
//    	}
//    	以上为走FDCScheduleXX2的方法
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",schVerManagerId,CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state",ScheduleStateEnum.AUDITTED));
    	filter.getFilterItems().add(new FilterItemInfo("isLatestVer",Boolean.TRUE,CompareType.EQUALS));
    	view.setFilter(filter);
    	ScheduleVerManagerCollection schVerCol = ScheduleVerManagerFactory.getRemoteInstance().getScheduleVerManagerCollection(view);
    	if(schVerCol.size() > 0){
    		ScheduleVerManagerFactory.getRemoteInstance().unAudit(schVerCol.get(0).getId());
    		FDCMsgBox.showInfo("操作成功！");
    	}else{
    		FDCMsgBox.showError("选择了不符合本操作条件的记录，请重新选择！");
    		SysUtil.abort();
    	}
    	refresh(null);
    }
//		流程图    
    public void actionViewWorkFlow_actionPerformed(ActionEvent e)  throws Exception {
    	if(!isSelected()) return;
//    	调用流程图的方法，参考任务属性卡物资管理的代码
    	int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		String itemID = null;
		if(tblMain.getCell(actRowIdx, "id").getValue() != null){
			itemID = tblMain.getCell(actRowIdx, "id").getValue().toString();
		}
		if(itemID == null) return;
		IEnactmentService service;
		try {
			service = EnactmentServiceFactory.createRemoteEnactService();
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(itemID);
			for (int i = 0, n = procInsts.length; i < n; i++) {
				// modify by gongyin,流程挂起时也显示流程图
				if ("open.running".equals(procInsts[i].getState())
						|| "open.not_running.suspended".equals(procInsts[i].getState())) {
					instInfo = procInsts[i];
				}
			}
			if (instInfo == null) {
				FDCMsgBox.showInfo(EASResource.getString(FrameWorkClientUtils.strResource + "Msg_WFHasNotInstance"));
				// MessageBox("没有正在运行的对应流程实例");
			} else {
				// 显示UI
				UIContext uiContext = new UIContext(this);
				uiContext.put("id", instInfo.getProcInstId());
				String className = BasicWorkFlowMonitorPanel.class.getName();
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className, uiContext);
				uiWindow.show();
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
    
    public void loadFields() {
    	super.loadFields();
    }
    
//    表格是否被选中
    private boolean isSelected(){
    	int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx < 0)
    		return false;
    	else 
    		return true;
    }
    
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
    	super.actionRefresh_actionPerformed(e);
//    	addStateCol();
    }
    
//    private void addStateCol() throws BOSException{
//    	Set schVerManIds = new HashSet();
//    	Map verPrjMap = new HashMap();
//    	for(int i=0;i<tblMain.getRowCount();i++){
//    		if(tblMain.getCell(i, "id") != null){
//    			String schVerManId = tblMain.getCell(i, "id").getValue().toString();
//    			schVerManIds.add(schVerManId);
//    		}
//    	}
//    	EntityViewInfo view = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	SelectorItemCollection sic = new SelectorItemCollection();
//    	filter.getFilterItems().add(new FilterItemInfo("SCHVerManager.id",schVerManIds,CompareType.INCLUDE));
//    	sic.add("id");
//    	sic.add("state");
//    	sic.add("SCHVerManager.id");
//    	view.setFilter(filter);
//    	view.setSelector(sic);
//    	ProjectScheduleCollection prjSchCol = ProjectScheduleFactory.getRemoteInstance().getProjectScheduleCollection(view);
//    	for(int i=0;i<prjSchCol.size();i++){
//    		ProjectScheduleInfo prjSchInfo = prjSchCol.get(i);
//    		verPrjMap.put(prjSchInfo.getSCHVerManager().getId().toString(), prjSchInfo.getState());
//    	}
//    	for(int i=0;i<tblMain.getRowCount();i++){
//    		if(tblMain.getCell(i, "id") != null && tblMain.getCell(i, "state") != null){
//    			String schVerManId = tblMain.getCell(i, "id").getValue().toString();
//    			FDCBillStateEnum state = (FDCBillStateEnum) verPrjMap.get(schVerManId);
//    			if(state == null){
//    				state = FDCBillStateEnum.SAVED;
//    			}
//    			tblMain.getCell(i, "state").setValue(state);
//    		}
//    	}
//    }
}