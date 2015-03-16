package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class CostEntryInfo extends AbstractCostEntryInfo implements Serializable 
{
    public CostEntryInfo()
    {
        super();
    }
    protected CostEntryInfo(String pkField)
    {
        super(pkField);
    }
}