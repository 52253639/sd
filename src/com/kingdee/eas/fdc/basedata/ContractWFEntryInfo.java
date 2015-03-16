package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ContractWFEntryInfo extends AbstractContractWFEntryInfo implements Serializable 
{
    public ContractWFEntryInfo()
    {
        super();
    }
    protected ContractWFEntryInfo(String pkField)
    {
        super(pkField);
    }
}