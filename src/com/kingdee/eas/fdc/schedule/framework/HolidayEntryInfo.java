package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;

public class HolidayEntryInfo extends AbstractHolidayEntryInfo implements Serializable 
{
    public HolidayEntryInfo()
    {
        super();
    }
    protected HolidayEntryInfo(String pkField)
    {
        super(pkField);
    }
}