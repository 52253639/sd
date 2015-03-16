package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PurchaseChangeInfo extends AbstractPurchaseChangeInfo implements Serializable 
{
    public PurchaseChangeInfo()
    {
        super();
    }
    protected PurchaseChangeInfo(String pkField)
    {
        super(pkField);
    }
}