package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskQualityPlanEventEntryFactory
{
    private TaskQualityPlanEventEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3658C145") ,com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3658C145") ,com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3658C145"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskQualityPlanEventEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3658C145"));
    }
}