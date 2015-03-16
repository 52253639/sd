package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyBillInfo extends AbstractTenancyBillInfo implements Serializable 
{
    public TenancyBillInfo()
    {
        super();
    }
    protected TenancyBillInfo(String pkField)
    {
        super(pkField);
    }
}