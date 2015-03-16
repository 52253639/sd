package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomAreaChangeHisInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRoomAreaChangeHisInfo()
    {
        this("id");
    }
    protected AbstractRoomAreaChangeHisInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:房间面积变更历史's 建筑面积property 
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
     * Object:房间面积变更历史's 套内面积property 
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
     * Object:房间面积变更历史's 实测建筑面积property 
     */
    public java.math.BigDecimal getActualBuildingArea()
    {
        return getBigDecimal("actualBuildingArea");
    }
    public void setActualBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualBuildingArea", item);
    }
    /**
     * Object:房间面积变更历史's 实测套内面积property 
     */
    public java.math.BigDecimal getActualRoomArea()
    {
        return getBigDecimal("actualRoomArea");
    }
    public void setActualRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualRoomArea", item);
    }
    /**
     * Object:房间面积变更历史's 房间IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getHead()
    {
        return getBOSUuid("head");
    }
    public void setHead(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("head", item);
    }
    /**
     * Object:房间面积变更历史's 业务类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:房间面积变更历史's 操作日期property 
     */
    public java.util.Date getOperationTime()
    {
        return getDate("operationTime");
    }
    public void setOperationTime(java.util.Date item)
    {
        setDate("operationTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD7BBD1F");
    }
}