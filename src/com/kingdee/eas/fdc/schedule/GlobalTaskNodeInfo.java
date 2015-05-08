package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class GlobalTaskNodeInfo extends AbstractGlobalTaskNodeInfo implements Serializable 
{
    public GlobalTaskNodeInfo()
    {
        super();
    }
    protected GlobalTaskNodeInfo(String pkField)
    {
        super(pkField);
    }
}