package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanIndexEntryFactory
{
    private PlanIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DCB88FE0") ,com.kingdee.eas.fdc.aimcost.IPlanIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DCB88FE0") ,com.kingdee.eas.fdc.aimcost.IPlanIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DCB88FE0"));
    }
    public static com.kingdee.eas.fdc.aimcost.IPlanIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IPlanIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DCB88FE0"));
    }
}