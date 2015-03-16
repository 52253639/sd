package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenAttachResourceEntryFactory
{
    private TenAttachResourceEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5FE744B") ,com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5FE744B") ,com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5FE744B"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourceEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5FE744B"));
    }
}