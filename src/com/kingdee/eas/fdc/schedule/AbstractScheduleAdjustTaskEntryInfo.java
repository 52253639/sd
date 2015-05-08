package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustTaskEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractScheduleAdjustTaskEntryInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustTaskEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:任务调整分录's 开始时间property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:任务调整分录's 结束时间property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:任务调整分录's 有效工期property 
     */
    public java.math.BigDecimal getEffectTimes()
    {
        return getBigDecimal("effectTimes");
    }
    public void setEffectTimes(java.math.BigDecimal item)
    {
        setBigDecimal("effectTimes", item);
    }
    /**
     * Object: 任务调整分录 's 任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("task", item);
    }
    /**
     * Object: 任务调整分录 's 调整单 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F2392EF1");
    }
}