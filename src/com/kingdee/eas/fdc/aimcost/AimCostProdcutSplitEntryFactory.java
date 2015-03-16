package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostProdcutSplitEntryFactory
{
    private AimCostProdcutSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostProdcutSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostProdcutSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59124F28") ,com.kingdee.eas.fdc.aimcost.IAimCostProdcutSplitEntry.class);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostProdcutSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostProdcutSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59124F28"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostProdcutSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostProdcutSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59124F28"));
    }
}