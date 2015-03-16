package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public abstract class AgioEntryInfo extends AbstractAgioEntryInfo implements Serializable 
{
    public AgioEntryInfo()
    {
        super();
    }
    protected AgioEntryInfo(String pkField)
    {
        super(pkField);
    }
}