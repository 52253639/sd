package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleAdjustTaskDependFactory
{
    private ScheduleAdjustTaskDependFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52B6FFCD") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52B6FFCD") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52B6FFCD"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskDepend)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52B6FFCD"));
    }
}