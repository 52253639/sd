package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MarketSurveyInfo extends AbstractMarketSurveyInfo implements Serializable 
{
    public MarketSurveyInfo()
    {
        super();
    }
    protected MarketSurveyInfo(String pkField)
    {
        super(pkField);
    }
}