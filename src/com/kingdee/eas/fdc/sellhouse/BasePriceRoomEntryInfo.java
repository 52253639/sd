package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BasePriceRoomEntryInfo extends AbstractBasePriceRoomEntryInfo implements Serializable 
{
    public BasePriceRoomEntryInfo()
    {
        super();
    }
    protected BasePriceRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
}