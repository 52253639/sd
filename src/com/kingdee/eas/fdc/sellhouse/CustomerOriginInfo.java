package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CustomerOriginInfo extends AbstractCustomerOriginInfo implements Serializable 
{
    public CustomerOriginInfo()
    {
        super();
    }
    protected CustomerOriginInfo(String pkField)
    {
        super(pkField);
    }
}