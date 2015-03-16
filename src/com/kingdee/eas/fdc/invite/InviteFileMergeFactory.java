package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteFileMergeFactory
{
    private InviteFileMergeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileMerge getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMerge)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EEBFE76F") ,com.kingdee.eas.fdc.invite.IInviteFileMerge.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteFileMerge getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMerge)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EEBFE76F") ,com.kingdee.eas.fdc.invite.IInviteFileMerge.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileMerge getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMerge)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EEBFE76F"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileMerge getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileMerge)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EEBFE76F"));
    }
}