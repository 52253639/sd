package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChangeEntryInfo extends AbstractChangeEntryInfo implements Serializable 
{
    public ChangeEntryInfo()
    {
        super();
    }
    protected ChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
}