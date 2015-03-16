package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class PageHeadInfo extends AbstractPageHeadInfo implements Serializable 
{
    public PageHeadInfo()
    {
        super();
    }
    protected PageHeadInfo(String pkField)
    {
        super(pkField);
    }
}