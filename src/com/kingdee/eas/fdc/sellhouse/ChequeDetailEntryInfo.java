package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChequeDetailEntryInfo extends AbstractChequeDetailEntryInfo implements Serializable 
{
    public ChequeDetailEntryInfo()
    {
        super();
    }
    protected ChequeDetailEntryInfo(String pkField)
    {
        super(pkField);
    }
}