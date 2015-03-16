package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplAuditIndexFactory
{
    private FDCSplAuditIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("44D17A5F") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("44D17A5F") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("44D17A5F"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("44D17A5F"));
    }
}