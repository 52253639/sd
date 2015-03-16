package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MarketTypeInfo extends AbstractMarketTypeInfo implements Serializable 
{
    public MarketTypeInfo()
    {
        super();
    }
    protected MarketTypeInfo(String pkField)
    {
        super(pkField);
    }
}