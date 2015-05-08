package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleQualityCheckPointFactory
{
    private ScheduleQualityCheckPointFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("25700EDA") ,com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("25700EDA") ,com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("25700EDA"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("25700EDA"));
    }
}