package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class ValueInputEntryInfo extends AbstractValueInputEntryInfo implements Serializable 
{
    public ValueInputEntryInfo()
    {
        super();
    }
    protected ValueInputEntryInfo(String pkField)
    {
        super(pkField);
    }
}