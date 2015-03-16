package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanGatherDateEntryFactory
{
    private ProjectMonthPlanGatherDateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("84AB89EA") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("84AB89EA") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("84AB89EA"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherDateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("84AB89EA"));
    }
}