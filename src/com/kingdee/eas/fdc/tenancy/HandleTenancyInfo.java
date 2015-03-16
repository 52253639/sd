package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class HandleTenancyInfo extends AbstractHandleTenancyInfo implements Serializable 
{
    public HandleTenancyInfo()
    {
        super();
    }
    protected HandleTenancyInfo(String pkField)
    {
        super(pkField);
    }
}