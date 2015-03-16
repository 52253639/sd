package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenRentBillDaysOfMonthInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTenRentBillDaysOfMonthInfo()
    {
        this("id");
    }
    protected AbstractTenRentBillDaysOfMonthInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:定租单月天数设置's 月天数property 
     */
    public java.math.BigDecimal getDaysOfMonth()
    {
        return getBigDecimal("daysOfMonth");
    }
    public void setDaysOfMonth(java.math.BigDecimal item)
    {
        setBigDecimal("daysOfMonth", item);
    }
    /**
     * Object:定租单月天数设置's 月天数设置property 
     */
    public com.kingdee.eas.fdc.tenancy.DaysOfMonthSettingEnum getDaysOfMonthSetting()
    {
        return com.kingdee.eas.fdc.tenancy.DaysOfMonthSettingEnum.getEnum(getString("daysOfMonthSetting"));
    }
    public void setDaysOfMonthSetting(com.kingdee.eas.fdc.tenancy.DaysOfMonthSettingEnum item)
    {
		if (item != null) {
        setString("daysOfMonthSetting", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9675751E");
    }
}