package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteDocumentsPointsFactory
{
    private InviteDocumentsPointsFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsPoints getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsPoints)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0821C2CE") ,com.kingdee.eas.fdc.invite.IInviteDocumentsPoints.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsPoints getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsPoints)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0821C2CE") ,com.kingdee.eas.fdc.invite.IInviteDocumentsPoints.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsPoints getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsPoints)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0821C2CE"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsPoints getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsPoints)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0821C2CE"));
    }
}