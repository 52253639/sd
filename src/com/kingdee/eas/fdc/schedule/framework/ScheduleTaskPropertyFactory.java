package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleTaskPropertyFactory
{
    private ScheduleTaskPropertyFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6CD1CA37") ,com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6CD1CA37") ,com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6CD1CA37"));
    }
    public static com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.framework.IScheduleTaskProperty)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6CD1CA37"));
    }
}