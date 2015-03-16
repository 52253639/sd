package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class PlanEntryInfo extends AbstractPlanEntryInfo implements Serializable 
{
    public PlanEntryInfo()
    {
        super();
    }
    protected PlanEntryInfo(String pkField)
    {
        super(pkField);
    }
}