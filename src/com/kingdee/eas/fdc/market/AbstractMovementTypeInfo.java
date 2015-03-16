package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMovementTypeInfo()
    {
        this("id");
    }
    protected AbstractMovementTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:活动类型's 简称property 
     */
    public String getShortName()
    {
        return getString("shortName");
    }
    public void setShortName(String item)
    {
        setString("shortName", item);
    }
    /**
     * Object: 活动类型 's 推广类型 property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getPopularizeType()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("popularizeType");
    }
    public void setPopularizeType(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("popularizeType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3A1648DA");
    }
}