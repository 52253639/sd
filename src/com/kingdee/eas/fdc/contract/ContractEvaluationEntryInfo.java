package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractEvaluationEntryInfo extends AbstractContractEvaluationEntryInfo implements Serializable 
{
    public ContractEvaluationEntryInfo()
    {
        super();
    }
    protected ContractEvaluationEntryInfo(String pkField)
    {
        super(pkField);
    }
}