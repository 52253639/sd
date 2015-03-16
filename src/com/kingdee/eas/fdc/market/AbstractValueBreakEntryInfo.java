package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValueBreakEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractValueBreakEntryInfo()
    {
        this("id");
    }
    protected AbstractValueBreakEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:总货值分解分录's 产品构成property 
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
     * Object: 总货值分解分录 's 产品类型 property 
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
     * Object:总货值分解分录's 面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:总货值分解分录's 套数property 
     */
    public int getPloidy()
    {
        return getInt("ploidy");
    }
    public void setPloidy(int item)
    {
        setInt("ploidy", item);
    }
    /**
     * Object:总货值分解分录's 均价property 
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
     * Object:总货值分解分录's 金额property 
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
     * Object:总货值分解分录's 年度property 
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
     * Object:总货值分解分录's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object: 总货值分解分录 's 总货值分解单据头 property 
     */
    public com.kingdee.eas.fdc.market.ValueBreakInfo getHead()
    {
        return (com.kingdee.eas.fdc.market.ValueBreakInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.market.ValueBreakInfo item)
    {
        put("head", item);
    }
    /**
     * Object:总货值分解分录's 面积段property 
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
     * Object: 总货值分解分录 's 自定义面积段 property 
     */
    public com.kingdee.eas.fdc.market.AreaSetInfo getNewAreaRange()
    {
        return (com.kingdee.eas.fdc.market.AreaSetInfo)get("newAreaRange");
    }
    public void setNewAreaRange(com.kingdee.eas.fdc.market.AreaSetInfo item)
    {
        put("newAreaRange", item);
    }
    /**
     * Object:总货值分解分录's 序号property 
     */
    public int getSequence()
    {
        return getInt("sequence");
    }
    public void setSequence(int item)
    {
        setInt("sequence", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("62FEB913");
    }
}