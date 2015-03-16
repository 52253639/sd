package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChequeRevListEntryInfo extends AbstractChequeRevListEntryInfo implements Serializable 
{
    public ChequeRevListEntryInfo()
    {
        super();
    }
    protected ChequeRevListEntryInfo(String pkField)
    {
        super(pkField);
    }
}