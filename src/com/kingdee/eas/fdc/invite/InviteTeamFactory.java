package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTeamFactory
{
    private InviteTeamFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteTeam getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeam)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E981F2A") ,com.kingdee.eas.fdc.invite.IInviteTeam.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteTeam getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeam)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E981F2A") ,com.kingdee.eas.fdc.invite.IInviteTeam.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteTeam getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeam)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E981F2A"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteTeam getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTeam)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E981F2A"));
    }
}