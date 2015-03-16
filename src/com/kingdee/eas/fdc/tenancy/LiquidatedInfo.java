package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class LiquidatedInfo extends AbstractLiquidatedInfo implements Serializable 
{
    public LiquidatedInfo()
    {
        super();
    }
    protected LiquidatedInfo(String pkField)
    {
        super(pkField);
    }
}