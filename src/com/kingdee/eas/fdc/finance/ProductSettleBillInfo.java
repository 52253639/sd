package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class ProductSettleBillInfo extends AbstractProductSettleBillInfo implements Serializable 
{
    public ProductSettleBillInfo()
    {
        super();
    }
    protected ProductSettleBillInfo(String pkField)
    {
        super(pkField);
    }
}