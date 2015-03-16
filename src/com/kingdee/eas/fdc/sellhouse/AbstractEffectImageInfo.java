package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEffectImageInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractEffectImageInfo()
    {
        this("id");
    }
    protected AbstractEffectImageInfo(String pkField)
    {
        super(pkField);
        put("elementEntry", new com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection());
    }
    /**
     * Object:效果图's 类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.EffectImageEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.EffectImageEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.EffectImageEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:效果图's 是否区分单元property 
     */
    public boolean isIsShowUnit()
    {
        return getBoolean("isShowUnit");
    }
    public void setIsShowUnit(boolean item)
    {
        setBoolean("isShowUnit", item);
    }
    /**
     * Object: 效果图 's 租售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 效果图 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object: 效果图 's 楼层 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo getBuildingFloor()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo)get("buildingFloor");
    }
    public void setBuildingFloor(com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo item)
    {
        put("buildingFloor", item);
    }
    /**
     * Object: 效果图 's 单元 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo getBuildingUnit()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo)get("buildingUnit");
    }
    public void setBuildingUnit(com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo item)
    {
        put("buildingUnit", item);
    }
    /**
     * Object: 效果图 's 元素分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection getElementEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection)get("elementEntry");
    }
    /**
     * Object:效果图's 效果图property 
     */
    public byte[] getEffectImage()
    {
        return (byte[])get("effectImage");
    }
    public void setEffectImage(byte[] item)
    {
        put("effectImage", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D6AA48F");
    }
}