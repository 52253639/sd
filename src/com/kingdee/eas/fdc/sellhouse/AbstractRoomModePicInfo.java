package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomModePicInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRoomModePicInfo()
    {
        this("id");
    }
    protected AbstractRoomModePicInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 户型图 's 户型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomModelInfo item)
    {
        put("head", item);
    }
    /**
     * Object:户型图's 户型图property 
     */
    public byte[] getImgData()
    {
        return (byte[])get("imgData");
    }
    public void setImgData(byte[] item)
    {
        put("imgData", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A63545D1");
    }
}