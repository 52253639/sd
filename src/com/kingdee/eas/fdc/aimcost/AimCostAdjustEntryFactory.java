package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostAdjustEntryFactory
{
    private AimCostAdjustEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("663575A8") ,com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("663575A8") ,com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("663575A8"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostAdjustEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("663575A8"));
    }
}