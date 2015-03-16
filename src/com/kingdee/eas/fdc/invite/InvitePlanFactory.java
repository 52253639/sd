package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitePlanFactory
{
    private InvitePlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInvitePlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E9667F6") ,com.kingdee.eas.fdc.invite.IInvitePlan.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInvitePlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E9667F6") ,com.kingdee.eas.fdc.invite.IInvitePlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInvitePlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E9667F6"));
    }
    public static com.kingdee.eas.fdc.invite.IInvitePlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E9667F6"));
    }
}