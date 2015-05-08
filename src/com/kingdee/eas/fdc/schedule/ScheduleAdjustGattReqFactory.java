package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleAdjustGattReqFactory
{
    private ScheduleAdjustGattReqFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6CDE9F68") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6CDE9F68") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6CDE9F68"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReq)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6CDE9F68"));
    }
}