package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OldIncRentEntryFactory
{
    private OldIncRentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOldIncRentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldIncRentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("379D0179") ,com.kingdee.eas.fdc.tenancy.IOldIncRentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOldIncRentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldIncRentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("379D0179") ,com.kingdee.eas.fdc.tenancy.IOldIncRentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOldIncRentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldIncRentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("379D0179"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOldIncRentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOldIncRentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("379D0179"));
    }
}