package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceRoomEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCommerceRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractCommerceRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 商机意向房间分录 's 商机 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    /**
     * Object: 商机意向房间分录 's 意向房间 property 
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
     * Object:商机意向房间分录's 计划更改意向property 
     */
    public boolean isPlanToChange()
    {
        return getBoolean("planToChange");
    }
    public void setPlanToChange(boolean item)
    {
        setBoolean("planToChange", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E9CCEE41");
    }
}