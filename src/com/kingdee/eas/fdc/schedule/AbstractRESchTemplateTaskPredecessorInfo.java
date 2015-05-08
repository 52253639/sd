package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRESchTemplateTaskPredecessorInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRESchTemplateTaskPredecessorInfo()
    {
        this("id");
    }
    protected AbstractRESchTemplateTaskPredecessorInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房地产计划模板任务前置关系 's 父节点 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 房地产计划模板任务前置关系 's 前置任务 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getPredecessorTask()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("predecessorTask");
    }
    public void setPredecessorTask(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("predecessorTask", item);
    }
    /**
     * Object: 房地产计划模板任务前置关系 's 当前任务 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("task", item);
    }
    /**
     * Object:房地产计划模板任务前置关系's 搭建时间property 
     */
    public int getDifferenceDay()
    {
        return getInt("differenceDay");
    }
    public void setDifferenceDay(int item)
    {
        setInt("differenceDay", item);
    }
    /**
     * Object:房地产计划模板任务前置关系's 前置类型property 
     */
    public com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum getPredecessorType()
    {
        return com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum.getEnum(getString("predecessorType"));
    }
    public void setPredecessorType(com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum item)
    {
		if (item != null) {
        setString("predecessorType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C46BD47B");
    }
}