package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractEvaluationInfo extends AbstractContractEvaluationInfo implements Serializable 
{
    public ContractEvaluationInfo()
    {
        super();
    }
    protected ContractEvaluationInfo(String pkField)
    {
        super(pkField);
    }
}