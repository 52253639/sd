package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectYearPlanDateEntryFactory
{
    private ProjectYearPlanDateEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3974FCF4") ,com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3974FCF4") ,com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3974FCF4"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanDateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3974FCF4"));
    }
}