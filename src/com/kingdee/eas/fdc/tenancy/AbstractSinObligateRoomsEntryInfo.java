package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSinObligateRoomsEntryInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSinObligateRoomsEntryInfo()
    {
        this("id");
    }
    protected AbstractSinObligateRoomsEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 诚意预留房间分录 's 诚意预留单头 property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 诚意预留房间分录 's 房间 property 
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
     * Object:诚意预留房间分录's 房间长编property 
     */
    public String getRoomLongNum()
    {
        return getString("roomLongNum");
    }
    public void setRoomLongNum(String item)
    {
        setString("roomLongNum", item);
    }
    /**
     * Object:诚意预留房间分录's 约定租金类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getPlightRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("plightRentType"));
    }
    public void setPlightRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("plightRentType", item.getValue());
		}
    }
    /**
     * Object:诚意预留房间分录's 约定租金property 
     */
    public java.math.BigDecimal getPlightRoomRent()
    {
        return getBigDecimal("plightRoomRent");
    }
    public void setPlightRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("plightRoomRent", item);
    }
    /**
     * Object:诚意预留房间分录's 约定租金单价property 
     */
    public java.math.BigDecimal getPlightRoomRentPrice()
    {
        return getBigDecimal("plightRoomRentPrice");
    }
    public void setPlightRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("plightRoomRentPrice", item);
    }
    /**
     * Object:诚意预留房间分录's 原租金类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getStandardRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("standardRentType"));
    }
    public void setStandardRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("standardRentType", item.getValue());
		}
    }
    /**
     * Object:诚意预留房间分录's 标准房间租价property 
     */
    public java.math.BigDecimal getStandardRoomRent()
    {
        return getBigDecimal("standardRoomRent");
    }
    public void setStandardRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRent", item);
    }
    /**
     * Object:诚意预留房间分录's 标准房租单价property 
     */
    public java.math.BigDecimal getStandardRoomRentPrice()
    {
        return getBigDecimal("standardRoomRentPrice");
    }
    public void setStandardRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRentPrice", item);
    }
    /**
     * Object:诚意预留房间分录's 起始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:诚意预留房间分录's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:诚意预留房间分录's 实际交房日期property 
     */
    public java.util.Date getActDeliveryRoomDate()
    {
        return getDate("actDeliveryRoomDate");
    }
    public void setActDeliveryRoomDate(java.util.Date item)
    {
        setDate("actDeliveryRoomDate", item);
    }
    /**
     * Object:诚意预留房间分录's 计租面积property 
     */
    public java.math.BigDecimal getTenancyArea()
    {
        return getBigDecimal("tenancyArea");
    }
    public void setTenancyArea(java.math.BigDecimal item)
    {
        setBigDecimal("tenancyArea", item);
    }
    /**
     * Object:诚意预留房间分录's 建筑面积property 
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
     * Object:诚意预留房间分录's 房间分录状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenRoomState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenRoomState"));
    }
    public void setTenRoomState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenRoomState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4977CB04");
    }
}