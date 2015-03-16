package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompensateRoomListInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractCompensateRoomListInfo()
    {
        this("id");
    }
    protected AbstractCompensateRoomListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 补差房间列表 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 补差房间列表 's 房间 property 
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
     * Object: 补差房间列表 's 签约单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getSign()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("sign");
    }
    public void setSign(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("sign", item);
    }
    /**
     * Object:补差房间列表's 参考补差款property 
     */
    public java.math.BigDecimal getRefAmount()
    {
        return getBigDecimal("refAmount");
    }
    public void setRefAmount(java.math.BigDecimal item)
    {
        setBigDecimal("refAmount", item);
    }
    /**
     * Object:补差房间列表's 最新总价property 
     */
    public java.math.BigDecimal getLastAmount()
    {
        return getBigDecimal("lastAmount");
    }
    public void setLastAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastAmount", item);
    }
    /**
     * Object:补差房间列表's 实际补差款property 
     */
    public java.math.BigDecimal getActAmount()
    {
        return getBigDecimal("actAmount");
    }
    public void setActAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actAmount", item);
    }
    /**
     * Object:补差房间列表's 主房产补差款property 
     */
    public java.math.BigDecimal getMainAmount()
    {
        return getBigDecimal("mainAmount");
    }
    public void setMainAmount(java.math.BigDecimal item)
    {
        setBigDecimal("mainAmount", item);
    }
    /**
     * Object:补差房间列表's 附属房产补差款property 
     */
    public java.math.BigDecimal getAttAmount()
    {
        return getBigDecimal("attAmount");
    }
    public void setAttAmount(java.math.BigDecimal item)
    {
        setBigDecimal("attAmount", item);
    }
    /**
     * Object:补差房间列表's 面积差异率property 
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
     * Object:补差房间列表's 是否作废property 
     */
    public boolean isIsNullify()
    {
        return getBoolean("isNullify");
    }
    public void setIsNullify(boolean item)
    {
        setBoolean("isNullify", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5EC8BC8B");
    }
}