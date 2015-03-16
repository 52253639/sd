package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynamicCostProductSplitEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractDynamicCostProductSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractDynamicCostProductSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 动态成本产品拆分分录 's 产品 property 
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
     * Object: 动态成本产品拆分分录 's 分摊类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportionType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportionType");
    }
    public void setApportionType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportionType", item);
    }
    /**
     * Object:动态成本产品拆分分录's 拆分金额property 
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
     * Object:动态成本产品拆分分录's 已发生直接金额property 
     */
    public java.math.BigDecimal getHanppenDirectAmount()
    {
        return getBigDecimal("hanppenDirectAmount");
    }
    public void setHanppenDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hanppenDirectAmount", item);
    }
    /**
     * Object:动态成本产品拆分分录's 待发生直接金额property 
     */
    public java.math.BigDecimal getIntendingDirectAmount()
    {
        return getBigDecimal("intendingDirectAmount");
    }
    public void setIntendingDirectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("intendingDirectAmount", item);
    }
    /**
     * Object:动态成本产品拆分分录's 目标成本指定分摊property 
     */
    public java.math.BigDecimal getAimCostAmount()
    {
        return getBigDecimal("aimCostAmount");
    }
    public void setAimCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostAmount", item);
    }
    /**
     * Object:动态成本产品拆分分录's 动态成本项Idproperty 
     */
    public String getDynamicCostId()
    {
        return getString("dynamicCostId");
    }
    public void setDynamicCostId(String item)
    {
        setString("dynamicCostId", item);
    }
    /**
     * Object:动态成本产品拆分分录's 指定分摊金额比例property 
     */
    public java.math.BigDecimal getAppointAmount()
    {
        return getBigDecimal("appointAmount");
    }
    public void setAppointAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appointAmount", item);
    }
    /**
     * Object:动态成本产品拆分分录's 是否最新版本property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object: 动态成本产品拆分分录 's 期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D63B3952");
    }
}