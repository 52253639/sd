package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SpecialDiscountEntryInfo extends AbstractSpecialDiscountEntryInfo implements Serializable 
{
    public SpecialDiscountEntryInfo()
    {
        super();
    }
    protected SpecialDiscountEntryInfo(String pkField)
    {
        super(pkField);
    }
}