package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteProjectEntryFactory
{
    private InviteProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2E2294A6") ,com.kingdee.eas.fdc.invite.IInviteProjectEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2E2294A6") ,com.kingdee.eas.fdc.invite.IInviteProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2E2294A6"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2E2294A6"));
    }
}