package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHabitationRecordInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractHabitationRecordInfo()
    {
        this("id");
    }
    protected AbstractHabitationRecordInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 入住记录 's 房间 property 
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
     * Object:入住记录's 入住身份property 
     */
    public com.kingdee.eas.fdc.sellhouse.HabitationStatusEnum getHabitationStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.HabitationStatusEnum.getEnum(getString("habitationStatus"));
    }
    public void setHabitationStatus(com.kingdee.eas.fdc.sellhouse.HabitationStatusEnum item)
    {
		if (item != null) {
        setString("habitationStatus", item.getValue());
		}
    }
    /**
     * Object:入住记录's 入住日期property 
     */
    public java.util.Date getHabitationDate()
    {
        return getDate("habitationDate");
    }
    public void setHabitationDate(java.util.Date item)
    {
        setDate("habitationDate", item);
    }
    /**
     * Object:入住记录's 搬出日期property 
     */
    public java.util.Date getMoveOutDate()
    {
        return getDate("MoveOutDate");
    }
    public void setMoveOutDate(java.util.Date item)
    {
        setDate("MoveOutDate", item);
    }
    /**
     * Object: 入住记录 's 入伙单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinInfo getRoomJoin()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinInfo)get("roomJoin");
    }
    public void setRoomJoin(com.kingdee.eas.fdc.sellhouse.RoomJoinInfo item)
    {
        put("roomJoin", item);
    }
    /**
     * Object: 入住记录 's 物业登记单 property 
     */
    public com.kingdee.eas.fdc.propertymgmt.JoinVoucherInfo getJoinVoucher()
    {
        return (com.kingdee.eas.fdc.propertymgmt.JoinVoucherInfo)get("joinVoucher");
    }
    public void setJoinVoucher(com.kingdee.eas.fdc.propertymgmt.JoinVoucherInfo item)
    {
        put("joinVoucher", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4CA7468D");
    }
}