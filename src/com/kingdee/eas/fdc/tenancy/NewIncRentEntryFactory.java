package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewIncRentEntryFactory
{
    private NewIncRentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.INewIncRentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewIncRentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("646A8F72") ,com.kingdee.eas.fdc.tenancy.INewIncRentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.INewIncRentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewIncRentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("646A8F72") ,com.kingdee.eas.fdc.tenancy.INewIncRentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.INewIncRentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewIncRentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("646A8F72"));
    }
    public static com.kingdee.eas.fdc.tenancy.INewIncRentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.INewIncRentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("646A8F72"));
    }
}