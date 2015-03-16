package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class RoomPropertyReportInfo extends AbstractRoomPropertyReportInfo implements Serializable 
{
    public RoomPropertyReportInfo()
    {
        super();
    }
    protected RoomPropertyReportInfo(String pkField)
    {
        super(pkField);
    }
}