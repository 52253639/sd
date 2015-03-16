package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostEntryFactory
{
    private CostEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E8E1892E") ,com.kingdee.eas.fdc.aimcost.ICostEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E8E1892E") ,com.kingdee.eas.fdc.aimcost.ICostEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E8E1892E"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E8E1892E"));
    }
}