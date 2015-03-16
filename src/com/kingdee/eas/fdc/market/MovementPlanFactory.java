package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementPlanFactory
{
    private MovementPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3A1444C9") ,com.kingdee.eas.fdc.market.IMovementPlan.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3A1444C9") ,com.kingdee.eas.fdc.market.IMovementPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3A1444C9"));
    }
    public static com.kingdee.eas.fdc.market.IMovementPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3A1444C9"));
    }
}