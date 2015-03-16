package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostIndexFactory
{
    private CostIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E919A98E") ,com.kingdee.eas.fdc.aimcost.ICostIndex.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E919A98E") ,com.kingdee.eas.fdc.aimcost.ICostIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E919A98E"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E919A98E"));
    }
}