/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.print.config.ui.HeaderFooterModel;
import com.kingdee.bos.ctrl.print.printjob.IPrintJob;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCTASKTree;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 * create by warship at 2010/08/13
 */
public class PlanEditUI extends AbstractPlanEditUI
{
	private static final String COL_TASKID = "id";//Task id
	private static final String COL_PROJECTID = "projectID";//工程 id
	private static final String COL_LEVEL = "level";//级次
	private static final String COL_LONGNUMBER = "longNumber";//WBS编码
	private static final String COL_NAME = "name";//任务名称
	
	private static final String COL_COMPLETE = "complete";//累计完成百分比
	private static final String COL_EFFECTTIMES = "effectTimes";//有效工期
	private static final String COL_START = "start";//开始时间
	private static final String COL_END = "end";//结束时间
	private static final String COL_NATUIETIMES = "natureTimes";//自然工期
	private static final String COL_DEPENDENTRYS = "dependEntrys";//前置任务 
	private static final String COL_ACTUALSTARTDATE = "actualStartDate";//实际开始时间
	private static final String COL_ACTUALENDDATE = "actualEndDate";//实际完工日期
	private static final String COL_ADMINDEMP = "adminDept";//责任部门
	private static final String COL_ADMINPERSON = "adminPerson";//责任人
	private static final String COL_ISLEAF = "isLeaf";//是否是叶子
	 
    private static final Logger logger = CoreUIObject.getLogger(PlanEditUI.class);
    
    /**
     * output class constructor
     */
    public PlanEditUI() throws Exception
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
    public void loadData()
    {
    	
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	btnSearch.setEnabled(true);
    	initProject();
    	initTable(getMailTable());
    	this.actionPrint.setEnabled(true);
    	this.actionPrintPreview.setEnabled(true);
    	this.actionProperty.setEnabled(true);
    	this.actionScheduleReport.setEnabled(true);
    	this.actionSave.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionSubmitOption.setVisible(false);
    	this.menuEdit.setVisible(false);
    	this.menuView.setVisible(false);
    	this.menuTool.setVisible(false);
    	this.actionSubmit.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.prmtProject.setEditable(false);
    }
    
    private void initProject(){
    	prmtProject.setEnabledMultiSelection(true);
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	if(this instanceof DempPlanEditUI){//按任务的责任部门过滤项目工程
    		filter.getFilterItems().add(new FilterItemInfo("id",TaskRptClientHelper.getProjectByTaskAdminDempFilterSql(),CompareType.INNER));
    	}else if(this instanceof PersonalPlanEditUI){//按任务的责任人过滤项目工程
    		filter.getFilterItems().add(new FilterItemInfo("id",TaskRptClientHelper.getProjectByTaskAdminPersonFilterSql(),CompareType.INNER));	
    	}
    	view.setFilter(filter);
    	prmtProject.setDisplayFormat("$name$");
    	prmtProject.setEditFormat("$number$");
    	prmtProject.setEntityViewInfo(view);
    	
    
    	
    		
    	
    	
    	
    }
    
    private void initTable(KDTable table){
    	table.setEditable(false);
    	IRow headRow = table.addHeadRow();
    	
    	IColumn column = table.addColumn();
    	column.setKey(COL_TASKID);
    	//column.setWidth(150);
    	headRow.getCell(COL_TASKID).setValue("TaskID");
    	column.getStyleAttributes().setHided(true);
    	
    	column = table.addColumn();
    	column.setKey(COL_PROJECTID);
    	//column.setWidth(150);
    	headRow.getCell(COL_PROJECTID).setValue("工程ID");
    	column.getStyleAttributes().setHided(true);
    	
    	column = table.addColumn();
    	column.setKey(COL_LEVEL);
    	column.setWidth(150);
    	//headRow.getCell(COL_LEVEL).setValue("级次");
    	column.getStyleAttributes().setHided(true);
    	
    	column = table.addColumn();
    	column.setKey(COL_ISLEAF);
    	column.setWidth(100);
    	//headRow.getCell(COL_ISLEAF).setValue("是否叶子");
    	column.getStyleAttributes().setHided(true);
    	
    	column = table.addColumn();
    	column.setKey(COL_LONGNUMBER);
    	column.setWidth(100);
    	headRow.getCell(COL_LONGNUMBER).setValue("WBS编码");
    	
    	column = table.addColumn();
    	column.setKey(COL_NAME);
    	column.setWidth(100);
    	headRow.getCell(COL_NAME).setValue("任务名称");
    	
    	column = table.addColumn();
    	column.setKey(COL_COMPLETE);
    	column.setWidth(100);
    	headRow.getCell(COL_COMPLETE).setValue("累计完成百分比");
    	column.getStyleAttributes().setNumberFormat("#,###");
    	column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	column = table.addColumn();
    	column.setKey(COL_EFFECTTIMES);
    	column.setWidth(100);
    	headRow.getCell(COL_EFFECTTIMES).setValue("有效工期");
    	column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	column.getStyleAttributes().setNumberFormat("##.00");
    	
    	column = table.addColumn();
    	column.setKey(COL_START);
    	column.setWidth(100);
    	column.getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
    	headRow.getCell(COL_START).setValue("开始时间");
    	
    	column = table.addColumn();
    	column.setKey(COL_END);
    	column.setWidth(100);
    	column.getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
    	headRow.getCell(COL_END).setValue("结束时间");
    	
    	
    	column = table.addColumn();
    	column.setKey(COL_NATUIETIMES);
    	column.setWidth(100);
    	headRow.getCell(COL_NATUIETIMES).setValue("自然工期");
    	column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	column.getStyleAttributes().setNumberFormat("##.00");
    	
    	column = table.addColumn();
    	column.setKey(COL_DEPENDENTRYS);
    	column.setWidth(100);
    	headRow.getCell(COL_DEPENDENTRYS).setValue("前置任务");
    	   	
    	
    	column = table.addColumn();
    	column.setKey(COL_ACTUALSTARTDATE);
    	column.setWidth(100);
    	column.getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
    	headRow.getCell(COL_ACTUALSTARTDATE).setValue("实际开始时间");
    	
    	column = table.addColumn();
    	column.setKey(COL_ACTUALENDDATE);
    	column.setWidth(100);
    	column.getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
    	headRow.getCell(COL_ACTUALENDDATE).setValue("实际完工日期");
    	
    	column = table.addColumn();
    	column.setKey(COL_ADMINDEMP);
    	column.setWidth(100);
    	headRow.getCell(COL_ADMINDEMP).setValue("责任部门");
    	
    	column = table.addColumn();
    	column.setKey(COL_ADMINPERSON);
    	column.setWidth(100);
    	headRow.getCell(COL_ADMINPERSON).setValue("责任人");
    }
    
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception
    {
         int taskState =prmtState.getSelectedIndex();
         Object []objects=(Object [])prmtProject.getValue();
         Map projectMap =new HashMap();
         Set projectID=new HashSet();
         if(objects!=null && objects.length>0){
			for(int i=0;i<objects.length;i++){
				if(objects[i] instanceof CurProjectInfo){
					CurProjectInfo project =(CurProjectInfo)objects[i];
					if(!projectMap.containsKey(project.getId().toString())){
						projectMap.put(project.getId().toString(), project);
						projectID.add(project.getId().toString());
					}
				}
			}
         }
         if(projectID.size()==0){
        	 FDCMsgBox.showError("工程项目不能为空！");
  			 abort(); 
         }else if(projectID.size()>0){
        	 List taskSet=null;
        	 //int maxLevel=getMaxLevel(projectID);//设置最大的level
        	 getMailTable().removeRows();
        	 if(this instanceof DempPlanEditUI){//按任务的责任部门过滤项目工程
        		 OrgUnitInfo currentFIUnit=SysContext.getSysContext().getCurrentOrgUnit();
        		 String adminDempID=null;
        		 if(currentFIUnit!=null && currentFIUnit.getId()!=null){
        			 adminDempID=currentFIUnit.getId().toString();
        		 }
        		 taskSet=FDCScheduleTaskFactory.getRemoteInstance().getTaskByAdminDemp(projectID, taskState,adminDempID);
        	 }else if(this instanceof PersonalPlanEditUI){//按任务的责任人过滤项目工程
        		 UserInfo user=SysContext.getSysContext().getCurrentUserInfo();
        		 PersonInfo person = user.getPerson();
        		 String personID=null;
        		 if(person!=null && person.getId()!=null){
        			 personID=person.getId().toString();
        		 }
        		 taskSet=FDCScheduleTaskFactory.getRemoteInstance().getTaskByAdminPerson(projectID, taskState,personID);
        	 }
        	 
        	 int proProjectCount=0;
        	 FDCScheduleTaskCollection taskCol=new FDCScheduleTaskCollection();
        	 for(Iterator it=projectID.iterator();it.hasNext();) {
        		 Map proMap =new HashMap();
        		 String projectId =(String)it.next();
        		 CurProjectInfo project =(CurProjectInfo)projectMap.get(projectId);
        		 taskCol.clear();
        		 for(Iterator taskIt=taskSet.iterator();taskIt.hasNext();){
        			 FDCScheduleTaskInfo task =(FDCScheduleTaskInfo)taskIt.next();
        			 
        			 if(projectId!=null){
        				 FDCScheduleInfo scheudle =(FDCScheduleInfo)task.getSchedule();
        				 if(scheudle!=null && scheudle.getProject()!= null){
	        				 CurProjectInfo temp_project =(CurProjectInfo)task.getSchedule().getProject();
	        				 if(project.getId()!=null && project.getId().toString()!=null && projectId.equals(temp_project.getId().toString())){
	        					 proMap.put(task.getId().toString(), task);
	        					 taskCol.add(task);
	        				 }
	        			 }
        				
        			 }
            		 
            	 }
        		 if(proMap.size()>0){
        			 FDCTASKTree.getTreeFromCollection(taskCol, projectId);
        			 //allProMap.put(projectId, proMap); 
        		 }
        		 if(taskCol!=null && taskCol.size()>0){
	        		 fillTable(proProjectCount,proMap,taskCol,project);
	        		 proProjectCount=proProjectCount+1;
        		 }
        		 
        	 }
        	
         }
         
    	
    }
    private void fillTable(int proProjectCount,Map taskMap,FDCScheduleTaskCollection taskCol,CurProjectInfo project) throws BOSException{
    	int maxLevel=0;
    	//获得所有任务的最大级次
    	for(Iterator it=taskCol.iterator();it.hasNext();){
    		FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)it.next();
    		if(task!=null && task.getLevel()!=0){
    			if(task.getLevel()>maxLevel){
    				maxLevel=task.getLevel();
    			}
    		}
    	}
    	//添加项目编码和名称，显示为红色
    	KDTable table=getMailTable();
    	IRow row = table.addRow();
    	row.getStyleAttributes().setBackground(Color.RED);
    	row.getCell(COL_NAME).setValue(project.getName());
    	//显示项目编码
    	if(proProjectCount>=0 &&proProjectCount<10){
			row.getCell(COL_LONGNUMBER).setValue("00"+proProjectCount);
		}else if(proProjectCount>=10 &&proProjectCount<100){
			row.getCell(COL_LONGNUMBER).setValue("0"+proProjectCount);
		}else if(proProjectCount>=100){
			row.getCell(COL_LONGNUMBER).setValue(""+proProjectCount);
		}
    	//设置树的级次
    	table.getTreeColumn().setDepth(maxLevel);
//    	 dependee 后置任务
//    	 dependant 前置任务
    	Map taskNameMap=new HashMap();
    	FDCScheduleTaskCollection taskCollection=taskCol;
    	//遍历每条任务
    	for(Iterator it=taskCollection.iterator();it.hasNext();){
    		StringBuffer strBuf=new StringBuffer();
    		FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)it.next();
    		if(task!=null && task.getId()!=null){
    			//找每个任务的前置任务
    			for(Iterator taskIt=taskCol.iterator();taskIt.hasNext();){
    				FDCScheduleTaskInfo parentTask=(FDCScheduleTaskInfo)taskIt.next();
    				if(parentTask!=null && parentTask.getDependEntrys()!=null){
    					//找每条任务的后置任务
    					FDCScheduleTaskDependCollection taskDependCol=(FDCScheduleTaskDependCollection)parentTask.getDependEntrys();
    			    	for(Iterator task_it=taskDependCol.iterator();task_it.hasNext();){
    			    		//找该任务的每条后置任务
    			    		FDCScheduleTaskDependInfo taskDependInfo = (FDCScheduleTaskDependInfo)task_it.next();
    			    		if(taskDependInfo!=null && taskDependInfo.getDependTask()!=null){
    			    			FDCScheduleTaskInfo dependTask=(FDCScheduleTaskInfo)taskDependInfo.getDependTask();
    			    			//如果外层任务等于该条任务的后置任务，说明该任务时外层任务的前置任务
    			    			if(dependTask!=null && dependTask.getId()!=null && task.getId().toString().equals(dependTask.getId().toString())){
    			    				if(strBuf.length()==0){
		    							strBuf.append(parentTask.getName());
		    						}else{
		    							strBuf.append(","+parentTask.getName());
		    						}
    			    			}
    			    		}
    			    	}
    				}
    			}
    			taskNameMap.put(task.getId().toString(), strBuf.toString());//保存每条任务的前置任务
    		}
    	}
    	//显示该项目的所有任务
    	for(Iterator it=taskCol.iterator();it.hasNext();){
    		FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)it.next();
    			String taskId=task.getId().toString();
		    	row = table.addRow();
		    	//主项任务显示为浅黄色
		    	if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(task.getWbs().getTaskType().getId().toString())){
		    		
		    		row.getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		    	}
		    	row.getCell(COL_TASKID).setValue(task.getId().toString());
		    	int task_level =task.getLevel();
		    	String parentName = "";
		    	if(task.getParent()!=null){
		    		parentName = task.getParent().getName();
		    	}
		    	
		    	row.setTreeLevel(task_level);
		    	row.getCell(COL_PROJECTID).setValue(project.getId().toString());
		    	row.getCell(COL_LONGNUMBER).setValue(task.getLongNumber().replace('!', '.'));
		    	row.getCell(COL_NAME).setValue(task.getName());
		    	row.getCell(COL_COMPLETE).setValue(task.getComplete());
		    	row.getCell(COL_EFFECTTIMES).setValue(task.getEffectTimes());
		    	row.getCell(COL_START).setValue(task.getStart());
		    	row.getCell(COL_END).setValue(task.getEnd());
		    	row.getCell(COL_NATUIETIMES).setValue(task.getNatureTimes());
		    	if(task!=null && task.getId()!=null && taskNameMap.containsKey(task.getId().toString())){
		    		String taskName=(String)taskNameMap.get(task.getId().toString());
		    		row.getCell(COL_DEPENDENTRYS).setValue(taskName);
		    	}

		    	row.getCell(COL_ACTUALSTARTDATE).setValue(task.getActualStartDate());
		    	row.getCell(COL_ACTUALENDDATE).setValue(task.getActualEndDate());
		    	if(task.getAdminDept()!=null){
		    		row.getCell(COL_ADMINDEMP).setValue(task.getAdminDept().getName());
		    	}
		    	
		    	if(task.getAdminPerson()!=null){
		    		row.getCell(COL_ADMINPERSON).setValue(task.getAdminPerson().getName());
		    	}
		    	row.getCell(COL_ISLEAF).setValue(new Boolean(task.isIsLeaf()));
    	}
    }
    protected int getMaxLevel(Set projectID) {
    	FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select max(tk.flevel) maxLevel from t_sch_fdcscheduletask tk inner join t_sch_fdcschedule sch " +
				"	on tk.fscheduleid=sch.fid where sch.FIsLatestVer=1 and ");
		builder.appendParam("sch.FProjectID",projectID.toArray());
		try {
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.next()) {
				return rowSet.getInt("maxLevel");
			}
		} catch (Exception e) {
			this.handUIException(e);
		}
		return 0;
	}

    


    public KDTable getMailTable(){
    	return this.mainTable;
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
		KDTable table = (KDTable) this.mainTable;
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow("任务状态明细表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().print();
    	//super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
		KDTable table = (KDTable) this.mainTable;
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow("任务状态明细表", sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
		table.getPrintManager().printPreview();
        //super.actionPrintPreview_actionPerformed(e);
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
        super.actionAttachment_actionPerformed(e);
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
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return FDCScheduleTaskFactory.getRemoteInstance();
	}
	protected IObjectValue createNewData(){
		return null;
	}
	public void actionExportProject_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionExportProject_actionPerformed(e);
	}
	public void actionScheduleReport_actionPerformed(ActionEvent e) throws Exception
	{
		//int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if(!fillByRespPerson()){
			FDCMsgBox.showError("基于任务填报工程量参数未启用！");
 			abort();
		}
		UIContext uiContext = new UIContext(this);
		List list= (List)getMailTable().getSelectManager().getBlocks();
		int totalRow=0;
		for(Iterator it=list.iterator();it.hasNext();){
			KDTSelectBlock sb = (KDTSelectBlock)it.next();
			int beginIndex=sb.getBeginRow();
			int endIndex=sb.getEndRow();
			totalRow=totalRow+(endIndex-beginIndex+1);
		}
		if(totalRow==0){
			return;
		}
		if(totalRow>1){
			FDCMsgBox.showError("请选择一条记录！");
 			abort();
		}
		int rowIndex=getMailTable().getSelectManager().getActiveRowIndex();
		IRow row =this.getMailTable().getRow(rowIndex);
		
		if(row.getCell(COL_TASKID).getValue()==null){
			FDCMsgBox.showError("请选择任务！");//请选择明细任务！
 			abort(); 
		}else{
			if(row.getCell(COL_ISLEAF).getValue()!=null && Boolean.FALSE.equals(row.getCell(COL_ISLEAF).getValue())){
				FDCMsgBox.showError("请选择明细任务！");
	 			abort();
			}
			String taskID=row.getCell(COL_TASKID).getValue().toString();
			uiContext.put("taskID",BOSUuid.read(taskID));
		}
		if(row.getCell(COL_PROJECTID).getValue()!=null){
			String projectID= row.getCell(COL_PROJECTID).getValue().toString();
			uiContext.put("projectId",BOSUuid.read(projectID));
		}
		Boolean isPlan = Boolean.TRUE;//当是计划汇总时，调用任务工程量填报
		uiContext.put("isPlan", isPlan);		
		String state = OprtState.ADDNEW;
		String ui="com.kingdee.eas.fdc.schedule.client.WorkAmountBillEditUI";
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ui, uiContext, null, state);
		uiWindow.show();
	}
    private boolean fillByRespPerson(){
		boolean workAmountBill = false;
		try {
			workAmountBill = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), FDCConstants.FDCSCH_PARAM_BASEONTASK);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workAmountBill;
	}

	public void actionProperty_actionPerformed(ActionEvent e) throws Exception
	{
		KDTSelectBlock sb;
		int size = getMailTable().getSelectManager().size(); // 获取选择块的总个数
		if(size==0){
			FDCMsgBox.showError("请选择一条任务！");//请选择明细任务！
 			abort();
		}
		String taskID=null;
		for (int i = 0; i < size; i++)
		{
			// 获取第i个选择块
			sb = getMailTable().getSelectManager().get(i);
			// 遍历每个选择块的所有行
			for (int j = sb.getTop(); j <= sb.getBottom(); j++)
			{
				IRow row =getMailTable().getRow(j);
				if(row.getCell(COL_TASKID).getValue()!=null){
					taskID=row.getCell(COL_TASKID).getValue().toString();
					break;//取第一条任务
				}
			}
			if(taskID!=null){
				break;
			}
		}
		if(taskID==null){
			FDCMsgBox.showError("请选择任务！");//选择的是项目，请选择明细任务！
 			abort();
		}
		FDCScheduleTaskInfo selectTask=getSelectedTask(taskID);
		List dependantTasks = FDCScheduleTaskFactory.getRemoteInstance().getDependantTask(taskID);
		selectTask.put("dependantTasks", dependantTasks);
		//selectTask.put("prefixTaskListInfo", "prefixTaskListInfo");
		UIContext uiContext = new UIContext(this);
		setOprtState(OprtState.VIEW);
		uiContext.put("OprtState", getOprtState());
		uiContext.put("selectTask", selectTask);

		String propertyUIName="com.kingdee.eas.fdc.schedule.client.FDCPlanTaskPropertyUI";
		try {
    		IUIWindow myUi = UIFactory.createUIFactory(UIFactoryName.MODEL).create(propertyUIName, uiContext,null,(String)uiContext.get("OprtState"),WinStyle.SHOW_TOOLBAR);
    		myUi.show();
    	} catch (UIException ex) {
    		ex.printStackTrace();
    	}
	}
	
	public FDCScheduleTaskInfo getSelectedTask(String taskID){
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("level"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("adminDept"));
		sic.add(new SelectorItemInfo("adminDept.name"));
		sic.add(new SelectorItemInfo("adminPerson"));
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("schedule"));
		sic.add(new SelectorItemInfo("schedule.project"));
		sic.add(new SelectorItemInfo("schedule.project.id"));
		sic.add(new SelectorItemInfo("schedule.project.name"));
		sic.add(new SelectorItemInfo("wbs.id"));
		sic.add(new SelectorItemInfo("wbs.name"));
		sic.add(new SelectorItemInfo("dependEntrys"));
		sic.add(new SelectorItemInfo("dependEntrys.task"));
		sic.add(new SelectorItemInfo("dependEntrys.task.id"));
		sic.add(new SelectorItemInfo("dependEntrys.task.name"));
		sic.add(new SelectorItemInfo("dependEntrys.dependTask"));
		sic.add(new SelectorItemInfo("dependEntrys.dependTask.id"));
		sic.add(new SelectorItemInfo("dependEntrys.dependTask.name"));
		
		
		sic.add(new SelectorItemInfo("wbs.number"));
		sic.add(new SelectorItemInfo("wbs.parent"));
		
		sic.add(new SelectorItemInfo("wbs.longNumber"));
		sic.add(new SelectorItemInfo("wbs.isLeaf"));
		sic.add(new SelectorItemInfo("wbs.level"));
		
		sic.add(new SelectorItemInfo("wbs.taskType"));
		sic.add(new SelectorItemInfo("wbs.taskType.id"));
		sic.add(new SelectorItemInfo("wbs.name"));
		FDCScheduleTaskInfo task=null;
		try {
			task = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo((new ObjectUuidPK(taskID)), sic);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return task;
	}
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception{
		
		Object []objects=(Object [])prmtProject.getValue();
        Map projectMap =new HashMap();
        Set projectID=new HashSet();
        if(objects!=null && objects.length>0){
			for(int i=0;i<objects.length;i++){
				if(objects[i] instanceof CurProjectInfo){
					CurProjectInfo project =(CurProjectInfo)objects[i];
					if(!projectMap.containsKey(project.getId().toString())){
						projectMap.put(project.getId().toString(), project);
						projectID.add(project.getId().toString());
					}
				}
			}
        }
        //Map allProMap =new HashMap();
        if(projectID.size()==0){
       	 return;
        }
        actionSearch_actionPerformed(e);
	}
}