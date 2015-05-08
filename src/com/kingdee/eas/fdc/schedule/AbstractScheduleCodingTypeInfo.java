package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleCodingTypeInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractScheduleCodingTypeInfo()
    {
        this("id");
    }
    protected AbstractScheduleCodingTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:进度编码类型's 编码property 
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
     * Object: 进度编码类型 's 任务属性 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskTypeInfo getScheduleType()
    {
        return (com.kingdee.eas.fdc.schedule.TaskTypeInfo)get("scheduleType");
    }
    public void setScheduleType(com.kingdee.eas.fdc.schedule.TaskTypeInfo item)
    {
        put("scheduleType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("197FC211");
    }
}