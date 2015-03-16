package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplKeepAuditEntyFactory
{
    private FDCSplKeepAuditEntyFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0EC9AC42") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0EC9AC42") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0EC9AC42"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplKeepAuditEnty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0EC9AC42"));
    }
}