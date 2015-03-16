package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPriceBuildingEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomPriceBuildingEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomPriceBuildingEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房价楼栋分录 's 房间价目 property 
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
     * Object: 房价楼栋分录 's 楼栋 property 
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
        return new BOSObjectType("C565102B");
    }
}