package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class WorkAmountBillInfo extends AbstractWorkAmountBillInfo implements Serializable 
{
    public WorkAmountBillInfo()
    {
        super();
    }
    protected WorkAmountBillInfo(String pkField)
    {
        super(pkField);
    }
    public void setEntry(com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection entry)
    {
        put("entry", entry);
    }
}