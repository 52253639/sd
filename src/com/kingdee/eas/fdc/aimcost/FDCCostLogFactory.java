package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostLogFactory
{
    private FDCCostLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D654F9A9") ,com.kingdee.eas.fdc.aimcost.IFDCCostLog.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D654F9A9") ,com.kingdee.eas.fdc.aimcost.IFDCCostLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D654F9A9"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D654F9A9"));
    }
}