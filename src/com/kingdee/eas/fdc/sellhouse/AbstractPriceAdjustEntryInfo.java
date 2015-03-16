package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPriceAdjustEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPriceAdjustEntryInfo()
    {
        this("id");
    }
    protected AbstractPriceAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 调价房间分录 's 调价头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 调价房间分录 's 房间 property 
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
     * Object:调价房间分录's 旧建筑面积property 
     */
    public java.math.BigDecimal getOldBuildingArea()
    {
        return getBigDecimal("oldBuildingArea");
    }
    public void setOldBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("oldBuildingArea", item);
    }
    /**
     * Object:调价房间分录's 原建筑单价property 
     */
    public java.math.BigDecimal getOldBuildingPrice()
    {
        return getBigDecimal("oldBuildingPrice");
    }
    public void setOldBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldBuildingPrice", item);
    }
    /**
     * Object:调价房间分录's 旧套内面积property 
     */
    public java.math.BigDecimal getOldRoomArea()
    {
        return getBigDecimal("oldRoomArea");
    }
    public void setOldRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("oldRoomArea", item);
    }
    /**
     * Object:调价房间分录's 原套内单价property 
     */
    public java.math.BigDecimal getOldRoomPrice()
    {
        return getBigDecimal("oldRoomPrice");
    }
    public void setOldRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldRoomPrice", item);
    }
    /**
     * Object:调价房间分录's 旧的是否按套内property 
     */
    public boolean isIsCalByRoomOld()
    {
        return getBoolean("isCalByRoomOld");
    }
    public void setIsCalByRoomOld(boolean item)
    {
        setBoolean("isCalByRoomOld", item);
    }
    /**
     * Object:调价房间分录's 新建筑面积property 
     */
    public java.math.BigDecimal getNewBuildingArea()
    {
        return getBigDecimal("newBuildingArea");
    }
    public void setNewBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("newBuildingArea", item);
    }
    /**
     * Object:调价房间分录's 新建筑单价property 
     */
    public java.math.BigDecimal getNewBuildingPrice()
    {
        return getBigDecimal("newBuildingPrice");
    }
    public void setNewBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newBuildingPrice", item);
    }
    /**
     * Object:调价房间分录's 新房间面积property 
     */
    public java.math.BigDecimal getNewRoomArea()
    {
        return getBigDecimal("newRoomArea");
    }
    public void setNewRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("newRoomArea", item);
    }
    /**
     * Object:调价房间分录's 新套内单价property 
     */
    public java.math.BigDecimal getNewRoomPrice()
    {
        return getBigDecimal("newRoomPrice");
    }
    public void setNewRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newRoomPrice", item);
    }
    /**
     * Object:调价房间分录's 新的是否按套内计算property 
     */
    public boolean isIsCalByRoomNew()
    {
        return getBoolean("isCalByRoomNew");
    }
    public void setIsCalByRoomNew(boolean item)
    {
        setBoolean("isCalByRoomNew", item);
    }
    /**
     * Object:调价房间分录's 建筑单价差额property 
     */
    public java.math.BigDecimal getBuildingPriceBalance()
    {
        return getBigDecimal("buildingPriceBalance");
    }
    public void setBuildingPriceBalance(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPriceBalance", item);
    }
    /**
     * Object:调价房间分录's 建筑单据差额比例property 
     */
    public java.math.BigDecimal getBuildingPriceBalanceScale()
    {
        return getBigDecimal("buildingPriceBalanceScale");
    }
    public void setBuildingPriceBalanceScale(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPriceBalanceScale", item);
    }
    /**
     * Object:调价房间分录's 套内单据差额property 
     */
    public java.math.BigDecimal getRoomPriceBalance()
    {
        return getBigDecimal("roomPriceBalance");
    }
    public void setRoomPriceBalance(java.math.BigDecimal item)
    {
        setBigDecimal("roomPriceBalance", item);
    }
    /**
     * Object:调价房间分录's 套内单价差额比例property 
     */
    public java.math.BigDecimal getRoomPriceBalanceScale()
    {
        return getBigDecimal("roomPriceBalanceScale");
    }
    public void setRoomPriceBalanceScale(java.math.BigDecimal item)
    {
        setBigDecimal("roomPriceBalanceScale", item);
    }
    /**
     * Object:调价房间分录's 销售面积类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum getSalesareatype()
    {
        return com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum.getEnum(getString("Salesareatype"));
    }
    public void setSalesareatype(com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum item)
    {
		if (item != null) {
        setString("Salesareatype", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9E38785A");
    }
}