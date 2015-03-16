package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class NewIncRentEntryInfo extends AbstractNewIncRentEntryInfo implements Serializable,IRentIncreadedInfo 
{
    public NewIncRentEntryInfo()
    {
        super();
    }
    protected NewIncRentEntryInfo(String pkField)
    {
        super(pkField);
    }
}