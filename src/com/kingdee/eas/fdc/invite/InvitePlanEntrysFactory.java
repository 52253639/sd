package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitePlanEntrysFactory
{
    private InvitePlanEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInvitePlanEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlanEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("892D09D7") ,com.kingdee.eas.fdc.invite.IInvitePlanEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInvitePlanEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlanEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("892D09D7") ,com.kingdee.eas.fdc.invite.IInvitePlanEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInvitePlanEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlanEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("892D09D7"));
    }
    public static com.kingdee.eas.fdc.invite.IInvitePlanEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInvitePlanEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("892D09D7"));
    }
}