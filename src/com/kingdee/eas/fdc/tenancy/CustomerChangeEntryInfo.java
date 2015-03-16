package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class CustomerChangeEntryInfo extends AbstractCustomerChangeEntryInfo implements Serializable 
{
    public CustomerChangeEntryInfo()
    {
        super();
    }
    protected CustomerChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
}