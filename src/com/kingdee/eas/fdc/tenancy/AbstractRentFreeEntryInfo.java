package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentFreeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRentFreeEntryInfo()
    {
        this("id");
    }
    protected AbstractRentFreeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 免租分录 's 租赁合同头 property 
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
     * Object:免租分录's 免租开始日期property 
     */
    public java.util.Date getFreeStartDate()
    {
        return getDate("freeStartDate");
    }
    public void setFreeStartDate(java.util.Date item)
    {
        setDate("freeStartDate", item);
    }
    /**
     * Object:免租分录's 免租结束日期property 
     */
    public java.util.Date getFreeEndDate()
    {
        return getDate("freeEndDate");
    }
    public void setFreeEndDate(java.util.Date item)
    {
        setDate("freeEndDate", item);
    }
    /**
     * Object:免租分录's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:免租分录's 免租类型property 
     */
    public com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum getFreeTenancyType()
    {
        return com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum.getEnum(getString("freeTenancyType"));
    }
    public void setFreeTenancyType(com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum item)
    {
		if (item != null) {
        setString("freeTenancyType", item.getValue());
		}
    }
    /**
     * Object:免租分录's 是否赠送property 
     */
    public boolean isIsPresent()
    {
        return getBoolean("isPresent");
    }
    public void setIsPresent(boolean item)
    {
        setBoolean("isPresent", item);
    }
    /**
     * Object: 免租分录 's 免租期审批单 property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeBillInfo getRentFreeBill()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeBillInfo)get("rentFreeBill");
    }
    public void setRentFreeBill(com.kingdee.eas.fdc.tenancy.RentFreeBillInfo item)
    {
        put("rentFreeBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7D898324");
    }
}