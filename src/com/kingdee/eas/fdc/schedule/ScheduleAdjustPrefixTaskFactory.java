package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleAdjustPrefixTaskFactory
{
    private ScheduleAdjustPrefixTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E36F42B3") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E36F42B3") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E36F42B3"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustPrefixTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E36F42B3"));
    }
}