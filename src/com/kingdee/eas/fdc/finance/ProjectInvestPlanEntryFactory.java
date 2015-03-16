package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectInvestPlanEntryFactory
{
    private ProjectInvestPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("480EB9CA") ,com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("480EB9CA") ,com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("480EB9CA"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectInvestPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("480EB9CA"));
    }
}