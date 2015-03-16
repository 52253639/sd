package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MovementPlanEntryInfo extends AbstractMovementPlanEntryInfo implements Serializable 
{
    public MovementPlanEntryInfo()
    {
        super();
    }
    protected MovementPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
}