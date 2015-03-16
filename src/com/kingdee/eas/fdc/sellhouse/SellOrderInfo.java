package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SellOrderInfo extends AbstractSellOrderInfo implements Serializable 
{
    public SellOrderInfo()
    {
        super();
    }
    protected SellOrderInfo(String pkField)
    {
        super(pkField);
    }
}