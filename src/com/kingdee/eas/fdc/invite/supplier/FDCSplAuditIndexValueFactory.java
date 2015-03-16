package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplAuditIndexValueFactory
{
    private FDCSplAuditIndexValueFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A660E158") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A660E158") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A660E158"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditIndexValue)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A660E158"));
    }
}