package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;

public class FDCScheduleTaskDependInfo extends AbstractFDCScheduleTaskDependInfo implements Serializable,IRESchTaskPredecessor 
{
    public FDCScheduleTaskDependInfo()
    {
        super();
    }
    protected FDCScheduleTaskDependInfo(String pkField)
    {
        super(pkField);
    }
	public IRESchTask getCurrentTask() {
		
		return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
	}
	
	public void setCurrentTask(IRESchTask task) {
		put("task", task);
	}

	public IRESchTask getPredecessor() {
	
		return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("dependTask");
	}
	public void setPredecessor(IRESchTask task) {
		put("dependTask", task);
	}
	
	public void setDifferenceDay(int item) {
		put("difference", new Integer(item));
	}
	
	public int getDifferenceDay() {
		Integer diff = (Integer) get("difference");
		if (diff != null) {
			return diff.intValue();
		}
		return 0;
	}
	
	public void setPredecessorType(TaskLinkTypeEnum item) {
		put("type", item);
	}	
	
	public TaskLinkTypeEnum getPredecessorType() {
		Object type = get("type");
		if (type instanceof String) {
			return TaskLinkTypeEnum.getEnum((String) type);
		}
		return (TaskLinkTypeEnum) type;
	}
	
	
}