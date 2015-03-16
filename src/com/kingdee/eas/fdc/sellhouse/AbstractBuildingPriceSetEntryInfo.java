package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingPriceSetEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildingPriceSetEntryInfo()
    {
        this("id");
    }
    protected AbstractBuildingPriceSetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 楼栋定价设置分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 楼栋定价设置分录 's 方案分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo getSchemeEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo)get("schemeEntry");
    }
    public void setSchemeEntry(com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo item)
    {
        put("schemeEntry", item);
    }
    /**
     * Object:楼栋定价设置分录's 因素内容-数字property 
     */
    public java.math.BigDecimal getFactorContentA()
    {
        return getBigDecimal("factorContentA");
    }
    public void setFactorContentA(java.math.BigDecimal item)
    {
        setBigDecimal("factorContentA", item);
    }
    /**
     * Object:楼栋定价设置分录's 因素内容-字符property 
     */
    public String getFactorContentS()
    {
        return getString("factorContentS");
    }
    public void setFactorContentS(String item)
    {
        setString("factorContentS", item);
    }
    /**
     * Object:楼栋定价设置分录's 因素值property 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    /**
     * Object:楼栋定价设置分录's 是否是基点property 
     */
    public boolean isIsBasePoint()
    {
        return getBoolean("isBasePoint");
    }
    public void setIsBasePoint(boolean item)
    {
        setBoolean("isBasePoint", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B574A74A");
    }
}