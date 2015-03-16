package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanProEntryFactory
{
    private ProjectMonthPlanProEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2C8EE8A4") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2C8EE8A4") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2C8EE8A4"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2C8EE8A4"));
    }
}