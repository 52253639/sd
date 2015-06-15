package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class EvaluationTypeEntryInfo extends AbstractEvaluationTypeEntryInfo implements Serializable 
{
    public EvaluationTypeEntryInfo()
    {
        super();
    }
    protected EvaluationTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
}