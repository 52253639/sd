package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class AgencyInfo extends AbstractAgencyInfo implements Serializable 
{
    public AgencyInfo()
    {
        super();
    }
    protected AgencyInfo(String pkField)
    {
        super(pkField);
    }
}