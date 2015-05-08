package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectWeekReportEntryFactory
{
    private ProjectWeekReportEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5A44EA07") ,com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5A44EA07") ,com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5A44EA07"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReportEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5A44EA07"));
    }
}