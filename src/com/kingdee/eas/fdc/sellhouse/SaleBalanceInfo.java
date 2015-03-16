package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SaleBalanceInfo extends AbstractSaleBalanceInfo implements Serializable 
{
    public SaleBalanceInfo()
    {
        super();
    }
    protected SaleBalanceInfo(String pkField)
    {
        super(pkField);
    }
}