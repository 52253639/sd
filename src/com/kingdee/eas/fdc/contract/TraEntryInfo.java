package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class TraEntryInfo extends AbstractTraEntryInfo implements Serializable 
{
    public TraEntryInfo()
    {
        super();
    }
    protected TraEntryInfo(String pkField)
    {
        super(pkField);
    }
}