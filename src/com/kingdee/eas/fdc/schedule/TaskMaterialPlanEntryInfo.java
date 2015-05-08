package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskMaterialPlanEntryInfo extends AbstractTaskMaterialPlanEntryInfo implements Serializable 
{
    public TaskMaterialPlanEntryInfo()
    {
        super();
    }
    protected TaskMaterialPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
}