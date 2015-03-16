package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class PlanInfo extends AbstractPlanInfo implements Serializable 
{
    public PlanInfo()
    {
        super();
    }
    protected PlanInfo(String pkField)
    {
        super(pkField);
    }
}