package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomPayEntryInfo extends AbstractRoomPayEntryInfo implements Serializable 
{
    public RoomPayEntryInfo()
    {
        super();
    }
    protected RoomPayEntryInfo(String pkField)
    {
        super(pkField);
    }
}