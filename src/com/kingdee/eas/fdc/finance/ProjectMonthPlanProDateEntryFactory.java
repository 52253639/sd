package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectMonthPlanProDateEntryFactory
{
    private ProjectMonthPlanProDateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10563C96") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10563C96") ,com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10563C96"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectMonthPlanProDateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10563C96"));
    }
}