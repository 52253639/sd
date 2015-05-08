package com.kingdee.eas.fdc.schedule.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ScheduleReportOrgFactory
{
    private ScheduleReportOrgFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("714FB2DB") ,com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("714FB2DB") ,com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("714FB2DB"));
    }
    public static com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.report.IScheduleReportOrg)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("714FB2DB"));
    }
}