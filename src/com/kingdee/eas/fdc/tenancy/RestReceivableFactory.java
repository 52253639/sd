package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RestReceivableFactory
{
    private RestReceivableFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRestReceivable getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivable)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B42ABEF9") ,com.kingdee.eas.fdc.tenancy.IRestReceivable.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRestReceivable getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivable)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B42ABEF9") ,com.kingdee.eas.fdc.tenancy.IRestReceivable.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRestReceivable getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivable)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B42ABEF9"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRestReceivable getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRestReceivable)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B42ABEF9"));
    }
}