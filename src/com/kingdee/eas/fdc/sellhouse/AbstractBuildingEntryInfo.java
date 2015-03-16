package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildingEntryInfo()
    {
        this("id");
    }
    protected AbstractBuildingEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: BuildingEntry 's head property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: BuildingEntry 's building property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B8FD6B23");
    }
}