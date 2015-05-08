package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleRptFacadeFactory
{
    private ScheduleRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8DC04843") ,com.kingdee.eas.fdc.schedule.IScheduleRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8DC04843") ,com.kingdee.eas.fdc.schedule.IScheduleRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8DC04843"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8DC04843"));
    }
}