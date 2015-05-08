package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskBizTypeInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractScheduleTaskBizTypeInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskBizTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 任务业务类型 's parent property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 任务业务类型 's bizType property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo getBizType()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo)get("bizType");
    }
    public void setBizType(com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo item)
    {
        put("bizType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0B475AFB");
    }
}