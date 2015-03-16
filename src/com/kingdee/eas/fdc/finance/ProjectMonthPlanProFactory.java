package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanProFactory
{
    private ProjectMonthPlanProFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanPro getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanPro)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A900C46E") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanPro.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanPro getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanPro)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A900C46E") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanPro.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanPro getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanPro)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A900C46E"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanPro getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanPro)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A900C46E"));
    }
}