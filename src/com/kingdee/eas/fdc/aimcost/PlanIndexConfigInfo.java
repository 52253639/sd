package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

import com.kingdee.eas.fdc.contract.AbstractContractWFTypeInfo;

public class PlanIndexConfigInfo extends AbstractPlanIndexConfigInfo implements Serializable 
{
    public PlanIndexConfigInfo()
    {
        super();
    }
    protected PlanIndexConfigInfo(String pkField)
    {
        super(pkField);
    }
}