package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplatePlanIndexEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTemplatePlanIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractTemplatePlanIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:规则指标分录's 是否是产品类型property 
     */
    public boolean isIsProduct()
    {
        return getBoolean("isProduct");
    }
    public void setIsProduct(boolean item)
    {
        setBoolean("isProduct", item);
    }
    /**
     * Object:规则指标分录's 产品名称property 
     */
    public String getName()
    {
        return getName((Locale)null);
    }
    public void setName(String item)
    {
		setName(item,(Locale)null);
    }
    public String getName(Locale local)
    {
        return TypeConversionUtils.objToString(get("name", local));
    }
    public void setName(String item, Locale local)
    {
        put("name", item, local);
    }
    /**
     * Object:规则指标分录's 占地面积property 
     */
    public java.math.BigDecimal getContainArea()
    {
        return getBigDecimal("containArea");
    }
    public void setContainArea(java.math.BigDecimal item)
    {
        setBigDecimal("containArea", item);
    }
    /**
     * Object:规则指标分录's 容积率或车位数property 
     */
    public java.math.BigDecimal getCubageRate()
    {
        return getBigDecimal("cubageRate");
    }
    public void setCubageRate(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRate", item);
    }
    /**
     * Object:规则指标分录's 建筑面积property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:规则指标分录's 可售面积property 
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
     * Object:规则指标分录's 产品比例property 
     */
    public java.math.BigDecimal getProductRate()
    {
        return getBigDecimal("productRate");
    }
    public void setProductRate(java.math.BigDecimal item)
    {
        setBigDecimal("productRate", item);
    }
    /**
     * Object:规则指标分录's 平均每户面积property 
     */
    public java.math.BigDecimal getUnitArea()
    {
        return getBigDecimal("unitArea");
    }
    public void setUnitArea(java.math.BigDecimal item)
    {
        setBigDecimal("unitArea", item);
    }
    /**
     * Object:规则指标分录's 单元数property 
     */
    public java.math.BigDecimal getUnits()
    {
        return getBigDecimal("units");
    }
    public void setUnits(java.math.BigDecimal item)
    {
        setBigDecimal("units", item);
    }
    /**
     * Object:规则指标分录's 户数 property 
     */
    public java.math.BigDecimal getDoors()
    {
        return getBigDecimal("doors");
    }
    public void setDoors(java.math.BigDecimal item)
    {
        setBigDecimal("doors", item);
    }
    /**
     * Object:规则指标分录's 类型property 
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
     * Object:规则指标分录's 索引property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    /**
     * Object: 规则指标分录 's 产品 property 
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
     * Object: 规则指标分录 's 父 property 
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
     * Object:规则指标分录's nullproperty 
     */
    public boolean isIsSplit()
    {
        return getBoolean("isSplit");
    }
    public void setIsSplit(boolean item)
    {
        setBoolean("isSplit", item);
    }
    /**
     * Object:规则指标分录's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:规则指标分录's 实际建造面积property 
     */
    public java.math.BigDecimal getConstructArea()
    {
        return getBigDecimal("constructArea");
    }
    public void setConstructArea(java.math.BigDecimal item)
    {
        setBigDecimal("constructArea", item);
    }
    /**
     * Object:规则指标分录's 电梯数property 
     */
    public int getElevators()
    {
        return getInt("elevators");
    }
    public void setElevators(int item)
    {
        setInt("elevators", item);
    }
    /**
     * Object:规则指标分录's 楼层数property 
     */
    public int getFloors()
    {
        return getInt("floors");
    }
    public void setFloors(int item)
    {
        setInt("floors", item);
    }
    /**
     * Object:规则指标分录's 层高property 
     */
    public java.math.BigDecimal getFloorHeight()
    {
        return getBigDecimal("floorHeight");
    }
    public void setFloorHeight(java.math.BigDecimal item)
    {
        setBigDecimal("floorHeight", item);
    }
    /**
     * Object:规则指标分录's 地上建筑面积property 
     */
    public java.math.BigDecimal getGroundArea()
    {
        return getBigDecimal("groundArea");
    }
    public void setGroundArea(java.math.BigDecimal item)
    {
        setBigDecimal("groundArea", item);
    }
    /**
     * Object:规则指标分录's 地下建筑面积property 
     */
    public java.math.BigDecimal getUnderGroundArea()
    {
        return getBigDecimal("underGroundArea");
    }
    public void setUnderGroundArea(java.math.BigDecimal item)
    {
        setBigDecimal("underGroundArea", item);
    }
    /**
     * Object:规则指标分录's 幢数property 
     */
    public int getBuild()
    {
        return getInt("build");
    }
    public void setBuild(int item)
    {
        setInt("build", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A2A6C3FA");
    }
}