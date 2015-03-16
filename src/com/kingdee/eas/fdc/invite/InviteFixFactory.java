package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteFixFactory
{
    private InviteFixFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteFix getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFix)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B8BA61A8") ,com.kingdee.eas.fdc.invite.IInviteFix.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteFix getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFix)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B8BA61A8") ,com.kingdee.eas.fdc.invite.IInviteFix.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteFix getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFix)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B8BA61A8"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteFix getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteFix)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B8BA61A8"));
    }
}