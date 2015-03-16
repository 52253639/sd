package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTypeFactory
{
    private InviteTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E986C07") ,com.kingdee.eas.fdc.invite.IInviteType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E986C07") ,com.kingdee.eas.fdc.invite.IInviteType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E986C07"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E986C07"));
    }
}