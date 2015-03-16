package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MediaTypeInfo extends AbstractMediaTypeInfo implements Serializable 
{
    public MediaTypeInfo()
    {
        super();
    }
    protected MediaTypeInfo(String pkField)
    {
        super(pkField);
    }
}