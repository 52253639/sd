package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenLiquidatedInfo extends AbstractTenLiquidatedInfo implements Serializable 
{
    public TenLiquidatedInfo()
    {
        super();
    }
    protected TenLiquidatedInfo(String pkField)
    {
        super(pkField);
    }
}