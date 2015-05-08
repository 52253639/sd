package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleAdjustTaskEntryFactory
{
    private ScheduleAdjustTaskEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F2392EF1") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F2392EF1") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F2392EF1"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustTaskEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F2392EF1"));
    }
}