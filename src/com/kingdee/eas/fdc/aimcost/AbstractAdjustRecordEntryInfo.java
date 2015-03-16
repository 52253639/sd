package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAdjustRecordEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractAdjustRecordEntryInfo()
    {
        this("id");
    }
    protected AbstractAdjustRecordEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
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
     * Object:分录's 调整科目名称property 
     */
    public String getAdjustAcctName()
    {
        return getString("adjustAcctName");
    }
    public void setAdjustAcctName(String item)
    {
        setString("adjustAcctName", item);
    }
    /**
     * Object:分录's 调整日期property 
     */
    public java.util.Date getAdjustDate()
    {
        return getDate("adjustDate");
    }
    public void setAdjustDate(java.util.Date item)
    {
        setDate("adjustDate", item);
    }
    /**
     * Object:分录's 工作量property 
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
     * Object:分录's 单位property 
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
     * Object:分录's 单价property 
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
     * Object:分录's 成本金额property 
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
     * Object: 分录 's 产品 property 
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
     * Object:分录's 说明property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:分录's 目标成本分录IDproperty 
     */
    public String getAimCostEntryId()
    {
        return getString("aimCostEntryId");
    }
    public void setAimCostEntryId(String item)
    {
        setString("aimCostEntryId", item);
    }
    /**
     * Object: 分录 's 调整类型 property 
     */
    public com.kingdee.eas.fdc.basedata.AdjustTypeInfo getAdjustType()
    {
        return (com.kingdee.eas.fdc.basedata.AdjustTypeInfo)get("adjustType");
    }
    public void setAdjustType(com.kingdee.eas.fdc.basedata.AdjustTypeInfo item)
    {
        put("adjustType", item);
    }
    /**
     * Object: 分录 's 调整原因 property 
     */
    public com.kingdee.eas.fdc.basedata.AdjustReasonInfo getAdjustReason()
    {
        return (com.kingdee.eas.fdc.basedata.AdjustReasonInfo)get("adjustReason");
    }
    public void setAdjustReason(com.kingdee.eas.fdc.basedata.AdjustReasonInfo item)
    {
        put("adjustReason", item);
    }
    /**
     * Object:分录's 是否竣工前调整property 
     */
    public boolean isIsBeforeSett()
    {
        return getBoolean("isBeforeSett");
    }
    public void setIsBeforeSett(boolean item)
    {
        setBoolean("isBeforeSett", item);
    }
    /**
     * Object:分录's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: 分录 's 调整期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object: 分录 's 调整人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAdjuster()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("adjuster");
    }
    public void setAdjuster(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("adjuster", item);
    }
    /**
     * Object:分录's 调整人姓名property 
     */
    public String getAdjusterName()
    {
        return getString("adjusterName");
    }
    public void setAdjusterName(String item)
    {
        setString("adjusterName", item);
    }
    /**
     * Object:分录's 目标成本调整单分录IDproperty 
     */
    public String getAdjustEntryId()
    {
        return getString("adjustEntryId");
    }
    public void setAdjustEntryId(String item)
    {
        setString("adjustEntryId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("52FA927D");
    }
}