package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostSplitFactory
{
    private CostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E9A79BD6") ,com.kingdee.eas.fdc.aimcost.ICostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E9A79BD6") ,com.kingdee.eas.fdc.aimcost.ICostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E9A79BD6"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E9A79BD6"));
    }
}