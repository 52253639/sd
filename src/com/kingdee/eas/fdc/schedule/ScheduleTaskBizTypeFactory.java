package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleTaskBizTypeFactory
{
    private ScheduleTaskBizTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskBizType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskBizType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0B475AFB") ,com.kingdee.eas.fdc.schedule.IScheduleTaskBizType.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskBizType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskBizType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0B475AFB") ,com.kingdee.eas.fdc.schedule.IScheduleTaskBizType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskBizType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskBizType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0B475AFB"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleTaskBizType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleTaskBizType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0B475AFB"));
    }
}