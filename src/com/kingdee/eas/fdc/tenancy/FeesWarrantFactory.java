package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FeesWarrantFactory
{
    private FeesWarrantFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrant getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrant)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AA2EA367") ,com.kingdee.eas.fdc.tenancy.IFeesWarrant.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrant getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrant)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AA2EA367") ,com.kingdee.eas.fdc.tenancy.IFeesWarrant.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrant getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrant)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AA2EA367"));
    }
    public static com.kingdee.eas.fdc.tenancy.IFeesWarrant getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IFeesWarrant)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AA2EA367"));
    }
}