package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class MarketingUnitInfo extends AbstractMarketingUnitInfo implements Serializable 
{
    public MarketingUnitInfo()
    {
        super();
    }
    protected MarketingUnitInfo(String pkField)
    {
        super(pkField);
    }
}