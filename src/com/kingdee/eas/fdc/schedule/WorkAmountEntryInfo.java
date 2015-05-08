package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class WorkAmountEntryInfo extends AbstractWorkAmountEntryInfo implements Serializable 
{
    public WorkAmountEntryInfo()
    {
        super();
    }
    protected WorkAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
}