package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyOrderInfo extends AbstractTenancyOrderInfo implements Serializable 
{
    public TenancyOrderInfo()
    {
        super();
    }
    protected TenancyOrderInfo(String pkField)
    {
        super(pkField);
    }
}