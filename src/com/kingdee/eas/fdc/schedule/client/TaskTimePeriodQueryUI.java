/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.print.config.ui.HeaderFooterModel;
import com.kingdee.bos.ctrl.print.printjob.IPrintJob;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
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
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleRptFacadeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class TaskTimePeriodQueryUI extends AbstractTaskTimePeriodQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(TaskTimePeriodQueryUI.class);
    private RetValue retValue;
    private Map taskLoadPctMap = new HashMap();
    /**
     * output class constructor
     */
    public TaskTimePeriodQueryUI() throws Exception
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
		prmtProject.setEditFormat("$codingNumber$");
		prmtProject.setCommitFormat("$codingNumber$");
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
    protected void prmtProject_willShow(SelectorEvent e) throws Exception {
    	super.prmtProject_willShow(e);
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
		IColumn column = table.addColumn();
		column.setKey("bizType");
		column.setWidth(100);
		headRow.getCell("bizType").setValue("业务类型");
		
		column = table.addColumn();
		column.setKey("number");
		column.setWidth(100);
		headRow.getCell("number").setValue("任务编码");

		column = table.addColumn();
		column.setKey("name");
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setEditable(false);
		headRow.getCell("name").setValue("任务名称");

		column = table.addColumn();
		column.setKey("startDate");
		column.setWidth(100);
		column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		headRow.getCell("startDate").setValue("计划开始时间");

		column = table.addColumn();
		column.setKey("endDate");
		column.setWidth(100);
		column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		headRow.getCell("endDate").setValue("计划结束时间");

		column = table.addColumn();
		column.setKey("actStartDate");
		column.setWidth(100);
		column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		headRow.getCell("actStartDate").setValue("实际开始时间");

		column = table.addColumn();
		column.setKey("actEndDate");
		column.setWidth(100);
		column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		headRow.getCell("actEndDate").setValue("实际结束时间");
		
		column = table.addColumn();
		column.setWidth(200);
		column.setKey("duration");
		column.getStyleAttributes().setNumberFormat("#,###");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("duration").setValue("有效工期（天）");

		column = table.addColumn();
		column.setKey("complete");
		column.setWidth(100);
		column.getStyleAttributes().setNumberFormat("##0.00%");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow.getCell("complete").setValue("完成百分比%");

		column = table.addColumn();
		column.setKey("adminPerson");
		headRow.getCell("adminPerson").setValue("责任人");

		column = table.addColumn();
		column.setWidth(200);
		column.setKey("adminDept");
		headRow.getCell("adminDept").setValue("责任部门");
		
		column = table.addColumn();
		column.setWidth(80);
		column.setKey("meeting");
		headRow.getCell("meeting").setValue("里程碑");
		
		column = table.addColumn();
		column.setWidth(80);
		column.setKey("isKey");
		headRow.getCell("isKey").setValue("关键节点");
		
		column = table.addColumn();
		column.setWidth(200);
		column.setKey("description");
		headRow.getCell("description").setValue("备注");

//		column = table.addColumn();
//		column.setWidth(200);
//		column.setKey("description");
//		headRow.getCell("description").setValue("备注");
		table.getStyleAttributes().setLocked(true);
/*		ActionMap actionMap = table.getActionMap();
		actionMap.put(KDTAction.CUT, new KDTDeleteAction(table));
		actionMap.put(KDTAction.DELETE, new KDTDeleteAction(table));*/

	}
    
    private void search() throws EASBizException, BOSException{
    	verify();
    	ParamValue param=getParam();
    	retValue = ScheduleRptFacadeFactory.getRemoteInstance().getTimeFilerTasks(param);
    	fillTable();
    }
    
    private void verify() {
		if(pkStartDate.getValue()==null&&pkEndDate.getValue()==null){
			FDCMsgBox.showWarning(this, "开始时间与结束时间不能同时为空!");
			SysUtil.abort();
		}
		
		if(pkStartDate.getValue()!=null&&pkEndDate.getValue()!=null){
			Date startDate=(Date)pkStartDate.getValue();
			Date endDate=(Date)pkEndDate.getValue();
			if(startDate.compareTo(endDate)>0){
				FDCMsgBox.showWarning(this, "开始时间不能大于结束时间");
				SysUtil.abort();
			}
			
		}
	}

	protected void fillTable(){
		resetTabPane();
    	if(retValue==null||retValue.size()==0){
    		getMainTable().removeRows();
    		return;
    	}
    	FDCScheduleCollection infos=(FDCScheduleCollection)retValue.get("FDCScheduleCollection");
    	taskLoadPctMap = (Map) retValue.get("workLoad");
    	if(infos==null){
    		
    		return;
    	}
    	fillMainTable();
    	for(int i=0;i<infos.size();i++){
    		KDTable table=getTable(i);
    		FDCScheduleInfo info = infos.get(i);
    		if(info==null){
    			continue;
    		}
			table.setUserObject(info);
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
    		FDCScheduleCollection infos=(FDCScheduleCollection)retValue.get("FDCScheduleCollection");
    		for(int i=0;i<infos.size();i++){
    			FDCScheduleInfo info=infos.get(i);
    			_fillSchedule(table, info);
    		}
    	}finally{
    		table.setRefresh(true);
    		table.repaint();
    	}
    }
    
    
    private void _fillTable(KDTable table){
    	table.removeRows();
    	try{
    		table.setRefresh(false);
    		FDCScheduleInfo info=(FDCScheduleInfo)table.getUserObject();
    		_fillSchedule(table, info);
    	}finally{
    		table.setRefresh(true);
    		table.repaint();
    	}
    	
    }
    
    
    /**
     * 填充一个计划的数据到table
     * @param table
     * @param info
     */
    private void _fillSchedule(KDTable table,FDCScheduleInfo info){
    	IRow row = table.addRow();
    	String digest=TaskRptClientHelper.getScheduleDigest(info);
    	row.getCell("name").setValue(digest);
    	row.setTreeLevel(0);
    	table.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), table.getColumnCount()-1);
    	int maxLength = 2;
    	//fill task
    	for(Iterator iter=info.getTaskEntrys().iterator();iter.hasNext();){
    		FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
    		row=table.addRow();
    		row.setUserObject(task);
    		loadRow(row);
    		row.setTreeLevel(task.getLevel());
    		maxLength = task.getLevel() > maxLength ? task.getLevel():maxLength;
    	}
    	table.getTreeColumn().setDepth(maxLength);
    }
    
    
    private void loadRow(IRow row) {
    	FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)row.getUserObject();
    	row.setTreeLevel(task.getLevel());
    	// 业务类型名称重组字符串 by duhongming
    	ScheduleTaskBizTypeCollection bizType = task.getBizType();
		if (bizType != null && bizType.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < bizType.size(); i++) {
				String name = bizType.get(i).getBizType().getName();
				if (i > 0) {
					sb.append("," + name);
				} else {
					sb.append(name);
				}

			}
			row.getCell("bizType").setValue(sb.toString());
		}
    	row.getCell("number").setValue(task.getNumber());
    	row.getCell("name").setValue(task.getName());
		row.getCell("startDate").setValue(task.getStart());
		row.getCell("endDate").setValue(task.getEnd());
		row.getCell("actStartDate").setValue(task.getActualStartDate());
		row.getCell("actEndDate").setValue(task.getActualEndDate());
		row.getCell("duration").setValue(task.getEffectTimes());
		row.getCell("meeting").setValue(Boolean.valueOf(task.isMeeting()));
		row.getCell("isKey").setValue(Boolean.valueOf(task.isIsKey()));
		row.getCell("description").setValue(task.getDescription());
//		row.getCell("freeTime").setValue(task.getFreeTime());
		//除以100,原因是列格式化的时候加了%,会乘以100
//		row.getCell("complete").setValue(FDCHelper.divide(task.getComplete(), FDCHelper.ONE_HUNDRED));
		if(task.getComplete()!= null){
			row.getCell("complete").setValue(FDCHelper.divide(task.getComplete(), FDCHelper.ONE_HUNDRED));
		}else{
			row.getCell("complete").setValue(FDCHelper.ZERO);
		}
		
		if (task.getAdminPerson() != null) {
			row.getCell("adminPerson").setValue(task.getAdminPerson().getName());
		}
		if (task.getAdminDept() != null) {
			row.getCell("adminDept").setValue(task.getAdminDept().getName());
		}
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
		FDCScheduleInfo info=(FDCScheduleInfo)table.getUserObject();
		this.tabReport.addTab(info.getName(), table);
    }
	
    private ParamValue getParam(){
    	ParamValue param=new ParamValue();
    	param.setString("projectId",FDCHelper.getF7Id(prmtProject));
    	param.setString("scheduleId",FDCHelper.getF7Id(prmtSchedule));
    	param.setString("adminDeptId",FDCHelper.getF7Id(prmtAdminDept));
    	param.setString("adminPersonId",FDCHelper.getF7Id(prmtAdminPerson));
    	param.put("startDate",pkStartDate.getValue());
    	param.put("EndDate",pkEndDate.getValue());
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
		header.addRow("任务信息表", sa);
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
		header.addRow("任务信息表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().printPreview();
	}
}