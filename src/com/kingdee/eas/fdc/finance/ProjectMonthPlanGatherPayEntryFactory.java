package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanGatherPayEntryFactory
{
    private ProjectMonthPlanGatherPayEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9F5B2364") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9F5B2364") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9F5B2364"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherPayEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9F5B2364"));
    }
}