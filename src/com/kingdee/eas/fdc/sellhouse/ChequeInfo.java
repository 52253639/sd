package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ChequeInfo extends AbstractChequeInfo implements Serializable 
{
    public ChequeInfo()
    {
        super();
    }
    protected ChequeInfo(String pkField)
    {
        super(pkField);
    }
}