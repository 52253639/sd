package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShareRoomModelsInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractShareRoomModelsInfo()
    {
        this("id");
    }
    protected AbstractShareRoomModelsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 共享户型列表 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 共享户型列表 's 户型 property 
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
        return new BOSObjectType("29A55029");
    }
}