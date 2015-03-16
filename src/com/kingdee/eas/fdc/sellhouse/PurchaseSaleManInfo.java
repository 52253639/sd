package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PurchaseSaleManInfo extends AbstractPurchaseSaleManInfo implements Serializable 
{
    public PurchaseSaleManInfo()
    {
        super();
    }
    protected PurchaseSaleManInfo(String pkField)
    {
        super(pkField);
    }
}