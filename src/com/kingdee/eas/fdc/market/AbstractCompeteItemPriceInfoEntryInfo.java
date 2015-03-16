package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompeteItemPriceInfoEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractCompeteItemPriceInfoEntryInfo()
    {
        this("id");
    }
    protected AbstractCompeteItemPriceInfoEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 价格信息 's null property 
     */
    public com.kingdee.eas.fdc.market.CompeteItemInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.CompeteItemInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.CompeteItemInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:价格信息's 编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:价格信息's 推盘日期property 
     */
    public java.util.Date getPushDate()
    {
        return getDate("pushDate");
    }
    public void setPushDate(java.util.Date item)
    {
        setDate("pushDate", item);
    }
    /**
     * Object:价格信息's 推售套数property 
     */
    public int getPushSaleSets()
    {
        return getInt("pushSaleSets");
    }
    public void setPushSaleSets(int item)
    {
        setInt("pushSaleSets", item);
    }
    /**
     * Object:价格信息's 推盘建筑面积property 
     */
    public java.math.BigDecimal getPusharchArea()
    {
        return getBigDecimal("pusharchArea");
    }
    public void setPusharchArea(java.math.BigDecimal item)
    {
        setBigDecimal("pusharchArea", item);
    }
    /**
     * Object:价格信息's 平均价property 
     */
    public java.math.BigDecimal getAvgPrice()
    {
        return getBigDecimal("avgPrice");
    }
    public void setAvgPrice(java.math.BigDecimal item)
    {
        setBigDecimal("avgPrice", item);
    }
    /**
     * Object:价格信息's 平均总价property 
     */
    public java.math.BigDecimal getAvgAllAmonut()
    {
        return getBigDecimal("avgAllAmonut");
    }
    public void setAvgAllAmonut(java.math.BigDecimal item)
    {
        setBigDecimal("avgAllAmonut", item);
    }
    /**
     * Object:价格信息's 起价property 
     */
    public java.math.BigDecimal getStPrice()
    {
        return getBigDecimal("stPrice");
    }
    public void setStPrice(java.math.BigDecimal item)
    {
        setBigDecimal("stPrice", item);
    }
    /**
     * Object:价格信息's 最高价property 
     */
    public java.math.BigDecimal getHighestPrice()
    {
        return getBigDecimal("highestPrice");
    }
    public void setHighestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("highestPrice", item);
    }
    /**
     * Object:价格信息's 备注property 
     */
    public String getRemarkE1()
    {
        return getString("remarkE1");
    }
    public void setRemarkE1(String item)
    {
        setString("remarkE1", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5AD2EE4");
    }
}