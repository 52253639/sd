package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class StandardTaskGuideEntryInfo extends AbstractStandardTaskGuideEntryInfo implements Serializable 
{
    public StandardTaskGuideEntryInfo()
    {
        super();
    }
    protected StandardTaskGuideEntryInfo(String pkField)
    {
        super(pkField);
    }
}