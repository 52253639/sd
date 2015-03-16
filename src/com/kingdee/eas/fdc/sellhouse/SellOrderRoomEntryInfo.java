package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SellOrderRoomEntryInfo extends AbstractSellOrderRoomEntryInfo implements Serializable 
{
    public SellOrderRoomEntryInfo()
    {
        super();
    }
    protected SellOrderRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
}