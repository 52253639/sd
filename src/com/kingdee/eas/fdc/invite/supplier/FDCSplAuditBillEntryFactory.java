package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplAuditBillEntryFactory
{
    private FDCSplAuditBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7F7113F2") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7F7113F2") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7F7113F2"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7F7113F2"));
    }
}