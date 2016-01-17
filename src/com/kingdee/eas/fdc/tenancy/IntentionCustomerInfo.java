package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class IntentionCustomerInfo extends AbstractIntentionCustomerInfo implements Serializable 
{
    public IntentionCustomerInfo()
    {
        super();
    }
    protected IntentionCustomerInfo(String pkField)
    {
        super(pkField);
    }
}