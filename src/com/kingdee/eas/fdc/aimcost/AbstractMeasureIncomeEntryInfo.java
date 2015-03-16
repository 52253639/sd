package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureIncomeEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMeasureIncomeEntryInfo()
    {
        this("id");
    }
    protected AbstractMeasureIncomeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 收入测算分录 's 父级收入科目 property 
     */
    public com.kingdee.eas.fdc.basedata.IncomeAccountInfo getIncomeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.IncomeAccountInfo)get("incomeAccount");
    }
    public void setIncomeAccount(com.kingdee.eas.fdc.basedata.IncomeAccountInfo item)
    {
        put("incomeAccount", item);
    }
    /**
     * Object:收入测算分录's 条目名称property 
     */
    public String getEntryName()
    {
        return getString("entryName");
    }
    public void setEntryName(String item)
    {
        setString("entryName", item);
    }
    /**
     * Object: 收入测算分录 's 所属产品 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: 收入测算分录 's 头 property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo item)
    {
        put("head", item);
    }
    /**
     * Object:收入测算分录's 预计平均售价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:收入测算分录's 可售面积property 
     */
    public java.math.BigDecimal getSellArea()
    {
        return getBigDecimal("sellArea");
    }
    public void setSellArea(java.math.BigDecimal item)
    {
        setBigDecimal("sellArea", item);
    }
    /**
     * Object:收入测算分录's 预计收入总额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:收入测算分录's 变化原因property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E7D8F5A2");
    }
}