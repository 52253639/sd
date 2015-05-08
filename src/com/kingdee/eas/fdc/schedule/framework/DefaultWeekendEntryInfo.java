package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;

public class DefaultWeekendEntryInfo extends AbstractDefaultWeekendEntryInfo implements Serializable 
{
    public DefaultWeekendEntryInfo()
    {
        super();
    }
    protected DefaultWeekendEntryInfo(String pkField)
    {
        super(pkField);
    }
}