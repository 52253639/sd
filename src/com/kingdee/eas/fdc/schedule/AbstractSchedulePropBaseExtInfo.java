package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSchedulePropBaseExtInfo extends com.kingdee.eas.fdc.schedule.framework.SchedulePropBaseInfo implements Serializable 
{
    public AbstractSchedulePropBaseExtInfo()
    {
        this("id");
    }
    protected AbstractSchedulePropBaseExtInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 进度属性扩展基类 's 任务扩展属性 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo getTaskExt()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo)get("taskExt");
    }
    public void setTaskExt(com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo item)
    {
        put("taskExt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("98E6C600");
    }
}