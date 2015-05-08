package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskAdjustManager;
import com.kingdee.eas.fdc.schedule.TaskAdjustManager.TaskAdjustManagerItem;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;

public class ScheduleAdjustRptHelper {
	private final KDTable table;
	private final ScheduleAdjustReqBillEditUI ui;
	private final ScheduleCalendarInfo calendarInfo;
	public ScheduleAdjustRptHelper(ScheduleAdjustReqBillEditUI ui) {
		this.ui = ui;
		table = ui.getRpTable();
		calendarInfo=ui.getBaseScheduleInfo().getCalendar();
		init();
	}

	private KDTable getTable() {
		return table;
	}


	private void init(){
		initTable();
		ui.contRpt.setEnableActive(false);
	}
	
	private void initTable() {
		table.checkParsed();
		table.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		table.getStyleAttributes().setLocked(true);
	}
	
	protected void fillTable(){
//		if(!hasChange){
//			return;
//		}
		ui.storeFields();
		getTaskList().adjustTasks();
		hasChange=false;
		_fillTable();
		
		//update total
		ui.txtTotalTimes.setValue(getTotalTimes());
	}
	
	private void _fillTable(){
		table.removeRows();
		int size=taskList.size();
		for(int i=0;i<size;i++){
			TaskAdjustManagerItem item = taskList.get(i);
			if(item.getNewTask() == null && item.getOldTask() == null){
				continue;
			}
			IRow row=table.addRow();
			fillItem(row,item);
		}
	}
	
	private void fillItem(IRow row,TaskAdjustManagerItem item) {
		FDCScheduleTaskInfo oldTask = item.getOldTask();
		FDCScheduleTaskInfo newTask = item.getNewTask();
		
		
		
		if(oldTask!=null){
			row.getCell("number").setValue(oldTask.getNumber());
			row.getCell("name").setValue(oldTask.getName());
			row.getCell("startDate").setValue(oldTask.getStart());
			row.getCell("endDate").setValue(oldTask.getEnd());
			row.getCell("effectTimes").setValue(oldTask.getEffectTimes());
			
			Integer zero = new Integer(0);
			row.getCell("effectTimesDiff").setValue(zero);
			row.getCell("startDateDiff").setValue(zero);
			row.getCell("endDateDiff").setValue(zero);
		}
		
		if(newTask!=null){
			if(oldTask==null){
				row.getCell("number").setValue(newTask.getNumber());
				row.getCell("name").setValue(newTask.getName());
			}
			row.getCell("adjustStartDate").setValue(newTask.getStart());
			row.getCell("adjustEndDate").setValue(newTask.getEnd());
			row.getCell("adjustEffectTimes").setValue(newTask.getEffectTimes());
			
			Color red = new Color(255,33,21,240);//Color.RED.brighter();
			Color blue = new Color(170,85,255,250);
			if(oldTask!=null){
//				根据所产生的差值不同判断是应该+1还是-1
				Integer startDiff = new Integer("0");
				Integer endDiff = new Integer("0");
				
				if(ScheduleCalendarHelper.getEffectTimes(oldTask.getStart(), newTask.getStart(), calendarInfo).intValue() > 0){
					startDiff = new Integer(ScheduleCalendarHelper.getEffectTimes(oldTask.getStart(), newTask.getStart(), calendarInfo).intValue() -1);
				}else if(ScheduleCalendarHelper.getEffectTimes(oldTask.getStart(), newTask.getStart(), calendarInfo).intValue() < 0){
					startDiff = new Integer(ScheduleCalendarHelper.getEffectTimes(oldTask.getStart(), newTask.getStart(), calendarInfo).intValue() +1);
				}
				
				if(ScheduleCalendarHelper.getEffectTimes(oldTask.getEnd(), newTask.getEnd(), calendarInfo).intValue() > 0){
					endDiff = new Integer(ScheduleCalendarHelper.getEffectTimes(oldTask.getEnd(), newTask.getEnd(), calendarInfo).intValue() -1);
				}else if(ScheduleCalendarHelper.getEffectTimes(oldTask.getEnd(), newTask.getEnd(), calendarInfo).intValue() < 0){
					endDiff = new Integer(ScheduleCalendarHelper.getEffectTimes(oldTask.getEnd(), newTask.getEnd(), calendarInfo).intValue() +1);
				}
				row.getCell("startDateDiff").setValue(startDiff);
				row.getCell("endDateDiff").setValue(endDiff);
				Integer effectDiff = new Integer(FDCHelper.subtract(newTask.getEffectTimes(), oldTask.getEffectTimes()).intValue());
				row.getCell("effectTimesDiff").setValue(effectDiff);
				if(effectDiff.intValue()>0){
					row.getStyleAttributes().setBackground(red);
				}else if(effectDiff.intValue()<0){
					row.getStyleAttributes().setBackground(blue);
				}
			}else{
				row.getCell("effectTimesDiff").setValue(newTask.getEffectTimes());
				row.getStyleAttributes().setBackground(red);
			}
		}
		
	}

	private boolean hasChange=false;
	
	/**
	 * 是否改变，在生成影响计划后会置hasChange=false；
	 */
	public void setChanged(){
		hasChange=true;
	}
	
	private TaskAdjustManager taskList=null;
	TaskAdjustManager getTaskList(){
		if(taskList==null){
			taskList=new TaskAdjustManager(ui.getBaseScheduleInfo());
		}
		return taskList;
	}
	
	
	/**
	 * 影响的总工期:调整后有效工期-调整前有效工期
	 * @return
	 */
	protected BigDecimal getTotalTimes(){
		if(calendarInfo==null){
			return FDCHelper.ZERO;
		}
		TaskAdjustManager taskList = getTaskList();
		if(hasChange){
			taskList.adjustTasks();
			hasChange=false;
		}
		int size=taskList.size();
		Date oldStartDate=null;
		Date newStartDate=null;
		Date oldEndDate=null;
		Date newEndDate=null;
		for(int i=0;i<size;i++){
			TaskAdjustManagerItem item=taskList.get(i);
			FDCScheduleTaskInfo oldTask = item.getOldTask();
			FDCScheduleTaskInfo newTask = item.getNewTask();
			if(oldTask!=null){
				//开始时间
				if(oldStartDate==null){
					oldStartDate=oldTask.getStart();
				}else{
					if(oldStartDate.compareTo(oldTask.getStart())>0	){
						oldStartDate=oldTask.getStart();
					}
				}
				//结束时间
				if(oldEndDate==null){
					oldEndDate=oldTask.getEnd();
				}else{
					if(oldEndDate.compareTo(oldTask.getEnd())<0	){
						oldEndDate=oldTask.getEnd();
					}
				}
			}
			
			if(newTask!=null){
				//开始时间
				if(newStartDate==null){
					newStartDate=newTask.getStart();
				}else{
					if(newStartDate.compareTo(newTask.getStart())>0	){
						newStartDate=newTask.getStart();
					}
				}
				
				if(newEndDate==null){
					newEndDate=newTask.getEnd();
				}else{
					if(newEndDate.compareTo(newTask.getEnd())<0	){
						newEndDate=newTask.getEnd();
					}
				}
			}else if(oldTask != null){
				//开始时间
				if(newStartDate==null){
					newStartDate=oldTask.getStart();
				}else if(oldTask != null){
					if(newStartDate.compareTo(oldTask.getStart())>0	){
						newStartDate=oldTask.getStart();
					}
				}
				
				//没有调整应该取之前的数据
				if(newEndDate==null){
					newEndDate=oldTask.getEnd();
				}else if(oldTask != null){
					if(newEndDate.compareTo(oldTask.getEnd())<0	){
						newEndDate=oldTask.getEnd();
					}
				}
			}
		}
		if(oldEndDate==null||newEndDate==null){
			return FDCHelper.ZERO;
		}
		BigDecimal oldTimes = ScheduleCalendarHelper.getEffectTimes(oldEndDate, oldStartDate, calendarInfo);
		BigDecimal newTimes = ScheduleCalendarHelper.getEffectTimes(newEndDate, newStartDate, calendarInfo);
		return FDCHelper.subtract(newTimes, oldTimes);
	}
}
