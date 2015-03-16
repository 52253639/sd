package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class DynamicCostInfo extends AbstractDynamicCostInfo implements Serializable 
{
    public DynamicCostInfo()
    {
        super();
    }
    protected DynamicCostInfo(String pkField)
    {
        super(pkField);
    }
}