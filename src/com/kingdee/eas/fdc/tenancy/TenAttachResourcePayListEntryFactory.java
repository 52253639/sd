package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenAttachResourcePayListEntryFactory
{
    private TenAttachResourcePayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF2704B3") ,com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF2704B3") ,com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF2704B3"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenAttachResourcePayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF2704B3"));
    }
}