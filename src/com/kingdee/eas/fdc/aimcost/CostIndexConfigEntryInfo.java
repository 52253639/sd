package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class CostIndexConfigEntryInfo extends AbstractCostIndexConfigEntryInfo implements Serializable 
{
    public CostIndexConfigEntryInfo()
    {
        super();
    }
    protected CostIndexConfigEntryInfo(String pkField)
    {
        super(pkField);
    }
}