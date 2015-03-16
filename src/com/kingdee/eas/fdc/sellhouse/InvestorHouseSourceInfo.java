package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class InvestorHouseSourceInfo extends AbstractInvestorHouseSourceInfo implements Serializable 
{
    public InvestorHouseSourceInfo()
    {
        super();
    }
    protected InvestorHouseSourceInfo(String pkField)
    {
        super(pkField);
    }
}