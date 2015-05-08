package com.kingdee.eas.fdc.schedule.framework.ext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.sourceforge.ganttproject.action.task.LinkTasksAction;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.TaskSelectionManager;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;

import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.util.KDGPCommonHelper;

public class LinkTasksActionExt extends LinkTasksAction {
    public LinkTasksActionExt(TaskManager taskManager, TaskSelectionManager selectionManager, UIFacade uiFacade) {
        super(taskManager, selectionManager, uiFacade);
    }
    protected void run(List selection) throws TaskDependencyException {
        for (int i=0; i<selection.size()-1; i++) {
            Task dependant = (Task) selection.get(i+1);
            Task dependee = (Task) selection.get(i);
            if(!KDGPCommonHelper.isScheduleTask(dependant)){
            	//不能连接到非本级任务
            	return;
            }
            
//            if(dependant instanceof KDTask){
//            	FDCScheduleTaskInfo fdcDependantTask = (FDCScheduleTaskInfo) ((KDTask)dependant).getScheduleTaskInfo();
//            	FDCScheduleTaskInfo fdcDependeeTask = (FDCScheduleTaskInfo) ((KDTask)dependee).getScheduleTaskInfo();
//            	if(fdcDependantTask != null && fdcDependantTask.getParent() != null){
//            		Date parentStart = fdcDependantTask.getParent().getStart();
//            		Date parentEnd = fdcDependantTask.getParent().getEnd();
//            		Date childStart = fdcDependeeTask.getEnd();
//            		Date childEnd = ScheduleCalendarHelper.getEndDate(childStart,new BigDecimal(fdcDependantTask.getDuration()), fdcDependantTask.getSchedule().getCalendar());
//            		if(parentStart.after(childStart) || parentEnd.before(childEnd)){
//            			FDCMsgBox.showError("被连接任务不能超过上级限制！");
//            			return;
//            		}
//            	}
//            }
            
			if (getTaskManager().getDependencyCollection().canCreateDependency(dependant, dependee)) {
				//处理如果新增的任务超过的话删除新增加的任务
				TaskDependency dependency = null;
				try {
					if (dependee instanceof KDTask) {
						KDTask task = (KDTask) dependee;
						ScheduleBaseInfo info = task.getScheduleTaskInfo().getScheduleBase();
						info.setBoolean("createDependency", true);
						info.remove("createDependencyFaile");
					}
					dependency = getTaskManager().getDependencyCollection().createDependency(dependant, dependee);
				} finally {
					if (dependee instanceof KDTask) {
						KDTask task = (KDTask) dependee;
						ScheduleBaseInfo info = task.getScheduleTaskInfo().getScheduleBase();
						if (dependency != null && info.getBoolean("createDependencyFaile")) {
							getTaskManager().getDependencyCollection().deleteDependency(dependency);
						}
						info.remove("createDependency");
						info.remove("createDependencyFaile");
					}
				}
			}
			
        }                
    }

    protected boolean isEnabled(List selection) {
        return selection.size()>=2;
    }
}