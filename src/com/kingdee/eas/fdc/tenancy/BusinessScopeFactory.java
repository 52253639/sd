package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BusinessScopeFactory
{
    private BusinessScopeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IBusinessScope getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBusinessScope)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5B406F6B") ,com.kingdee.eas.fdc.tenancy.IBusinessScope.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IBusinessScope getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBusinessScope)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5B406F6B") ,com.kingdee.eas.fdc.tenancy.IBusinessScope.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IBusinessScope getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBusinessScope)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5B406F6B"));
    }
    public static com.kingdee.eas.fdc.tenancy.IBusinessScope getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBusinessScope)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5B406F6B"));
    }
}