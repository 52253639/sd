package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeptTaskProgressReportFactory
{
    private DeptTaskProgressReportFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5D06FD21") ,com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5D06FD21") ,com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5D06FD21"));
    }
    public static com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IDeptTaskProgressReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5D06FD21"));
    }
}