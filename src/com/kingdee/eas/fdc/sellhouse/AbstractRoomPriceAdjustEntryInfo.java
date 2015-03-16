package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPriceAdjustEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomPriceAdjustEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomPriceAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房间价目分录 's 房间价目管理 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 房间价目分录 's 房间 property 
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
     * Object:房间价目分录's 原总价property 
     */
    public java.math.BigDecimal getOldSumAmount()
    {
        return getBigDecimal("oldSumAmount");
    }
    public void setOldSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldSumAmount", item);
    }
    /**
     * Object:房间价目分录's 新总价property 
     */
    public java.math.BigDecimal getNewSumAmount()
    {
        return getBigDecimal("newSumAmount");
    }
    public void setNewSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newSumAmount", item);
    }
    /**
     * Object:房间价目分录's 原建筑面积property 
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
     * Object:房间价目分录's 原建筑单价property 
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
     * Object:房间价目分录's 原套内面积property 
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
     * Object:房间价目分录's 原套内单价property 
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
     * Object:房间价目分录's 原是否按照套内计算property 
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
     * Object:房间价目分录's 新建筑面积property 
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
     * Object:房间价目分录's 新建筑单价property 
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
     * Object:房间价目分录's 新套内面积property 
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
     * Object:房间价目分录's 新套内单价property 
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
     * Object:房间价目分录's 新是否按照套内计算property 
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
     * Object:房间价目分录's 建筑单价差额property 
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
     * Object:房间价目分录's 建筑单据差额比例property 
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
     * Object:房间价目分录's 套内单据差额property 
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
     * Object:房间价目分录's 套内单价差额比例property 
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
     * Object:房间价目分录's 销售方式property 
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
     * Object:房间价目分录's 定价类型property 
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
     * Object:房间价目分录's 现底总价property 
     */
    public java.math.BigDecimal getNewBaseStandardPrice()
    {
        return getBigDecimal("newBaseStandardPrice");
    }
    public void setNewBaseStandardPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newBaseStandardPrice", item);
    }
    /**
     * Object:房间价目分录's 原底总价property 
     */
    public java.math.BigDecimal getOldBaseStandardPrice()
    {
        return getBigDecimal("oldBaseStandardPrice");
    }
    public void setOldBaseStandardPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldBaseStandardPrice", item);
    }
    /**
     * Object:房间价目分录's 现总经理折扣property 
     */
    public java.math.BigDecimal getNewManagerAgio()
    {
        return getBigDecimal("newManagerAgio");
    }
    public void setNewManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("newManagerAgio", item);
    }
    /**
     * Object:房间价目分录's 原总经理折扣property 
     */
    public java.math.BigDecimal getOldManagerAgio()
    {
        return getBigDecimal("oldManagerAgio");
    }
    public void setOldManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("oldManagerAgio", item);
    }
    /**
     * Object:房间价目分录's 现案场经理折扣property 
     */
    public java.math.BigDecimal getNewSceneManagerAgio()
    {
        return getBigDecimal("newSceneManagerAgio");
    }
    public void setNewSceneManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("newSceneManagerAgio", item);
    }
    /**
     * Object:房间价目分录's 原案场经理折扣property 
     */
    public java.math.BigDecimal getOldSceneManagerAgio()
    {
        return getBigDecimal("oldSceneManagerAgio");
    }
    public void setOldSceneManagerAgio(java.math.BigDecimal item)
    {
        setBigDecimal("oldSceneManagerAgio", item);
    }
    /**
     * Object:房间价目分录's 现营销总监折扣property 
     */
    public java.math.BigDecimal getNewSalesDirectorAgio()
    {
        return getBigDecimal("newSalesDirectorAgio");
    }
    public void setNewSalesDirectorAgio(java.math.BigDecimal item)
    {
        setBigDecimal("newSalesDirectorAgio", item);
    }
    /**
     * Object:房间价目分录's 原营销总监折扣property 
     */
    public java.math.BigDecimal getOldSalesDirectorAgio()
    {
        return getBigDecimal("oldSalesDirectorAgio");
    }
    public void setOldSalesDirectorAgio(java.math.BigDecimal item)
    {
        setBigDecimal("oldSalesDirectorAgio", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD32DB70");
    }
}