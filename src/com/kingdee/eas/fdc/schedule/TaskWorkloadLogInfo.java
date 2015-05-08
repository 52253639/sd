package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskWorkloadLogInfo extends AbstractTaskWorkloadLogInfo implements Serializable 
{
    public TaskWorkloadLogInfo()
    {
        super();
    }
    protected TaskWorkloadLogInfo(String pkField)
    {
        super(pkField);
    }
}