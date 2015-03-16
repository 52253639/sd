package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanFactory
{
    private ProjectMonthPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("23EAA9BF") ,com.kingdee.eas.fdc.finance.IProjectMonthPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("23EAA9BF") ,com.kingdee.eas.fdc.finance.IProjectMonthPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("23EAA9BF"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("23EAA9BF"));
    }
}