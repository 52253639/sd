package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PurCustomerEntryInfo extends AbstractPurCustomerEntryInfo implements Serializable 
{
    public PurCustomerEntryInfo()
    {
        super();
    }
    protected PurCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
}