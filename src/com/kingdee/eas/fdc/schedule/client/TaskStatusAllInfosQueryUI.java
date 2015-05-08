/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.print.config.ui.HeaderFooterModel;
import com.kingdee.bos.ctrl.print.printjob.IPrintJob;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleRptFacadeFactory;
import com.kingdee.eas.fdc.schedule.StatusTaskResult;
import com.kingdee.eas.fdc.schedule.StatusTaskResult.StatusTaskItem;
import com.kingdee.eas.fdc.schedule.StatusTaskResult.StatusTaskResultItem;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class TaskStatusAllInfosQueryUI extends AbstractTaskStatusAllInfosQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(TaskStatusAllInfosQueryUI.class);
    
    /**
     * output class constructor
     */
    public TaskStatusAllInfosQueryUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	view.getFilter().getFilterItems().add(new FilterItemInfo("id",TaskRptClientHelper.getProjectFilterSql(),CompareType.INNER));
    	prmtProject.setEntityViewInfo(view);
    	String cuId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
    	if(ScheduleClientHelper.chooseAllOrg()){
    		ScheduleClientHelper.setRespDept(prmtAdminDept, this, null);
    	}else{
    		ScheduleClientHelper.setRespDept(prmtAdminDept, this, cuId);
    	}
//		prmtAdminDept.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");
		prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
		ScheduleClientHelper.setPersonF7(prmtAdminPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId()!=null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():null);
		prmtSchedule.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleQuery");
		prmtProject.setEditFormat("$codingNumber$");
		prmtProject.setCommitFormat("$codingNumber$");
		prmtSchedule.setDisplayFormat("$name$");
		prmtSchedule.setEditFormat("$number$");
		prmtSchedule.setCommitFormat("$number$");
		initTable(getMainTable());
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    }
    protected void initWorkButton() {
    	super.initWorkButton();
    }
    public void serchAction_actionPerformed(ActionEvent e) throws Exception {
    	search();
    }
    
    public void exportExcelAction_actionPerformed(ActionEvent e) throws Exception {
    	List tables=new ArrayList();
    	for(int i=0;i<tabReport.getTabCount();i++){
    		tables.add(new Object[]{tabReport.getTitleAt(i),tabReport.getComponentAt(i)});
    	}
    	FDCTableHelper.exportExcel(this, tables);
    }
    
    protected void prmtProject_willShow(SelectorEvent e) throws Exception {
//    	super.prmtProject_willShow(e);
    }
    protected void prmtAdminDept_dataChanged(DataChangeEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getNewValue();
		if(newValue!=null&&newValue!=oldValue&&!newValue.equals(oldValue)){
			prmtAdminPerson.setValue(null);
		}
	}
    protected void prmtAdminPerson_willShow(SelectorEvent e) throws Exception {
		//责任人是否按当前用户所在组织进行过滤 by cassiel_peng 2010-06-07
		CtrlUnitInfo currentCtrlUnit = SysContext.getSysContext().getCurrentCtrlUnit();
		if(!FDCSCHClientHelper.filterRespPerson()){//跟合同的责任人保持一致
			if(FDCSCHClientHelper.canSelectOtherOrgPerson()){
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,null );
			}else{
				if(currentCtrlUnit==null){
					FDCMsgBox.showWarning("管理单元为空，出错！");
					SysUtil.abort();
				}
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,currentCtrlUnit.getId().toString());
			}
		}else{
			//如果没有选择责任部门
			if(prmtAdminDept.getValue()==null){
				if(currentCtrlUnit==null){
					FDCMsgBox.showWarning("管理单元为空，出错！");
					SysUtil.abort();
				}
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,currentCtrlUnit.getId().toString());
			}else{
				if(prmtAdminDept.getValue()!=null&&prmtAdminDept.getValue() instanceof CostCenterOrgUnitInfo){
					if(((CostCenterOrgUnitInfo)prmtAdminDept.getValue()).getCU()==null){
						FDCMsgBox.showWarning("管理单元为空，出错！");
						SysUtil.abort();
					}
					String cuID = ((CostCenterOrgUnitInfo)prmtAdminDept.getValue()).getCU().getId().toString();
					FDCClientUtils.setPersonF7(prmtAdminPerson, this,cuID);
				}
			}
		}
	}
    protected void prmtSchedule_willShow(SelectorEvent e) throws Exception {
    	//按工程项目做隔离
    	String prjId=FDCHelper.getF7Id(this.prmtProject);
    	this.prmtSchedule.getQueryAgent().resetRuntimeEntityView();
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
		if(prjId!=null){
			view.getFilter().appendFilterItem("project.id", prjId);
		}else{
			view.getFilter().getFilterItems().add(new FilterItemInfo("project.id",TaskRptClientHelper.getProjectFilterSql(),CompareType.INNER));
		}
		this.prmtSchedule.setEntityViewInfo(view);
    }
    
    protected void initListener() {
    	super.initListener();
    }
    private void initTable(KDTable table) {
		IRow headRow = table.addHeadRow();
		IColumn column=table.addColumn();
		column.setKey("scheduleName");
		column.setWidth(150);
		headRow.getCell("scheduleName").setValue("计划名称");

		column = table.addColumn();
		column.setKey("status");
		headRow.getCell("status").setValue("任务状态");

		column = table.addColumn();
		column.setKey("statusItem");
		column.setWidth(120);
		headRow.getCell("statusItem").setValue("任务状态明细");


		column = table.addColumn();
		column.setKey("count");
		column.getStyleAttributes().setNumberFormat("###,##0");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("count").setValue("任务数");
		table.getStyleAttributes().setLocked(true);
	}
 
    private RetValue retValue;
    private void search() throws EASBizException, BOSException{
    	verify();
    	ParamValue param=getParam();
    	retValue = ScheduleRptFacadeFactory.getRemoteInstance().getFilterStatusAllInfos(param);
    	fillTable();
    }
    
    private void verify() {
    	
	}

	protected void fillTable(){
		resetTabPane();
    	if(retValue==null){
    		getMainTable().removeRows();
    		return;
    	}
    	StatusTaskResult result=(StatusTaskResult)retValue.get("StatusTaskResult");
    	if(result==null){
    		getMainTable().removeRows();
    		return;
    	}
    	fillMainTable();
    	for(int i=0,size=result.size();i<size;i++){
    		KDTable table=getTable(i);
    		StatusTaskResultItem resultItem=result.getItem(i);
    		if(resultItem==null){
    			continue;
    		}
			table.setUserObject(resultItem);
			_fillTable(table);
			addTabPane(table);
    	}
    }
    
    private void fillMainTable(){
    	KDTable table=getMainTable();
    	try{
    		table.setRefresh(false);
    		table.removeRows();
    		table.getTreeColumn().setDepth(2);
    		StatusTaskResult result=(StatusTaskResult)retValue.get("StatusTaskResult");
    		for(int i=0,size=result.size();i<size;i++){
    			StatusTaskResultItem resultItem=result.getItem(i);
    			_fillResultItem(table, resultItem);
    		}
    		
    	}finally{
    		table.setRefresh(true);
    		table.repaint();
    	}
    }
    
    
	private void _fillTable(KDTable table) {
		table.removeRows();
		try {
			table.setRefresh(false);
			StatusTaskResultItem item = (StatusTaskResultItem) table.getUserObject();
			_fillResultItem(table, item);
		} finally {
			table.setRefresh(true);
			table.repaint();
		}
	}
    
    
    private final static String str_task="任务";
    /**
     * 填充一个计划的数据到table
     * @param table
     * @param info
     */
    private void _fillResultItem(KDTable table,StatusTaskResultItem item){
    	IRow row = table.addRow();
    	int start=row.getRowIndex();
    	FDCScheduleInfo info = item.getScheduleInfo();
		String digest=TaskRptClientHelper.getScheduleDigest(info);
    	row.getCell("scheduleName").setValue(digest);
    	row.setTreeLevel(0);
    	table.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), table.getColumnCount()-1);
    	
    	//fill task
    	fillStatusTaskItem(info.getName(), table, item.getNormalUnStartItem());
    	fillStatusTaskItem(info.getName(), table, item.getNormalFinishedItem());
    	fillStatusTaskItem(info.getName(), table, item.getDelayFinishedItem());
    	fillStatusTaskItem(info.getName(), table, item.getNormalExcecutingItem());
    	fillStatusTaskItem(info.getName(), table, item.getDelayUnFinishedItem());
    	fillStatusTaskItem(info.getName(), table, item.getDelayUnStartItem());
    	
    	table.getMergeManager().mergeBlock(start+1, 0, table.getRowCount()-1, 2,KDTMergeManager.FREE_ROW_MERGE);
    }
    
    private void fillStatusTaskItem(String scheduleName,KDTable table,StatusTaskItem statusTaskItem){
    	int size = statusTaskItem.getSize();
    	IRow row=table.addRow();
    	row.getCell("scheduleName").setValue(scheduleName);
    	row.getCell("status").setValue(statusTaskItem.getTaskExecuteStatus().getAlias()+str_task);
    	row.getCell("statusItem").setValue(statusTaskItem.getTaskExecuteStatusItemEnum().getAlias()+str_task);
    	row.getCell("count").setValue(new Integer(size));
    	
    	row.setTreeLevel(1);

    }
    
    
    public boolean destroyWindow() {
    	boolean destroyWindow = super.destroyWindow();
    	if(destroyWindow){
    		tableList.clear();
    		tableList=null;
    	}
		return destroyWindow;
    }
    
    private List tableList=new ArrayList();
    
    private KDTable getTable(int index){
    	if(index<0){
    		throw new NullPointerException("index must be great than 0!");
    	}
    	KDTable table=null;
    	if(tableList.size()>=index){
    		table=new KDTable();
    		initTable(table);
    		tableList.add(table);
    	}else{
    		table=(KDTable)tableList.get(index);
    	}
    	
    	return table;
    }

	private void resetTabPane(){
    	for(int i=this.tabReport.getTabCount()-1;i>0;i--){
    		this.tabReport.removeTabAt(i);
    	}
    }
	private void addTabPane(KDTable table){
		StatusTaskResultItem item=(StatusTaskResultItem)table.getUserObject();
		this.tabReport.addTab(item.getScheduleInfo().getName(), table);
    }
	
    private ParamValue getParam(){
    	ParamValue param=new ParamValue();
    	param.setString("projectId",FDCHelper.getF7Id(prmtProject));
    	param.setString("scheduleId",FDCHelper.getF7Id(prmtSchedule));
    	param.setString("adminDeptId",FDCHelper.getF7Id(prmtAdminDept));
    	param.setString("adminPersonId",FDCHelper.getF7Id(prmtAdminPerson));
    	return param;
    }
	private KDTable getMainTable(){
    	return this.tblMain;
    }
	protected KDTable getTableForCommon() {
		return tblMain;
	}
	public KDTable getTableForPrint() {
		return (KDTable)tabReport.getSelectedComponent();
	}
	protected KDTable getTableForPrintSetting() {
		return (KDTable)tabReport.getSelectedComponent();
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.tabReport.getSelectedComponent();
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow("任务状态汇总表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().print();
	}

	public void actionPreview_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.tabReport.getSelectedComponent();
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow("任务状态汇总表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().printPreview();
	}
}