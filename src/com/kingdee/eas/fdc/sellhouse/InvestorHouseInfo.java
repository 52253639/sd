package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class InvestorHouseInfo extends AbstractInvestorHouseInfo implements Serializable 
{
    public InvestorHouseInfo()
    {
        super();
    }
    protected InvestorHouseInfo(String pkField)
    {
        super(pkField);
    }
}