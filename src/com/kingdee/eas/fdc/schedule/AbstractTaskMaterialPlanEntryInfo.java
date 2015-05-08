package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskMaterialPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTaskMaterialPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractTaskMaterialPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:任务属性物料计划分录's 计划时间property 
     */
    public java.util.Date getPlanTime()
    {
        return getDate("planTime");
    }
    public void setPlanTime(java.util.Date item)
    {
        setDate("planTime", item);
    }
    /**
     * Object:任务属性物料计划分录's 标题property 
     */
    public String getTitle()
    {
        return getString("title");
    }
    public void setTitle(String item)
    {
        setString("title", item);
    }
    /**
     * Object: 任务属性物料计划分录 's 表头 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5A59047");
    }
}