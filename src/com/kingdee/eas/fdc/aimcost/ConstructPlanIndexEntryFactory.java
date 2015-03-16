package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConstructPlanIndexEntryFactory
{
    private ConstructPlanIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D443B49") ,com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D443B49") ,com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D443B49"));
    }
    public static com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IConstructPlanIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D443B49"));
    }
}