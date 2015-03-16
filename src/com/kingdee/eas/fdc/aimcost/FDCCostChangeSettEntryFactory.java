package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostChangeSettEntryFactory
{
    private FDCCostChangeSettEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("58EBC455") ,com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("58EBC455") ,com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("58EBC455"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostChangeSettEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("58EBC455"));
    }
}