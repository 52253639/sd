package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChequeCustomerEntryInfo extends AbstractChequeCustomerEntryInfo implements Serializable 
{
    public ChequeCustomerEntryInfo()
    {
        super();
    }
    protected ChequeCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
}