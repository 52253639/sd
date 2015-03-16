package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreconcertPostponeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPreconcertPostponeInfo()
    {
        this("id");
    }
    protected AbstractPreconcertPostponeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:预定延期's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:预定延期's 现预定日期property 
     */
    public java.util.Date getNowDat()
    {
        return getDate("nowDat");
    }
    public void setNowDat(java.util.Date item)
    {
        setDate("nowDat", item);
    }
    /**
     * Object:预定延期's 原预定日期property 
     */
    public java.util.Date getOriginalDate()
    {
        return getDate("originalDate");
    }
    public void setOriginalDate(java.util.Date item)
    {
        setDate("originalDate", item);
    }
    /**
     * Object:预定延期's 原预定有效期property 
     */
    public java.util.Date getOriginalAvailab()
    {
        return getDate("originalAvailab");
    }
    public void setOriginalAvailab(java.util.Date item)
    {
        setDate("originalAvailab", item);
    }
    /**
     * Object:预定延期's 现预定有效期property 
     */
    public java.util.Date getNowavailab()
    {
        return getDate("nowavailab");
    }
    public void setNowavailab(java.util.Date item)
    {
        setDate("nowavailab", item);
    }
    /**
     * Object: 预定延期 's 房间 property 
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
     * Object: 预定延期 's 预定单编码 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59919274");
    }
}