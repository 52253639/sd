package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class ScheduleTaskBizTypeInfo extends AbstractScheduleTaskBizTypeInfo implements Serializable 
{
    public ScheduleTaskBizTypeInfo()
    {
        super();
    }
    protected ScheduleTaskBizTypeInfo(String pkField)
    {
        super(pkField);
    }
    public String toString() {
		/* TODO 自动生成方法存根 */
		return getBizType().getName();
	}
}