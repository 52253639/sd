package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class ProgressReportBaseInfo extends AbstractProgressReportBaseInfo implements Serializable 
{
    public ProgressReportBaseInfo()
    {
        super();
    }
    protected ProgressReportBaseInfo(String pkField)
    {
        super(pkField);
    }
}