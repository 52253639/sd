package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;

import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.SCHHelper;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskCollection;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.util.SysUtil;

/**
 * dependee 后置任务
 * dependant 前置任务
 * 
 * @author xiaohong_shi
 *
 */
public class PlanPredecessorsExtPanelHelper  implements ITaskPanelHelper{
	private final FDCPlanTaskPropertyUI taskPropertiesUI;
    private final FDCScheduleTaskInfo myTask;
    private final KDTable table;
	public PlanPredecessorsExtPanelHelper(FDCPlanTaskPropertyUI ui){
		taskPropertiesUI=ui;
		this.myTask=ui.getSelectTask();
		table=ui.predecessorsTable;
		initTable();
	}

	private void initTable(){
		getTable().checkParsed();
		//添加，删除按钮
		ItemAction addNewAction=new ItemAction(){
			public void actionPerformed(ActionEvent e) {
				actionAddNew_Performed(e);
			}
		};
		ItemAction deleteAction=new ItemAction(){
			public void actionPerformed(ActionEvent e) {
				actionDelete_Performed(e);
			}
		};
//		JButton btnAddNew = taskPropertiesUI.contPredecessorPanel.add(addNewAction);
//		btnAddNew.setText("新增");
//		btnAddNew.setIcon(SCHClientHelper.ICON_ADDLINE);
//		JButton btnDelete = taskPropertiesUI.contPredecessorPanel.add(deleteAction);
//		btnDelete.setText("删除");
//		btnDelete.setIcon(SCHClientHelper.ICON_REMOVELINE);
		taskPropertiesUI.contPredecessorPanel.setVisibleOfExpandButton(true);
		taskPropertiesUI.contPredecessorPanel.setEnableActive(false);
		//WBSNumber F7
		KDBizPromptBox f7=new KDBizPromptBox();
		f7.setDisplayFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleTaskQuery");
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		String prjId=myTask.getSchedule().getProject().getId().toString();
		view.getFilter().appendFilterItem("schedule.project.id",prjId);
		f7.setEntityViewInfo(view);
		ObjectValueRender renderer=new ObjectValueRender();
		renderer.setFormat(new BizDataFormat("$number$"));
		getTable().getColumn("number").setEditor(new KDTDefaultCellEditor(f7));
		getTable().getColumn("number").setRenderer(renderer);
		getTable().getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		getTable().getColumn("name").getStyleAttributes().setLocked(true);
		f7.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				
			}
		});
		
		//任务名称
		
		//搭接关系
		KDComboBox linkBox=new KDComboBox();
		linkBox.addItems(TaskLinkTypeEnum.getEnumList().toArray());
		getTable().getColumn("linkType").setEditor(new KDTDefaultCellEditor(linkBox));
		getTable().getColumn("linkType").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		//搭接时间
		KDFormattedTextField linkTimes=new KDFormattedTextField();
		linkTimes.setDataType(KDFormattedTextField.INTEGER_TYPE);
		linkTimes.setPrecision(0);
		linkTimes.setRemoveingZeroInDispaly(true);
		linkTimes.setRemoveingZeroInEdit(true);
		getTable().getColumn("linkTimes").setEditor(new KDTDefaultCellEditor(linkTimes));
		getTable().getColumn("linkTimes").getStyleAttributes().setLocked(false);
		getTable().getColumn("linkTimes").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		getTable().addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	
	protected void actionDelete_Performed(ActionEvent e) {
		IRow row = FDCTableHelper.getSelectedRow(getTable());
		if(row==null){
			FDCMsgBox.showInfo("请先选择行");
			SysUtil.abort();
		}
		getTable().removeRow(row.getRowIndex());
	}

	protected void actionAddNew_Performed(ActionEvent e) {
		IRow row = getTable().addRow();
		row.setUserObject(createDependency());
		row.getCell("linkTimes").setValue(FDCHelper.ZERO);
		row.getCell("linkType").setValue(TaskLinkTypeEnum.FINISH_START);
		
	}

	private void loadRow(IRow row){
		FDCScheduleTaskInfo taskInfo=(FDCScheduleTaskInfo)row.getUserObject();
		if(taskInfo==null){
			return;
		}
		row.getCell("number").setValue(taskInfo.getLongNumber().replace('!', '.'));
		row.getCell("name").setValue(taskInfo.getName());
		if(taskInfo.get("type")==null){
			row.getCell("linkType").setValue("完成-开始");
		}else if("1finish_start".equals(taskInfo.get("type"))){
			row.getCell("linkType").setValue("完成-开始");
		}
		else if("2start_start".equals(taskInfo.get("type"))){
			row.getCell("linkType").setValue("开始-开始");
		}
		else if("3finish_finish".equals(taskInfo.get("type"))){
			row.getCell("linkType").setValue("完成-完成");
		}
		//row.getCell("linkType").setValue(taskInfo.get("type")==null?TaskLinkTypeEnum.FINISH_START:taskInfo.get("type"));
		row.getCell("linkTimes").setValue(taskInfo.get("difference"));
	}
	public void load(){
		//getTaskInfo();从这里得到搭接关系
		getTable().removeRows();
		FDCScheduleTaskInfo info = getTaskInfo();
		if(info==null){
			return;
		}
		List dependantTasks = (List) info.get("dependantTasks");
		if(dependantTasks==null){
			return;
		}
		for(Iterator it=dependantTasks.iterator();it.hasNext();){
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo)it.next();
			if(task==null){
				continue;
			}
			IRow row = getTable().addRow();
			row.setUserObject(task);
			loadRow(row);
		}
	}
	
	/**
	 * 将结果更新到Info 的depend分录
	 */
	public void store(){
		ScheduleNewTaskEntryInfo newTaskEntryInfo = getNewTaskEntryInfo();
		if(newTaskEntryInfo==null){
			return;
		}
		newTaskEntryInfo.getPrefixEntrys().clear();
		for(int i=0;i<getTable().getRowCount();i++){
			IRow row=getTable().getRow(i);
			if(row.getUserObject()!=null){
				ScheduleAdjustPrefixTaskInfo dependInfo=(ScheduleAdjustPrefixTaskInfo)row.getUserObject();
				if(dependInfo!=null){
					dependInfo.setTask(newTaskEntryInfo);
					dependInfo.setPrefixTask((FDCScheduleTaskInfo)row.getCell("number").getValue());
					dependInfo.setType((TaskLinkTypeEnum)row.getCell("linkType").getValue());
					Integer value = (Integer)row.getCell("linkTimes").getValue();
					if(value!=null){
						dependInfo.setDifference(value.intValue());
					}else{
						dependInfo.setDifference(0);
					}
					newTaskEntryInfo.getPrefixEntrys().add(dependInfo);
				}
			}
		}
	}
	
	private void verify(){
		
		ScheduleNewTaskEntryInfo newTaskEntryInfo = getNewTaskEntryInfo();
		String taskNumber=newTaskEntryInfo.getNumber();
		
		//前置任务不能是当前任务的上下级,或自己
		for(int i=0;i<getTable().getRowCount();i++){
			IRow row=getTable().getRow(i);
			if(row.getUserObject()==null
					||row.getCell("number").getValue()==null
					||row.getCell("linkType").getValue()==null){
				//每一行必须录入task
				FDCMsgBox.showConfirm2(taskPropertiesUI, "前置任务,搭接关系不能为空！");
				SysUtil.abort();
			}
			FDCScheduleTaskInfo prefixTask=(FDCScheduleTaskInfo)row.getCell("number").getValue();
			if(prefixTask.getNumber()!=null){
				String prefixNumber=prefixTask.getNumber();
				if(prefixNumber.startsWith(taskNumber+".")||taskNumber.startsWith(prefixNumber+".")){
					FDCMsgBox.showConfirm2(taskPropertiesUI, "前置任务不能为当前任务的上下级");
					SysUtil.abort();
				}
			}
		}
		//前置任务不能重复
		for(int i=0;i<getTable().getRowCount();i++){
			IRow row=getTable().getRow(i);
			FDCScheduleTaskInfo prefixTask=(FDCScheduleTaskInfo)row.getCell("number").getValue();
			for(int j=i+1;j<getTable().getRowCount();j++){
				IRow afterRow=getTable().getRow(j);
				FDCScheduleTaskInfo prefixTask2=(FDCScheduleTaskInfo)afterRow.getCell("number").getValue();
				if(prefixTask.getId().equals(prefixTask2.getId())){
					FDCMsgBox.showConfirm2(taskPropertiesUI, "前置任务不能重复");
					SysUtil.abort();
				}
			}
		}
	}
	protected void table_editStopped(KDTEditEvent e) {
		IRow row=getTable().getRow(e.getRowIndex());
		if(getTable().getColumnIndex("number")==e.getColIndex()){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)e.getValue();
			if(task!=null){
				row.getCell("name").setValue(task.getName());
				ScheduleAdjustPrefixTaskInfo scheduleAdjustPrefixTaskInfo=(ScheduleAdjustPrefixTaskInfo)row.getUserObject();
				scheduleAdjustPrefixTaskInfo.setPrefixTask(task);
				loadRow(row);
			}else{
				row.getCell("name").setValue(null);
			}
		}
		
		//计算搭接时间
		ScheduleNewTaskEntryInfo newTaskEntryInfo = getNewTaskEntryInfo();
		ScheduleCalendarInfo calendarInfo=(ScheduleCalendarInfo)newTaskEntryInfo.get("ScheduleCalendarInfo");
		ScheduleAdjustPrefixTaskInfo prefixTaskEntry=(ScheduleAdjustPrefixTaskInfo)row.getUserObject();
		if(newTaskEntryInfo!=null&&prefixTaskEntry.getPrefixTask()!=null){
			FDCScheduleTaskInfo prefixTask=prefixTaskEntry.getPrefixTask();
			int linkTimes=0;
			BigDecimal effectTimes=null;
			if(prefixTaskEntry.getType()==TaskLinkTypeEnum.FINISH_START){
				effectTimes=ScheduleCalendarHelper.getEffectTimes(prefixTask.getEnd(), newTaskEntryInfo.getStart(), calendarInfo);
			}
			if(prefixTaskEntry.getType()==TaskLinkTypeEnum.FINISH_FINISH){
				effectTimes=ScheduleCalendarHelper.getEffectTimes(prefixTask.getEnd(), newTaskEntryInfo.getEnd(), calendarInfo);
			}
			if(prefixTaskEntry.getType()==TaskLinkTypeEnum.START_START){
				effectTimes=ScheduleCalendarHelper.getEffectTimes(prefixTask.getStart(), newTaskEntryInfo.getStart(), calendarInfo);
			}
			linkTimes=effectTimes.intValue()-1;
			prefixTaskEntry.setDifference(linkTimes);
			row.getCell("linkTimes").setValue(new Integer(linkTimes));
		}
		
	}

	private KDTable getTable(){
		return table;
	}
	
    /**
     * create a new dependency
     * @param newTask
     * @return
     * @throws TaskDependencyException
     */
    private ScheduleAdjustPrefixTaskInfo createDependency(){
    	ScheduleAdjustPrefixTaskInfo depend=new ScheduleAdjustPrefixTaskInfo();
    	depend.setType(TaskLinkTypeEnum.FINISH_START);
    	depend.setDifference(0);
        return depend;
    }
    
    public void commit(){
    	if(taskPropertiesUI.isExecuting()||taskPropertiesUI.getOprtState().equals(OprtState.VIEW)){
    		return;
    	}
    	this.verify();
    	this.store();
    }

	public void setEditUIStatus() {
		
	}

	public void setExecutingUIStatus() {
		this.getTable().getStyleAttributes().setLocked(true);
		Object[] buttons = taskPropertiesUI.contPredecessorPanel.getButtons();
		for(int i=0;i<buttons.length;i++){
			if(buttons[i] instanceof JComponent){
				((JComponent)buttons[i]).setEnabled(false);
			}
		}
	}

	public void setViewUIStatus() {
		this.getTable().getStyleAttributes().setLocked(true);
		Object[] buttons = taskPropertiesUI.contPredecessorPanel.getButtons();
		for(int i=0;i<buttons.length;i++){
			if(buttons[i] instanceof JComponent){
				((JComponent)buttons[i]).setEnabled(false);
			}
		}
	}
	
	private FDCScheduleTaskInfo getTaskInfo(){
		return myTask;
	}
	private FDCScheduleInfo getScheduleInfo(){
		FDCScheduleTaskInfo task=myTask;
		if(task!=null){
			return task.getSchedule();
		}
		return null;
	}
	
	private ScheduleNewTaskEntryInfo getNewTaskEntryInfo(){
		if(getTaskInfo()!=null){
			return (ScheduleNewTaskEntryInfo)getTaskInfo().get("ScheduleNewTaskEntryInfo");
		}
		return null;
	}
}

