package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTimeConsultFactory
{
    private InviteTimeConsultFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteTimeConsult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTimeConsult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F70982F2") ,com.kingdee.eas.fdc.invite.IInviteTimeConsult.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteTimeConsult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTimeConsult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F70982F2") ,com.kingdee.eas.fdc.invite.IInviteTimeConsult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteTimeConsult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTimeConsult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F70982F2"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteTimeConsult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteTimeConsult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F70982F2"));
    }
}