package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskMaterialPlanInfo extends AbstractTaskMaterialPlanInfo implements Serializable 
{
    public TaskMaterialPlanInfo()
    {
        super();
    }
    protected TaskMaterialPlanInfo(String pkField)
    {
        super(pkField);
    }
}