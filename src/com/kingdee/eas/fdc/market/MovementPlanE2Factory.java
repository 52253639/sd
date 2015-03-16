package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementPlanE2Factory
{
    private MovementPlanE2Factory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06163F16") ,com.kingdee.eas.fdc.market.IMovementPlanE2.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementPlanE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06163F16") ,com.kingdee.eas.fdc.market.IMovementPlanE2.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06163F16"));
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06163F16"));
    }
}