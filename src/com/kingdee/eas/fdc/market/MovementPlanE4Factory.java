package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementPlanE4Factory
{
    private MovementPlanE4Factory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE4 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE4)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06163F18") ,com.kingdee.eas.fdc.market.IMovementPlanE4.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementPlanE4 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE4)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06163F18") ,com.kingdee.eas.fdc.market.IMovementPlanE4.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE4 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE4)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06163F18"));
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE4 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE4)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06163F18"));
    }
}