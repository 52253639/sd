package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanGatherFactory
{
    private ProjectMonthPlanGatherFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGather getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGather)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0003689A") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGather.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGather getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGather)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0003689A") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanGather.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGather getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGather)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0003689A"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanGather getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanGather)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0003689A"));
    }
}