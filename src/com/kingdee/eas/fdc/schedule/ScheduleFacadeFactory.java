package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleFacadeFactory
{
    private ScheduleFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("32D25F67") ,com.kingdee.eas.fdc.schedule.IScheduleFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IScheduleFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("32D25F67") ,com.kingdee.eas.fdc.schedule.IScheduleFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("32D25F67"));
    }
    public static com.kingdee.eas.fdc.schedule.IScheduleFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IScheduleFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("32D25F67"));
    }
}