package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectYearPlanEntryFactory
{
    private ProjectYearPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("24688802") ,com.kingdee.eas.fdc.finance.IProjectYearPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("24688802") ,com.kingdee.eas.fdc.finance.IProjectYearPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("24688802"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("24688802"));
    }
}