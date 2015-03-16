package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AIMAimCostProductSplitEntryFactory
{
    private AIMAimCostProductSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1EB8A1E3") ,com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1EB8A1E3") ,com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1EB8A1E3"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAIMAimCostProductSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1EB8A1E3"));
    }
}