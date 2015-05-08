package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanAdjustReasonFactory
{
    private PlanAdjustReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IPlanAdjustReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPlanAdjustReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF5EB852") ,com.kingdee.eas.fdc.schedule.IPlanAdjustReason.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IPlanAdjustReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPlanAdjustReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF5EB852") ,com.kingdee.eas.fdc.schedule.IPlanAdjustReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IPlanAdjustReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPlanAdjustReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF5EB852"));
    }
    public static com.kingdee.eas.fdc.schedule.IPlanAdjustReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IPlanAdjustReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF5EB852"));
    }
}