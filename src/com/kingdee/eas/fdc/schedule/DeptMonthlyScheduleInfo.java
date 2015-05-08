package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class DeptMonthlyScheduleInfo extends AbstractDeptMonthlyScheduleInfo implements Serializable 
{
    public DeptMonthlyScheduleInfo()
    {
        super();
    }
    protected DeptMonthlyScheduleInfo(String pkField)
    {
        super(pkField);
    }
}