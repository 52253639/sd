package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanIndexConfigFactory
{
    private PlanIndexConfigFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexConfig getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexConfig)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B6FAE994") ,com.kingdee.eas.fdc.aimcost.IPlanIndexConfig.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexConfig getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexConfig)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B6FAE994") ,com.kingdee.eas.fdc.aimcost.IPlanIndexConfig.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexConfig getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexConfig)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B6FAE994"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexConfig getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexConfig)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B6FAE994"));
    }
}