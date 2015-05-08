package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustPrefixTaskInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo implements Serializable 
{
    public AbstractScheduleAdjustPrefixTaskInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustPrefixTaskInfo(String pkField)
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
     * Object: 进度调整任务依赖 's 前置任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getPrefixTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("prefixTask");
    }
    public void setPrefixTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("prefixTask", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E36F42B3");
    }
}