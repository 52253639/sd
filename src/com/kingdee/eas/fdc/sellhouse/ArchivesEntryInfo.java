package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ArchivesEntryInfo extends AbstractArchivesEntryInfo implements Serializable 
{
    public ArchivesEntryInfo()
    {
        super();
    }
    protected ArchivesEntryInfo(String pkField)
    {
        super(pkField);
    }
}