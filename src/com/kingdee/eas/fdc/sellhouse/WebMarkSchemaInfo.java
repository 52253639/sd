package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class WebMarkSchemaInfo extends AbstractWebMarkSchemaInfo implements Serializable 
{
    public WebMarkSchemaInfo()
    {
        super();
    }
    protected WebMarkSchemaInfo(String pkField)
    {
        super(pkField);
    }
}