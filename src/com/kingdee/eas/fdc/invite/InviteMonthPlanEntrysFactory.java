package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteMonthPlanEntrysFactory
{
    private InviteMonthPlanEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7CBDAA7D") ,com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7CBDAA7D") ,com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7CBDAA7D"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteMonthPlanEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7CBDAA7D"));
    }
}