package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicCostFactory
{
    private DynamicCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5C696F5") ,com.kingdee.eas.fdc.aimcost.IDynamicCost.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynamicCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5C696F5") ,com.kingdee.eas.fdc.aimcost.IDynamicCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5C696F5"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5C696F5"));
    }
}