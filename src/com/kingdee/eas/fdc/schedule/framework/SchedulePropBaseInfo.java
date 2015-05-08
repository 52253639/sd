package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;

public class SchedulePropBaseInfo extends AbstractSchedulePropBaseInfo implements Serializable 
{
    public SchedulePropBaseInfo()
    {
        super();
    }
    protected SchedulePropBaseInfo(String pkField)
    {
        super(pkField);
    }
}