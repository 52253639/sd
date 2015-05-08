package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class TemplateTaskWithBizTypeInfo extends AbstractTemplateTaskWithBizTypeInfo implements Serializable 
{
    public TemplateTaskWithBizTypeInfo()
    {
        super();
    }
    protected TemplateTaskWithBizTypeInfo(String pkField)
    {
        super(pkField);
    }
}