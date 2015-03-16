package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplPeriodAuditBillFactory
{
    private FDCSplPeriodAuditBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3BD7751F") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3BD7751F") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3BD7751F"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3BD7751F"));
    }
}