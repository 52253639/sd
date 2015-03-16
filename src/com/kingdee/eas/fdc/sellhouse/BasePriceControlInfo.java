package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BasePriceControlInfo extends AbstractBasePriceControlInfo implements Serializable 
{
    public BasePriceControlInfo()
    {
        super();
    }
    protected BasePriceControlInfo(String pkField)
    {
        super(pkField);
    }
}