package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierInviteRecordFactory
{
    private SupplierInviteRecordFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecord getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecord)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A4E9E1CA") ,com.kingdee.eas.fdc.invite.ISupplierInviteRecord.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecord getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecord)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A4E9E1CA") ,com.kingdee.eas.fdc.invite.ISupplierInviteRecord.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecord getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecord)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A4E9E1CA"));
    }
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecord getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecord)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A4E9E1CA"));
    }
}