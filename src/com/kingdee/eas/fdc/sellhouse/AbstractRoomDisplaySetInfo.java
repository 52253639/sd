package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomDisplaySetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomDisplaySetInfo()
    {
        this("id");
    }
    protected AbstractRoomDisplaySetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:房间显示设置's 设置值property 
     */
    public byte[] getValue()
    {
        return (byte[])get("value");
    }
    public void setValue(byte[] item)
    {
        put("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4CB06F16");
    }
}