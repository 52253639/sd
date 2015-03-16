package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SincerObligateFactory
{
    private SincerObligateFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ISincerObligate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerObligate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("37796DDC") ,com.kingdee.eas.fdc.tenancy.ISincerObligate.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ISincerObligate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerObligate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("37796DDC") ,com.kingdee.eas.fdc.tenancy.ISincerObligate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ISincerObligate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerObligate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("37796DDC"));
    }
    public static com.kingdee.eas.fdc.tenancy.ISincerObligate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ISincerObligate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("37796DDC"));
    }
}