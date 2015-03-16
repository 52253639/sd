package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ContractTypeInfo extends AbstractContractTypeInfo implements Serializable 
{
    public ContractTypeInfo()
    {
        super();
    }
    protected ContractTypeInfo(String pkField)
    {
        super(pkField);
    }
}