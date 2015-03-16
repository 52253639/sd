package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostFactory
{
    private AimCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B08D497B") ,com.kingdee.eas.fdc.aimcost.IAimCost.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B08D497B") ,com.kingdee.eas.fdc.aimcost.IAimCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B08D497B"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B08D497B"));
    }
}