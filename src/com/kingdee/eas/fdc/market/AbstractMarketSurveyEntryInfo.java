package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSurveyEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSurveyEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSurveyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.market.MarketSurveyInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MarketSurveyInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MarketSurveyInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 日期property 
     */
    public java.util.Date getDateMonth()
    {
        return getDate("dateMonth");
    }
    public void setDateMonth(java.util.Date item)
    {
        setDate("dateMonth", item);
    }
    /**
     * Object:分录's 起价property 
     */
    public java.math.BigDecimal getPriceStart()
    {
        return getBigDecimal("priceStart");
    }
    public void setPriceStart(java.math.BigDecimal item)
    {
        setBigDecimal("priceStart", item);
    }
    /**
     * Object:分录's 最高价property 
     */
    public java.math.BigDecimal getPriceMax()
    {
        return getBigDecimal("priceMax");
    }
    public void setPriceMax(java.math.BigDecimal item)
    {
        setBigDecimal("priceMax", item);
    }
    /**
     * Object:分录's 均价property 
     */
    public java.math.BigDecimal getPriceAverage()
    {
        return getBigDecimal("priceAverage");
    }
    public void setPriceAverage(java.math.BigDecimal item)
    {
        setBigDecimal("priceAverage", item);
    }
    /**
     * Object:分录's 放盘面积property 
     */
    public java.math.BigDecimal getPlateArea()
    {
        return getBigDecimal("plateArea");
    }
    public void setPlateArea(java.math.BigDecimal item)
    {
        setBigDecimal("plateArea", item);
    }
    /**
     * Object:分录's 销售面积property 
     */
    public java.math.BigDecimal getSalesArea()
    {
        return getBigDecimal("salesArea");
    }
    public void setSalesArea(java.math.BigDecimal item)
    {
        setBigDecimal("salesArea", item);
    }
    /**
     * Object:分录's 销售套数property 
     */
    public int getSalesNum()
    {
        return getInt("salesNum");
    }
    public void setSalesNum(int item)
    {
        setInt("salesNum", item);
    }
    /**
     * Object:分录's 销售金额property 
     */
    public java.math.BigDecimal getSalesMoney()
    {
        return getBigDecimal("salesMoney");
    }
    public void setSalesMoney(java.math.BigDecimal item)
    {
        setBigDecimal("salesMoney", item);
    }
    /**
     * Object: 分录 's 房屋分类 property 
     */
    public com.kingdee.eas.fdc.market.HouseAnlysisInfo getHouseType()
    {
        return (com.kingdee.eas.fdc.market.HouseAnlysisInfo)get("houseType");
    }
    public void setHouseType(com.kingdee.eas.fdc.market.HouseAnlysisInfo item)
    {
        put("houseType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CA7B25AB");
    }
}