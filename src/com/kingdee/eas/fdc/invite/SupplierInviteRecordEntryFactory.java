package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierInviteRecordEntryFactory
{
    private SupplierInviteRecordEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("53CDC4C8") ,com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("53CDC4C8") ,com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("53CDC4C8"));
    }
    public static com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.ISupplierInviteRecordEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("53CDC4C8"));
    }
}