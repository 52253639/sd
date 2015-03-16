package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ReceiveGatherEntryInfo extends AbstractReceiveGatherEntryInfo implements Serializable 
{
    public ReceiveGatherEntryInfo()
    {
        super();
    }
    protected ReceiveGatherEntryInfo(String pkField)
    {
        super(pkField);
    }
}