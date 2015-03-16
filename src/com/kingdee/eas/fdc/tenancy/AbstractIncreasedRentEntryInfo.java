package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIncreasedRentEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractIncreasedRentEntryInfo()
    {
        this("id");
    }
    protected AbstractIncreasedRentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 租金递增列表分录 's 租赁合同头 property 
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
     * Object:租金递增列表分录's 递增日期property 
     */
    public java.util.Date getIncreaseDate()
    {
        return getDate("increaseDate");
    }
    public void setIncreaseDate(java.util.Date item)
    {
        setDate("increaseDate", item);
    }
    /**
     * Object:租金递增列表分录's 递增方式property 
     */
    public com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum getIncreaseType()
    {
        return com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum.getEnum(getString("increaseType"));
    }
    public void setIncreaseType(com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum item)
    {
		if (item != null) {
        setString("increaseType", item.getValue());
		}
    }
    /**
     * Object:租金递增列表分录's 值property 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    /**
     * Object:租金递增列表分录's 递增类型property 
     */
    public com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum getIncreaseStyle()
    {
        return com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum.getEnum(getString("increaseStyle"));
    }
    public void setIncreaseStyle(com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum item)
    {
		if (item != null) {
        setString("increaseStyle", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3E3035E0");
    }
}