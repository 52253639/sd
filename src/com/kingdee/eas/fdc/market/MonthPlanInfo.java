package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MonthPlanInfo extends AbstractMonthPlanInfo implements Serializable 
{
    public MonthPlanInfo()
    {
        super();
    }
    protected MonthPlanInfo(String pkField)
    {
        super(pkField);
    }
}