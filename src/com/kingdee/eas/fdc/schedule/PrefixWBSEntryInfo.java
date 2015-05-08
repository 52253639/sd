package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class PrefixWBSEntryInfo extends AbstractPrefixWBSEntryInfo implements Serializable 
{
    public PrefixWBSEntryInfo()
    {
        super();
    }
    protected PrefixWBSEntryInfo(String pkField)
    {
        super(pkField);
    }
}