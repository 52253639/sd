package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class CompeteItemInfo extends AbstractCompeteItemInfo implements Serializable 
{
    public CompeteItemInfo()
    {
        super();
    }
    protected CompeteItemInfo(String pkField)
    {
        super(pkField);
    }
}