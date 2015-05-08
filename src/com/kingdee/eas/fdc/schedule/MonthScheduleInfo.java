package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class MonthScheduleInfo extends AbstractMonthScheduleInfo implements Serializable 
{
    public MonthScheduleInfo()
    {
        super();
    }
    protected MonthScheduleInfo(String pkField)
    {
        super(pkField);
    }
}