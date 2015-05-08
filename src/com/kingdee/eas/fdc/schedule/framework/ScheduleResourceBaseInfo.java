package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;

public class ScheduleResourceBaseInfo extends AbstractScheduleResourceBaseInfo implements Serializable 
{
    public ScheduleResourceBaseInfo()
    {
        super();
    }
    protected ScheduleResourceBaseInfo(String pkField)
    {
        super(pkField);
    }
}