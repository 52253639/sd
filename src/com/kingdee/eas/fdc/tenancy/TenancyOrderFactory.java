package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyOrderFactory
{
    private TenancyOrderFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrder getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrder)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FA35C857") ,com.kingdee.eas.fdc.tenancy.ITenancyOrder.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrder getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrder)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FA35C857") ,com.kingdee.eas.fdc.tenancy.ITenancyOrder.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrder getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrder)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FA35C857"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyOrder getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyOrder)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FA35C857"));
    }
}