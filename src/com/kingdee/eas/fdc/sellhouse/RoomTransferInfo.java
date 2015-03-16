package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomTransferInfo extends AbstractRoomTransferInfo implements Serializable 
{
    public RoomTransferInfo()
    {
        super();
    }
    protected RoomTransferInfo(String pkField)
    {
        super(pkField);
    }
}