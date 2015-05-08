package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class OpReportEntryBaseInfo extends AbstractOpReportEntryBaseInfo implements Serializable 
{
    public OpReportEntryBaseInfo()
    {
        super();
    }
    protected OpReportEntryBaseInfo(String pkField)
    {
        super(pkField);
    }
}