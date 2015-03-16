package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;

public class SupplierStockInfo extends AbstractSupplierStockInfo implements Serializable 
{
    public SupplierStockInfo()
    {
        super();
    }
    protected SupplierStockInfo(String pkField)
    {
        super(pkField);
    }
}