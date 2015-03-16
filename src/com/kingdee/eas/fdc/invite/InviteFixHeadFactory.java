package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteFixHeadFactory
{
    private InviteFixHeadFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteFixHead getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFixHead)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2C038FA8") ,com.kingdee.eas.fdc.invite.IInviteFixHead.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteFixHead getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFixHead)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2C038FA8") ,com.kingdee.eas.fdc.invite.IInviteFixHead.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteFixHead getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFixHead)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2C038FA8"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteFixHead getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFixHead)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2C038FA8"));
    }
}