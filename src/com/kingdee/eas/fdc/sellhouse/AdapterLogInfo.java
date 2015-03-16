package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class AdapterLogInfo extends AbstractAdapterLogInfo implements Serializable 
{
    public AdapterLogInfo()
    {
        super();
    }
    protected AdapterLogInfo(String pkField)
    {
        super(pkField);
    }
}