package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthReportEntryFactory
{
    private ProjectMonthReportEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5285F9A1") ,com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5285F9A1") ,com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5285F9A1"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReportEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5285F9A1"));
    }
}