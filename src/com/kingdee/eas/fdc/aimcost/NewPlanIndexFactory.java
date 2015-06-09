package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewPlanIndexFactory
{
    private NewPlanIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("302E8840") ,com.kingdee.eas.fdc.aimcost.INewPlanIndex.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("302E8840") ,com.kingdee.eas.fdc.aimcost.INewPlanIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("302E8840"));
    }
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("302E8840"));
    }
}