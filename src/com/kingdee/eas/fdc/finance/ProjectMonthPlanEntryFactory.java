package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanEntryFactory
{
    private ProjectMonthPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6B44ADF3") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6B44ADF3") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6B44ADF3"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6B44ADF3"));
    }
}