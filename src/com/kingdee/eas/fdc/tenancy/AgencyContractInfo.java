package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class AgencyContractInfo extends AbstractAgencyContractInfo implements Serializable 
{
    public AgencyContractInfo()
    {
        super();
    }
    protected AgencyContractInfo(String pkField)
    {
        super(pkField);
    }
}