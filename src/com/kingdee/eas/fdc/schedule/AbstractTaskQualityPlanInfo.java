package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskQualityPlanInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskQualityPlanInfo()
    {
        this("id");
    }
    protected AbstractTaskQualityPlanInfo(String pkField)
    {
        super(pkField);
        put("preventionEntrys", new com.kingdee.eas.fdc.schedule.TaskQualityPreventionEntryCollection());
        put("eventEntrys", new com.kingdee.eas.fdc.schedule.TaskQualityPlanEventEntryCollection());
    }
    /**
     * Object: 质量计划 's 预防办法分录 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskQualityPreventionEntryCollection getPreventionEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.TaskQualityPreventionEntryCollection)get("preventionEntrys");
    }
    /**
     * Object: 质量计划 's 重大事件分录 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskQualityPlanEventEntryCollection getEventEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.TaskQualityPlanEventEntryCollection)get("eventEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F3B7536D");
    }
}