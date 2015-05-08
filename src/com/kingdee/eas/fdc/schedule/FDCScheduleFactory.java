package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCScheduleFactory
{
    private FDCScheduleFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchedule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchedule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D0FA7B86") ,com.kingdee.eas.fdc.schedule.IFDCSchedule.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCSchedule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchedule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D0FA7B86") ,com.kingdee.eas.fdc.schedule.IFDCSchedule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchedule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchedule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D0FA7B86"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCSchedule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCSchedule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D0FA7B86"));
    }
}