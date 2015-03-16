package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanTypeFactory
{
    private PlanTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPlanType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A07BD6D4") ,com.kingdee.eas.fdc.market.IPlanType.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPlanType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A07BD6D4") ,com.kingdee.eas.fdc.market.IPlanType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPlanType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A07BD6D4"));
    }
    public static com.kingdee.eas.fdc.market.IPlanType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A07BD6D4"));
    }
}