package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BillAdjustInfo extends AbstractBillAdjustInfo implements Serializable 
{
    public BillAdjustInfo()
    {
        super();
    }
    protected BillAdjustInfo(String pkField)
    {
        super(pkField);
    }
}