package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHERoomModelInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHERoomModelInfo()
    {
        this("id");
    }
    protected AbstractSHERoomModelInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 售楼户型 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 售楼户型 's 户型类别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getRoomModel()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("roomModel");
    }
    public void setRoomModel(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("roomModel", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9C08FE99");
    }
}