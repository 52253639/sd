package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEPriceHistoryEntryInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSHEPriceHistoryEntryInfo()
    {
        this("id");
    }
    protected AbstractSHEPriceHistoryEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 售楼历史定价分录 's null property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("head", item);
    }
    /**
     * Object:售楼历史定价分录's 套内单价property 
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
     * Object:售楼历史定价分录's 建筑单价property 
     */
    public java.math.BigDecimal getBuildPrice()
    {
        return getBigDecimal("buildPrice");
    }
    public void setBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildPrice", item);
    }
    /**
     * Object:售楼历史定价分录's 是否按套内面积计算property 
     */
    public boolean isIsCalByRoomArea()
    {
        return getBoolean("isCalByRoomArea");
    }
    public void setIsCalByRoomArea(boolean item)
    {
        setBoolean("isCalByRoomArea", item);
    }
    /**
     * Object:售楼历史定价分录's 是否调价property 
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
     * Object:售楼历史定价分录's 建筑面积property 
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
     * Object:售楼历史定价分录's 套内面积property 
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
     * Object: 售楼历史定价分录 's 定价单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo getRoomPriceBill()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo)get("roomPriceBill");
    }
    public void setRoomPriceBill(com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo item)
    {
        put("roomPriceBill", item);
    }
    /**
     * Object: 售楼历史定价分录 's 调价单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo getPriceAdjustBill()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo)get("priceAdjustBill");
    }
    public void setPriceAdjustBill(com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo item)
    {
        put("priceAdjustBill", item);
    }
    /**
     * Object:售楼历史定价分录's 单据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum getPriceType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum.getEnum(getString("priceType"));
    }
    public void setPriceType(com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum item)
    {
		if (item != null) {
        setString("priceType", item.getValue());
		}
    }
    /**
     * Object:售楼历史定价分录's 标准总价property 
     */
    public java.math.BigDecimal getStandardTotalPrice()
    {
        return getBigDecimal("standardTotalPrice");
    }
    public void setStandardTotalPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2F1FB8B2");
    }
}