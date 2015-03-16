package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomAttachmentEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomAttachmentEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomAttachmentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房间附属房产分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 房间附属房产分录 's 房间 property 
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
        return new BOSObjectType("05680093");
    }
}