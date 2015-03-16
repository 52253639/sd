package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteMonthPlanFactory
{
    private InviteMonthPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F0BDFD1C") ,com.kingdee.eas.fdc.invite.IInviteMonthPlan.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F0BDFD1C") ,com.kingdee.eas.fdc.invite.IInviteMonthPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F0BDFD1C"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F0BDFD1C"));
    }
}