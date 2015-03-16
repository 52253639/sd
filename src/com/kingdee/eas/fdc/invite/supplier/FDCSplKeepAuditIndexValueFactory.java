package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplKeepAuditIndexValueFactory
{
    private FDCSplKeepAuditIndexValueFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("69F97413") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("69F97413") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("69F97413"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditIndexValue)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("69F97413"));
    }
}