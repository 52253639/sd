package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleAdjustGattReqWBSEntryFactory
{
    private ScheduleAdjustGattReqWBSEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EE2091D2") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EE2091D2") ,com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EE2091D2"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleAdjustGattReqWBSEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EE2091D2"));
    }
}