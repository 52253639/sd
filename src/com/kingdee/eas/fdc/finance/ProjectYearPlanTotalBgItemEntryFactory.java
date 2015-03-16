package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectYearPlanTotalBgItemEntryFactory
{
    private ProjectYearPlanTotalBgItemEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F368A2E6") ,com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F368A2E6") ,com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F368A2E6"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectYearPlanTotalBgItemEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F368A2E6"));
    }
}