package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SellSupplyPlanFactory
{
    private SellSupplyPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ISellSupplyPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("66ED29BB") ,com.kingdee.eas.fdc.market.ISellSupplyPlan.class);
    }
    
    public static com.kingdee.eas.fdc.market.ISellSupplyPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("66ED29BB") ,com.kingdee.eas.fdc.market.ISellSupplyPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ISellSupplyPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("66ED29BB"));
    }
    public static com.kingdee.eas.fdc.market.ISellSupplyPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ISellSupplyPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("66ED29BB"));
    }
}