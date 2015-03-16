package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayPlanInfo extends AbstractPayPlanInfo implements Serializable 
{
    public PayPlanInfo()
    {
        super();
    }
    protected PayPlanInfo(String pkField)
    {
        super(pkField);
    }
}