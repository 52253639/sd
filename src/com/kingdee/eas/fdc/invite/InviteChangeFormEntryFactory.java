package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteChangeFormEntryFactory
{
    private InviteChangeFormEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B929A951") ,com.kingdee.eas.fdc.invite.IInviteChangeFormEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B929A951") ,com.kingdee.eas.fdc.invite.IInviteChangeFormEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B929A951"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteChangeFormEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteChangeFormEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B929A951"));
    }
}