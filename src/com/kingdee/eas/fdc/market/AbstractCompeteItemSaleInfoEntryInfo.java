package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompeteItemSaleInfoEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractCompeteItemSaleInfoEntryInfo()
    {
        this("id");
    }
    protected AbstractCompeteItemSaleInfoEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 销售情况 's null property 
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
     * Object:销售情况's 编码property 
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
     * Object:销售情况's 开始日期property 
     */
    public java.util.Date getStDate()
    {
        return getDate("stDate");
    }
    public void setStDate(java.util.Date item)
    {
        setDate("stDate", item);
    }
    /**
     * Object:销售情况's 截至日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:销售情况's 销售套数property 
     */
    public java.math.BigDecimal getSaleSets()
    {
        return getBigDecimal("saleSets");
    }
    public void setSaleSets(java.math.BigDecimal item)
    {
        setBigDecimal("saleSets", item);
    }
    /**
     * Object:销售情况's 销售面积property 
     */
    public java.math.BigDecimal getSaleArea()
    {
        return getBigDecimal("saleArea");
    }
    public void setSaleArea(java.math.BigDecimal item)
    {
        setBigDecimal("saleArea", item);
    }
    /**
     * Object:销售情况's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AE07E4F4");
    }
}