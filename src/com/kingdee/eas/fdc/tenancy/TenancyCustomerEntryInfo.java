package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyCustomerEntryInfo extends AbstractTenancyCustomerEntryInfo implements Serializable 
{
    public TenancyCustomerEntryInfo()
    {
        super();
    }
    protected TenancyCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
}