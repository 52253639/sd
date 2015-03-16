package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementPlanE5Factory
{
    private MovementPlanE5Factory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE5 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE5)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06163F19") ,com.kingdee.eas.fdc.market.IMovementPlanE5.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementPlanE5 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE5)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06163F19") ,com.kingdee.eas.fdc.market.IMovementPlanE5.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE5 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE5)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06163F19"));
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE5 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE5)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06163F19"));
    }
}