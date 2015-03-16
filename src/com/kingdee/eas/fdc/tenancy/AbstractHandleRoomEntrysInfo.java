package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHandleRoomEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractHandleRoomEntrysInfo()
    {
        this("id");
    }
    protected AbstractHandleRoomEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:房间交接分录's 交接类型property 
     */
    public String getHandleType()
    {
        return getString("handleType");
    }
    public void setHandleType(String item)
    {
        setString("handleType", item);
    }
    /**
     * Object:房间交接分录's 原交接状态property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleStateEnum getOldHandleState()
    {
        return com.kingdee.eas.fdc.tenancy.HandleStateEnum.getEnum(getString("oldHandleState"));
    }
    public void setOldHandleState(com.kingdee.eas.fdc.tenancy.HandleStateEnum item)
    {
		if (item != null) {
        setString("oldHandleState", item.getValue());
		}
    }
    /**
     * Object:房间交接分录's 现交接状态property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleStateEnum getNewHandleState()
    {
        return com.kingdee.eas.fdc.tenancy.HandleStateEnum.getEnum(getString("newHandleState"));
    }
    public void setNewHandleState(com.kingdee.eas.fdc.tenancy.HandleStateEnum item)
    {
		if (item != null) {
        setString("newHandleState", item.getValue());
		}
    }
    /**
     * Object: 房间交接分录 's 租赁合同房间 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo getTenancyRoom()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo)get("tenancyRoom");
    }
    public void setTenancyRoom(com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo item)
    {
        put("tenancyRoom", item);
    }
    /**
     * Object: 房间交接分录 's 租赁配套资源 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo getAttach()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo)get("attach");
    }
    public void setAttach(com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo item)
    {
        put("attach", item);
    }
    /**
     * Object:房间交接分录's 房间编码property 
     */
    public String getTenancyRoomNumber()
    {
        return getString("tenancyRoomNumber");
    }
    public void setTenancyRoomNumber(String item)
    {
        setString("tenancyRoomNumber", item);
    }
    /**
     * Object:房间交接分录's 办理状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TransactStateEnum getTransactState()
    {
        return com.kingdee.eas.fdc.tenancy.TransactStateEnum.getEnum(getString("transactState"));
    }
    public void setTransactState(com.kingdee.eas.fdc.tenancy.TransactStateEnum item)
    {
		if (item != null) {
        setString("transactState", item.getValue());
		}
    }
    /**
     * Object:房间交接分录's 完成日期property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object: 房间交接分录 's 房间交接单 property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleTenancyInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.HandleTenancyInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.HandleTenancyInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("89BC1AED");
    }
}