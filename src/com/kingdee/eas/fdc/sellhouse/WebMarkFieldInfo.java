package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class WebMarkFieldInfo extends AbstractWebMarkFieldInfo implements Serializable 
{
    public WebMarkFieldInfo()
    {
        super();
    }
    protected WebMarkFieldInfo(String pkField)
    {
        super(pkField);
    }
}