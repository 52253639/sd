package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostLogProductEntryFactory
{
    private FDCCostLogProductEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("22FA210C") ,com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("22FA210C") ,com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("22FA210C"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogProductEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("22FA210C"));
    }
}