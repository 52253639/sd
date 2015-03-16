package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanEntryFactory
{
    private PlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6E26B418") ,com.kingdee.eas.fdc.market.IPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6E26B418") ,com.kingdee.eas.fdc.market.IPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6E26B418"));
    }
    public static com.kingdee.eas.fdc.market.IPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6E26B418"));
    }
}