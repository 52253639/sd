package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCostLogChangeSettEntryFactory
{
    private FDCCostLogChangeSettEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0F0FF487") ,com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0F0FF487") ,com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0F0FF487"));
    }
    public static com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IFDCCostLogChangeSettEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0F0FF487"));
    }
}