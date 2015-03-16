package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class InviteBaseFileInfo extends AbstractInviteBaseFileInfo implements Serializable 
{
    public InviteBaseFileInfo()
    {
        super();
    }
    protected InviteBaseFileInfo(String pkField)
    {
        super(pkField);
    }
}