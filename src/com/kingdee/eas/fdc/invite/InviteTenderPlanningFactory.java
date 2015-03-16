package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTenderPlanningFactory
{
    private InviteTenderPlanningFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanning getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanning)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2277AADE") ,com.kingdee.eas.fdc.invite.IInviteTenderPlanning.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanning getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanning)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2277AADE") ,com.kingdee.eas.fdc.invite.IInviteTenderPlanning.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanning getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanning)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2277AADE"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanning getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanning)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2277AADE"));
    }
}