package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PaymentSplitInfo extends AbstractPaymentSplitInfo implements Serializable 
{
    public PaymentSplitInfo()
    {
        super();
    }
    protected PaymentSplitInfo(String pkField)
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