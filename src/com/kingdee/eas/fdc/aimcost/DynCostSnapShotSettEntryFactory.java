package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostSnapShotSettEntryFactory
{
    private DynCostSnapShotSettEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7A9818CD") ,com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7A9818CD") ,com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7A9818CD"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShotSettEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7A9818CD"));
    }
}