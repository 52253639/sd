package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class BizTypeInfo extends AbstractBizTypeInfo implements Serializable 
{
    public BizTypeInfo()
    {
        super();
    }
    protected BizTypeInfo(String pkField)
    {
        super(pkField);
    }
}