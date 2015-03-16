package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteSupplierEntryFactory
{
    private InviteSupplierEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("759997D9") ,com.kingdee.eas.fdc.invite.IInviteSupplierEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("759997D9") ,com.kingdee.eas.fdc.invite.IInviteSupplierEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("759997D9"));
    }
    public static com.kingdee.eas.fdc.invite.IInviteSupplierEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IInviteSupplierEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("759997D9"));
    }
}