package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class TemplateFileInfo extends AbstractTemplateFileInfo implements Serializable 
{
    public TemplateFileInfo()
    {
        super();
    }
    protected TemplateFileInfo(String pkField)
    {
        super(pkField);
    }
}