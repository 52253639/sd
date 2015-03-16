package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PriceAdjustEntryInfo extends AbstractPriceAdjustEntryInfo implements Serializable 
{
    public PriceAdjustEntryInfo()
    {
        super();
    }
    protected PriceAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
}