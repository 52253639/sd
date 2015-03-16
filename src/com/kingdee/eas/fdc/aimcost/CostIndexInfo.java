package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class CostIndexInfo extends AbstractCostIndexInfo implements Serializable 
{
    public CostIndexInfo()
    {
        super();
    }
    protected CostIndexInfo(String pkField)
    {
        super(pkField);
    }
}