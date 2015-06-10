package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewPlanIndexPTFactory
{
    private NewPlanIndexPTFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndexPT getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndexPT)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DEAD8244") ,com.kingdee.eas.fdc.aimcost.INewPlanIndexPT.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndexPT getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndexPT)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DEAD8244") ,com.kingdee.eas.fdc.aimcost.INewPlanIndexPT.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndexPT getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndexPT)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DEAD8244"));
    }
    public static com.kingdee.eas.fdc.aimcost.INewPlanIndexPT getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.INewPlanIndexPT)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DEAD8244"));
    }
}