package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CustomerWorkAreaEntryInfo extends AbstractCustomerWorkAreaEntryInfo implements Serializable 
{
    public CustomerWorkAreaEntryInfo()
    {
        super();
    }
    protected CustomerWorkAreaEntryInfo(String pkField)
    {
        super(pkField);
    }
}