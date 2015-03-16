package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValueInputPriceEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractValueInputPriceEntryInfo()
    {
        this("id");
    }
    protected AbstractValueInputPriceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 货值填报定价分录 's 头 property 
     */
    public com.kingdee.eas.fdc.market.ValueInputInfo getHead()
    {
        return (com.kingdee.eas.fdc.market.ValueInputInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.market.ValueInputInfo item)
    {
        put("head", item);
    }
    /**
     * Object:货值填报定价分录's 项目property 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:货值填报定价分录's 楼栋property 
     */
    public String getBuild()
    {
        return getString("build");
    }
    public void setBuild(String item)
    {
        setString("build", item);
    }
    /**
     * Object:货值填报定价分录's 单元property 
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
     * Object:货值填报定价分录's 房号property 
     */
    public String getRoom()
    {
        return getString("room");
    }
    public void setRoom(String item)
    {
        setString("room", item);
    }
    /**
     * Object:货值填报定价分录's 销售面积类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellTypeEnum getSellType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellTypeEnum.getEnum(getString("sellType"));
    }
    public void setSellType(com.kingdee.eas.fdc.sellhouse.SellTypeEnum item)
    {
		if (item != null) {
        setString("sellType", item.getValue());
		}
    }
    /**
     * Object:货值填报定价分录's 定价类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum getPriceType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum.getEnum(getInt("priceType"));
    }
    public void setPriceType(com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum item)
    {
		if (item != null) {
        setInt("priceType", item.getValue());
		}
    }
    /**
     * Object:货值填报定价分录's 表总价property 
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
     * Object:货值填报定价分录's 底总价property 
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
     * Object:货值填报定价分录's 建筑单价property 
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
     * Object:货值填报定价分录's 套内单价property 
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
     * Object:货值填报定价分录's 建筑面积property 
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
     * Object:货值填报定价分录's 套内面积property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C1BC95B3");
    }
}