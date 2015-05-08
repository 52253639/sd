package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class FDCMonthTaskEntryInfo extends AbstractFDCMonthTaskEntryInfo implements Serializable 
{
    public FDCMonthTaskEntryInfo()
    {
        super();
    }
    protected FDCMonthTaskEntryInfo(String pkField)
    {
        super(pkField);
    }
}