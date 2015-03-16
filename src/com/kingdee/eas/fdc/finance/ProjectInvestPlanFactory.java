package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectInvestPlanFactory
{
    private ProjectInvestPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2E405388") ,com.kingdee.eas.fdc.finance.IProjectInvestPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2E405388") ,com.kingdee.eas.fdc.finance.IProjectInvestPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2E405388"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2E405388"));
    }
}