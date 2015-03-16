package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplQualificationAuditBillFactory
{
    private FDCSplQualificationAuditBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("36BC9435") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("36BC9435") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("36BC9435"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("36BC9435"));
    }
}