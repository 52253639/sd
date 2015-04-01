package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherBillEntryFactory
{
    private OtherBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("220E0964") ,com.kingdee.eas.fdc.tenancy.IOtherBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IOtherBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("220E0964") ,com.kingdee.eas.fdc.tenancy.IOtherBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("220E0964"));
    }
    public static com.kingdee.eas.fdc.tenancy.IOtherBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IOtherBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("220E0964"));
    }
}