package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskMaterialPlanInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskMaterialPlanInfo()
    {
        this("id");
    }
    protected AbstractTaskMaterialPlanInfo(String pkField)
    {
        super(pkField);
        put("itemEntrys", new com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection());
    }
    /**
     * Object: 任务属性物资计划 's 物资事项分录 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection getItemEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection)get("itemEntrys");
    }
    /**
     * Object:任务属性物资计划's 计划时间property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object:任务属性物资计划's 描述property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0FB2A2EB");
    }
}