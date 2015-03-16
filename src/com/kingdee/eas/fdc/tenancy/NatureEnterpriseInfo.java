package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class NatureEnterpriseInfo extends AbstractNatureEnterpriseInfo implements Serializable 
{
    public NatureEnterpriseInfo()
    {
        super();
    }
    protected NatureEnterpriseInfo(String pkField)
    {
        super(pkField);
    }
}