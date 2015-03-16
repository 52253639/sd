package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomPropertyBookEntryInfo extends AbstractRoomPropertyBookEntryInfo implements Serializable 
{
    public RoomPropertyBookEntryInfo()
    {
        super();
    }
    protected RoomPropertyBookEntryInfo(String pkField)
    {
        super(pkField);
    }
}