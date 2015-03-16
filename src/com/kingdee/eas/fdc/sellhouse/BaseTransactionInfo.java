package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BaseTransactionInfo extends AbstractBaseTransactionInfo implements Serializable 
{
    public BaseTransactionInfo()
    {
        super();
    }
    protected BaseTransactionInfo(String pkField)
    {
        super(pkField);
    }
}