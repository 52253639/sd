package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyRentBillInfo extends AbstractTenancyRentBillInfo implements Serializable 
{
    public TenancyRentBillInfo()
    {
        super();
    }
    protected TenancyRentBillInfo(String pkField)
    {
        super(pkField);
    }
}