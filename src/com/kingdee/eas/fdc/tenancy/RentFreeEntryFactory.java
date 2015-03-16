package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RentFreeEntryFactory
{
    private RentFreeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IRentFreeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7D898324") ,com.kingdee.eas.fdc.tenancy.IRentFreeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IRentFreeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7D898324") ,com.kingdee.eas.fdc.tenancy.IRentFreeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IRentFreeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7D898324"));
    }
    public static com.kingdee.eas.fdc.tenancy.IRentFreeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IRentFreeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7D898324"));
    }
}