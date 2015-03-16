package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PaymentPrjPayEntryInfo extends AbstractPaymentPrjPayEntryInfo implements Serializable 
{
    public PaymentPrjPayEntryInfo()
    {
        super();
    }
    protected PaymentPrjPayEntryInfo(String pkField)
    {
        super(pkField);
    }
}