package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class CustomerChangeOldEntryInfo extends AbstractCustomerChangeOldEntryInfo implements Serializable 
{
    public CustomerChangeOldEntryInfo()
    {
        super();
    }
    protected CustomerChangeOldEntryInfo(String pkField)
    {
        super(pkField);
    }
}