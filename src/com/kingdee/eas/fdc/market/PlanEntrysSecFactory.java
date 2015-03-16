package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanEntrysSecFactory
{
    private PlanEntrysSecFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IPlanEntrysSec getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntrysSec)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D53472F6") ,com.kingdee.eas.fdc.market.IPlanEntrysSec.class);
    }
    
    public static com.kingdee.eas.fdc.market.IPlanEntrysSec getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntrysSec)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D53472F6") ,com.kingdee.eas.fdc.market.IPlanEntrysSec.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IPlanEntrysSec getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntrysSec)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D53472F6"));
    }
    public static com.kingdee.eas.fdc.market.IPlanEntrysSec getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IPlanEntrysSec)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D53472F6"));
    }
}