package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PurchaseChangeCustomerInfo extends AbstractPurchaseChangeCustomerInfo implements Serializable 
{
    public PurchaseChangeCustomerInfo()
    {
        super();
    }
    protected PurchaseChangeCustomerInfo(String pkField)
    {
        super(pkField);
    }
}