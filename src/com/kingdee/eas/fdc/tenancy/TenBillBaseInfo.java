package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenBillBaseInfo extends AbstractTenBillBaseInfo implements Serializable 
{
    public TenBillBaseInfo()
    {
        super();
    }
    protected TenBillBaseInfo(String pkField)
    {
        super(pkField);
    }
}