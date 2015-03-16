package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteProjectFactory
{
    private InviteProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4BEE1F2C") ,com.kingdee.eas.fdc.invite.IInviteProject.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4BEE1F2C") ,com.kingdee.eas.fdc.invite.IInviteProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4BEE1F2C"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4BEE1F2C"));
    }
}