package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteFileMergEntryFactory
{
    private InviteFileMergEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileMergEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMergEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("33016A5C") ,com.kingdee.eas.fdc.invite.IInviteFileMergEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteFileMergEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMergEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("33016A5C") ,com.kingdee.eas.fdc.invite.IInviteFileMergEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileMergEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMergEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("33016A5C"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileMergEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMergEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("33016A5C"));
    }
}