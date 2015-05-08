package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskQualityPlanFactory
{
    private TaskQualityPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F3B7536D") ,com.kingdee.eas.fdc.schedule.ITaskQualityPlan.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F3B7536D") ,com.kingdee.eas.fdc.schedule.ITaskQualityPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F3B7536D"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F3B7536D"));
    }
}