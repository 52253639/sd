package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSellOrderRoomEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSellOrderRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractSellOrderRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 盘次房间分录 's 盘次头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellOrderInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellOrderInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SellOrderInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 盘次房间分录 's 房间 property 
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
     * Object:盘次房间分录's 盘次执行状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum getState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:盘次房间分录's 撤盘日期property 
     */
    public java.util.Date getQuitOrderDate()
    {
        return getDate("quitOrderDate");
    }
    public void setQuitOrderDate(java.util.Date item)
    {
        setDate("quitOrderDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3EB8C2B6");
    }
}