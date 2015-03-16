package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteModeFactory
{
    private InviteModeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteMode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E951670") ,com.kingdee.eas.fdc.invite.IInviteMode.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteMode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E951670") ,com.kingdee.eas.fdc.invite.IInviteMode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteMode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E951670"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteMode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E951670"));
    }
}