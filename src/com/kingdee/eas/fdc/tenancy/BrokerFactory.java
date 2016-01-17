package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BrokerFactory
{
    private BrokerFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IBroker getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBroker)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E65886C2") ,com.kingdee.eas.fdc.tenancy.IBroker.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IBroker getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBroker)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E65886C2") ,com.kingdee.eas.fdc.tenancy.IBroker.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IBroker getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBroker)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E65886C2"));
    }
    public static com.kingdee.eas.fdc.tenancy.IBroker getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IBroker)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E65886C2"));
    }
}