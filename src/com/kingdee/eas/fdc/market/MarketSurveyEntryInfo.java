package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MarketSurveyEntryInfo extends AbstractMarketSurveyEntryInfo implements Serializable 
{
    public MarketSurveyEntryInfo()
    {
        super();
    }
    protected MarketSurveyEntryInfo(String pkField)
    {
        super(pkField);
    }
}