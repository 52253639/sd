package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class InvoiceInfo extends AbstractInvoiceInfo implements Serializable 
{
    public InvoiceInfo()
    {
        super();
    }
    protected InvoiceInfo(String pkField)
    {
        super(pkField);
    }
}