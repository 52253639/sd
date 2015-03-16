package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteContentFactory
{
    private InviteContentFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteContent getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContent)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9715088C") ,com.kingdee.eas.fdc.invite.IInviteContent.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteContent getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContent)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9715088C") ,com.kingdee.eas.fdc.invite.IInviteContent.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteContent getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContent)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9715088C"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteContent getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteContent)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9715088C"));
    }
}