package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonthPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractMonthPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:销售月度计划分录's 产品构成property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum getType()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object: 销售月度计划分录 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:销售月度计划分录's 面积计划数property 
     */
    public java.math.BigDecimal getAreaPlan()
    {
        return getBigDecimal("areaPlan");
    }
    public void setAreaPlan(java.math.BigDecimal item)
    {
        setBigDecimal("areaPlan", item);
    }
    /**
     * Object:销售月度计划分录's 面积实际数property 
     */
    public java.math.BigDecimal getAreaActual()
    {
        return getBigDecimal("areaActual");
    }
    public void setAreaActual(java.math.BigDecimal item)
    {
        setBigDecimal("areaActual", item);
    }
    /**
     * Object:销售月度计划分录's 套数计划数property 
     */
    public int getPloidyPlan()
    {
        return getInt("ploidyPlan");
    }
    public void setPloidyPlan(int item)
    {
        setInt("ploidyPlan", item);
    }
    /**
     * Object:销售月度计划分录's 套数实际数property 
     */
    public int getPloidyActual()
    {
        return getInt("ploidyActual");
    }
    public void setPloidyActual(int item)
    {
        setInt("ploidyActual", item);
    }
    /**
     * Object:销售月度计划分录's 均价计划数property 
     */
    public java.math.BigDecimal getPricePlan()
    {
        return getBigDecimal("pricePlan");
    }
    public void setPricePlan(java.math.BigDecimal item)
    {
        setBigDecimal("pricePlan", item);
    }
    /**
     * Object:销售月度计划分录's 均价实际数property 
     */
    public java.math.BigDecimal getPriceActual()
    {
        return getBigDecimal("priceActual");
    }
    public void setPriceActual(java.math.BigDecimal item)
    {
        setBigDecimal("priceActual", item);
    }
    /**
     * Object:销售月度计划分录's 金额计划数property 
     */
    public java.math.BigDecimal getAmountPlan()
    {
        return getBigDecimal("amountPlan");
    }
    public void setAmountPlan(java.math.BigDecimal item)
    {
        setBigDecimal("amountPlan", item);
    }
    /**
     * Object:销售月度计划分录's 金额实际数property 
     */
    public java.math.BigDecimal getAmountActual()
    {
        return getBigDecimal("amountActual");
    }
    public void setAmountActual(java.math.BigDecimal item)
    {
        setBigDecimal("amountActual", item);
    }
    /**
     * Object:销售月度计划分录's 计划回款property 
     */
    public java.math.BigDecimal getRecoverPlan()
    {
        return getBigDecimal("recoverPlan");
    }
    public void setRecoverPlan(java.math.BigDecimal item)
    {
        setBigDecimal("recoverPlan", item);
    }
    /**
     * Object:销售月度计划分录's 实际回款property 
     */
    public java.math.BigDecimal getRecoverActual()
    {
        return getBigDecimal("recoverActual");
    }
    public void setRecoverActual(java.math.BigDecimal item)
    {
        setBigDecimal("recoverActual", item);
    }
    /**
     * Object:销售月度计划分录's 年度property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:销售月度计划分录's 月份property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object: 销售月度计划分录 's 销售月度计划头 property 
     */
    public com.kingdee.eas.fdc.market.MonthPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MonthPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MonthPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:销售月度计划分录's 面积段property 
     */
    public String getAreaRange()
    {
        return getString("areaRange");
    }
    public void setAreaRange(String item)
    {
        setString("areaRange", item);
    }
    /**
     * Object: 销售月度计划分录 's 自定义面积段 property 
     */
    public com.kingdee.eas.fdc.market.AreaSetInfo getNewAreaRange()
    {
        return (com.kingdee.eas.fdc.market.AreaSetInfo)get("newAreaRange");
    }
    public void setNewAreaRange(com.kingdee.eas.fdc.market.AreaSetInfo item)
    {
        put("newAreaRange", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("071C503A");
    }
}