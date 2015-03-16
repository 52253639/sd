package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class IncreasedRentEntryInfo extends AbstractIncreasedRentEntryInfo implements Serializable 
{
    public IncreasedRentEntryInfo()
    {
        super();
    }
    protected IncreasedRentEntryInfo(String pkField)
    {
        super(pkField);
    }
}