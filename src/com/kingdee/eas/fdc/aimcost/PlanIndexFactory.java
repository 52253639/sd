package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanIndexFactory
{
    private PlanIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("768287B2") ,com.kingdee.eas.fdc.aimcost.IPlanIndex.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPlanIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("768287B2") ,com.kingdee.eas.fdc.aimcost.IPlanIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("768287B2"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("768287B2"));
    }
}