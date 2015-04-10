package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class InvoiceBillEntryInfo extends AbstractInvoiceBillEntryInfo implements Serializable 
{
    public InvoiceBillEntryInfo()
    {
        super();
    }
    protected InvoiceBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}