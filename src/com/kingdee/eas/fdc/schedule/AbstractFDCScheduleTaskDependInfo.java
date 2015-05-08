package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCScheduleTaskDependInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo implements Serializable 
{
    public AbstractFDCScheduleTaskDependInfo()
    {
        this("id");
    }
    protected AbstractFDCScheduleTaskDependInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房地产进度后置任务 's 任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("task", item);
    }
    /**
     * Object: 房地产进度后置任务 's 后置任务 property 
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
        return new BOSObjectType("C848A877");
    }
}