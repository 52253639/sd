package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MovementPlanEntryFactory
{
    private MovementPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("56F46929") ,com.kingdee.eas.fdc.market.IMovementPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMovementPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("56F46929") ,com.kingdee.eas.fdc.market.IMovementPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("56F46929"));
    }
    public static com.kingdee.eas.fdc.market.IMovementPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMovementPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("56F46929"));
    }
}