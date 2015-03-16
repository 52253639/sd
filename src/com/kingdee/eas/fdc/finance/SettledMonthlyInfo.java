package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class SettledMonthlyInfo extends AbstractSettledMonthlyInfo implements Serializable 
{
    public SettledMonthlyInfo()
    {
        super();
    }
    protected SettledMonthlyInfo(String pkField)
    {
        super(pkField);
    }
}