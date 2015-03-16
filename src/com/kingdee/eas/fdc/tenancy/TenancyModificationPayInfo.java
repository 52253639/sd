package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class TenancyModificationPayInfo extends AbstractTenancyModificationPayInfo implements Serializable 
{
    public TenancyModificationPayInfo()
    {
        super();
    }
    protected TenancyModificationPayInfo(String pkField)
    {
        super(pkField);
    }
}