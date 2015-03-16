package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyModificationFactory
{
    private TenancyModificationFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyModification getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModification)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4E1D0473") ,com.kingdee.eas.fdc.tenancy.ITenancyModification.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyModification getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModification)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4E1D0473") ,com.kingdee.eas.fdc.tenancy.ITenancyModification.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyModification getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModification)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4E1D0473"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyModification getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyModification)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4E1D0473"));
    }
}