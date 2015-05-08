package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TaskScheduleLogFactory
{
    private TaskScheduleLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.ITaskScheduleLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskScheduleLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1F9527D2") ,com.kingdee.eas.fdc.schedule.ITaskScheduleLog.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.ITaskScheduleLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskScheduleLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1F9527D2") ,com.kingdee.eas.fdc.schedule.ITaskScheduleLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.ITaskScheduleLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskScheduleLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1F9527D2"));
    }
    public static com.kingdee.eas.fdc.schedule.ITaskScheduleLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.ITaskScheduleLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1F9527D2"));
    }
}