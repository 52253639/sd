package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostFactory
{
    private FDCCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7799479B") ,com.kingdee.eas.fdc.aimcost.IFDCCost.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7799479B") ,com.kingdee.eas.fdc.aimcost.IFDCCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7799479B"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7799479B"));
    }
}