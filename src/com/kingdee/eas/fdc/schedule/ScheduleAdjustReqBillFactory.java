package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleAdjustReqBillFactory
{
    private ScheduleAdjustReqBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B955E3C9") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B955E3C9") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B955E3C9"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustReqBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B955E3C9"));
    }
}