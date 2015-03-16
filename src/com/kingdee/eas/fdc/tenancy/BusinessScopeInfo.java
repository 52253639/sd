package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class BusinessScopeInfo extends AbstractBusinessScopeInfo implements Serializable 
{
    public BusinessScopeInfo()
    {
        super();
    }
    protected BusinessScopeInfo(String pkField)
    {
        super(pkField);
    }
}