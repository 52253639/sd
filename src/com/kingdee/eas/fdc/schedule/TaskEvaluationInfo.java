package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TaskEvaluationInfo extends AbstractTaskEvaluationInfo implements Serializable 
{
    public TaskEvaluationInfo()
    {
        super();
    }
    protected TaskEvaluationInfo(String pkField)
    {
        super(pkField);
    }
}