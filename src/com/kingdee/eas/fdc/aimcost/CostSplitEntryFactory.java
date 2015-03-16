package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostSplitEntryFactory
{
    private CostSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.ICostSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F70E723C") ,com.kingdee.eas.fdc.aimcost.ICostSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.ICostSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F70E723C") ,com.kingdee.eas.fdc.aimcost.ICostSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.ICostSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F70E723C"));
    }
    public static com.kingdee.eas.fdc.aimcost.ICostSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.ICostSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F70E723C"));
    }
}