package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementPlanE3Factory
{
    private MovementPlanE3Factory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("06163F17") ,com.kingdee.eas.fdc.market.IMovementPlanE3.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementPlanE3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("06163F17") ,com.kingdee.eas.fdc.market.IMovementPlanE3.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("06163F17"));
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanE3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanE3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("06163F17"));
    }
}