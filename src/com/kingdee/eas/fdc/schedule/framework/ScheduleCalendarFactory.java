package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleCalendarFactory
{
    private ScheduleCalendarFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8725097B") ,com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8725097B") ,com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8725097B"));
    }
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleCalendar)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8725097B"));
    }
}