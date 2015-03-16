package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPriceAdjustHisInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRoomPriceAdjustHisInfo()
    {
        this("id");
    }
    protected AbstractRoomPriceAdjustHisInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:房间价目历史's 房价单据Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getBillId()
    {
        return getBOSUuid("billId");
    }
    public void setBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("billId", item);
    }
    /**
     * Object: 房间价目历史 's 房间 property 
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
     * Object:房间价目历史's 预测房号property 
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
     * Object:房间价目历史's 实测房号property 
     */
    public String getRoomNo()
    {
        return getString("roomNo");
    }
    public void setRoomNo(String item)
    {
        setString("roomNo", item);
    }
    /**
     * Object:房间价目历史's 销售方式property 
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
     * Object:房间价目历史's 标准总价property 
     */
    public java.math.BigDecimal getStandAmount()
    {
        return getBigDecimal("standAmount");
    }
    public void setStandAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standAmount", item);
    }
    /**
     * Object:房间价目历史's 建筑面积property 
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
     * Object:房间价目历史's nullproperty 
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
     * Object:房间价目历史's 套内面积property 
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
     * Object:房间价目历史's 套内单价property 
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
     * Object:房间价目历史's 销售状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getSellState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("sellState"));
    }
    public void setSellState(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("sellState", item.getValue());
		}
    }
    /**
     * Object:房间价目历史's 变更状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.sellhouse.ChangeStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    /**
     * Object:房间价目历史's 定价类型property 
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
     * Object:房间价目历史's 定价方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum getPriceMode()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum.getEnum(getString("priceMode"));
    }
    public void setPriceMode(com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum item)
    {
		if (item != null) {
        setString("priceMode", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CEA60E10");
    }
}