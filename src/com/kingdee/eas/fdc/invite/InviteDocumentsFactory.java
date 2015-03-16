package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteDocumentsFactory
{
    private InviteDocumentsFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocuments getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocuments)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("76766E0B") ,com.kingdee.eas.fdc.invite.IInviteDocuments.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteDocuments getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocuments)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("76766E0B") ,com.kingdee.eas.fdc.invite.IInviteDocuments.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocuments getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocuments)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("76766E0B"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocuments getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocuments)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("76766E0B"));
    }
}