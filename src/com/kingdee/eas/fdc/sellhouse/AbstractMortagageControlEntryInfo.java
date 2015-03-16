package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMortagageControlEntryInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMortagageControlEntryInfo()
    {
        this("id");
    }
    protected AbstractMortagageControlEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 抵押控制分录 's 房间 property 
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
     * Object: 抵押控制分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MortagageControlInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.MortagageControlInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.MortagageControlInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("57C7A7EF");
    }
}