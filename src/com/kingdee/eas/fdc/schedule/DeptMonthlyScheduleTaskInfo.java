package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class DeptMonthlyScheduleTaskInfo extends AbstractDeptMonthlyScheduleTaskInfo implements Serializable 
{
    public DeptMonthlyScheduleTaskInfo()
    {
        super();
    }
    protected DeptMonthlyScheduleTaskInfo(String pkField)
    {
        super(pkField);
    }
}