package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCScheduleTaskExtInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCScheduleTaskExtInfo()
    {
        this("id");
    }
    protected AbstractFDCScheduleTaskExtInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 进度任务扩展属性 's 任务WBS property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getWbs()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("wbs");
    }
    public void setWbs(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("wbs", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("99CA6BF6");
    }
}