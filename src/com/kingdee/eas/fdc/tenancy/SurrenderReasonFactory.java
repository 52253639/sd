package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SurrenderReasonFactory
{
    private SurrenderReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ISurrenderReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISurrenderReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("21B32501") ,com.kingdee.eas.fdc.tenancy.ISurrenderReason.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ISurrenderReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISurrenderReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("21B32501") ,com.kingdee.eas.fdc.tenancy.ISurrenderReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ISurrenderReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISurrenderReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("21B32501"));
    }
    public static com.kingdee.eas.fdc.tenancy.ISurrenderReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISurrenderReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("21B32501"));
    }
}