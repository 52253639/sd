package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class OldRentFreeEntryInfo extends AbstractOldRentFreeEntryInfo implements Serializable,IRentFreeInfo 
{
    public OldRentFreeEntryInfo()
    {
        super();
    }
    protected OldRentFreeEntryInfo(String pkField)
    {
        super(pkField);
    }
}