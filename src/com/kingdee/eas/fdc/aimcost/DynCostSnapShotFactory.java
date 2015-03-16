package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostSnapShotFactory
{
    private DynCostSnapShotFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShot getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShot)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C2CE2BB3") ,com.kingdee.eas.fdc.aimcost.IDynCostSnapShot.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShot getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShot)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C2CE2BB3") ,com.kingdee.eas.fdc.aimcost.IDynCostSnapShot.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShot getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShot)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C2CE2BB3"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostSnapShot getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostSnapShot)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C2CE2BB3"));
    }
}