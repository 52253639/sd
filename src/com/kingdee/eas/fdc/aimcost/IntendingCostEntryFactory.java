package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IntendingCostEntryFactory
{
    private IntendingCostEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IIntendingCostEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IIntendingCostEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("614C142D") ,com.kingdee.eas.fdc.aimcost.IIntendingCostEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IIntendingCostEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IIntendingCostEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("614C142D") ,com.kingdee.eas.fdc.aimcost.IIntendingCostEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IIntendingCostEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IIntendingCostEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("614C142D"));
    }
    public static com.kingdee.eas.fdc.aimcost.IIntendingCostEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IIntendingCostEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("614C142D"));
    }
}