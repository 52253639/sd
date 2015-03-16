package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgioRoomEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAgioRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractAgioRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 指定折扣房间 's 折扣头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.AgioBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 指定折扣房间 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7D3A3AF0");
    }
}