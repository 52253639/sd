package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.shape.PaintCellRenderer;
import net.sourceforge.ganttproject.shape.ShapeConstants;
import net.sourceforge.ganttproject.shape.ShapePaint;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.TaskMutator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.MileStoneStatusEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;

public class PlanTaskMainPanelHelper  implements ITaskPanelHelper {
	private GanttLanguage language = GanttLanguage.getInstance(); // language
	private final FDCPlanTaskPropertyUI taskProperties;
	private final FDCScheduleTaskInfo task;
	public PlanTaskMainPanelHelper(FDCPlanTaskPropertyUI ui) {
		taskProperties=ui;
		task=ui.getSelectTask();
		initCtrl();
	}
    
    public void initCtrl(){
    	taskProperties.comboxPriority.removeAllItems();
    	taskProperties.comboxPriority.addItem(language.getText("low"));
    	taskProperties.comboxPriority.addItem(language.getText("normal"));
    	taskProperties.comboxPriority.addItem(language.getText("hight"));
    	taskProperties.comboxPriority.setEditable(false);
    	taskProperties.txtProcess.setEditable(false);
    	taskProperties.pkActualDate.setEditable(false);
    	taskProperties.pkActualDate.setEnabled(false);
    	taskProperties.pkActualStartDate.setEditable(false);
    	taskProperties.pkActualStartDate.setEnabled(false);
    	
    	//gantt project's shape Renderer
    	taskProperties.comboxShape.removeAllItems();
    	taskProperties.comboxShape.setRenderer(new PaintCellRenderer());
    	taskProperties.comboxShape.addItems(ShapeConstants.PATTERN_LIST);
/*    	taskProperties.txtWBSNumber.setEnabled(false);
    	taskProperties.txtName.setEnabled(false);
    	taskProperties.txtEffectTimes.setRequired(true);
    	taskProperties.txtNatureTimes.setEnabled(false);*/
    	taskProperties.txtStartDate.setSupportedEmpty(false);
    	taskProperties.txtEndDate.setSupportedEmpty(false);
    	taskProperties.f7Part.setEnabled(false);
    	DataChangeListener myDateListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				date_DataChanged(e);
			}
		};
		taskProperties.txtStartDate.addDataChangeListener(myDateListener);
    	
    	taskProperties.txtEndDate.addDataChangeListener(myDateListener);
    	
    	ScheduleClientHelper.setPersonF7(taskProperties.prmtManager, taskProperties, SysContext.getSysContext().getCurrentCtrlUnit()!=null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():null);
    	ScheduleClientHelper.setPersonF7(taskProperties.prmtNoter, taskProperties, SysContext.getSysContext().getCurrentCtrlUnit()!=null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():null);
    	ScheduleClientHelper.setPersonF7(taskProperties.prmtAdministrator, taskProperties, SysContext.getSysContext().getCurrentCtrlUnit()!=null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():null);
    	ScheduleClientHelper.setPersonF7(taskProperties.prmtExecor, taskProperties, SysContext.getSysContext().getCurrentCtrlUnit()!=null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():null);
    	ScheduleClientHelper.setPersonF7(taskProperties.prmtRistResPerson, taskProperties, SysContext.getSysContext().getCurrentCtrlUnit()!=null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():null);
    }
    
    /**
     * 任务基本信息加载
     */
    public void load(){
    	FDCScheduleTaskInfo taskInfo=this.task;
    	//FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo)task.getScheduleTaskInfo();
    	taskProperties.txtWBSNumber.setText(task.getLongNumber().replace('!', '.'));
    	taskProperties.txtName.setText(taskInfo.getName());
    	taskProperties.txtStartDate.setValue(taskInfo.getStart());
    	taskProperties.txtEndDate.setValue(taskInfo.getEnd());
    	taskProperties.txtEffectTimes.setValue(taskInfo.getEffectTimes());
    	taskProperties.txtNatureTimes.setValue(taskInfo.getNatureTimes());
    	taskProperties.f7Part.setValue(taskInfo.get("adminDept"));
    	taskProperties.txtProcess.setValue(taskInfo.getComplete());
    	//taskProperties.txtColor.setColor((taskInfo.getColor());
    	taskProperties.comboxPriority.setSelectedIndex(taskInfo.getPriority());
    	taskProperties.prmtAdministrator.setValue(taskInfo.getAdministrator());
    	taskProperties.prmtRistResPerson.setValue(taskInfo.getRiskResPerson());
    	taskProperties.prmtNoter.setValue(taskInfo.getNoter());
    	taskProperties.prmtManager.setValue(taskInfo.getManager());
    	taskProperties.pkActualDate.setValue(taskInfo.getActualEndDate());
    	taskProperties.pkActualStartDate.setValue(taskInfo.getActualStartDate());
    	taskProperties.txtDescription.setText(taskInfo.getDescription());
    	taskProperties.chkIsMileStone.setSelected(taskInfo.isMeeting());
    	taskProperties.txtCompletePercent.setMaximumValue(FDCHelper.MAX_VALUE);
    	taskProperties.txtCompletePercent.setMinimumValue(FDCHelper.MIN_VALUE);
    	taskProperties.txtCompletePercent.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	taskProperties.txtCompletePercent.setValue(taskInfo.getComplete());

//        if (task.shapeDefined()) {
//            for (int j = 0; j < ShapeConstants.PATTERN_LIST.length; j++) {
//                if (task.getShape().equals(ShapeConstants.PATTERN_LIST[j])) {
//                	taskProperties.comboxShape.setSelectedIndex(j);
//                    break;
//                }
//            }
//        }
    	taskProperties.chkKey.setSelected(taskInfo.isIsKey());
    	taskProperties.txtDescription.setText(taskInfo.getDescription());
    	//other filed bind
//        this.prmtAdministrator.setValue(value)
    	taskProperties.prmtExecor.setValue(taskInfo.getAdminPerson());
    	taskProperties.chkIsMileStone.setEnabled(true);
    	taskProperties.comboxMileStoneStatus.addItems(MileStoneStatusEnum.getEnumList().toArray());
    	if(taskInfo.isMeeting()){
    		taskProperties.comboxMileStoneStatus.setSelectedItem(taskInfo.getMileStoneStatus());
    	}else{
    		taskProperties.comboxMileStoneStatus.setSelectedItem(MileStoneStatusEnum.end);
    	}
    	taskProperties.comboxMileStoneStatus.setEnabled(false);
    	taskProperties.chkIsMileStone.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				if(taskProperties.chkIsMileStone.isSelected()){
					taskProperties.comboxMileStoneStatus.setEnabled(true);
				}else{
					taskProperties.comboxMileStoneStatus.setEnabled(false);
				}
			}
    	});
    	taskProperties.chkIsMileStone.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				if(taskProperties.chkIsMileStone.isSelected()){
					taskProperties.comboxMileStoneStatus.setEnabled(true);
				}else{
					taskProperties.comboxMileStoneStatus.setEnabled(false);
				}
			}
    	});
    }
    
    public void commit(){
//    	if(taskProperties.isExecuting()||taskProperties.getOprtState().equals(OprtState.VIEW)){
//    		return;
//    	}
//    	FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo)task.getScheduleTaskInfo();
//    	if(taskProperties.isExecuting()){
//    		if(taskProperties.pkActualStartDate.isEnabled() && taskProperties.pkActualStartDate.isEnabled()){
//    			if(taskProperties.pkActualStartDate.getValue() != null){
//    				try {
//    					FDCSQLBuilder builder = new FDCSQLBuilder();
//    					builder.appendSql("update T_SCH_FDCScheduleTask set FActualstartDate=? where FID=?");
//    					builder.addParam((Date) taskProperties.pkActualStartDate.getValue());
//    					builder.addParam(taskInfo.getId().toString());
//						builder.executeUpdate();
//						taskInfo.setActualStartDate((Date) taskProperties.pkActualStartDate.getValue());
//					} catch (BOSException e) {
//						e.printStackTrace();
//						taskProperties.handUIException(e);
//					}
//    	    	}
//    			return;
//    		}else{
//    			return;
//    		}
//    	}
//		
//		
//		
//		if(!taskProperties.txtStartDate.getValue().equals(taskInfo.getStart())){
//			TaskMutator mutator = task.createMutator();
//			mutator.setStart(ScheduleParserHelper.parseDateToGanttCalendar((Date)taskProperties.txtStartDate.getValue()));
//	    	mutator.setDuration(task.getManager().createLength(FDCHelper.toBigDecimal(taskProperties.txtEffectTimes.getBigDecimalValue()).intValue()));
//	    	
//	    	mutator.commit();
//		}
//		if(FDCHelper.toBigDecimal(taskProperties.txtProcess.getNumberValue()).intValue()!=task.getCompletionPercentage()){
//			TaskMutator mutator = task.createMutator();
//			mutator.setCompletionPercentage(FDCHelper.toBigDecimal(taskProperties.txtProcess.getNumberValue()).intValue());
//			
//			mutator.commit();
//		}
//		
//		//color
//		
//		if(!taskProperties.txtColor.getColor().equals(task.getColor())){
//			task.setColor(taskProperties.txtColor.getColor());
//		}
//		//shape 
//		if((task.getShape()==null&&taskProperties.comboxShape.getSelectedIndex()!=0)||(task.getShape()!=null&&
//				(!task.getShape().equals(taskProperties.comboxShape.getSelectedItem())))){
//			task.setShape((ShapePaint)taskProperties.comboxShape.getSelectedItem());
//		}
//		
//		if(task.getShape()!=null){
//			task.setShape(new ShapePaint(task.getShape(),
//                    Color.white, task.getColor()));
//		}
//		task.setPriority(taskProperties.comboxPriority.getSelectedIndex());
//		if(taskProperties.getScheduleGanttProject()==null){
//			taskInfo.setAdminDept((FullOrgUnitInfo)taskProperties.f7Part.getValue());
//			taskInfo.setAdminPerson((PersonInfo)taskProperties.prmtExecor.getValue());
//		}else{
//			try {
//				task.getCustomValues().setValue(GanttTreeTableModelExt.strColAdminDept, taskProperties.f7Part.getValue());
//				task.getCustomValues().setValue(GanttTreeTableModelExt.stradminPerson, taskProperties.prmtExecor.getValue());
//				task.getCustomValues().setValue(GanttTreeTableModelExt.strColActualEndDate, taskProperties.pkActualDate.getValue());
//				
//				
//			} catch (CustomColumnsException e) {
//				e.printStackTrace();
//				taskProperties.handUIException(e);
//			}
//		}
//    	//update other Info
//    	taskInfo.setManager((PersonInfo)taskProperties.prmtManager.getValue());
//    	taskInfo.setNoter((PersonInfo)taskProperties.prmtNoter.getValue());
//    	taskInfo.setAdministrator((PersonInfo)taskProperties.prmtAdministrator.getValue());
//    	taskInfo.setRiskResPerson((PersonInfo)taskProperties.prmtRistResPerson.getValue());
//    	taskInfo.setIsKey(taskProperties.chkKey.isSelected());
//    	taskInfo.setDescription(taskProperties.txtDescription.getText());
////    	if(taskProperties.pkActualStartDate.getValue() != null){
////    		taskInfo.setActualStartDate((Date) taskProperties.pkActualStartDate.getValue());
////    	}
//    	if(taskProperties.chkIsMileStone.isSelected()){
//    		taskInfo.setMeeting(true);
//    	}else{
//    		taskInfo.setMeeting(false);
//    	}
//    	taskInfo.setMileStoneStatus((MileStoneStatusEnum) taskProperties.comboxMileStoneStatus.getSelectedItem());
    }

	private void date_DataChanged(DataChangeEvent e) {
		//不支持空
		Date beginDate=(Date)taskProperties.txtStartDate.getValue();
		Date endDate = (Date)taskProperties.txtEndDate.getValue();
//		long duration=ScheduleParserHelper.getDuration(task, beginDate, endDate); 
//		TaskLength natureLength=task.getManager().createLength(task.getDuration().getTimeUnit(),beginDate , endDate);
//		taskProperties.txtEffectTimes.setValue(FDCHelper.toBigDecimal(String.valueOf(duration+1)));
//		taskProperties.txtNatureTimes.setValue(FDCHelper.toBigDecimal(String.valueOf(natureLength.getLength())));
	}

	public void setEditUIStatus() {
		// TODO Auto-generated method stub
		
	}

	public void setExecutingUIStatus() {
		taskProperties.lockContainer(taskProperties.mainPanel);		
		taskProperties.pkActualStartDate.setEnabled(true);
	}

	public void setViewUIStatus() {
		taskProperties.lockContainer(taskProperties.mainPanel);
	}
    
}



