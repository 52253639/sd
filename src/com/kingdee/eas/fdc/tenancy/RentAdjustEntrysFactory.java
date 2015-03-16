package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RentAdjustEntrysFactory
{
    private RentAdjustEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B9EE432") ,com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B9EE432") ,com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B9EE432"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentAdjustEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B9EE432"));
    }
}