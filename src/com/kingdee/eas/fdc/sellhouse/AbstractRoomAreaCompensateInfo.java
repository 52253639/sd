package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomAreaCompensateInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomAreaCompensateInfo()
    {
        this("id");
    }
    protected AbstractRoomAreaCompensateInfo(String pkField)
    {
        super(pkField);
        put("roomListHis", new com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisCollection());
        put("roomList", new com.kingdee.eas.fdc.sellhouse.CompensateRoomListCollection());
    }
    /**
     * Object: 补差办理 's 房间 property 
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
     * Object:补差办理's 补差日期property 
     */
    public java.util.Date getCompensateDate()
    {
        return getDate("compensateDate");
    }
    public void setCompensateDate(java.util.Date item)
    {
        setDate("compensateDate", item);
    }
    /**
     * Object: 补差办理 's 经办人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("transactor");
    }
    public void setTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("transactor", item);
    }
    /**
     * Object:补差办理's 面积差异率property 
     */
    public java.math.BigDecimal getCompensateRate()
    {
        return getBigDecimal("compensateRate");
    }
    public void setCompensateRate(java.math.BigDecimal item)
    {
        setBigDecimal("compensateRate", item);
    }
    /**
     * Object:补差办理's 最新总价property 
     */
    public java.math.BigDecimal getLatestAmount()
    {
        return getBigDecimal("latestAmount");
    }
    public void setLatestAmount(java.math.BigDecimal item)
    {
        setBigDecimal("latestAmount", item);
    }
    /**
     * Object: 补差办理 's 方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo getScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo)get("scheme");
    }
    public void setScheme(com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo item)
    {
        put("scheme", item);
    }
    /**
     * Object:补差办理's 补差状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum getCompensateState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum.getEnum(getString("compensateState"));
    }
    public void setCompensateState(com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum item)
    {
		if (item != null) {
        setString("compensateState", item.getValue());
		}
    }
    /**
     * Object:补差办理's 是否方案补差property 
     */
    public boolean isIsCalcByScheme()
    {
        return getBoolean("isCalcByScheme");
    }
    public void setIsCalcByScheme(boolean item)
    {
        setBoolean("isCalcByScheme", item);
    }
    /**
     * Object: 补差办理 's 补差房间列表 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CompensateRoomListCollection getRoomList()
    {
        return (com.kingdee.eas.fdc.sellhouse.CompensateRoomListCollection)get("roomList");
    }
    /**
     * Object:补差办理's 应补差额property 
     */
    public java.math.BigDecimal getCompensateAmount()
    {
        return getBigDecimal("compensateAmount");
    }
    public void setCompensateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("compensateAmount", item);
    }
    /**
     * Object:补差办理's 补差类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateTypeEnum getCompensateType()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateTypeEnum.getEnum(getString("compensateType"));
    }
    public void setCompensateType(com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateTypeEnum item)
    {
		if (item != null) {
        setString("compensateType", item.getValue());
		}
    }
    /**
     * Object: 补差办理 's 楼栋 property 
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
     * Object: 补差办理 's 单元 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo getUnit()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object: 补差办理 's 项目 property 
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
     * Object: 补差办理 's 补差房间历史 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisCollection getRoomListHis()
    {
        return (com.kingdee.eas.fdc.sellhouse.CompensateRoomListHisCollection)get("roomListHis");
    }
    /**
     * Object:补差办理's 应完成日期property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("455E117A");
    }
}