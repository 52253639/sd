package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class CostSplitEntryInfo extends AbstractCostSplitEntryInfo implements Serializable 
{
    public CostSplitEntryInfo()
    {
        super();
    }
    protected CostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}