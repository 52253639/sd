package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingRoomEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildingRoomEntrysInfo()
    {
        this("id");
    }
    protected AbstractBuildingRoomEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 楼栋房间分录 's 头 property 
     */
    public com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 楼栋房间分录 's 房间信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRooms()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("rooms");
    }
    public void setRooms(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("rooms", item);
    }
    /**
     * Object:楼栋房间分录's 租金单价property 
     */
    public java.math.BigDecimal getStandardRentPrice()
    {
        return getBigDecimal("standardRentPrice");
    }
    public void setStandardRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardRentPrice", item);
    }
    /**
     * Object:楼栋房间分录's 标准租金property 
     */
    public java.math.BigDecimal getStandardRent()
    {
        return getBigDecimal("standardRent");
    }
    public void setStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRent", item);
    }
    /**
     * Object:楼栋房间分录's 是否调整property 
     */
    public boolean isIsAdjust()
    {
        return getBoolean("isAdjust");
    }
    public void setIsAdjust(boolean item)
    {
        setBoolean("isAdjust", item);
    }
    /**
     * Object:楼栋房间分录's 套内面积(原房间面积)property 
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
     * Object:楼栋房间分录's 租金类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("rentType"));
    }
    public void setRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("rentType", item.getValue());
		}
    }
    /**
     * Object:楼栋房间分录's 租赁状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenancyState"));
    }
    public void setTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenancyState", item.getValue());
		}
    }
    /**
     * Object:楼栋房间分录's 房间长编码property 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:楼栋房间分录's 房间编码property 
     */
    public String getRoomNumber()
    {
        return getString("roomNumber");
    }
    public void setRoomNumber(String item)
    {
        setString("roomNumber", item);
    }
    /**
     * Object: 楼栋房间分录 's 房间单独定价时定租单头 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo getTenRentBillHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo)get("tenRentBillHead");
    }
    public void setTenRentBillHead(com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo item)
    {
        put("tenRentBillHead", item);
    }
    /**
     * Object:楼栋房间分录's 定租计算方式property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModeEnum getTenancyModel()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyModeEnum.getEnum(getString("tenancyModel"));
    }
    public void setTenancyModel(com.kingdee.eas.fdc.tenancy.TenancyModeEnum item)
    {
		if (item != null) {
        setString("tenancyModel", item.getValue());
		}
    }
    /**
     * Object:楼栋房间分录's 建筑面积property 
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
     * Object:楼栋房间分录's 建筑租金单价property 
     */
    public java.math.BigDecimal getBuildingRentPrice()
    {
        return getBigDecimal("buildingRentPrice");
    }
    public void setBuildingRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingRentPrice", item);
    }
    /**
     * Object:楼栋房间分录's 套内租金单价property 
     */
    public java.math.BigDecimal getRoomRentPrice()
    {
        return getBigDecimal("roomRentPrice");
    }
    public void setRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomRentPrice", item);
    }
    /**
     * Object:楼栋房间分录's 计租面积property 
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
     * Object:楼栋房间分录's 租金单价property 
     */
    public java.math.BigDecimal getTenancyRentPrice()
    {
        return getBigDecimal("tenancyRentPrice");
    }
    public void setTenancyRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("tenancyRentPrice", item);
    }
    /**
     * Object:楼栋房间分录's 日单价property 
     */
    public java.math.BigDecimal getDayPrice()
    {
        return getBigDecimal("dayPrice");
    }
    public void setDayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dayPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("01373A39");
    }
}