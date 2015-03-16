package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomUsageInfo extends AbstractRoomUsageInfo implements Serializable 
{
    public RoomUsageInfo()
    {
        super();
    }
    protected RoomUsageInfo(String pkField)
    {
        super(pkField);
    }
}