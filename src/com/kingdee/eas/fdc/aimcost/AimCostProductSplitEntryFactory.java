package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostProductSplitEntryFactory
{
    private AimCostProductSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59124F28") ,com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("59124F28") ,com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59124F28"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostProductSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59124F28"));
    }
}