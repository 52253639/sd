package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class BaseInviteInfo extends AbstractBaseInviteInfo implements Serializable 
{
    public BaseInviteInfo()
    {
        super();
    }
    protected BaseInviteInfo(String pkField)
    {
        super(pkField);
    }
}