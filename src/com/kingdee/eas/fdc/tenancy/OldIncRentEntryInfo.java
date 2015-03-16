package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class OldIncRentEntryInfo extends AbstractOldIncRentEntryInfo implements Serializable,IRentIncreadedInfo 
{
    public OldIncRentEntryInfo()
    {
        super();
    }
    protected OldIncRentEntryInfo(String pkField)
    {
        super(pkField);
    }
}