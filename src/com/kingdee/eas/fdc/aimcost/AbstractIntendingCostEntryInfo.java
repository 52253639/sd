package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIntendingCostEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractIntendingCostEntryInfo()
    {
        this("id");
    }
    protected AbstractIntendingCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 待发生成本 's 动态成本 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynamicCostInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:待发生成本's 成本科目名称property 
     */
    public String getCostAcctName()
    {
        return getString("costAcctName");
    }
    public void setCostAcctName(String item)
    {
        setString("costAcctName", item);
    }
    /**
     * Object:待发生成本's 工作量property 
     */
    public java.math.BigDecimal getWorkload()
    {
        return getBigDecimal("workload");
    }
    public void setWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("workload", item);
    }
    /**
     * Object:待发生成本's 单位property 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:待发生成本's 单价property 
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
     * Object:待发生成本's 成本金额property 
     */
    public java.math.BigDecimal getCostAmount()
    {
        return getBigDecimal("costAmount");
    }
    public void setCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costAmount", item);
    }
    /**
     * Object: 待发生成本 's 产品 property 
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
     * Object:待发生成本's 说明property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("614C142D");
    }
}