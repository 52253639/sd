package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectYearPlanTotalEntryFactory
{
    private ProjectYearPlanTotalEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C22F61FE") ,com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C22F61FE") ,com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C22F61FE"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C22F61FE"));
    }
}