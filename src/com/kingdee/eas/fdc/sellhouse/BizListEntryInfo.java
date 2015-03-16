package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BizListEntryInfo extends AbstractBizListEntryInfo implements Serializable 
{
    public BizListEntryInfo()
    {
        super();
    }
    protected BizListEntryInfo(String pkField)
    {
        super(pkField);
    }
}