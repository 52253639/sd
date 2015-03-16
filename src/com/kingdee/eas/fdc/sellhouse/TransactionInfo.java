package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class TransactionInfo extends AbstractTransactionInfo implements Serializable 
{
    public TransactionInfo()
    {
        super();
    }
    protected TransactionInfo(String pkField)
    {
        super(pkField);
    }
}