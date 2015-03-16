package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class TranCustomerEntryInfo extends AbstractTranCustomerEntryInfo implements Serializable 
{
    public TranCustomerEntryInfo()
    {
        super();
    }
    protected TranCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
}