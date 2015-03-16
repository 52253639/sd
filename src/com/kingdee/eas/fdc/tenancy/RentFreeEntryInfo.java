package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class RentFreeEntryInfo extends AbstractRentFreeEntryInfo implements Serializable 
{
    public RentFreeEntryInfo()
    {
        super();
    }
    protected RentFreeEntryInfo(String pkField)
    {
        super(pkField);
    }
}