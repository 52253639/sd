package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PurCustomerInfo extends AbstractPurCustomerInfo implements Serializable 
{
    public PurCustomerInfo()
    {
        super();
    }
    protected PurCustomerInfo(String pkField)
    {
        super(pkField);
    }
}