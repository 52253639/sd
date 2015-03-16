package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class SharePropertyInfo extends AbstractSharePropertyInfo implements Serializable 
{
    public SharePropertyInfo()
    {
        super();
    }
    protected SharePropertyInfo(String pkField)
    {
        super(pkField);
    }
}