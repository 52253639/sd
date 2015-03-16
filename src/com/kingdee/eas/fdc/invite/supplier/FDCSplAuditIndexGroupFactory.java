package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplAuditIndexGroupFactory
{
    private FDCSplAuditIndexGroupFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB452F60") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB452F60") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB452F60"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexGroup)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB452F60"));
    }
}