package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskWorkloadLogInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskWorkloadLogInfo()
    {
        this("id");
    }
    protected AbstractTaskWorkloadLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:任务工程量日志's 工程量完工百分比property 
     */
    public java.math.BigDecimal getCompletePercent()
    {
        return getBigDecimal("completePercent");
    }
    public void setCompletePercent(java.math.BigDecimal item)
    {
        setBigDecimal("completePercent", item);
    }
    /**
     * Object:任务工程量日志's 计划工程量property 
     */
    public java.math.BigDecimal getPlanWorkload()
    {
        return getBigDecimal("planWorkload");
    }
    public void setPlanWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("planWorkload", item);
    }
    /**
     * Object: 任务工程量日志 's 单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getMeasureUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("measureUnit");
    }
    public void setMeasureUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("measureUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BCE5F2F2");
    }
}