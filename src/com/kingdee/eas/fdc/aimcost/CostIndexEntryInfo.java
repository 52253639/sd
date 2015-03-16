package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class CostIndexEntryInfo extends AbstractCostIndexEntryInfo implements Serializable 
{
    public CostIndexEntryInfo()
    {
        super();
    }
    protected CostIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
}