package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MovementPlanInfo extends AbstractMovementPlanInfo implements Serializable 
{
    public MovementPlanInfo()
    {
        super();
    }
    protected MovementPlanInfo(String pkField)
    {
        super(pkField);
    }
}