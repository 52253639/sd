package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyRentAdjustInfo extends AbstractTenancyRentAdjustInfo implements Serializable 
{
    public TenancyRentAdjustInfo()
    {
        super();
    }
    protected TenancyRentAdjustInfo(String pkField)
    {
        super(pkField);
    }
}