package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteFileItemEntryFactory
{
    private InviteFileItemEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileItemEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItemEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52AD60D6") ,com.kingdee.eas.fdc.invite.IInviteFileItemEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteFileItemEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItemEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52AD60D6") ,com.kingdee.eas.fdc.invite.IInviteFileItemEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileItemEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItemEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52AD60D6"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteFileItemEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFileItemEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52AD60D6"));
    }
}