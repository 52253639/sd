package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class RevFDCCustomerEntryInfo extends AbstractRevFDCCustomerEntryInfo implements Serializable 
{
    public RevFDCCustomerEntryInfo()
    {
        super();
    }
    protected RevFDCCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
}