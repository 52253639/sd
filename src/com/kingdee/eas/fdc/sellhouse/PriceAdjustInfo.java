package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PriceAdjustInfo extends AbstractPriceAdjustInfo implements Serializable 
{
    public PriceAdjustInfo()
    {
        super();
    }
    protected PriceAdjustInfo(String pkField)
    {
        super(pkField);
    }
}