package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class InvoiceBillInfo extends AbstractInvoiceBillInfo implements Serializable 
{
    public InvoiceBillInfo()
    {
        super();
    }
    protected InvoiceBillInfo(String pkField)
    {
        super(pkField);
    }
}