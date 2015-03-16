package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RentRemissionEntryFactory
{
    private RentRemissionEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRemissionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemissionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("09A090FB") ,com.kingdee.eas.fdc.tenancy.IRentRemissionEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRentRemissionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemissionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("09A090FB") ,com.kingdee.eas.fdc.tenancy.IRentRemissionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRemissionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemissionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("09A090FB"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRentRemissionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentRemissionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("09A090FB"));
    }
}