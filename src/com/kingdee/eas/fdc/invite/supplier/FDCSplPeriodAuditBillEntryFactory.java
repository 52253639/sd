package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplPeriodAuditBillEntryFactory
{
    private FDCSplPeriodAuditBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("726FFE93") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("726FFE93") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("726FFE93"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("726FFE93"));
    }
}