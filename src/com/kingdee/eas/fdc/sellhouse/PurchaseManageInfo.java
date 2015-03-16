package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class PurchaseManageInfo extends AbstractPurchaseManageInfo implements Serializable 
{
    public PurchaseManageInfo()
    {
        super();
    }
    protected PurchaseManageInfo(String pkField)
    {
        super(pkField);
    }
}