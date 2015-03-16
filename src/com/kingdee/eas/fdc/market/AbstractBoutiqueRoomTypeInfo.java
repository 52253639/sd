package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBoutiqueRoomTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBoutiqueRoomTypeInfo()
    {
        this("id");
    }
    protected AbstractBoutiqueRoomTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 精品户型 's 组别 property 
     */
    public com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo getTreeid()
    {
        return (com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object: 精品户型 's 平层类型 property 
     */
    public com.kingdee.eas.fdc.market.FlatLayerTypeInfo getFlatLayerType()
    {
        return (com.kingdee.eas.fdc.market.FlatLayerTypeInfo)get("flatLayerType");
    }
    public void setFlatLayerType(com.kingdee.eas.fdc.market.FlatLayerTypeInfo item)
    {
        put("flatLayerType", item);
    }
    /**
     * Object: 精品户型 's 开发商 property 
     */
    public com.kingdee.eas.fdc.market.DeveloperManageInfo getDeveloper()
    {
        return (com.kingdee.eas.fdc.market.DeveloperManageInfo)get("developer");
    }
    public void setDeveloper(com.kingdee.eas.fdc.market.DeveloperManageInfo item)
    {
        put("developer", item);
    }
    /**
     * Object:精品户型's 建筑面积property 
     */
    public java.math.BigDecimal getArchitectArea()
    {
        return getBigDecimal("architectArea");
    }
    public void setArchitectArea(java.math.BigDecimal item)
    {
        setBigDecimal("architectArea", item);
    }
    /**
     * Object:精品户型's 套内面积property 
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
     * Object:精品户型's 单价property 
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
     * Object:精品户型's 面宽property 
     */
    public java.math.BigDecimal getWideSide()
    {
        return getBigDecimal("wideSide");
    }
    public void setWideSide(java.math.BigDecimal item)
    {
        setBigDecimal("wideSide", item);
    }
    /**
     * Object:精品户型's 建筑时间property 
     */
    public java.util.Date getBuildTime()
    {
        return getDate("buildTime");
    }
    public void setBuildTime(java.util.Date item)
    {
        setDate("buildTime", item);
    }
    /**
     * Object:精品户型's 户型说明property 
     */
    public String getRoomTypeExplain()
    {
        return getString("roomTypeExplain");
    }
    public void setRoomTypeExplain(String item)
    {
        setString("roomTypeExplain", item);
    }
    /**
     * Object:精品户型's 户型点评property 
     */
    public String getRoomTypeReview()
    {
        return getString("roomTypeReview");
    }
    public void setRoomTypeReview(String item)
    {
        setString("roomTypeReview", item);
    }
    /**
     * Object: 精品户型 's 户型类别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo getRoomType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo)get("roomType");
    }
    public void setRoomType(com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo item)
    {
        put("roomType", item);
    }
    /**
     * Object: 精品户型 's 方向 property 
     */
    public com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo getDirection()
    {
        return (com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo)get("direction");
    }
    public void setDirection(com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo item)
    {
        put("direction", item);
    }
    /**
     * Object: 精品户型 's 房屋形式 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getRoomStyle()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("roomStyle");
    }
    public void setRoomStyle(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("roomStyle", item);
    }
    /**
     * Object:精品户型's 精品户型分类property 
     */
    public String getBoutiqueHouseTy()
    {
        return getString("boutiqueHouseTy");
    }
    public void setBoutiqueHouseTy(String item)
    {
        setString("boutiqueHouseTy", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A051FA0A");
    }
}