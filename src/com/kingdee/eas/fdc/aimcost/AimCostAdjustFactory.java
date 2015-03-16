package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostAdjustFactory
{
    private AimCostAdjustFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjust getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjust)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D8EF24EA") ,com.kingdee.eas.fdc.aimcost.IAimCostAdjust.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjust getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjust)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D8EF24EA") ,com.kingdee.eas.fdc.aimcost.IAimCostAdjust.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjust getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjust)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D8EF24EA"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjust getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjust)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D8EF24EA"));
    }
}