package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHandleTenancyInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractHandleTenancyInfo()
    {
        this("id");
    }
    protected AbstractHandleTenancyInfo(String pkField)
    {
        super(pkField);
        put("handleRoomEntrys", new com.kingdee.eas.fdc.tenancy.HandleRoomEntrysCollection());
    }
    /**
     * Object: 房间交接单 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object: 房间交接单 's 交接单分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.HandleRoomEntrysCollection getHandleRoomEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.HandleRoomEntrysCollection)get("handleRoomEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("03B8A0AF");
    }
}