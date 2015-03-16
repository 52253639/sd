package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentRemissionInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractRentRemissionInfo()
    {
        this("id");
    }
    protected AbstractRentRemissionInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection());
    }
    /**
     * Object: 租金减免 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancy");
    }
    public void setTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancy", item);
    }
    /**
     * Object:租金减免's 房间property 
     */
    public String getRoomName()
    {
        return getString("roomName");
    }
    public void setRoomName(String item)
    {
        setString("roomName", item);
    }
    /**
     * Object:租金减免's 配套资源property 
     */
    public String getAttachResource()
    {
        return getString("attachResource");
    }
    public void setAttachResource(String item)
    {
        setString("attachResource", item);
    }
    /**
     * Object:租金减免's 客户名称property 
     */
    public String getCustomerName()
    {
        return getString("customerName");
    }
    public void setCustomerName(String item)
    {
        setString("customerName", item);
    }
    /**
     * Object:租金减免's 合同租期property 
     */
    public java.math.BigDecimal getLeaseCount()
    {
        return getBigDecimal("leaseCount");
    }
    public void setLeaseCount(java.math.BigDecimal item)
    {
        setBigDecimal("leaseCount", item);
    }
    /**
     * Object: 租金减免 's 租金减免分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection)get("entry");
    }
    /**
     * Object: 租金减免 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DADF01B7");
    }
}