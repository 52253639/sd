package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class MarketingUnitMemberInfo extends AbstractMarketingUnitMemberInfo implements Serializable 
{
    public MarketingUnitMemberInfo()
    {
        super();
    }
    protected MarketingUnitMemberInfo(String pkField)
    {
        super(pkField);
    }
}