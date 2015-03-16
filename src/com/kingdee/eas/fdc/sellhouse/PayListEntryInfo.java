package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PayListEntryInfo extends AbstractPayListEntryInfo implements Serializable 
{
    public PayListEntryInfo()
    {
        super();
    }
    protected PayListEntryInfo(String pkField)
    {
        super(pkField);
    }
}