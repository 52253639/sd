package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChannelTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractChannelTypeInfo()
    {
        this("id");
    }
    protected AbstractChannelTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 渠道分类 's 组别 property 
     */
    public com.kingdee.eas.fdc.market.ChannelTypeTreeInfo getTreeid()
    {
        return (com.kingdee.eas.fdc.market.ChannelTypeTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.fdc.market.ChannelTypeTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:渠道分类's 是否已启用property 
     */
    public boolean isStatrusing()
    {
        return getBoolean("statrusing");
    }
    public void setStatrusing(boolean item)
    {
        setBoolean("statrusing", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("878CC46C");
    }
}