package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateTaskWithBizTypeInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTemplateTaskWithBizTypeInfo()
    {
        this("id");
    }
    protected AbstractTemplateTaskWithBizTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 模板任务与业务类型关联 's 业务类型 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo getBizType()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo)get("bizType");
    }
    public void setBizType(com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo item)
    {
        put("bizType", item);
    }
    /**
     * Object: 模板任务与业务类型关联 's 任务 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("task", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A5ECA412");
    }
}