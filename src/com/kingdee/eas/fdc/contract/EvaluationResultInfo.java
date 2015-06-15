package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class EvaluationResultInfo extends AbstractEvaluationResultInfo implements Serializable 
{
    public EvaluationResultInfo()
    {
        super();
    }
    protected EvaluationResultInfo(String pkField)
    {
        super(pkField);
    }
}