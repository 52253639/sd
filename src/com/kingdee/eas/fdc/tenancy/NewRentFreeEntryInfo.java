package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class NewRentFreeEntryInfo extends AbstractNewRentFreeEntryInfo implements Serializable,IRentFreeInfo 
{
    public NewRentFreeEntryInfo()
    {
        super();
    }
    protected NewRentFreeEntryInfo(String pkField)
    {
        super(pkField);
    }
}