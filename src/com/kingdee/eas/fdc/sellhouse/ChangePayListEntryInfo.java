package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChangePayListEntryInfo extends AbstractChangePayListEntryInfo implements Serializable 
{
    public ChangePayListEntryInfo()
    {
        super();
    }
    protected ChangePayListEntryInfo(String pkField)
    {
        super(pkField);
    }
}