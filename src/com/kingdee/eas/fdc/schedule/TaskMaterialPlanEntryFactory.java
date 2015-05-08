package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskMaterialPlanEntryFactory
{
    private TaskMaterialPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5A59047") ,com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5A59047") ,com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5A59047"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskMaterialPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5A59047"));
    }
}