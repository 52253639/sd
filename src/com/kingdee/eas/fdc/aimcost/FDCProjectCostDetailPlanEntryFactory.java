package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProjectCostDetailPlanEntryFactory
{
    private FDCProjectCostDetailPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C017FB60") ,com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C017FB60") ,com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C017FB60"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCProjectCostDetailPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C017FB60"));
    }
}