package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomKeepDownBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomKeepDownBillInfo()
    {
        this("id");
    }
    protected AbstractRoomKeepDownBillInfo(String pkField)
    {
        super(pkField);
        put("customerEntry", new com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryCollection());
    }
    /**
     * Object: 房间保留 's 房间 property 
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
     * Object:房间保留's 取消预留日期property 
     */
    public java.util.Date getCancelDate()
    {
        return getDate("cancelDate");
    }
    public void setCancelDate(java.util.Date item)
    {
        setDate("cancelDate", item);
    }
    /**
     * Object: 房间保留 's 客户分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryCollection)get("customerEntry");
    }
    /**
     * Object:房间保留's 保留天数property 
     */
    public int getKeepDate()
    {
        return getInt("keepDate");
    }
    public void setKeepDate(int item)
    {
        setInt("keepDate", item);
    }
    /**
     * Object: 房间保留 's 取消预留人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCacelStaff()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("cacelStaff");
    }
    public void setCacelStaff(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("cacelStaff", item);
    }
    /**
     * Object: 房间保留 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:房间保留's 客户名称组合property 
     */
    public String getCusStr()
    {
        return getString("cusStr");
    }
    public void setCusStr(String item)
    {
        setString("cusStr", item);
    }
    /**
     * Object:房间保留's 业务状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum getBizState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:房间保留's 预留原因property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomKeepDownReasonEnum getReason()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomKeepDownReasonEnum.getEnum(getString("reason"));
    }
    public void setReason(com.kingdee.eas.fdc.sellhouse.RoomKeepDownReasonEnum item)
    {
		if (item != null) {
        setString("reason", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10008BC4");
    }
}