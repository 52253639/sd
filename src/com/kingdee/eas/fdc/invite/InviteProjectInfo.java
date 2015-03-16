package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class InviteProjectInfo extends AbstractInviteProjectInfo implements Serializable 
{
    public InviteProjectInfo()
    {
        super();
    }
    protected InviteProjectInfo(String pkField)
    {
        super(pkField);
    }
    public boolean isInviteTypeIsMaterail(){
    	if(getInviteType()==null||getInviteType().getLongNumber()==null)
    		return false;
    	return getInviteType().getLongNumber().equals(INVITETYPE_MATERIALLONGNUMBER)||getInviteType().getLongNumber().startsWith(INVITETYPE_MATERIALLONGNUMBER+"!");
    }
    public static String INVITETYPE_MATERIALLONGNUMBER = "001";
}