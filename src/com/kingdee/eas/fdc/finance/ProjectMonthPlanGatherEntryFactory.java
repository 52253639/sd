package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanGatherEntryFactory
{
    private ProjectMonthPlanGatherEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0CA7FFF8") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0CA7FFF8") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0CA7FFF8"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGatherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0CA7FFF8"));
    }
}