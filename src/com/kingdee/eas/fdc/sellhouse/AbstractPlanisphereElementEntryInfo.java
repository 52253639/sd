package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanisphereElementEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPlanisphereElementEntryInfo()
    {
        this("id");
    }
    protected AbstractPlanisphereElementEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 平面示意图元素分录 's 主体 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PlanisphereInfo getPlanisphere()
    {
        return (com.kingdee.eas.fdc.sellhouse.PlanisphereInfo)get("planisphere");
    }
    public void setPlanisphere(com.kingdee.eas.fdc.sellhouse.PlanisphereInfo item)
    {
        put("planisphere", item);
    }
    /**
     * Object:平面示意图元素分录's 类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.PlanisphereElementEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.PlanisphereElementEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.PlanisphereElementEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:平面示意图元素分录's 元素名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:平面示意图元素分录's 轮廓定位property 
     */
    public byte[] getOutLineLocationData()
    {
        return (byte[])get("outLineLocationData");
    }
    public void setOutLineLocationData(byte[] item)
    {
        put("outLineLocationData", item);
    }
    /**
     * Object:平面示意图元素分录's 名称定位property 
     */
    public byte[] getNameLocationData()
    {
        return (byte[])get("nameLocationData");
    }
    public void setNameLocationData(byte[] item)
    {
        put("nameLocationData", item);
    }
    /**
     * Object:平面示意图元素分录's 轮廓背景色property 
     */
    public int getOutLineBackColor()
    {
        return getInt("outLineBackColor");
    }
    public void setOutLineBackColor(int item)
    {
        setInt("outLineBackColor", item);
    }
    /**
     * Object:平面示意图元素分录's 轮廓边框颜色property 
     */
    public int getOutLineColor()
    {
        return getInt("outLineColor");
    }
    public void setOutLineColor(int item)
    {
        setInt("outLineColor", item);
    }
    /**
     * Object:平面示意图元素分录's 名称背景颜色property 
     */
    public int getNameBackColor()
    {
        return getInt("nameBackColor");
    }
    public void setNameBackColor(int item)
    {
        setInt("nameBackColor", item);
    }
    /**
     * Object:平面示意图元素分录's 名称颜色property 
     */
    public int getNameColor()
    {
        return getInt("nameColor");
    }
    public void setNameColor(int item)
    {
        setInt("nameColor", item);
    }
    /**
     * Object: 平面示意图元素分录 's 元素关联的项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProjectEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProjectEntry");
    }
    public void setSellProjectEntry(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProjectEntry", item);
    }
    /**
     * Object: 平面示意图元素分录 's 元素关联的楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuildingEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("buildingEntry");
    }
    public void setBuildingEntry(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("buildingEntry", item);
    }
    /**
     * Object: 平面示意图元素分录 's 元素关联的房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoomEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("roomEntry");
    }
    public void setRoomEntry(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("roomEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("28567C68");
    }
}