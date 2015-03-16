package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class RoomPropertyBatchInfo extends AbstractRoomPropertyBatchInfo implements Serializable 
{
    public RoomPropertyBatchInfo()
    {
        super();
    }
    protected RoomPropertyBatchInfo(String pkField)
    {
        super(pkField);
    }
}