package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractWithoutTextInfo extends AbstractContractWithoutTextInfo implements Serializable 
{
    public ContractWithoutTextInfo()
    {
        super();
    }
    protected ContractWithoutTextInfo(String pkField)
    {
        super(pkField);
    }
}