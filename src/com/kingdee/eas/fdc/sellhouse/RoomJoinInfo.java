package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomJoinInfo extends AbstractRoomJoinInfo implements Serializable 
{
    public RoomJoinInfo()
    {
        super();
    }
    protected RoomJoinInfo(String pkField)
    {
        super(pkField);
    }
}