package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomPlanIndexEntryFactory
{
    private CustomPlanIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E7402071") ,com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E7402071") ,com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E7402071"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICustomPlanIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E7402071"));
    }
}