package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTenderPlanningEntryFactory
{
    private InviteTenderPlanningEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("66968834") ,com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("66968834") ,com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("66968834"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTenderPlanningEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("66968834"));
    }
}