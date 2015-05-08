package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class WorkTaskInfo extends AbstractWorkTaskInfo implements Serializable 
{
    public WorkTaskInfo()
    {
        super();
    }
    protected WorkTaskInfo(String pkField)
    {
        super(pkField);
    }
}