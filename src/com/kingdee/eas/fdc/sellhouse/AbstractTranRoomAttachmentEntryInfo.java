package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranRoomAttachmentEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTranRoomAttachmentEntryInfo()
    {
        this("");
    }
    protected AbstractTranRoomAttachmentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 附属房产信息基类 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:附属房产信息基类's 并入金额property 
     */
    public java.math.BigDecimal getMergeAmount()
    {
        return getBigDecimal("mergeAmount");
    }
    public void setMergeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("mergeAmount", item);
    }
    /**
     * Object:附属房产信息基类's 是否并入合同property 
     */
    public boolean isIsAttachcmentToContract()
    {
        return getBoolean("isAttachcmentToContract");
    }
    public void setIsAttachcmentToContract(boolean item)
    {
        setBoolean("isAttachcmentToContract", item);
    }
    /**
     * Object: 附属房产信息基类 's 附属房产 property 
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
     * Object:附属房产信息基类's 房间标准总价快照property 
     */
    public java.math.BigDecimal getStandardTotalAmount()
    {
        return getBigDecimal("standardTotalAmount");
    }
    public void setStandardTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalAmount", item);
    }
    /**
     * Object:附属房产信息基类's 折扣property 
     */
    public String getAgioDes()
    {
        return getString("agioDes");
    }
    public void setAgioDes(String item)
    {
        setString("agioDes", item);
    }
    /**
     * Object:附属房产信息基类's 房间建筑面积快照property 
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
     * Object:附属房产信息基类's 房间套内面积快照property 
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
     * Object:附属房产信息基类's 房间建筑单价快照property 
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
     * Object:附属房产信息基类's 房间套内单价快照property 
     */
    public java.math.BigDecimal getRoomPrice()
    {
        return getBigDecimal("roomPrice");
    }
    public void setRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("95A68C4E");
    }
}