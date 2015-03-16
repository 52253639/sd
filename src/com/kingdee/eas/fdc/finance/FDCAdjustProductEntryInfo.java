package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class FDCAdjustProductEntryInfo extends AbstractFDCAdjustProductEntryInfo implements Serializable 
{
    public FDCAdjustProductEntryInfo()
    {
        super();
    }
    protected FDCAdjustProductEntryInfo(String pkField)
    {
        super(pkField);
    }
}