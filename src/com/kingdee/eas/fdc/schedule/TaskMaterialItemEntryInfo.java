package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskMaterialItemEntryInfo extends AbstractTaskMaterialItemEntryInfo implements Serializable 
{
    public TaskMaterialItemEntryInfo()
    {
        super();
    }
    protected TaskMaterialItemEntryInfo(String pkField)
    {
        super(pkField);
    }
}