package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class StoreOptionInfo extends AbstractStoreOptionInfo implements Serializable 
{
    public StoreOptionInfo()
    {
        super();
    }
    protected StoreOptionInfo(String pkField)
    {
        super(pkField);
    }
}