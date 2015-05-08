package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthScheduleFactory
{
    private MonthScheduleFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IMonthSchedule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IMonthSchedule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C896D921") ,com.kingdee.eas.fdc.schedule.IMonthSchedule.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IMonthSchedule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IMonthSchedule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C896D921") ,com.kingdee.eas.fdc.schedule.IMonthSchedule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IMonthSchedule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IMonthSchedule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C896D921"));
    }
    public static com.kingdee.eas.fdc.schedule.IMonthSchedule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IMonthSchedule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C896D921"));
    }
}