package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTypeEntryFactory
{
    private InviteTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5BBD18AB") ,com.kingdee.eas.fdc.invite.IInviteTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5BBD18AB") ,com.kingdee.eas.fdc.invite.IInviteTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5BBD18AB"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5BBD18AB"));
    }
}