package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskWorkResultEntryInfo extends AbstractTaskWorkResultEntryInfo implements Serializable 
{
    public TaskWorkResultEntryInfo()
    {
        super();
    }
    protected TaskWorkResultEntryInfo(String pkField)
    {
        super(pkField);
    }
}