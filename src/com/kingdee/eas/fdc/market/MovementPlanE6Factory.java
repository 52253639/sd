package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementPlanE6Factory
{
    private MovementPlanE6Factory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE6 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE6)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06163F1A") ,com.kingdee.eas.fdc.market.IMovementPlanE6.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementPlanE6 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE6)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06163F1A") ,com.kingdee.eas.fdc.market.IMovementPlanE6.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE6 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE6)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06163F1A"));
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE6 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE6)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06163F1A"));
    }
}