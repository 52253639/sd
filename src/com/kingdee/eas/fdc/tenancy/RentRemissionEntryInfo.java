package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class RentRemissionEntryInfo extends AbstractRentRemissionEntryInfo implements Serializable 
{
    public RentRemissionEntryInfo()
    {
        super();
    }
    protected RentRemissionEntryInfo(String pkField)
    {
        super(pkField);
    }
}