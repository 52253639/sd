package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskDependInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractScheduleTaskDependInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskDependInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:后置任务's 类型property 
     */
    public com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum getType()
    {
        return com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:后置任务's 延迟property 
     */
    public int getDifference()
    {
        return getInt("difference");
    }
    public void setDifference(int item)
    {
        setInt("difference", item);
    }
    /**
     * Object:后置任务's 硬性property 
     */
    public com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum getHardness()
    {
        return com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum.getEnum(getString("hardness"));
    }
    public void setHardness(com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum item)
    {
		if (item != null) {
        setString("hardness", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("57DFF34E");
    }
}