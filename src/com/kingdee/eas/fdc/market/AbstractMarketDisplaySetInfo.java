package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketDisplaySetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketDisplaySetInfo()
    {
        this("id");
    }
    protected AbstractMarketDisplaySetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:营销功能设置's 设置值property 
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
        return new BOSObjectType("2182556D");
    }
}