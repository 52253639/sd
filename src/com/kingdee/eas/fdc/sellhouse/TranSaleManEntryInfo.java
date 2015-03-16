package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class TranSaleManEntryInfo extends AbstractTranSaleManEntryInfo implements Serializable 
{
    public TranSaleManEntryInfo()
    {
        super();
    }
    protected TranSaleManEntryInfo(String pkField)
    {
        super(pkField);
    }
}