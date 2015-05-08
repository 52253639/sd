package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCScheduleTaskExtFactory
{
    private FDCScheduleTaskExtFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("99CA6BF6") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("99CA6BF6") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("99CA6BF6"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskExt)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("99CA6BF6"));
    }
}