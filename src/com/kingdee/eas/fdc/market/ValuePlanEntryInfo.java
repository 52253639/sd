package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class ValuePlanEntryInfo extends AbstractValuePlanEntryInfo implements Serializable 
{
    public ValuePlanEntryInfo()
    {
        super();
    }
    protected ValuePlanEntryInfo(String pkField)
    {
        super(pkField);
    }
}