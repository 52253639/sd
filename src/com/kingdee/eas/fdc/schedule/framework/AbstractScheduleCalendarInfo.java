package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleCalendarInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractScheduleCalendarInfo()
    {
        this("id");
    }
    protected AbstractScheduleCalendarInfo(String pkField)
    {
        super(pkField);
        put("holidayEntrys", new com.kingdee.eas.fdc.schedule.framework.HolidayEntryCollection());
        put("weekendEntrys", new com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryCollection());
    }
    /**
     * Object: 进度日历 's 节假日 property 
     */
    public com.kingdee.eas.fdc.schedule.framework.HolidayEntryCollection getHolidayEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.framework.HolidayEntryCollection)get("holidayEntrys");
    }
    /**
     * Object: 进度日历 's 周末 property 
     */
    public com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryCollection getWeekendEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryCollection)get("weekendEntrys");
    }
    /**
     * Object: 进度日历 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:进度日历's 工程项目property 
     */
    public String getObjectId()
    {
        return getString("objectId");
    }
    public void setObjectId(String item)
    {
        setString("objectId", item);
    }
    /**
     * Object:进度日历's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:进度日历's 编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:进度日历's 是否启用property 
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
     * Object:进度日历's 是否系统预设property 
     */
    public boolean isIsSys()
    {
        return getBoolean("isSys");
    }
    public void setIsSys(boolean item)
    {
        setBoolean("isSys", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8725097B");
    }
}