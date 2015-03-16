package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BatchManageInfo extends AbstractBatchManageInfo implements Serializable 
{
    public BatchManageInfo()
    {
        super();
    }
    protected BatchManageInfo(String pkField)
    {
        super(pkField);
    }
}