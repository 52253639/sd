package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteClarifyEntryFactory
{
    private InviteClarifyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteClarifyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarifyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D99FE6FD") ,com.kingdee.eas.fdc.invite.IInviteClarifyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteClarifyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarifyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D99FE6FD") ,com.kingdee.eas.fdc.invite.IInviteClarifyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteClarifyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarifyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D99FE6FD"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteClarifyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarifyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D99FE6FD"));
    }
}