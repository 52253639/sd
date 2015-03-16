package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomModelInfo extends AbstractRoomModelInfo implements Serializable 
{
    public RoomModelInfo()
    {
        super();
    }
    protected RoomModelInfo(String pkField)
    {
        super(pkField);
    }
}