package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class FlatLayerTypeInfo extends AbstractFlatLayerTypeInfo implements Serializable 
{
    public FlatLayerTypeInfo()
    {
        super();
    }
    protected FlatLayerTypeInfo(String pkField)
    {
        super(pkField);
    }
}