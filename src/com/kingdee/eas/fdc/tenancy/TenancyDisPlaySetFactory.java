package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyDisPlaySetFactory
{
    private TenancyDisPlaySetFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("01ADF0B7") ,com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("01ADF0B7") ,com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("01ADF0B7"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyDisPlaySet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("01ADF0B7"));
    }
}