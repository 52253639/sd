package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class FamillyEarningInfo extends AbstractFamillyEarningInfo implements Serializable 
{
    public FamillyEarningInfo()
    {
        super();
    }
    protected FamillyEarningInfo(String pkField)
    {
        super(pkField);
    }
}