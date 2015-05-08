package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCScheduleTaskFacadeFactory
{
    private FDCScheduleTaskFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CB7402A5") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CB7402A5") ,com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CB7402A5"));
    }
    public static com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IFDCScheduleTaskFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CB7402A5"));
    }
}