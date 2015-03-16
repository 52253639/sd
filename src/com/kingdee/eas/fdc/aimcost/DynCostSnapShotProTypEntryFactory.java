package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostSnapShotProTypEntryFactory
{
    private DynCostSnapShotProTypEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC1B8CC1") ,com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC1B8CC1") ,com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC1B8CC1"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotProTypEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC1B8CC1"));
    }
}