package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class NewPlanIndexInfo extends AbstractNewPlanIndexInfo implements Serializable 
{
    public NewPlanIndexInfo()
    {
        super();
    }
    protected NewPlanIndexInfo(String pkField)
    {
        super(pkField);
    }
}