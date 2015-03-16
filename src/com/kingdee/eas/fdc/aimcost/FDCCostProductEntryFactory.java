package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostProductEntryFactory
{
    private FDCCostProductEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DF0F13FE") ,com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DF0F13FE") ,com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DF0F13FE"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostProductEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DF0F13FE"));
    }
}