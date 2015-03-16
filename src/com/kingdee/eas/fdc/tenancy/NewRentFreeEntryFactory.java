package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewRentFreeEntryFactory
{
    private NewRentFreeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.INewRentFreeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewRentFreeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3ECE416") ,com.kingdee.eas.fdc.tenancy.INewRentFreeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.INewRentFreeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewRentFreeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3ECE416") ,com.kingdee.eas.fdc.tenancy.INewRentFreeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.INewRentFreeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewRentFreeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3ECE416"));
    }
    public static com.kingdee.eas.fdc.tenancy.INewRentFreeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewRentFreeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3ECE416"));
    }
}