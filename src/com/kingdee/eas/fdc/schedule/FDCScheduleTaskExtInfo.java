package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class FDCScheduleTaskExtInfo extends AbstractFDCScheduleTaskExtInfo implements Serializable 
{
    public FDCScheduleTaskExtInfo()
    {
        super();
    }
    protected FDCScheduleTaskExtInfo(String pkField)
    {
        super(pkField);
    }
}