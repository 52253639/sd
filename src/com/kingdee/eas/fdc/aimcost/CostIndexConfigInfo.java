package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class CostIndexConfigInfo extends AbstractCostIndexConfigInfo implements Serializable 
{
    public CostIndexConfigInfo()
    {
        super();
    }
    protected CostIndexConfigInfo(String pkField)
    {
        super(pkField);
    }
}