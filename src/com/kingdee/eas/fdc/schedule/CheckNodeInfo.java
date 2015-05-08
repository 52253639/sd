package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class CheckNodeInfo extends AbstractCheckNodeInfo implements Serializable 
{
    public CheckNodeInfo()
    {
        super();
    }
    protected CheckNodeInfo(String pkField)
    {
        super(pkField);
    }
}