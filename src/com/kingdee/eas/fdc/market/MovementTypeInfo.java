package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MovementTypeInfo extends AbstractMovementTypeInfo implements Serializable 
{
    public MovementTypeInfo()
    {
        super();
    }
    protected MovementTypeInfo(String pkField)
    {
        super(pkField);
    }
}