package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleVerManagerFactory
{
    private ScheduleVerManagerFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManager getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManager)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3FA212D7") ,com.kingdee.eas.fdc.schedule.IScheduleVerManager.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManager getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManager)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3FA212D7") ,com.kingdee.eas.fdc.schedule.IScheduleVerManager.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManager getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManager)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3FA212D7"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManager getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManager)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3FA212D7"));
    }
}