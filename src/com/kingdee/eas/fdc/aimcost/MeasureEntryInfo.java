package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class MeasureEntryInfo extends AbstractMeasureEntryInfo implements Serializable 
{
    public MeasureEntryInfo()
    {
        super();
    }
    protected MeasureEntryInfo(String pkField)
    {
        super(pkField);
    }
}