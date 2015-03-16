package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectYearPlanFactory
{
    private ProjectYearPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9F9C8250") ,com.kingdee.eas.fdc.finance.IProjectYearPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectYearPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9F9C8250") ,com.kingdee.eas.fdc.finance.IProjectYearPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9F9C8250"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9F9C8250"));
    }
}