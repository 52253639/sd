package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class CustomerChangeNameInfo extends AbstractCustomerChangeNameInfo implements Serializable 
{
    public CustomerChangeNameInfo()
    {
        super();
    }
    protected CustomerChangeNameInfo(String pkField)
    {
        super(pkField);
    }
}