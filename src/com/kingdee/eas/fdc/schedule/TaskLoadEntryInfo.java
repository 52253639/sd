package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskLoadEntryInfo extends AbstractTaskLoadEntryInfo implements Serializable 
{
    public TaskLoadEntryInfo()
    {
        super();
    }
    protected TaskLoadEntryInfo(String pkField)
    {
        super(pkField);
    }
}