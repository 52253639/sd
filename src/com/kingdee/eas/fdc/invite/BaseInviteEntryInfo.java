package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class BaseInviteEntryInfo extends AbstractBaseInviteEntryInfo implements Serializable 
{
    public BaseInviteEntryInfo()
    {
        super();
    }
    protected BaseInviteEntryInfo(String pkField)
    {
        super(pkField);
    }
}