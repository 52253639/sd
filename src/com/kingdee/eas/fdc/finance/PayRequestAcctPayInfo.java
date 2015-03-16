package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayRequestAcctPayInfo extends AbstractPayRequestAcctPayInfo implements Serializable 
{
    public PayRequestAcctPayInfo()
    {
        super();
    }
    protected PayRequestAcctPayInfo(String pkField)
    {
        super(pkField);
    }
}