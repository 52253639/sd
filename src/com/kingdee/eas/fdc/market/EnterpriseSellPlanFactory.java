package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EnterpriseSellPlanFactory
{
    private EnterpriseSellPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseSellPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSellPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C1486FAD") ,com.kingdee.eas.fdc.market.IEnterpriseSellPlan.class);
    }
    
    public static com.kingdee.eas.fdc.market.IEnterpriseSellPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSellPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C1486FAD") ,com.kingdee.eas.fdc.market.IEnterpriseSellPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseSellPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSellPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C1486FAD"));
    }
    public static com.kingdee.eas.fdc.market.IEnterpriseSellPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.IEnterpriseSellPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C1486FAD"));
    }
}