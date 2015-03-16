package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SellProjectInfo extends AbstractSellProjectInfo implements Serializable 
{
    public SellProjectInfo()
    {
        super();
    }
    protected SellProjectInfo(String pkField)
    {
        super(pkField);
    }
}