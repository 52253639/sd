package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PaymentNoCostSplitEntryInfo extends AbstractPaymentNoCostSplitEntryInfo implements Serializable 
{
    public PaymentNoCostSplitEntryInfo()
    {
        super();
    }
    protected PaymentNoCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}