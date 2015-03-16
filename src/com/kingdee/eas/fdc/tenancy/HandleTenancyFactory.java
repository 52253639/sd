package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HandleTenancyFactory
{
    private HandleTenancyFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IHandleTenancy getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleTenancy)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("03B8A0AF") ,com.kingdee.eas.fdc.tenancy.IHandleTenancy.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IHandleTenancy getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleTenancy)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("03B8A0AF") ,com.kingdee.eas.fdc.tenancy.IHandleTenancy.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IHandleTenancy getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleTenancy)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("03B8A0AF"));
    }
    public static com.kingdee.eas.fdc.tenancy.IHandleTenancy getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHandleTenancy)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("03B8A0AF"));
    }
}