/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportFacadeFactory;
import com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesNewUI;
import com.kingdee.eas.fdc.schedule.client.FDCSpecialScheduleTaskPropertiesUI;

/**
 * output class name
 */
public class RptTaskListUI extends AbstractRptTaskListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RptTaskListUI.class);
    
    /**
     * output class constructor
     */
    public RptTaskListUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTable();
    	Map resultMap = ScheduleReportFacadeFactory.getRemoteInstance().getScheduleTaskInfo(getTaskParam());
    	if(resultMap.get("taskList")!= null){
    		FDCScheduleTaskCollection tasks = (FDCScheduleTaskCollection) resultMap.get("taskList");
    		selectionSortTask(tasks);
    		fillTable(tasks);
    		
    	}
    	if(resultMap.get("orgInfo")!= null){
    		String orgInfoName =  (String) resultMap.get("orgInfo");
    		RptTaskListUI.this.kDTextField1.setText(orgInfoName);
    	}
		
    }
    //穿透页面中计划顺序应按照计划编制页面的顺序显示
    public static void selectionSortTask(FDCScheduleTaskCollection tasks) {  
    	for(int i=0;i<tasks.size();i++) {    
    		for(int j=i+1;j<tasks.size();j++) {     
    			if(tasks.get(i).get("longnumber").toString().compareTo(tasks.get(j).get("longnumber").toString()) >0 ) {           
    				tasks.swap(i, j);
    			}
    		}
    	}
    }
    
	private void initTable() {
    	this.taskTable.checkParsed();
    	this.taskTable.getStyleAttributes().setLocked(true);
    	this.taskTable.getColumn("startDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    	this.taskTable.getColumn("endate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		
	}
	
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
	}

	private void fillTable(FDCScheduleTaskCollection tasks) {
		IRow row = null;
		FDCScheduleTaskInfo task = null;
		String evaPerson = null;
		String evaPersonOther = null;
		List<String> prjList = new ArrayList<String>();
		StringBuffer strPrjNames = new StringBuffer();
		for(int i=0;i<tasks.size();i++){
			row = this.taskTable.addRow();
			task = tasks.get(i);
			row.getCell("projectName").setValue(task.getSchedule().getProject().getName());
			if(!prjList.contains(task.getSchedule().getProject().getName())){
				prjList.add(task.getSchedule().getProject().getName());
			}
			row.getCell("taskName").setValue(task.getName());
			row.getCell("taskType").setValue(task.getTaskType());
			row.getCell("startDate").setValue(task.getStart());
			row.getCell("endate").setValue(task.getEnd());
			row.getCell("checkdate").setValue(task.getCheckDate());
			row.getCell("actStartDate").setValue(task.getActualStartDate());
			row.getCell("actEndDate").setValue(task.getActualEndDate());
			row.getCell("adminDept").setValue(task.getAdminDept()!=null?task.getAdminDept().getName():null);
			row.getCell("adminPerson").setValue(task.getAdminPerson()!= null?task.getAdminPerson().getName():null);
			row.getCell("scheduleEvaPerson").setValue(task.getPlanEvaluatePerson());
			evaPerson = task.getQualityEvaluatePerson()!=null?task.getQualityEvaluatePerson().getName():null;
			//TODO 旭辉的两个质量评价人 需要改实体，先注释
//			evaPersonOther = task.getQualityEvaluatePersonOther()!=null?task.getQualityEvaluatePersonOther().getName():null;
			//两个质量评价人都要显示
			if (evaPersonOther != null){
				row.getCell("quatityEvaPerson").setValue(evaPerson+";"+evaPersonOther);
			}else{				
				row.getCell("quatityEvaPerson").setValue(evaPerson);
			}
			row.setUserObject(task);
		}
		taskTable.getColumn("projectName").setGroup(true);
		taskTable.getGroupManager().group();
		
		for(int i=0;i<prjList.size();i++){
			strPrjNames.append(prjList.get(i));
			if(i<prjList.size()-1){
				strPrjNames.append(",");	
			}
		}
		
		this.kDTextField2.setText(strPrjNames.toString());
	}
    
    private Map getTaskParam(){
    	Map contextMap = getUIContext();
    	Date startDate = (Date) contextMap.get("startDate");
    	Date endDate = (Date) contextMap.get("endDate");
    	//初始化界面控件
    	this.kDDatePicker1.setValue(startDate);
    	this.kDDatePicker2.setValue(endDate);
    	
        Map paramMap = new HashMap();
        
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("currentOrgId", contextMap.get("currentOrgId"));
        paramMap.put("chartType", contextMap.get("chartType"));
        paramMap.put("state", contextMap.get("state"));
        paramMap.put("selectedOrgs", contextMap.get("selectedOrgs"));
    	
    	return paramMap;
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output taskTable_tableClicked method
     */
    protected void taskTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //获取当前行绑定的任务信息，弹出属性界面，查看信息
    	int rowIndex = taskTable.getSelectManager().getActiveRowIndex();
    	if(taskTable.getRow(rowIndex).getUserObject()!= null){
    		
    		FDCScheduleTaskInfo selectedTask = (FDCScheduleTaskInfo) taskTable.getRow(rowIndex).getUserObject();
    		String propertiesTaskUIName = FDCScheduleTaskPropertiesNewUI.class.getName();
    		if(getUIContext().get("chartType") != null){
    			String chartType = (String) getUIContext().get("chartType");
    			if(chartType.equals("proPrj")){
    				propertiesTaskUIName = FDCSpecialScheduleTaskPropertiesUI.class.getName();
    			}
    		}
    		UIContext uiContext = new UIContext();
    		uiContext.put("selectTask", selectedTask);
    		uiContext.put("project", selectedTask.getSchedule().getProject());
    		uiContext.put("Owner", this);
    		
    		UIFactory.createUIFactory().create(propertiesTaskUIName, uiContext).show();
    		
    	}
    }

    

}