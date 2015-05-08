package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskdisplaycolumnsInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractScheduleTaskdisplaycolumnsInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskdisplaycolumnsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:进度计划列显示's 顺序property 
     */
    public int getOrder()
    {
        return getInt("order");
    }
    public void setOrder(int item)
    {
        setInt("order", item);
    }
    /**
     * Object:进度计划列显示's 宽度property 
     */
    public int getWidth()
    {
        return getInt("width");
    }
    public void setWidth(int item)
    {
        setInt("width", item);
    }
    /**
     * Object: 进度计划列显示 's 属性 property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo getProperty()
    {
        return (com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo)get("property");
    }
    public void setProperty(com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo item)
    {
        put("property", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4A6E4F9D");
    }
}