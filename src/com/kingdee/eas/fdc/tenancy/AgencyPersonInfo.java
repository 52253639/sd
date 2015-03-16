package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class AgencyPersonInfo extends AbstractAgencyPersonInfo implements Serializable 
{
    public AgencyPersonInfo()
    {
        super();
    }
    protected AgencyPersonInfo(String pkField)
    {
        super(pkField);
    }
}