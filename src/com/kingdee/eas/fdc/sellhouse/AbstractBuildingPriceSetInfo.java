package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingPriceSetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildingPriceSetInfo()
    {
        this("id");
    }
    protected AbstractBuildingPriceSetInfo(String pkField)
    {
        super(pkField);
        put("priceSetEntry", new com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryCollection());
    }
    /**
     * Object: 楼栋定价方案设置 's 定价方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo getPriceScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo)get("priceScheme");
    }
    public void setPriceScheme(com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo item)
    {
        put("priceScheme", item);
    }
    /**
     * Object: 楼栋定价方案设置 's 定价设置分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryCollection getPriceSetEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryCollection)get("priceSetEntry");
    }
    /**
     * Object: 楼栋定价方案设置 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:楼栋定价方案设置's 基点标准property 
     */
    public java.math.BigDecimal getBasePointAmount()
    {
        return getBigDecimal("basePointAmount");
    }
    public void setBasePointAmount(java.math.BigDecimal item)
    {
        setBigDecimal("basePointAmount", item);
    }
    /**
     * Object:楼栋定价方案设置's 总价标准property 
     */
    public java.math.BigDecimal getSumPriceAmount()
    {
        return getBigDecimal("sumPriceAmount");
    }
    public void setSumPriceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sumPriceAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B13BF608");
    }
}