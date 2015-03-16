package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OldRentFreeEntryFactory
{
    private OldRentFreeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7708B2EF") ,com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7708B2EF") ,com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7708B2EF"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldRentFreeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7708B2EF"));
    }
}