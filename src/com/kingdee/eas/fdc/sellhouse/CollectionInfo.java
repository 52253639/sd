package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CollectionInfo extends AbstractCollectionInfo implements Serializable 
{
    public CollectionInfo()
    {
        super();
    }
    protected CollectionInfo(String pkField)
    {
        super(pkField);
    }
}