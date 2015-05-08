package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeptMonthlyScheduleInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDeptMonthlyScheduleInfo()
    {
        this("id");
    }
    protected AbstractDeptMonthlyScheduleInfo(String pkField)
    {
        super(pkField);
        put("tasks", new com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection());
    }
    /**
     * Object:部门月度计划's 计划月份property 
     */
    public java.util.Date getScheduleMonth()
    {
        return getDate("scheduleMonth");
    }
    public void setScheduleMonth(java.util.Date item)
    {
        setDate("scheduleMonth", item);
    }
    /**
     * Object: 部门月度计划 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    /**
     * Object: 部门月度计划 's 任务分录 property 
     */
    public com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection getTasks()
    {
        return (com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection)get("tasks");
    }
    /**
     * Object:部门月度计划's 计划月份（年份）property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:部门月度计划's 计划月份（月份）property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E32C1929");
    }
}