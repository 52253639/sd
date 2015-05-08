package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class OpReportBaseInfo extends AbstractOpReportBaseInfo implements Serializable 
{
    public OpReportBaseInfo()
    {
        super();
    }
    protected OpReportBaseInfo(String pkField)
    {
        super(pkField);
    }
}