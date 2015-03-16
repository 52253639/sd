package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class PlanIndexInfo extends AbstractPlanIndexInfo implements Serializable 
{
    public PlanIndexInfo()
    {
        super();
    }
    protected PlanIndexInfo(String pkField)
    {
        super(pkField);
    }
}