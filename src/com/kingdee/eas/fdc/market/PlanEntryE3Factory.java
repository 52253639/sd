package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanEntryE3Factory
{
    private PlanEntryE3Factory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPlanEntryE3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntryE3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7F4A16A6") ,com.kingdee.eas.fdc.market.IPlanEntryE3.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPlanEntryE3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntryE3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7F4A16A6") ,com.kingdee.eas.fdc.market.IPlanEntryE3.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPlanEntryE3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntryE3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7F4A16A6"));
    }
    public static com.kingdee.eas.fdc.market.IPlanEntryE3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntryE3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7F4A16A6"));
    }
}