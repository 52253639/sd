package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicCostProductSplitEntryFactory
{
    private DynamicCostProductSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D63B3952") ,com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D63B3952") ,com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D63B3952"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostProductSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D63B3952"));
    }
}