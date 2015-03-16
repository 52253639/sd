package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteDocumentsEntryFactory
{
    private InviteDocumentsEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EF23B927") ,com.kingdee.eas.fdc.invite.IInviteDocumentsEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EF23B927") ,com.kingdee.eas.fdc.invite.IInviteDocumentsEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EF23B927"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteDocumentsEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteDocumentsEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EF23B927"));
    }
}