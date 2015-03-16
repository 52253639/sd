package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanDateEntryFactory
{
    private ProjectMonthPlanDateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C8FAC265") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C8FAC265") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C8FAC265"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanDateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C8FAC265"));
    }
}