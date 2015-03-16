package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class MonthEntryInfo extends AbstractMonthEntryInfo implements Serializable 
{
    public MonthEntryInfo()
    {
        super();
    }
    protected MonthEntryInfo(String pkField)
    {
        super(pkField);
    }
}