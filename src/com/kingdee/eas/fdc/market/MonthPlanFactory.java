package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthPlanFactory
{
    private MonthPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IMonthPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A4CA7718") ,com.kingdee.eas.fdc.market.IMonthPlan.class);
    }
    
    public static com.kingdee.eas.fdc.market.IMonthPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A4CA7718") ,com.kingdee.eas.fdc.market.IMonthPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IMonthPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A4CA7718"));
    }
    public static com.kingdee.eas.fdc.market.IMonthPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IMonthPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A4CA7718"));
    }
}