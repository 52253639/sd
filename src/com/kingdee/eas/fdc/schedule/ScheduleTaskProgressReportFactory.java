package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleTaskProgressReportFactory
{
    private ScheduleTaskProgressReportFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("75F545F3") ,com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("75F545F3") ,com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("75F545F3"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("75F545F3"));
    }
}