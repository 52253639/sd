package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BankPaymentInfo extends AbstractBankPaymentInfo implements Serializable 
{
    public BankPaymentInfo()
    {
        super();
    }
    protected BankPaymentInfo(String pkField)
    {
        super(pkField);
    }
}