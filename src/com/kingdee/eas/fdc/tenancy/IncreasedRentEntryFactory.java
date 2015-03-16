package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IncreasedRentEntryFactory
{
    private IncreasedRentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3E3035E0") ,com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3E3035E0") ,com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3E3035E0"));
    }
    public static com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IIncreasedRentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3E3035E0"));
    }
}