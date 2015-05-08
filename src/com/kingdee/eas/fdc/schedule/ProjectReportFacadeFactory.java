package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectReportFacadeFactory
{
    private ProjectReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EEF72511") ,com.kingdee.eas.fdc.schedule.IProjectReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EEF72511") ,com.kingdee.eas.fdc.schedule.IProjectReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EEF72511"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EEF72511"));
    }
}