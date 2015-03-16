package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PaymentSplitEntryInfo extends AbstractPaymentSplitEntryInfo implements Serializable 
{
    public PaymentSplitEntryInfo()
    {
        super();
    }
    protected PaymentSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}