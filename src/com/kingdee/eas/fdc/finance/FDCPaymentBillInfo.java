package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class FDCPaymentBillInfo extends AbstractFDCPaymentBillInfo implements Serializable 
{
    public FDCPaymentBillInfo()
    {
        super();
    }
    protected FDCPaymentBillInfo(String pkField)
    {
        super(pkField);
    }
}