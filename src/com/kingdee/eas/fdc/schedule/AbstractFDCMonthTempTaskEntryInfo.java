package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCMonthTempTaskEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCMonthTempTaskEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCMonthTempTaskEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:月度进度计划_临时任务's 任务编码property 
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
     * Object:月度进度计划_临时任务's 任务名称property 
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
     * Object:月度进度计划_临时任务's 开始时间property 
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
     * Object:月度进度计划_临时任务's 结束时间property 
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
     * Object:月度进度计划_临时任务's 工期property 
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
     * Object:月度进度计划_临时任务's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object: 月度进度计划_临时任务 's 月度计划 property 
     */
    public com.kingdee.eas.fdc.schedule.MonthScheduleInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.MonthScheduleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.MonthScheduleInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("86AC6008");
    }
}