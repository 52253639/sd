package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PaymentNoCostSplitInfo extends AbstractPaymentNoCostSplitInfo implements Serializable 
{
    public PaymentNoCostSplitInfo()
    {
        super();
    }
    protected PaymentNoCostSplitInfo(String pkField)
    {
        super(pkField);
    }
    public String getLogInfo() {
		String retValue = "";
        if(this.getPaymentBill()!=null&&this.getPaymentBill().getNumber()!= null)
        {
            retValue = this.getPaymentBill().getNumber();
        }
        return retValue;
	}
}