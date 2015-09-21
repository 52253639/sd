package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialDiscountEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSpecialDiscountEntryInfo()
    {
        this("id");
    }
    protected AbstractSpecialDiscountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 特殊优惠折扣单分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 特殊优惠折扣单分录 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 建筑面积property 
     */
    public java.math.BigDecimal getBuildingArea()
    {
        return getBigDecimal("buildingArea");
    }
    public void setBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingArea", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 套内面积property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 建筑单价property 
     */
    public java.math.BigDecimal getBuildingPrice()
    {
        return getBigDecimal("buildingPrice");
    }
    public void setBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPrice", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 套内单价property 
     */
    public java.math.BigDecimal getRoomPrice()
    {
        return getBigDecimal("roomPrice");
    }
    public void setRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomPrice", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 标准总价property 
     */
    public java.math.BigDecimal getStandardTotalAmount()
    {
        return getBigDecimal("standardTotalAmount");
    }
    public void setStandardTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalAmount", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 优惠金额property 
     */
    public java.math.BigDecimal getDiscountAmount()
    {
        return getBigDecimal("discountAmount");
    }
    public void setDiscountAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAmount", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 优惠后房间总价property 
     */
    public java.math.BigDecimal getDiscountAfAmount()
    {
        return getBigDecimal("discountAfAmount");
    }
    public void setDiscountAfAmount(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfAmount", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 表价优惠折扣比例property 
     */
    public java.math.BigDecimal getDiscountPercent()
    {
        return getBigDecimal("discountPercent");
    }
    public void setDiscountPercent(java.math.BigDecimal item)
    {
        setBigDecimal("discountPercent", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 优惠后建筑单价property 
     */
    public java.math.BigDecimal getDiscountAfBPrice()
    {
        return getBigDecimal("discountAfBPrice");
    }
    public void setDiscountAfBPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfBPrice", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 优惠后套内单价property 
     */
    public java.math.BigDecimal getDiscountAfRPrice()
    {
        return getBigDecimal("discountAfRPrice");
    }
    public void setDiscountAfRPrice(java.math.BigDecimal item)
    {
        setBigDecimal("discountAfRPrice", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 底价property 
     */
    public java.math.BigDecimal getBaseStandardPrice()
    {
        return getBigDecimal("baseStandardPrice");
    }
    public void setBaseStandardPrice(java.math.BigDecimal item)
    {
        setBigDecimal("baseStandardPrice", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 底价下优惠点数property 
     */
    public java.math.BigDecimal getBasePercent()
    {
        return getBigDecimal("basePercent");
    }
    public void setBasePercent(java.math.BigDecimal item)
    {
        setBigDecimal("basePercent", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 底价下优惠金额property 
     */
    public java.math.BigDecimal getBaseDiscountAmount()
    {
        return getBigDecimal("baseDiscountAmount");
    }
    public void setBaseDiscountAmount(java.math.BigDecimal item)
    {
        setBigDecimal("baseDiscountAmount", item);
    }
    /**
     * Object: 特殊优惠折扣单分录 's 付款方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 表价优惠折扣-总经理折扣property 
     */
    public java.math.BigDecimal getSubManagerAgio()
    {
        return getBigDecimal("subManagerAgio");
    }
    public void setSubManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("subManagerAgio", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 表价优惠折扣-案场经理折扣property 
     */
    public java.math.BigDecimal getSubSceneManagerAgio()
    {
        return getBigDecimal("subSceneManagerAgio");
    }
    public void setSubSceneManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("subSceneManagerAgio", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 表价优惠折扣-营销总监折扣property 
     */
    public java.math.BigDecimal getSubSalesDirectorAgio()
    {
        return getBigDecimal("subSalesDirectorAgio");
    }
    public void setSubSalesDirectorAgio(java.math.BigDecimal item)
    {
        setBigDecimal("subSalesDirectorAgio", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 公共折扣后建筑单价property 
     */
    public java.math.BigDecimal getAgioPrice()
    {
        return getBigDecimal("agioPrice");
    }
    public void setAgioPrice(java.math.BigDecimal item)
    {
        setBigDecimal("agioPrice", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 公共折扣房间总价property 
     */
    public java.math.BigDecimal getAgioAmount()
    {
        return getBigDecimal("agioAmount");
    }
    public void setAgioAmount(java.math.BigDecimal item)
    {
        setBigDecimal("agioAmount", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 底单价property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    /**
     * Object:特殊优惠折扣单分录's 单价差额property 
     */
    public java.math.BigDecimal getSubPrice()
    {
        return getBigDecimal("subPrice");
    }
    public void setSubPrice(java.math.BigDecimal item)
    {
        setBigDecimal("subPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8D90D2D3");
    }
}