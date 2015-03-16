package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RentRemissionFactory
{
    private RentRemissionFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRemission getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemission)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DADF01B7") ,com.kingdee.eas.fdc.tenancy.IRentRemission.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRentRemission getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemission)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DADF01B7") ,com.kingdee.eas.fdc.tenancy.IRentRemission.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRemission getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemission)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DADF01B7"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRemission getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemission)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DADF01B7"));
    }
}