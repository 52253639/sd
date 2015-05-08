package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleNewTaskEntryFactory
{
    private ScheduleNewTaskEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5FBB33DB") ,com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5FBB33DB") ,com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5FBB33DB"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleNewTaskEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5FBB33DB"));
    }
}