package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BankPaymentEntryInfo extends AbstractBankPaymentEntryInfo implements Serializable 
{
    public BankPaymentEntryInfo()
    {
        super();
    }
    protected BankPaymentEntryInfo(String pkField)
    {
        super(pkField);
    }
}