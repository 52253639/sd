package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplPeriodAuditIndexValueFactory
{
    private FDCSplPeriodAuditIndexValueFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("133F4AD7") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("133F4AD7") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("133F4AD7"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplPeriodAuditIndexValue)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("133F4AD7"));
    }
}