package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SpecialDiscountInfo extends AbstractSpecialDiscountInfo implements Serializable 
{
    public SpecialDiscountInfo()
    {
        super();
    }
    protected SpecialDiscountInfo(String pkField)
    {
        super(pkField);
    }
}