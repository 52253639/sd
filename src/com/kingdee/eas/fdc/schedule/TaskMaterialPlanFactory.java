package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskMaterialPlanFactory
{
    private TaskMaterialPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0FB2A2EB") ,com.kingdee.eas.fdc.schedule.ITaskMaterialPlan.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0FB2A2EB") ,com.kingdee.eas.fdc.schedule.ITaskMaterialPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0FB2A2EB"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0FB2A2EB"));
    }
}