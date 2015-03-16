package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TempConstrPlanIndexEntryFactory
{
    private TempConstrPlanIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("382A7863") ,com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("382A7863") ,com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("382A7863"));
    }
    public static com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ITempConstrPlanIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("382A7863"));
    }
}