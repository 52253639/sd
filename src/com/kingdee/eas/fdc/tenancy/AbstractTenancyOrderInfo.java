package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyOrderInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyOrderInfo()
    {
        this("id");
    }
    protected AbstractTenancyOrderInfo(String pkField)
    {
        super(pkField);
        put("roomEntrys", new com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection());
    }
    /**
     * Object:放租单's 放租时间property 
     */
    public java.util.Date getOrderDate()
    {
        return getDate("orderDate");
    }
    public void setOrderDate(java.util.Date item)
    {
        setDate("orderDate", item);
    }
    /**
     * Object:放租单's 执行时间property 
     */
    public java.util.Date getExecuteDate()
    {
        return getDate("executeDate");
    }
    public void setExecuteDate(java.util.Date item)
    {
        setDate("executeDate", item);
    }
    /**
     * Object:放租单's 是否被执行property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: 放租单 's 执行人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getExecuteUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("executeUser");
    }
    public void setExecuteUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("executeUser", item);
    }
    /**
     * Object: 放租单 's 销售项目 property 
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
     * Object: 放租单 's 房间分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection getRoomEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection)get("roomEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FA35C857");
    }
}