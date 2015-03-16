package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class CustomPlanIndexEntryInfo extends AbstractCustomPlanIndexEntryInfo implements Serializable 
{
    public CustomPlanIndexEntryInfo()
    {
        super();
    }
    protected CustomPlanIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
}