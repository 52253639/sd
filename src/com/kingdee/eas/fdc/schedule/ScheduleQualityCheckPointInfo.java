package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class ScheduleQualityCheckPointInfo extends AbstractScheduleQualityCheckPointInfo implements Serializable 
{
    public ScheduleQualityCheckPointInfo()
    {
        super();
    }
    protected ScheduleQualityCheckPointInfo(String pkField)
    {
        super(pkField);
    }
}