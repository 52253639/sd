package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskScheduleLogInfo extends AbstractTaskScheduleLogInfo implements Serializable 
{
    public TaskScheduleLogInfo()
    {
        super();
    }
    protected TaskScheduleLogInfo(String pkField)
    {
        super(pkField);
    }
}