package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplQualificationAuditEntryFactory
{
    private FDCSplQualificationAuditEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A1029E04") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A1029E04") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A1029E04"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplQualificationAuditEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A1029E04"));
    }
}