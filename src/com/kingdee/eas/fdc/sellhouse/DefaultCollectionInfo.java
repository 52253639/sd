package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class DefaultCollectionInfo extends AbstractDefaultCollectionInfo implements Serializable 
{
    public DefaultCollectionInfo()
    {
        super();
    }
    protected DefaultCollectionInfo(String pkField)
    {
        super(pkField);
    }
}