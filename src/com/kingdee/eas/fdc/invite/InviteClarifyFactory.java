package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteClarifyFactory
{
    private InviteClarifyFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteClarify getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarify)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("913E7275") ,com.kingdee.eas.fdc.invite.IInviteClarify.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteClarify getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarify)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("913E7275") ,com.kingdee.eas.fdc.invite.IInviteClarify.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteClarify getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarify)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("913E7275"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteClarify getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteClarify)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("913E7275"));
    }
}