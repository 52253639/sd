package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class LinkmanEntryInfo extends AbstractLinkmanEntryInfo implements Serializable 
{
    public LinkmanEntryInfo()
    {
        super();
    }
    protected LinkmanEntryInfo(String pkField)
    {
        super(pkField);
    }
}