package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class FeeEntryInfo extends AbstractFeeEntryInfo implements Serializable 
{
    public FeeEntryInfo()
    {
        super();
    }
    protected FeeEntryInfo(String pkField)
    {
        super(pkField);
    }
}