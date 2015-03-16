package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MarketManageInfo extends AbstractMarketManageInfo implements Serializable 
{
    public MarketManageInfo()
    {
        super();
    }
    protected MarketManageInfo(String pkField)
    {
        super(pkField);
    }
}