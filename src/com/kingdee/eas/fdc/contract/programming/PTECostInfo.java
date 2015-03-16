package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class PTECostInfo extends AbstractPTECostInfo implements Serializable 
{
    public PTECostInfo()
    {
        super();
    }
    protected PTECostInfo(String pkField)
    {
        super(pkField);
    }
}