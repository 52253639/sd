package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CustomerEntryInfo extends AbstractCustomerEntryInfo implements Serializable 
{
    public CustomerEntryInfo()
    {
        super();
    }
    protected CustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
}