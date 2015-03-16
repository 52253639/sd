package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SellOrderPlanEntryInfo extends AbstractSellOrderPlanEntryInfo implements Serializable 
{
    public SellOrderPlanEntryInfo()
    {
        super();
    }
    protected SellOrderPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
}