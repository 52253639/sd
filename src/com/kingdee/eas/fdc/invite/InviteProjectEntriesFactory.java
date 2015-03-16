package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteProjectEntriesFactory
{
    private InviteProjectEntriesFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntries getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntries)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2FCFD3C4") ,com.kingdee.eas.fdc.invite.IInviteProjectEntries.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntries getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntries)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2FCFD3C4") ,com.kingdee.eas.fdc.invite.IInviteProjectEntries.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntries getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntries)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2FCFD3C4"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntries getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntries)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2FCFD3C4"));
    }
}