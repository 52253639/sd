package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class PurchaseBillInfo extends AbstractPurchaseBillInfo implements Serializable 
{
    public PurchaseBillInfo()
    {
        super();
    }
    protected PurchaseBillInfo(String pkField)
    {
        super(pkField);
    }
}