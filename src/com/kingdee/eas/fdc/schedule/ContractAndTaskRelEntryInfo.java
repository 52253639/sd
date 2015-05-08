package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class ContractAndTaskRelEntryInfo extends AbstractContractAndTaskRelEntryInfo implements Serializable 
{
    public ContractAndTaskRelEntryInfo()
    {
        super();
    }
    protected ContractAndTaskRelEntryInfo(String pkField)
    {
        super(pkField);
    }
}