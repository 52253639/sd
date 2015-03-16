package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostProdcutSplitEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAimCostProdcutSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractAimCostProdcutSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: null 's 产品 property 
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
     * Object: null 's 分摊类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getApportionType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("apportionType");
    }
    public void setApportionType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("apportionType", item);
    }
    /**
     * Object:null's 拆分金额property 
     */
    public java.math.BigDecimal getSplitAmount()
    {
        return getBigDecimal("splitAmount");
    }
    public void setSplitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("splitAmount", item);
    }
    /**
     * Object:null's 直接金额property 
     */
    public java.math.BigDecimal getDirectAmount()
    {
        return getBigDecimal("directAmount");
    }
    public void setDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("directAmount", item);
    }
    /**
     * Object:null's nullproperty 
     */
    public String getCostEntryId()
    {
        return getString("costEntryId");
    }
    public void setCostEntryId(String item)
    {
        setString("costEntryId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59124F28");
    }
}