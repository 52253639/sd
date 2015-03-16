package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class CustomerChangeLogInfo extends AbstractCustomerChangeLogInfo implements Serializable 
{
    public CustomerChangeLogInfo()
    {
        super();
    }
    protected CustomerChangeLogInfo(String pkField)
    {
        super(pkField);
    }
}