package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHolidayEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractHolidayEntryInfo()
    {
        this("id");
    }
    protected AbstractHolidayEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:节假日分录's 节假日property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object: 节假日分录 's 项目日历 property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:节假日分录's 节假日名称property 
     */
    public String getHolidayName()
    {
        return getString("holidayName");
    }
    public void setHolidayName(String item)
    {
        setString("holidayName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("85D83880");
    }
}