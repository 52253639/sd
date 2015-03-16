package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitePreSplitEntryFactory
{
    private InvitePreSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E08DF64E") ,com.kingdee.eas.fdc.invite.IInvitePreSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E08DF64E") ,com.kingdee.eas.fdc.invite.IInvitePreSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E08DF64E"));
    }
    public static com.kingdee.eas.fdc.invite.IInvitePreSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePreSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E08DF64E"));
    }
}