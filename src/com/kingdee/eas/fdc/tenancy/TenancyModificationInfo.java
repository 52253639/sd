package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyModificationInfo extends AbstractTenancyModificationInfo implements Serializable 
{
    public TenancyModificationInfo()
    {
        super();
    }
    protected TenancyModificationInfo(String pkField)
    {
        super(pkField);
    }
}