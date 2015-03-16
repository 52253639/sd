package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractMarketTypeInfo()
    {
        this("id");
    }
    protected AbstractMarketTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 活动类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:活动类型's 启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("99052E27");
    }
}