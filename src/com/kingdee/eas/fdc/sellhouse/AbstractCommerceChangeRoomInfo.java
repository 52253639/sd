package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceChangeRoomInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCommerceChangeRoomInfo()
    {
        this("id");
    }
    protected AbstractCommerceChangeRoomInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 意向房源 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: 意向房源 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B1B06801");
    }
}