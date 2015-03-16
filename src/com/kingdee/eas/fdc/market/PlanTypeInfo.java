package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class PlanTypeInfo extends AbstractPlanTypeInfo implements Serializable 
{
    public PlanTypeInfo()
    {
        super();
    }
    protected PlanTypeInfo(String pkField)
    {
        super(pkField);
    }
}