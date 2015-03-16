package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateCustomPlanIndexEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTemplateCustomPlanIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractTemplateCustomPlanIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:模板自定义指标's 指标值property 
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
     * Object:模板自定义指标's 是否产品property 
     */
    public int getIsProduct()
    {
        return getInt("isProduct");
    }
    public void setIsProduct(int item)
    {
        setInt("isProduct", item);
    }
    /**
     * Object: 模板自定义指标 's 模板指标头 property 
     */
    public com.kingdee.eas.fdc.aimcost.TemplatePlanIndexInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.TemplatePlanIndexInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.TemplatePlanIndexInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 模板自定义指标 's 指标 property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportType");
    }
    public void setApportType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportType", item);
    }
    /**
     * Object: 模板自定义指标 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2619590B");
    }
}