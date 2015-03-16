package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MarketChargePaymentEntryInfo extends AbstractMarketChargePaymentEntryInfo implements Serializable 
{
    public MarketChargePaymentEntryInfo()
    {
        super();
    }
    protected MarketChargePaymentEntryInfo(String pkField)
    {
        super(pkField);
    }
}