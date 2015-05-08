package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleVerManagerEntryFactory
{
    private ScheduleVerManagerEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F2089568") ,com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F2089568") ,com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F2089568"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleVerManagerEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F2089568"));
    }
}