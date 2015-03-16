package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class InviteFormInfo extends AbstractInviteFormInfo implements Serializable 
{
    public InviteFormInfo()
    {
        super();
    }
    protected InviteFormInfo(String pkField)
    {
        super(pkField);
    }
}