package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteFormFactory
{
    private InviteFormFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteForm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteForm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E91E991") ,com.kingdee.eas.fdc.invite.IInviteForm.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteForm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteForm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E91E991") ,com.kingdee.eas.fdc.invite.IInviteForm.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteForm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteForm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E91E991"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteForm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteForm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E91E991"));
    }
}