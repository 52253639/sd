package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustTaskDependInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo implements Serializable 
{
    public AbstractScheduleAdjustTaskDependInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustTaskDependInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 进度调整任务依赖 's 新增的任务 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo item)
    {
        put("task", item);
    }
    /**
     * Object: 进度调整任务依赖 's 后置任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getDependTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("dependTask");
    }
    public void setDependTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("dependTask", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("52B6FFCD");
    }
}