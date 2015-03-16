package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquipmentEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquipmentEntryInfo()
    {
        this("id");
    }
    protected AbstractEquipmentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 配套设备分录 's 租赁房间 property 
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
     * Object: 配套设备分录 's 配套设备档案 property 
     */
    public com.kingdee.eas.fdc.propertymgmt.PpmDevFileMaintenanceInfo getDev()
    {
        return (com.kingdee.eas.fdc.propertymgmt.PpmDevFileMaintenanceInfo)get("dev");
    }
    public void setDev(com.kingdee.eas.fdc.propertymgmt.PpmDevFileMaintenanceInfo item)
    {
        put("dev", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("124D916D");
    }
}