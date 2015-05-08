package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthReportFactory
{
    private ProjectMonthReportFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("58784951") ,com.kingdee.eas.fdc.schedule.IProjectMonthReport.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("58784951") ,com.kingdee.eas.fdc.schedule.IProjectMonthReport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("58784951"));
    }
    public static com.kingdee.eas.fdc.schedule.IProjectMonthReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IProjectMonthReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("58784951"));
    }
}