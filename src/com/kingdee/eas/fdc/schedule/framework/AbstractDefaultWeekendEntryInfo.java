package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultWeekendEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDefaultWeekendEntryInfo()
    {
        this("id");
    }
    protected AbstractDefaultWeekendEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:周末's 周末property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum getWeekend()
    {
        return com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum.getEnum(getInt("weekend"));
    }
    public void setWeekend(com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum item)
    {
		if (item != null) {
        setInt("weekend", item.getValue());
		}
    }
    /**
     * Object: 周末 's 项目日历 property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5474A8E6");
    }
}