package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectWeekReportFactory
{
    private ProjectWeekReportFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("23D2312B") ,com.kingdee.eas.fdc.schedule.IProjectWeekReport.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("23D2312B") ,com.kingdee.eas.fdc.schedule.IProjectWeekReport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("23D2312B"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectWeekReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectWeekReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("23D2312B"));
    }
}