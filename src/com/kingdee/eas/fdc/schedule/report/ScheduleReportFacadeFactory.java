package com.kingdee.eas.fdc.schedule.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleReportFacadeFactory
{
    private ScheduleReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("19531543") ,com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("19531543") ,com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("19531543"));
    }
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("19531543"));
    }
}