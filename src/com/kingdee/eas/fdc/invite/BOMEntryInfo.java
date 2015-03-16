package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class BOMEntryInfo extends AbstractBOMEntryInfo implements Serializable 
{
    public BOMEntryInfo()
    {
        super();
    }
    protected BOMEntryInfo(String pkField)
    {
        super(pkField);
    }
}