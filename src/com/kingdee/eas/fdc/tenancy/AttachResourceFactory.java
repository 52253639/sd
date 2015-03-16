package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AttachResourceFactory
{
    private AttachResourceFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResource getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResource)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C947175C") ,com.kingdee.eas.fdc.tenancy.IAttachResource.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IAttachResource getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResource)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C947175C") ,com.kingdee.eas.fdc.tenancy.IAttachResource.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResource getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResource)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C947175C"));
    }
    public static com.kingdee.eas.fdc.tenancy.IAttachResource getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IAttachResource)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C947175C"));
    }
}