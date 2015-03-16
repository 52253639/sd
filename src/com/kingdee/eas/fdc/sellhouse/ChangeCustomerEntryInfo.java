package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChangeCustomerEntryInfo extends AbstractChangeCustomerEntryInfo implements Serializable 
{
    public ChangeCustomerEntryInfo()
    {
        super();
    }
    protected ChangeCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
}